# Script de Diagnóstico de Login
# ================================

$BaseUrl = "http://localhost:9999/api/v1"
$ColorSuccess = "Green"
$ColorError = "Red"
$ColorInfo = "Cyan"
$ColorWarning = "Yellow"

Write-Host "`n╔════════════════════════════════════════════════════════════╗" -ForegroundColor $ColorInfo
Write-Host "║         DIAGNÓSTICO DE PROBLEMAS DE LOGIN                 ║" -ForegroundColor $ColorInfo
Write-Host "╚════════════════════════════════════════════════════════════╝`n" -ForegroundColor $ColorInfo

# Credenciales del usuario
$Correo = "luan.huanca@tuguia.bo"
$Password = "luanh7303712"

# ============================================
# PASO 1: Verificar Conexión al Servidor
# ============================================
Write-Host "PASO 1: Verificando conexión al servidor..." -ForegroundColor $ColorInfo
try {
    $TestUrl = "http://localhost:9999"
    $Response = Invoke-WebRequest -Uri "$TestUrl/api/v1/admin" -Method Get -TimeoutSec 3 -UseBasicParsing -ErrorAction Stop
    Write-Host "  ✓ Servidor accesible" -ForegroundColor $ColorSuccess
} catch {
    Write-Host "  ✗ Error: No se puede conectar al servidor" -ForegroundColor $ColorError
    Write-Host "  Asegúrate de que el backend esté corriendo" -ForegroundColor $ColorWarning
    exit 1
}

# ============================================
# PASO 2: Buscar Admin por Correo
# ============================================
Write-Host "`nPASO 2: Buscando admin con correo '$Correo'..." -ForegroundColor $ColorInfo
try {
    $Response = Invoke-RestMethod -Uri "$BaseUrl/admin" -Method Get -ErrorAction Stop
    $Admin = $Response.result | Where-Object { $_.correo -eq $Correo }
    
    if ($Admin) {
        Write-Host "  ✓ Admin encontrado" -ForegroundColor $ColorSuccess
        Write-Host "    ID:              $($Admin.idAdmin)" -ForegroundColor Gray
        Write-Host "    Correo:          $($Admin.correo)" -ForegroundColor Gray
        Write-Host "    Rol ID:          $($Admin.rol.idRol)" -ForegroundColor Gray
        Write-Host "    Rol Nombre:      $($Admin.rol.rol)" -ForegroundColor Gray
        Write-Host "    Estado Cuenta:   $($Admin.estadoCuenta)" -ForegroundColor $(if ($Admin.estadoCuenta) { $ColorSuccess } else { $ColorError })
        Write-Host "    Intentos Fallidos: $($Admin.intentosFallidos)" -ForegroundColor Gray
        
        if (-not $Admin.estadoCuenta) {
            Write-Host "`n  ⚠️  ¡CUENTA BLOQUEADA!" -ForegroundColor $ColorError
            Write-Host "    Motivo: $($Admin.motivoBloqueo)" -ForegroundColor $ColorError
            Write-Host "    Fecha:  $($Admin.fechaBloqueo)" -ForegroundColor $ColorError
            Write-Host "`n  Solución: Desbloquear la cuenta primero" -ForegroundColor $ColorWarning
            Write-Host "  Comando: POST $BaseUrl/bloqueo/admin/$($Admin.idAdmin)/desbloquear" -ForegroundColor $ColorWarning
            
            # Ofrecer desbloquear
            Write-Host "`n  ¿Deseas desbloquear esta cuenta ahora? (S/N)" -ForegroundColor $ColorWarning
            $Desbloquear = Read-Host
            
            if ($Desbloquear -eq "S" -or $Desbloquear -eq "s") {
                try {
                    Write-Host "`n  Desbloqueando cuenta..." -ForegroundColor $ColorInfo
                    $DesbloqueoResponse = Invoke-RestMethod -Uri "$BaseUrl/bloqueo/admin/$($Admin.idAdmin)/desbloquear" -Method POST -ErrorAction Stop
                    Write-Host "  ✓ Cuenta desbloqueada exitosamente" -ForegroundColor $ColorSuccess
                    
                    # Actualizar datos del admin
                    $Response = Invoke-RestMethod -Uri "$BaseUrl/admin" -Method Get -ErrorAction Stop
                    $Admin = $Response.result | Where-Object { $_.correo -eq $Correo }
                } catch {
                    Write-Host "  ✗ Error al desbloquear: $($_.Exception.Message)" -ForegroundColor $ColorError
                    exit 1
                }
            } else {
                Write-Host "`n  La cuenta sigue bloqueada. Login no será posible." -ForegroundColor $ColorError
                exit 1
            }
        }
        
        $AdminId = $Admin.idAdmin
    } else {
        Write-Host "  ✗ No se encontró admin con ese correo" -ForegroundColor $ColorError
        Write-Host "`n  Admins disponibles:" -ForegroundColor $ColorWarning
        foreach ($a in $Response.result) {
            Write-Host "    - $($a.correo) (ID: $($a.idAdmin), Rol: $($a.rol.rol))" -ForegroundColor Gray
        }
        Write-Host "`n  ¿El admin existe? Si no, créalo primero." -ForegroundColor $ColorWarning
        exit 1
    }
} catch {
    Write-Host "  ✗ Error al obtener admins: $($_.Exception.Message)" -ForegroundColor $ColorError
    exit 1
}

# ============================================
# PASO 3: Verificar Intentos de Login
# ============================================
Write-Host "`nPASO 3: Verificando histórico de intentos..." -ForegroundColor $ColorInfo
try {
    $InfoResponse = Invoke-RestMethod -Uri "$BaseUrl/bloqueo/admin/$AdminId/info" -Method Get -ErrorAction Stop
    $Info = $InfoResponse.result
    
    Write-Host "  Estado:            $($Info.bloqueada)" -ForegroundColor $(if ($Info.bloqueada) { $ColorError } else { $ColorSuccess })
    Write-Host "  Intentos Fallidos: $($Info.intentosFallidos)/5" -ForegroundColor Gray
    
    if ($Info.bloqueada) {
        Write-Host "  Motivo:            $($Info.motivoBloqueo)" -ForegroundColor Gray
        Write-Host "  Fecha Bloqueo:     $($Info.fechaBloqueo)" -ForegroundColor Gray
    }
} catch {
    Write-Host "  ⚠️  No se pudo obtener información de bloqueo" -ForegroundColor $ColorWarning
}

# ============================================
# PASO 4: Intentar Login
# ============================================
Write-Host "`nPASO 4: Intentando login..." -ForegroundColor $ColorInfo
Write-Host "  Correo:   $Correo" -ForegroundColor Gray
Write-Host "  Password: $Password" -ForegroundColor Gray

try {
    $LoginBody = @{
        correo = $Correo
        password = $Password
    } | ConvertTo-Json
    
    $LoginResponse = Invoke-RestMethod -Uri "$BaseUrl/admin/login" -Method POST -Body $LoginBody -ContentType "application/json" -ErrorAction Stop
    
    Write-Host "`n  ✓ ¡LOGIN EXITOSO!" -ForegroundColor $ColorSuccess
    Write-Host "`n  Datos del admin autenticado:" -ForegroundColor $ColorInfo
    Write-Host "    ID:       $($LoginResponse.result.idAdmin)" -ForegroundColor Gray
    Write-Host "    Correo:   $($LoginResponse.result.correo)" -ForegroundColor Gray
    Write-Host "    Rol:      $($LoginResponse.result.rol.rol)" -ForegroundColor Gray
    
} catch {
    Write-Host "`n  ✗ LOGIN FALLÓ" -ForegroundColor $ColorError
    
    # Intentar extraer el mensaje de error
    $ErrorMessage = $_.Exception.Message
    
    try {
        if ($_.ErrorDetails.Message) {
            $ErrorData = $_.ErrorDetails.Message | ConvertFrom-Json
            if ($ErrorData.message) {
                $ErrorMessage = $ErrorData.message
            }
        }
    } catch {
        # No se pudo parsear JSON, usar mensaje original
    }
    
    Write-Host "  Mensaje: $ErrorMessage" -ForegroundColor $ColorError
    
    # Analizar tipo de error
    if ($ErrorMessage -match "CUENTA_BLOQUEADA") {
        Write-Host "`n  🔒 DIAGNÓSTICO: Cuenta bloqueada" -ForegroundColor $ColorWarning
        Write-Host "  Solución: Desbloquea la cuenta con:" -ForegroundColor $ColorWarning
        Write-Host "  POST $BaseUrl/bloqueo/admin/$AdminId/desbloquear" -ForegroundColor Gray
    }
    elseif ($ErrorMessage -match "PASSWORD_INCORRECTO") {
        Write-Host "`n  🔑 DIAGNÓSTICO: Contraseña incorrecta" -ForegroundColor $ColorWarning
        
        if ($ErrorMessage -match "Le quedan (\d+) intentos") {
            $IntentosRestantes = $Matches[1]
            Write-Host "  Te quedan $IntentosRestantes intentos antes de bloqueo" -ForegroundColor $ColorWarning
        }
        
        Write-Host "`n  Posibles causas:" -ForegroundColor $ColorInfo
        Write-Host "    1. La contraseña es incorrecta" -ForegroundColor Gray
        Write-Host "    2. El admin fue creado con otra contraseña" -ForegroundColor Gray
        Write-Host "    3. La contraseña fue cambiada recientemente" -ForegroundColor Gray
        
        Write-Host "`n  Soluciones:" -ForegroundColor $ColorInfo
        Write-Host "    1. Verifica que la contraseña sea correcta" -ForegroundColor Gray
        Write-Host "    2. Si olvidaste la contraseña, usa el endpoint de cambio" -ForegroundColor Gray
        Write-Host "    3. Crea un nuevo admin con contraseña conocida" -ForegroundColor Gray
    }
    elseif ($ErrorMessage -match "Correo o contraseña incorrectos") {
        Write-Host "`n  📧 DIAGNÓSTICO: Usuario no encontrado o contraseña incorrecta" -ForegroundColor $ColorWarning
        Write-Host "  El correo no existe o la contraseña no coincide" -ForegroundColor $ColorInfo
    }
    
    exit 1
}

# ============================================
# PASO 5: Verificar Permisos (si login exitoso)
# ============================================
if ($AdminId) {
    Write-Host "`nPASO 5: Verificando permisos del admin..." -ForegroundColor $ColorInfo
    try {
        $PermisosResponse = Invoke-RestMethod -Uri "$BaseUrl/gestion-permisos/admin/$AdminId/permisos-efectivos" -Method Get -ErrorAction Stop
        $Permisos = $PermisosResponse.result
        
        Write-Host "  Permisos asignados: $($Permisos.Count)" -ForegroundColor Gray
        
        if ($Permisos.Count -gt 0) {
            Write-Host "`n  Lista de permisos:" -ForegroundColor $ColorInfo
            foreach ($permiso in $Permisos) {
                $Tipo = $permiso.tipoPermiso
                $Color = switch ($Tipo) {
                    "ROL" { "Green" }
                    "ADICIONAL" { "Cyan" }
                    "TEMPORAL" { "Yellow" }
                    default { "Gray" }
                }
                Write-Host "    → $($permiso.permiso) ($Tipo)" -ForegroundColor $Color
            }
        } else {
            Write-Host "  ⚠️  El admin no tiene permisos asignados" -ForegroundColor $ColorWarning
            Write-Host "  Los permisos se asignan automáticamente por rol" -ForegroundColor $ColorInfo
        }
    } catch {
        Write-Host "  ⚠️  No se pudo obtener permisos" -ForegroundColor $ColorWarning
    }
}

# ============================================
# RESUMEN FINAL
# ============================================
Write-Host "`n╔════════════════════════════════════════════════════════════╗" -ForegroundColor $ColorInfo
Write-Host "║                    DIAGNÓSTICO COMPLETO                    ║" -ForegroundColor $ColorInfo
Write-Host "╚════════════════════════════════════════════════════════════╝`n" -ForegroundColor $ColorInfo

Write-Host "✅ Si llegaste aquí, el diagnóstico está completo." -ForegroundColor $ColorSuccess
Write-Host "`nSi el login falló, revisa los pasos anteriores para identificar el problema." -ForegroundColor $ColorInfo

