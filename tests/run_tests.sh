#!/bin/bash

# ============================================
# Script de Pruebas Automatizadas - Sistema Oasis
# ============================================
# Descripción: Ejecuta pruebas end-to-end del sistema completo
# Uso: ./run_tests.sh
# ============================================

set +e  # Continuar en caso de errores

# Configuración
BASE_URL="${1:-http://localhost:9999/api/v1}"

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
GRAY='\033[0;37m'
NC='\033[0m' # No Color

# Contadores
TESTS_PASSED=0
TESTS_FAILED=0
TESTS_TOTAL=0

# IDs de registros creados
CLIENTE_ID=""
ADMIN_ID=""
PERSONA_CLIENTE_ID=""
PERSONA_ADMIN_ID=""

# ============================================
# FUNCIONES AUXILIARES
# ============================================

print_header() {
    echo -e "\n${CYAN}============================================${NC}"
    echo -e "${CYAN} $1${NC}"
    echo -e "${CYAN}============================================${NC}\n"
}

print_test_result() {
    local test_name="$1"
    local passed="$2"
    local message="$3"
    
    ((TESTS_TOTAL++))
    
    if [ "$passed" = "true" ]; then
        ((TESTS_PASSED++))
        echo -e "${GREEN}✓ $test_name${NC}"
        if [ -n "$message" ]; then
            echo -e "${GRAY}  → $message${NC}"
        fi
    else
        ((TESTS_FAILED++))
        echo -e "${RED}✗ $test_name${NC}"
        if [ -n "$message" ]; then
            echo -e "${RED}  → $message${NC}"
        fi
    fi
}

api_call() {
    local method="$1"
    local endpoint="$2"
    local data="$3"
    
    local url="${BASE_URL}${endpoint}"
    
    if [ -n "$data" ]; then
        curl -s -X "$method" "$url" \
            -H "Content-Type: application/json" \
            -d "$data" \
            -w "\n%{http_code}"
    else
        curl -s -X "$method" "$url" \
            -H "Content-Type: application/json" \
            -w "\n%{http_code}"
    fi
}

# ============================================
# INICIO DE PRUEBAS
# ============================================

echo -e "${CYAN}"
cat << "EOF"
╔════════════════════════════════════════════════════════════╗
║     SUITE DE PRUEBAS AUTOMATIZADAS - SISTEMA OASIS        ║
╚════════════════════════════════════════════════════════════╝
EOF
echo -e "${NC}"

echo -e "\nBase URL: ${CYAN}$BASE_URL${NC}"
echo -e "Fecha: $(date '+%Y-%m-%d %H:%M:%S')\n"

# Verificar que el servidor esté corriendo
echo -e "${CYAN}Verificando conexión con el servidor...${NC}"

# Extraer la URL base sin /api/v1
TEST_URL=$(echo "$BASE_URL" | sed 's|/api/v1$||')
if [ -z "$TEST_URL" ]; then
    TEST_URL="http://localhost:9999"
fi

# Intentar con varios endpoints hasta encontrar uno que funcione
CONNECTED=false
ENDPOINTS=("$BASE_URL/cliente" "$BASE_URL/admin" "$TEST_URL/actuator/health" "$TEST_URL/")

for endpoint in "${ENDPOINTS[@]}"; do
    if curl -s -f "$endpoint" -m 3 > /dev/null 2>&1; then
        CONNECTED=true
        break
    fi
done

if [ "$CONNECTED" = true ]; then
    echo -e "${GREEN}✓ Servidor accesible en $TEST_URL${NC}"
else
    echo -e "${RED}✗ Error: No se puede conectar al servidor${NC}"
    echo -e "${RED}  URL intentada: $BASE_URL${NC}"
    echo -e "${RED}  Asegúrate de que el backend esté corriendo en el puerto correcto.${NC}"
    echo -e "${YELLOW}  Comando para verificar: lsof -i :9999 o netstat -tuln | grep 9999${NC}"
    echo -e "\n${YELLOW}¿Deseas continuar de todos modos? (s/N)${NC}"
    read -r CONTINUE
    if [ "$CONTINUE" != "s" ] && [ "$CONTINUE" != "S" ]; then
        exit 1
    fi
fi

# ============================================
# PRUEBA 1: CREACIÓN DE CLIENTE
# ============================================

print_header "PRUEBA 1: CREACIÓN DE CLIENTE"

# 1.1 Crear Persona para Cliente
echo -e "${CYAN}1.1. Creando persona para cliente...${NC}"
RESPONSE=$(api_call "POST" "/persona" '{
  "nombre": "Juan",
  "apellido": "Pérez Test",
  "genero": "M",
  "telefono": "123456789",
  "fechaNacimiento": "1990-01-15",
  "direccion": "Av. Principal 123"
}')

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ]; then
    PERSONA_CLIENTE_ID=$(echo "$BODY" | grep -o '"idPersona":[0-9]*' | cut -d':' -f2)
    print_test_result "Crear Persona para Cliente" "true" "ID: $PERSONA_CLIENTE_ID"
else
    print_test_result "Crear Persona para Cliente" "false" "HTTP $HTTP_CODE"
    PERSONA_CLIENTE_ID=1
    echo -e "${YELLOW}⚠ Usando ID de persona por defecto: 1${NC}"
fi

# 1.2 Crear Cliente
echo -e "\n${CYAN}1.2. Creando cliente...${NC}"
RESPONSE=$(api_call "POST" "/cliente" "{
  \"correo\": \"cliente.test@oasis.com\",
  \"password\": \"TestPass123\",
  \"idPersona\": $PERSONA_CLIENTE_ID
}")

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ]; then
    CLIENTE_ID=$(echo "$BODY" | grep -o '"idCliente":[0-9]*' | cut -d':' -f2)
    ESTADO_CUENTA=$(echo "$BODY" | grep -o '"estadoCuenta":true')
    INTENTOS=$(echo "$BODY" | grep -o '"intentosFallidos":0')
    
    if [ -n "$CLIENTE_ID" ] && [ -n "$ESTADO_CUENTA" ] && [ -n "$INTENTOS" ]; then
        print_test_result "Crear Cliente" "true" "ID: $CLIENTE_ID, Estado: Activo, Intentos: 0"
    else
        print_test_result "Crear Cliente" "false" "Validaciones fallidas"
    fi
else
    print_test_result "Crear Cliente" "false" "HTTP $HTTP_CODE"
fi

# ============================================
# PRUEBA 2: LOGIN EXITOSO
# ============================================

print_header "PRUEBA 2: LOGIN EXITOSO"

RESPONSE=$(api_call "POST" "/cliente/login" '{
  "correo": "cliente.test@oasis.com",
  "password": "TestPass123"
}')

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "200" ]; then
    print_test_result "Login Exitoso de Cliente" "true"
else
    print_test_result "Login Exitoso de Cliente" "false" "HTTP $HTTP_CODE"
fi

# ============================================
# PRUEBA 3: INTENTOS FALLIDOS
# ============================================

print_header "PRUEBA 3: INTENTOS FALLIDOS Y BLOQUEO AUTOMÁTICO"

for i in {1..5}; do
    echo -e "\n${CYAN}3.$i. Intento fallido $i/5...${NC}"
    
    RESPONSE=$(api_call "POST" "/cliente/login" "{
      \"correo\": \"cliente.test@oasis.com\",
      \"password\": \"PasswordIncorrecta$i\"
    }")
    
    HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
    BODY=$(echo "$RESPONSE" | sed '$d')
    
    if [ $i -lt 5 ]; then
        INTENTOS_RESTANTES=$((5 - i))
        if echo "$BODY" | grep -q "Le quedan $INTENTOS_RESTANTES intentos"; then
            print_test_result "Intento Fallido $i/5" "true" "Mensaje correcto"
        else
            print_test_result "Intento Fallido $i/5" "false" "Mensaje incorrecto"
        fi
    else
        if echo "$BODY" | grep -q "CUENTA_BLOQUEADA"; then
            print_test_result "Bloqueo Automático (Intento 5/5)" "true" "Cuenta bloqueada"
        else
            print_test_result "Bloqueo Automático (Intento 5/5)" "false" "No se bloqueó"
        fi
    fi
    
    sleep 0.5
done

# ============================================
# PRUEBA 4: VERIFICAR BLOQUEO
# ============================================

print_header "PRUEBA 4: VERIFICACIÓN DE BLOQUEO"

# 4.1 Intentar login con contraseña correcta
echo -e "${CYAN}4.1. Intentando login con contraseña correcta (debe fallar)...${NC}"
RESPONSE=$(api_call "POST" "/cliente/login" '{
  "correo": "cliente.test@oasis.com",
  "password": "TestPass123"
}')

BODY=$(echo "$RESPONSE" | sed '$d')

if echo "$BODY" | grep -q "CUENTA_BLOQUEADA"; then
    print_test_result "Login Bloqueado (con password correcta)" "true"
else
    print_test_result "Login Bloqueado (con password correcta)" "false"
fi

# 4.2 Verificar estado
echo -e "\n${CYAN}4.2. Verificando estado de cuenta...${NC}"
RESPONSE=$(api_call "GET" "/bloqueo/cliente/$CLIENTE_ID/info")

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ]; then
    if echo "$BODY" | grep -q '"bloqueada":true' && echo "$BODY" | grep -q '"intentosFallidos":5'; then
        print_test_result "Estado de Cuenta Bloqueada" "true" "Bloqueada con 5 intentos"
    else
        print_test_result "Estado de Cuenta Bloqueada" "false" "Estado incorrecto"
    fi
else
    print_test_result "Estado de Cuenta Bloqueada" "false" "HTTP $HTTP_CODE"
fi

# ============================================
# PRUEBA 5: DESBLOQUEO
# ============================================

print_header "PRUEBA 5: DESBLOQUEO DE CUENTA"

# 5.1 Desbloquear
echo -e "${CYAN}5.1. Desbloqueando cuenta...${NC}"
RESPONSE=$(api_call "POST" "/bloqueo/cliente/$CLIENTE_ID/desbloquear")
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "200" ]; then
    print_test_result "Desbloquear Cuenta" "true"
else
    print_test_result "Desbloquear Cuenta" "false" "HTTP $HTTP_CODE"
fi

# 5.2 Verificar desbloqueo
echo -e "\n${CYAN}5.2. Verificando desbloqueo...${NC}"
RESPONSE=$(api_call "GET" "/bloqueo/cliente/$CLIENTE_ID/info")
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ]; then
    if echo "$BODY" | grep -q '"bloqueada":false' && echo "$BODY" | grep -q '"intentosFallidos":0'; then
        print_test_result "Verificar Desbloqueo" "true" "Cuenta activa con 0 intentos"
    else
        print_test_result "Verificar Desbloqueo" "false" "Estado incorrecto"
    fi
else
    print_test_result "Verificar Desbloqueo" "false" "HTTP $HTTP_CODE"
fi

# 5.3 Login después de desbloqueo
echo -e "\n${CYAN}5.3. Login después de desbloqueo...${NC}"
RESPONSE=$(api_call "POST" "/cliente/login" '{
  "correo": "cliente.test@oasis.com",
  "password": "TestPass123"
}')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "200" ]; then
    print_test_result "Login Después de Desbloqueo" "true"
else
    print_test_result "Login Después de Desbloqueo" "false" "HTTP $HTTP_CODE"
fi

# ============================================
# PRUEBA 6: CAMBIO DE CONTRASEÑA
# ============================================

print_header "PRUEBA 6: CAMBIO DE CONTRASEÑA"

# 6.1 Validar contraseña actual
echo -e "${CYAN}6.1. Validando contraseña actual...${NC}"
RESPONSE=$(api_call "POST" "/cliente/$CLIENTE_ID/validate-password" '{
  "password": "TestPass123"
}')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" = "200" ] && echo "$BODY" | grep -q '"valid":true'; then
    print_test_result "Validar Contraseña Actual (Correcta)" "true"
else
    print_test_result "Validar Contraseña Actual (Correcta)" "false"
fi

# 6.2 Cambiar contraseña
echo -e "\n${CYAN}6.2. Cambiando contraseña...${NC}"
RESPONSE=$(api_call "PUT" "/cliente/$CLIENTE_ID/password" '{
  "password": "NuevaPassword456"
}')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "200" ]; then
    print_test_result "Cambiar Contraseña" "true"
else
    print_test_result "Cambiar Contraseña" "false" "HTTP $HTTP_CODE"
fi

# 6.3 Intentar reutilizar contraseña
echo -e "\n${CYAN}6.3. Intentando reutilizar contraseña anterior (debe fallar)...${NC}"
RESPONSE=$(api_call "PUT" "/cliente/$CLIENTE_ID/password" '{
  "password": "TestPass123"
}')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" != "200" ] && echo "$BODY" | grep -q "ya fue usada anteriormente"; then
    print_test_result "Bloquear Reutilización de Contraseña" "true"
else
    print_test_result "Bloquear Reutilización de Contraseña" "false"
fi

# 6.4 Login con nueva contraseña
echo -e "\n${CYAN}6.4. Login con nueva contraseña...${NC}"
RESPONSE=$(api_call "POST" "/cliente/login" '{
  "correo": "cliente.test@oasis.com",
  "password": "NuevaPassword456"
}')
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "200" ]; then
    print_test_result "Login con Nueva Contraseña" "true"
else
    print_test_result "Login con Nueva Contraseña" "false" "HTTP $HTTP_CODE"
fi

# ============================================
# RESUMEN FINAL
# ============================================

echo -e "\n\n${CYAN}============================================${NC}"
echo -e "${CYAN}           RESUMEN DE PRUEBAS${NC}"
echo -e "${CYAN}============================================${NC}\n"

echo -e "${CYAN}Total de pruebas:  $TESTS_TOTAL${NC}"
echo -e "${GREEN}Exitosas:          $TESTS_PASSED${NC}"

if [ $TESTS_FAILED -eq 0 ]; then
    echo -e "${GREEN}Fallidas:          $TESTS_FAILED${NC}"
else
    echo -e "${RED}Fallidas:          $TESTS_FAILED${NC}"
fi

if [ $TESTS_TOTAL -gt 0 ]; then
    SUCCESS_RATE=$(( (TESTS_PASSED * 100) / TESTS_TOTAL ))
    
    if [ $SUCCESS_RATE -gt 80 ]; then
        echo -e "${GREEN}Tasa de éxito:     $SUCCESS_RATE%${NC}"
    elif [ $SUCCESS_RATE -gt 50 ]; then
        echo -e "${YELLOW}Tasa de éxito:     $SUCCESS_RATE%${NC}"
    else
        echo -e "${RED}Tasa de éxito:     $SUCCESS_RATE%${NC}"
    fi
fi

echo -e "\n${CYAN}============================================${NC}\n"

if [ $TESTS_FAILED -eq 0 ]; then
    echo -e "${GREEN}🎉 ¡TODAS LAS PRUEBAS PASARON EXITOSAMENTE!${NC}"
    exit 0
else
    echo -e "${YELLOW}⚠️  ALGUNAS PRUEBAS FALLARON${NC}"
    echo -e "${YELLOW}Revisa los mensajes de error arriba para más detalles.${NC}"
    exit 1
fi

