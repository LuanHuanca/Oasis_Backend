# ============================================
# Script de Pruebas Automatizadas - Sistema Oasis
# ============================================
# Descripción: Ejecuta pruebas end-to-end del sistema completo
# Uso: .\run_tests.ps1
# ============================================

param(
    [string]$BaseUrl = "http://localhost:9999/api/v1"
)

# Configuración
$ErrorActionPreference = "Continue"
$ProgressPreference = "SilentlyContinue"

# Colores
$ColorSuccess = "Green"
$ColorError = "Red"
$ColorInfo = "Cyan"
$ColorWarning = "Yellow"

# Contadores (globales para que las funciones puedan modificarlas)
$global:TestsTotal = 0
$global:TestsPassed = 0
$global:TestsFailed = 0

# IDs de registros creados (se actualizan durante las pruebas)
$ClienteId = $null
$AdminId = $null
$PersonaClienteId = $null
$PersonaAdminId = $null

# ============================================
# FUNCIONES AUXILIARES
# ============================================

function Write-TestHeader {
    param([string]$Title)
    Write-Host "`n============================================" -ForegroundColor $ColorInfo
    Write-Host " $Title" -ForegroundColor $ColorInfo
    Write-Host "============================================`n" -ForegroundColor $ColorInfo
}

function Write-TestResult {
    param(
        [string]$TestName,
        [bool]$Passed,
        [string]$Message = ""
    )
    
    $global:TestsTotal++
    
    if ($Passed) {
        $global:TestsPassed++
        Write-Host "✓ $TestName" -ForegroundColor $ColorSuccess
        if ($Message) {
            Write-Host "  → $Message" -ForegroundColor Gray
        }
    } else {
        $global:TestsFailed++
        Write-Host "✗ $TestName" -ForegroundColor $ColorError
        if ($Message) {
            Write-Host "  → $Message" -ForegroundColor $ColorError
        }
    }
}

function Invoke-ApiTest {
    param(
        [string]$Method,
        [string]$Endpoint,
        [hashtable]$Body = @{},
        [int]$ExpectedStatus = 200
    )
    
    try {
        $Uri = "$BaseUrl$Endpoint"
        $Headers = @{
            "Content-Type" = "application/json"
        }
        
        $Params = @{
            Uri = $Uri
            Method = $Method
            Headers = $Headers
        }
        
        if ($Body.Count -gt 0) {
            $Params.Body = ($Body | ConvertTo-Json -Depth 10)
        }
        
        $Response = Invoke-RestMethod @Params -ErrorAction Stop
        
        return @{
            Success = $true
            StatusCode = 200
            Data = $Response
            Error = $null
        }
    }
    catch {
        $StatusCode = 0
        $ErrorMessage = $_.Exception.Message
        $ErrorBody = $null
        
        # Manejo moderno de errores HTTP en PowerShell
        if ($_.Exception -is [Microsoft.PowerShell.Commands.HttpResponseException]) {
            $StatusCode = [int]$_.Exception.Response.StatusCode.value__
            
            try {
                $ErrorBody = $_.ErrorDetails.Message
                if ($ErrorBody) {
                    $ErrorData = $ErrorBody | ConvertFrom-Json
                    if ($ErrorData.message) {
                        $ErrorMessage = $ErrorData.message
                    }
                }
            } catch {
                # Si no se puede parsear JSON, usar el mensaje de error original
                if ($ErrorBody) {
                    $ErrorMessage = $ErrorBody
                }
            }
        }
        # Compatibilidad con versiones antiguas de PowerShell
        elseif ($_.Exception.Response) {
            $StatusCode = [int]$_.Exception.Response.StatusCode
            
            try {
                $Stream = $_.Exception.Response.GetResponseStream()
                $Reader = New-Object System.IO.StreamReader($Stream)
                $ErrorBody = $Reader.ReadToEnd()
                $Reader.Close()
                $Stream.Close()
                
                $ErrorData = $ErrorBody | ConvertFrom-Json
                $ErrorMessage = $ErrorData.message
            } catch {
                if ($ErrorBody) {
                    $ErrorMessage = $ErrorBody
                }
            }
        }
        
        return @{
            Success = ($StatusCode -eq $ExpectedStatus)
            StatusCode = $StatusCode
            Data = $null
            Error = $ErrorMessage
        }
    }
}

function Wait-ForEnter {
    param([string]$Message = "Presiona Enter para continuar...")
    Write-Host "`n$Message" -ForegroundColor $ColorWarning
    Read-Host
}

# ============================================
# INICIO DE PRUEBAS
# ============================================

Write-Host @"
╔════════════════════════════════════════════════════════════╗
║     SUITE DE PRUEBAS AUTOMATIZADAS - SISTEMA OASIS        ║
╚════════════════════════════════════════════════════════════╝
"@ -ForegroundColor $ColorInfo

Write-Host "`nBase URL: $BaseUrl" -ForegroundColor $ColorInfo
Write-Host "Fecha: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')`n" -ForegroundColor Gray

# Verificar que el servidor esté corriendo
Write-Host "Verificando conexión con el servidor..." -ForegroundColor $ColorInfo
try {
    # Intentar conectar a un endpoint que sabemos que existe
    $TestUrl = $BaseUrl -replace '/api/v1$', ''
    if (-not $TestUrl) {
        $TestUrl = "http://localhost:9999"
    }
    
    # Intentar con varios endpoints hasta encontrar uno que funcione
    $Connected = $false
    $Endpoints = @("/api/v1/cliente", "/api/v1/admin", "/actuator/health", "/")
    
    foreach ($endpoint in $Endpoints) {
        try {
            $TestUri = if ($endpoint -eq "/") { $TestUrl } else { "$TestUrl$endpoint" }
            $Response = Invoke-WebRequest -Uri $TestUri -Method Get -TimeoutSec 3 -UseBasicParsing -ErrorAction Stop
            $Connected = $true
            break
        } catch {
            # Probar siguiente endpoint
            continue
        }
    }
    
    if ($Connected) {
        Write-Host "✓ Servidor accesible en $TestUrl" -ForegroundColor $ColorSuccess
    } else {
        throw "No se pudo conectar"
    }
} catch {
    Write-Host "✗ Error: No se puede conectar al servidor" -ForegroundColor $ColorError
    Write-Host "  URL intentada: $BaseUrl" -ForegroundColor $ColorError
    Write-Host "  Asegúrate de que el backend esté corriendo en el puerto correcto." -ForegroundColor $ColorError
    Write-Host "  Comando para verificar: netstat -ano | findstr :9999" -ForegroundColor $ColorWarning
    Write-Host "`n¿Deseas continuar de todos modos? (S/N)" -ForegroundColor $ColorWarning
    $Continue = Read-Host
    if ($Continue -ne "S" -and $Continue -ne "s") {
        exit 1
    }
}

# ============================================
# PRUEBA 1: CREACIÓN DE CLIENTE
# ============================================

Write-TestHeader "PRUEBA 1: CREACIÓN DE CLIENTE"

# 1.1 Crear Persona para Cliente
Write-Host "1.1. Creando persona para cliente..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/persona" -Body @{
    nombre = "Juan"
    apellido = "Pérez Test"
    genero = "M"
    telefono = "123456789"
    fechaNacimiento = "1990-01-15"
    direccion = "Av. Principal 123"
}

if ($Result.Success) {
    $PersonaClienteId = $Result.Data.result.idPersona
    Write-TestResult "Crear Persona para Cliente" $true "ID: $PersonaClienteId"
} else {
    Write-TestResult "Crear Persona para Cliente" $false $Result.Error
    Write-Host "`n⚠ Usando ID de persona por defecto: 1" -ForegroundColor $ColorWarning
    $PersonaClienteId = 1
}

# 1.2 Crear Cliente
Write-Host "`n1.2. Creando cliente..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente" -Body @{
    correo = "cliente.test@oasis.com"
    password = "TestPass123"
    idPersona = $PersonaClienteId
}

if ($Result.Success) {
    $ClienteId = $Result.Data.result.idCliente
    $EstadoCuenta = $Result.Data.result.estadoCuenta
    $IntentosFallidos = $Result.Data.result.intentosFallidos
    
    $Validations = @(
        ($EstadoCuenta -eq $true),
        ($IntentosFallidos -eq 0)
    )
    
    if ($Validations -notcontains $false) {
        Write-TestResult "Crear Cliente" $true "ID: $ClienteId, Estado: Activo, Intentos: 0"
    } else {
        Write-TestResult "Crear Cliente" $false "Validaciones fallidas"
    }
} else {
    Write-TestResult "Crear Cliente" $false $Result.Error
}

# ============================================
# PRUEBA 2: LOGIN EXITOSO
# ============================================

Write-TestHeader "PRUEBA 2: LOGIN EXITOSO"

$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/login" -Body @{
    correo = "cliente.test@oasis.com"
    password = "TestPass123"
}

if ($Result.Success) {
    Write-TestResult "Login Exitoso de Cliente" $true
} else {
    Write-TestResult "Login Exitoso de Cliente" $false $Result.Error
}

# ============================================
# PRUEBA 3: INTENTOS FALLIDOS (BLOQUEO AUTOMÁTICO)
# ============================================

Write-TestHeader "PRUEBA 3: INTENTOS FALLIDOS Y BLOQUEO AUTOMÁTICO"

for ($i = 1; $i -le 5; $i++) {
    Write-Host "`n3.$i. Intento fallido $i/5..." -ForegroundColor $ColorInfo
    
    $Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/login" -Body @{
        correo = "cliente.test@oasis.com"
        password = "PasswordIncorrecta$i"
    } -ExpectedStatus 400
    
    if ($i -lt 5) {
        # Intentos 1-4: Debe indicar intentos restantes
        $IntentosRestantes = 5 - $i
        $TestPassed = $Result.Error -match "Le quedan $IntentosRestantes intentos"
        Write-TestResult "Intento Fallido $i/5" $TestPassed "Respuesta: $($Result.Error)"
    } else {
        # Intento 5: Debe bloquear la cuenta
        $TestPassed = $Result.Error -match "CUENTA_BLOQUEADA"
        Write-TestResult "Bloqueo Automático (Intento 5/5)" $TestPassed "Respuesta: $($Result.Error)"
    }
    
    Start-Sleep -Milliseconds 500
}

# ============================================
# PRUEBA 4: VERIFICAR BLOQUEO
# ============================================

Write-TestHeader "PRUEBA 4: VERIFICACIÓN DE BLOQUEO"

# 4.1 Intentar login con contraseña correcta (debe fallar por bloqueo)
Write-Host "4.1. Intentando login con contraseña correcta (debe fallar)..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/login" -Body @{
    correo = "cliente.test@oasis.com"
    password = "TestPass123"
} -ExpectedStatus 400

$TestPassed = $Result.Error -match "CUENTA_BLOQUEADA"
Write-TestResult "Login Bloqueado (con password correcta)" $TestPassed

# 4.2 Verificar estado de cuenta
Write-Host "`n4.2. Verificando estado de cuenta..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method GET -Endpoint "/bloqueo/cliente/$ClienteId/info"

if ($Result.Success) {
    $Bloqueada = $Result.Data.result.bloqueada
    $Intentos = $Result.Data.result.intentosFallidos
    $Motivo = $Result.Data.result.motivoBloqueo
    
    $TestPassed = ($Bloqueada -eq $true) -and ($Intentos -eq 5)
    Write-TestResult "Estado de Cuenta Bloqueada" $TestPassed "Bloqueada: $Bloqueada, Intentos: $Intentos"
    
    if ($TestPassed) {
        Write-Host "  → Motivo: $Motivo" -ForegroundColor Gray
    }
} else {
    Write-TestResult "Estado de Cuenta Bloqueada" $false $Result.Error
}

# ============================================
# PRUEBA 5: DESBLOQUEO
# ============================================

Write-TestHeader "PRUEBA 5: DESBLOQUEO DE CUENTA"

# 5.1 Desbloquear cuenta
Write-Host "5.1. Desbloqueando cuenta..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/bloqueo/cliente/$ClienteId/desbloquear"

Write-TestResult "Desbloquear Cuenta" $Result.Success

# 5.2 Verificar desbloqueo
Write-Host "`n5.2. Verificando desbloqueo..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method GET -Endpoint "/bloqueo/cliente/$ClienteId/info"

if ($Result.Success) {
    $Bloqueada = $Result.Data.result.bloqueada
    $Intentos = $Result.Data.result.intentosFallidos
    
    $TestPassed = ($Bloqueada -eq $false) -and ($Intentos -eq 0)
    Write-TestResult "Verificar Desbloqueo" $TestPassed "Bloqueada: $Bloqueada, Intentos: $Intentos"
} else {
    Write-TestResult "Verificar Desbloqueo" $false $Result.Error
}

# 5.3 Login exitoso después de desbloqueo
Write-Host "`n5.3. Login después de desbloqueo..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/login" -Body @{
    correo = "cliente.test@oasis.com"
    password = "TestPass123"
}

Write-TestResult "Login Después de Desbloqueo" $Result.Success

# ============================================
# PRUEBA 6: CAMBIO DE CONTRASEÑA
# ============================================

Write-TestHeader "PRUEBA 6: CAMBIO DE CONTRASEÑA"

# 6.1 Validar contraseña actual (correcta)
Write-Host "6.1. Validando contraseña actual..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/$ClienteId/validate-password" -Body @{
    password = "TestPass123"
}

if ($Result.Success) {
    $Valid = $Result.Data.result.valid
    Write-TestResult "Validar Contraseña Actual (Correcta)" ($Valid -eq $true)
} else {
    Write-TestResult "Validar Contraseña Actual (Correcta)" $false $Result.Error
}

# 6.2 Validar contraseña incorrecta
Write-Host "`n6.2. Validando contraseña incorrecta..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/$ClienteId/validate-password" -Body @{
    password = "PasswordIncorrecta"
} -ExpectedStatus 400

Write-TestResult "Validar Contraseña Incorrecta" (-not $Result.Success)

# 6.3 Cambiar contraseña
Write-Host "`n6.3. Cambiando contraseña..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method PUT -Endpoint "/cliente/$ClienteId/password" -Body @{
    password = "NuevaPassword456"
}

Write-TestResult "Cambiar Contraseña" $Result.Success

# 6.4 Intentar reutilizar contraseña anterior
Write-Host "`n6.4. Intentando reutilizar contraseña anterior (debe fallar)..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method PUT -Endpoint "/cliente/$ClienteId/password" -Body @{
    password = "TestPass123"
} -ExpectedStatus 400

$TestPassed = $Result.Error -match "ya fue usada anteriormente"
Write-TestResult "Bloquear Reutilización de Contraseña" $TestPassed

# 6.5 Login con nueva contraseña
Write-Host "`n6.5. Login con nueva contraseña..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/login" -Body @{
    correo = "cliente.test@oasis.com"
    password = "NuevaPassword456"
}

Write-TestResult "Login con Nueva Contraseña" $Result.Success

# ============================================
# PRUEBA 7: BLOQUEO MANUAL
# ============================================

Write-TestHeader "PRUEBA 7: BLOQUEO MANUAL"

# 7.1 Bloquear manualmente
Write-Host "7.1. Bloqueando cuenta manualmente..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/bloqueo/cliente/$ClienteId/bloquear" -Body @{
    motivo = "Prueba de bloqueo manual por administrador"
}

Write-TestResult "Bloqueo Manual" $Result.Success

# 7.2 Verificar bloqueo manual
Write-Host "`n7.2. Verificando bloqueo manual..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method GET -Endpoint "/bloqueo/cliente/$ClienteId/info"

if ($Result.Success) {
    $Bloqueada = $Result.Data.result.bloqueada
    $Motivo = $Result.Data.result.motivoBloqueo
    
    $TestPassed = ($Bloqueada -eq $true) -and ($Motivo -like "*manual*")
    Write-TestResult "Verificar Bloqueo Manual" $TestPassed "Motivo: $Motivo"
} else {
    Write-TestResult "Verificar Bloqueo Manual" $false $Result.Error
}

# 7.3 Login debe fallar
Write-Host "`n7.3. Intentando login (debe fallar)..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/cliente/login" -Body @{
    correo = "cliente.test@oasis.com"
    password = "NuevaPassword456"
} -ExpectedStatus 400

$TestPassed = $Result.Error -match "CUENTA_BLOQUEADA"
Write-TestResult "Login Bloqueado Manualmente" $TestPassed

# Desbloquear para siguiente prueba
Write-Host "`n7.4. Desbloqueando para siguientes pruebas..." -ForegroundColor $ColorInfo
$null = Invoke-ApiTest -Method POST -Endpoint "/bloqueo/cliente/$ClienteId/desbloquear"

# ============================================
# PRUEBA 8: CREACIÓN DE ADMINISTRADOR
# ============================================

Write-TestHeader "PRUEBA 8: CREACIÓN DE ADMINISTRADOR"

# 8.1 Crear Persona para Admin
Write-Host "8.1. Creando persona para admin..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/persona" -Body @{
    nombre = "María"
    apellido = "García Test"
    genero = "F"
    telefono = "987654321"
    fechaNacimiento = "1985-05-20"
    direccion = "Calle Secundaria 456"
}

if ($Result.Success) {
    $PersonaAdminId = $Result.Data.result.idPersona
    Write-TestResult "Crear Persona para Admin" $true "ID: $PersonaAdminId"
} else {
    Write-TestResult "Crear Persona para Admin" $false $Result.Error
    Write-Host "`n⚠ Usando ID de persona por defecto: 2" -ForegroundColor $ColorWarning
    $PersonaAdminId = 2
}

# 8.2 Crear Admin con Rol "Tecnología"
Write-Host "`n8.2. Creando administrador..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/admin" -Body @{
    correo = "admin.test@oasis.com"
    password = "AdminPass123"
    idPersona = $PersonaAdminId
    rol = @{
        idRol = 2  # Tecnología
    }
}

if ($Result.Success) {
    $AdminId = $Result.Data.result.idAdmin
    $RolNombre = $Result.Data.result.rol.rol
    $EstadoCuenta = $Result.Data.result.estadoCuenta
    
    $TestPassed = ($EstadoCuenta -eq $true) -and ($RolNombre -eq "Tecnología")
    Write-TestResult "Crear Administrador" $TestPassed "ID: $AdminId, Rol: $RolNombre"
} else {
    Write-TestResult "Crear Administrador" $false $Result.Error
}

# 8.3 Login de Admin
Write-Host "`n8.3. Login de administrador..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method POST -Endpoint "/admin/login" -Body @{
    correo = "admin.test@oasis.com"
    password = "AdminPass123"
}

Write-TestResult "Login de Administrador" $Result.Success

# ============================================
# PRUEBA 9: PERMISOS
# ============================================

Write-TestHeader "PRUEBA 9: SISTEMA DE PERMISOS"

# 9.1 Obtener permisos efectivos
Write-Host "9.1. Obteniendo permisos efectivos..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method GET -Endpoint "/gestion-permisos/admin/$AdminId/permisos-efectivos"

if ($Result.Success) {
    $Permisos = $Result.Data.result
    $CantidadPermisos = $Permisos.Count
    Write-TestResult "Obtener Permisos Efectivos" $true "Cantidad: $CantidadPermisos permisos"
    
    Write-Host "`n  Permisos del rol 'Tecnología':" -ForegroundColor Gray
    foreach ($permiso in $Permisos) {
        Write-Host "    → $($permiso.permiso) ($($permiso.tipoPermiso))" -ForegroundColor Gray
    }
} else {
    Write-TestResult "Obtener Permisos Efectivos" $false $Result.Error
}

# 9.2 Verificar permiso que debe tener (Editar Usuario - ID: 1)
Write-Host "`n9.2. Verificando permiso 'Editar Usuario'..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method GET -Endpoint "/gestion-permisos/admin/$AdminId/tiene-permiso/1"

if ($Result.Success) {
    $TienePermiso = $Result.Data.result.tienePermiso
    Write-TestResult "Tiene Permiso 'Editar Usuario'" ($TienePermiso -eq $true)
} else {
    Write-TestResult "Tiene Permiso 'Editar Usuario'" $false $Result.Error
}

# 9.3 Verificar permiso que NO debe tener (Registrar Cuentas - ID: 9)
Write-Host "`n9.3. Verificando permiso 'Registrar Cuentas' (no debe tener)..." -ForegroundColor $ColorInfo
$Result = Invoke-ApiTest -Method GET -Endpoint "/gestion-permisos/admin/$AdminId/tiene-permiso/9"

if ($Result.Success) {
    $TienePermiso = $Result.Data.result.tienePermiso
    Write-TestResult "NO Tiene Permiso 'Registrar Cuentas'" ($TienePermiso -eq $false)
} else {
    Write-TestResult "NO Tiene Permiso 'Registrar Cuentas'" $false $Result.Error
}

# ============================================
# RESUMEN FINAL
# ============================================

Write-Host "`n`n" -NoNewline
Write-Host "============================================" -ForegroundColor $ColorInfo
Write-Host "           RESUMEN DE PRUEBAS" -ForegroundColor $ColorInfo
Write-Host "============================================`n" -ForegroundColor $ColorInfo

Write-Host "Total de pruebas:  $global:TestsTotal" -ForegroundColor $ColorInfo
Write-Host "Exitosas:          $global:TestsPassed" -ForegroundColor $ColorSuccess
Write-Host "Fallidas:          $global:TestsFailed" -ForegroundColor $(if ($global:TestsFailed -eq 0) { $ColorSuccess } else { $ColorError })

$SuccessRate = if ($global:TestsTotal -gt 0) { [math]::Round(($global:TestsPassed / $global:TestsTotal) * 100, 2) } else { 0 }
Write-Host "Tasa de éxito:     $SuccessRate%" -ForegroundColor $(if ($SuccessRate -gt 80) { $ColorSuccess } elseif ($SuccessRate -gt 50) { $ColorWarning } else { $ColorError })

Write-Host "`n============================================`n" -ForegroundColor $ColorInfo

if ($global:TestsFailed -eq 0 -and $global:TestsTotal -gt 0) {
    Write-Host "🎉 ¡TODAS LAS PRUEBAS PASARON EXITOSAMENTE!" -ForegroundColor $ColorSuccess
    exit 0
} else {
    Write-Host "⚠️  ALGUNAS PRUEBAS FALLARON" -ForegroundColor $ColorWarning
    Write-Host "Revisa los mensajes de error arriba para más detalles." -ForegroundColor $ColorWarning
    exit 1
}

