# 🧪 Suite de Pruebas Automatizadas - Sistema Oasis

## 📋 Descripción

Este directorio contiene scripts de pruebas automatizadas end-to-end para validar toda la funcionalidad del sistema Oasis.

---

## 📁 Archivos Disponibles

| Archivo | Descripción | Plataforma |
|---------|-------------|------------|
| `test_suite_completo.http` | Pruebas HTTP individuales | Visual Studio Code con REST Client |
| `run_tests.ps1` | Script automatizado completo | Windows (PowerShell) |
| `run_tests.sh` | Script automatizado completo | Linux/Mac (Bash) |

---

## 🚀 Uso

### **Opción 1: Visual Studio Code + REST Client**

1. Instalar extensión [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) en VS Code
2. Abrir `test_suite_completo.http`
3. Hacer clic en "Send Request" sobre cada prueba
4. Verificar las respuestas manualmente

**Ventajas:**
- Control total sobre cada prueba
- Ver respuestas completas
- Depuración fácil

**Desventajas:**
- Manual (requiere clic en cada prueba)
- No genera reporte automático

---

### **Opción 2: PowerShell (Windows)**

```powershell
# Ejecutar desde la raíz del proyecto
cd tests
.\run_tests.ps1
```

**Con URL personalizada:**
```powershell
.\run_tests.ps1 -BaseUrl "http://localhost:8080/api/v1"
```

**Ventajas:**
- Totalmente automatizado
- Reporte de resultados
- Contador de pruebas pasadas/fallidas
- Colores en consola

---

### **Opción 3: Bash (Linux/Mac)**

```bash
# Dar permisos de ejecución (primera vez)
chmod +x run_tests.sh

# Ejecutar
./run_tests.sh
```

**Con URL personalizada:**
```bash
./run_tests.sh "http://localhost:8080/api/v1"
```

**Ventajas:**
- Totalmente automatizado
- Reporte de resultados
- Compatible con CI/CD

---

## 🧪 Pruebas Incluidas

### **1. Creación de Usuario Cliente ✓**
- Crear persona
- Crear cliente con contraseña hasheada
- Verificar inicialización de campos de bloqueo

### **2. Login Exitoso ✓**
- Login con credenciales correctas
- Verificar respuesta exitosa

### **3. Intentos Fallidos y Bloqueo Automático ✓**
- 5 intentos de login con contraseña incorrecta
- Verificar contador de intentos restantes
- Verificar bloqueo automático en el 5to intento

### **4. Verificación de Bloqueo ✓**
- Intentar login con contraseña correcta (debe fallar por bloqueo)
- Verificar estado de cuenta bloqueada
- Verificar mensaje específico

### **5. Desbloqueo de Cuenta ✓**
- Desbloquear cuenta manualmente
- Verificar que contador se reinicia a 0
- Login exitoso después de desbloqueo

### **6. Cambio de Contraseña ✓**
- Validar contraseña actual (correcta e incorrecta)
- Cambiar contraseña exitosamente
- Intentar reutilizar contraseña anterior (debe fallar)
- Verificar historial de contraseñas
- Login con nueva contraseña

### **7. Bloqueo Manual ✓**
- Bloquear cuenta manualmente con motivo
- Verificar bloqueo manual
- Intentar login (debe fallar)

### **8. Creación de Administrador ✓**
- Crear persona para admin
- Crear admin con rol asignado
- Verificar inicialización de campos
- Login de administrador

### **9. Sistema de Permisos ✓**
- Obtener permisos efectivos del rol
- Verificar permiso que debe tener
- Verificar permiso que NO debe tener
- Asignar permiso adicional
- Asignar permiso temporal
- Verificar permisos combinados (ROL + ADICIONAL + TEMPORAL)

### **10. Cambio de Rol ✓**
- Cambiar rol del administrador
- Limpiar permisos adicionales y temporales
- Verificar nuevos permisos del rol

---

## 📊 Ejemplo de Salida

```
╔════════════════════════════════════════════════════════════╗
║     SUITE DE PRUEBAS AUTOMATIZADAS - SISTEMA OASIS        ║
╚════════════════════════════════════════════════════════════╝

Base URL: http://localhost:9999/api/v1
Fecha: 2025-10-28 14:30:00

✓ Servidor accesible

============================================
 PRUEBA 1: CREACIÓN DE CLIENTE
============================================

1.1. Creando persona para cliente...
✓ Crear Persona para Cliente
  → ID: 15

1.2. Creando cliente...
✓ Crear Cliente
  → ID: 10, Estado: Activo, Intentos: 0

============================================
 PRUEBA 2: LOGIN EXITOSO
============================================

✓ Login Exitoso de Cliente

============================================
 PRUEBA 3: INTENTOS FALLIDOS Y BLOQUEO AUTOMÁTICO
============================================

3.1. Intento fallido 1/5...
✓ Intento Fallido 1/5
  → Respuesta: Le quedan 4 intentos

3.2. Intento fallido 2/5...
✓ Intento Fallido 2/5
  → Respuesta: Le quedan 3 intentos

...

============================================
           RESUMEN DE PRUEBAS
============================================

Total de pruebas:  28
Exitosas:          28
Fallidas:          0
Tasa de éxito:     100%

============================================

🎉 ¡TODAS LAS PRUEBAS PASARON EXITOSAMENTE!
```

---

## ⚙️ Requisitos Previos

### **Para todos los scripts:**
1. Backend corriendo en `http://localhost:9999` (o URL especificada)
2. Base de datos con esquema actualizado
3. Endpoints disponibles

### **Para PowerShell:**
- Windows 10/11 con PowerShell 5.1+
- Módulo `Invoke-RestMethod` (incluido por defecto)

### **Para Bash:**
- Linux/Mac con Bash
- `curl` instalado
- `jq` (opcional, para mejor formateo de JSON)

### **Para REST Client:**
- Visual Studio Code
- Extensión REST Client instalada

---

## 🔧 Configuración

### **Cambiar URL del Backend:**

**PowerShell:**
```powershell
.\run_tests.ps1 -BaseUrl "http://tu-servidor:puerto/api/v1"
```

**Bash:**
```bash
./run_tests.sh "http://tu-servidor:puerto/api/v1"
```

**REST Client:**
Editar la primera línea de `test_suite_completo.http`:
```http
@baseUrl = http://tu-servidor:puerto/api/v1
```

---

## 🐛 Troubleshooting

### **Error: "No se puede conectar al servidor"**

**Solución:**
1. Verificar que el backend esté corriendo: `netstat -ano | findstr :9999` (Windows) o `lsof -i :9999` (Linux/Mac)
2. Verificar la URL correcta
3. Verificar firewall

### **Error: "Unauthorized" o "403 Forbidden"**

**Solución:**
- Verificar que CORS esté configurado correctamente en el backend
- Los scripts no requieren autenticación JWT (añadir si es necesario)

### **Pruebas fallan en "Crear Persona"**

**Solución:**
- Verificar que el endpoint `/persona` existe
- Si no existe, los scripts usan IDs por defecto (1, 2, 3...)
- Actualizar los IDs manualmente en los scripts

### **Error: "Column 'estadocuenta' does not exist"**

**Solución:**
- Recrear la base de datos con el script actualizado:
```bash
psql -U postgres -d oasis -f BDD/BD_OASIS.sql
```

---

## 📈 Integración con CI/CD

### **GitHub Actions:**

```yaml
name: API Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      
      - name: Start Backend
        run: |
          ./gradlew bootRun &
          sleep 30
      
      - name: Run Tests
        run: |
          cd tests
          chmod +x run_tests.sh
          ./run_tests.sh
      
      - name: Upload Results
        if: always()
        uses: actions/upload-artifact@v2
        with:
          name: test-results
          path: tests/results.txt
```

---

## 📝 Personalización

### **Agregar Nueva Prueba:**

1. Abrir `run_tests.ps1` o `run_tests.sh`
2. Agregar sección nueva:

```powershell
# PowerShell
Write-TestHeader "PRUEBA X: TU NUEVA PRUEBA"

$Result = Invoke-ApiTest -Method POST -Endpoint "/tu-endpoint" -Body @{
    campo1 = "valor1"
    campo2 = "valor2"
}

Write-TestResult "Nombre de la Prueba" $Result.Success
```

```bash
# Bash
print_header "PRUEBA X: TU NUEVA PRUEBA"

RESPONSE=$(api_call "POST" "/tu-endpoint" '{
  "campo1": "valor1",
  "campo2": "valor2"
}')

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" = "200" ]; then
    print_test_result "Nombre de la Prueba" "true"
else
    print_test_result "Nombre de la Prueba" "false"
fi
```

---

## ✅ Checklist de Pruebas

Antes de ejecutar las pruebas, verifica:

- [ ] Backend está corriendo
- [ ] Base de datos está actualizada
- [ ] No hay datos de prueba anteriores que puedan interferir
- [ ] Puerto 9999 está disponible
- [ ] Todos los endpoints están desplegados

Después de ejecutar las pruebas:

- [ ] Todas las pruebas pasaron (100%)
- [ ] No hay errores en los logs del backend
- [ ] Datos de prueba se crearon correctamente
- [ ] Sistema de bloqueo funciona
- [ ] Sistema de permisos funciona

---

## 🎯 Casos de Uso

### **Desarrollo:**
```bash
# Ejecutar después de cada cambio importante
./run_tests.sh
```

### **Antes de Commit:**
```bash
# Verificar que no rompiste nada
./run_tests.sh && git commit -m "feat: nueva funcionalidad"
```

### **Deployment:**
```bash
# Verificar que el deployment fue exitoso
./run_tests.sh "https://api-produccion.oasis.com/api/v1"
```

### **Demo:**
```bash
# Crear datos de prueba para demostración
./run_tests.sh
```

---

## 📞 Soporte

**¿Problemas con los tests?**
- Revisar logs del backend
- Verificar respuestas del API
- Contactar al equipo de backend

**¿Quieres agregar más pruebas?**
- Ver ejemplos en `test_suite_completo.http`
- Seguir el mismo formato que las pruebas existentes

---

## 🎉 ¡Listo!

Ahora tienes una suite completa de pruebas automatizadas para validar todo el sistema Oasis.

**Ejecuta y disfruta:** `./run_tests.sh` o `.\run_tests.ps1`

