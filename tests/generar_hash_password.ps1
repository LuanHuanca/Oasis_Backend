# ============================================
# GENERADOR DE HASH DE CONTRASEÑA
# ============================================
# Este script genera el hash correcto para una contraseña
# usando el mismo salt que el sistema OASIS

param(
    [Parameter(Mandatory=$false)]
    [string]$Password = ""
)

$ColorInfo = "Cyan"
$ColorSuccess = "Green"
$ColorWarning = "Yellow"
$ColorError = "Red"

Write-Host "`n╔════════════════════════════════════════════════════════════╗" -ForegroundColor $ColorInfo
Write-Host "║       GENERADOR DE HASH DE CONTRASEÑA - OASIS             ║" -ForegroundColor $ColorInfo
Write-Host "╚════════════════════════════════════════════════════════════╝`n" -ForegroundColor $ColorInfo

# Si no se proporciona password, pedir al usuario
if ([string]::IsNullOrWhiteSpace($Password)) {
    Write-Host "Introduce la contraseña que deseas hashear:" -ForegroundColor $ColorInfo
    $SecurePassword = Read-Host -AsSecureString
    $BSTR = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($SecurePassword)
    $Password = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR)
}

Write-Host "Contraseña original: $Password" -ForegroundColor Gray
Write-Host "`nGenerando hash..." -ForegroundColor $ColorInfo

# Salt que usa el sistema
$Salt = "Aqm,24Dla"
$PasswordConSalt = $Password + $Salt

Write-Host "Contraseña + Salt: $PasswordConSalt" -ForegroundColor Gray

# Hacer petición al backend para obtener el hash
$BaseUrl = "http://localhost:9999/api/v1"

Write-Host "`n⚠️  NOTA: Para obtener el hash correcto, necesitas usar BCrypt" -ForegroundColor $ColorWarning
Write-Host "PowerShell no tiene BCrypt nativo. Usa una de estas opciones:`n" -ForegroundColor $ColorWarning

Write-Host "OPCIÓN 1: Crear un admin temporal y copiar su hash" -ForegroundColor $ColorSuccess
Write-Host "=========================================" -ForegroundColor Gray
Write-Host @"
POST http://localhost:9999/api/v1/admin/create
Content-Type: application/json

{
  "correo": "temp@test.com",
  "password": "$Password",
  "rolId": 1,
  "idPersona": 1
}

Luego consulta en la BD:
SELECT password FROM admin WHERE correo = 'temp@test.com';
Copia el hash y úsalo para actualizar tu admin real.
"@ -ForegroundColor Gray

Write-Host "`nOPCIÓN 2: Usar el endpoint de cambio de contraseña" -ForegroundColor $ColorSuccess
Write-Host "=========================================" -ForegroundColor Gray
Write-Host @"
POST http://localhost:9999/api/v1/admin/password/1
Content-Type: application/json

{
  "password": "$Password"
}

Esto cambia directamente la contraseña del admin con ID 1.
"@ -ForegroundColor Gray

Write-Host "`nOPCIÓN 3: Usar una herramienta online de BCrypt" -ForegroundColor $ColorSuccess
Write-Host "=========================================" -ForegroundColor Gray
Write-Host "1. Ve a: https://bcrypt-generator.com/" -ForegroundColor Gray
Write-Host "2. Introduce: $PasswordConSalt" -ForegroundColor Gray
Write-Host "3. Usa 10 rounds (costo)" -ForegroundColor Gray
Write-Host "4. Copia el hash generado" -ForegroundColor Gray
Write-Host "5. Ejecuta en PostgreSQL:" -ForegroundColor Gray
Write-Host "   UPDATE admin SET password = '<hash_copiado>' WHERE idadmin = 1;" -ForegroundColor Gray

Write-Host "`nOPCIÓN 4: Ejecutar prueba de creación" -ForegroundColor $ColorSuccess
Write-Host "=========================================" -ForegroundColor Gray
Write-Host "¿Quieres crear un admin temporal para obtener el hash? (S/N)" -ForegroundColor $ColorWarning
$Crear = Read-Host

if ($Crear -eq "S" -or $Crear -eq "s") {
    try {
        Write-Host "`nCreando admin temporal..." -ForegroundColor $ColorInfo
        
        $Body = @{
            correo = "temp.hash.test@oasis.com"
            password = $Password
            rolId = 1
            idPersona = 1
        } | ConvertTo-Json
        
        $Response = Invoke-RestMethod -Uri "$BaseUrl/admin/create" -Method POST -Body $Body -ContentType "application/json" -ErrorAction Stop
        
        Write-Host "✓ Admin temporal creado" -ForegroundColor $ColorSuccess
        
        # Obtener el hash del admin creado
        Write-Host "`nObteniendo hash de la base de datos..." -ForegroundColor $ColorInfo
        Write-Host "Ejecuta en PostgreSQL:" -ForegroundColor $ColorWarning
        Write-Host "SELECT password FROM admin WHERE correo = 'temp.hash.test@oasis.com';" -ForegroundColor Gray
        
        Write-Host "`nLuego puedes:" -ForegroundColor $ColorInfo
        Write-Host "1. Copiar el hash" -ForegroundColor Gray
        Write-Host "2. Usarlo para actualizar tu admin real:" -ForegroundColor Gray
        Write-Host "   UPDATE admin SET password = '<hash_copiado>' WHERE idadmin = 1;" -ForegroundColor Gray
        Write-Host "3. Eliminar el admin temporal:" -ForegroundColor Gray
        Write-Host "   DELETE FROM admin WHERE correo = 'temp.hash.test@oasis.com';" -ForegroundColor Gray
        
    } catch {
        Write-Host "`n✗ Error al crear admin temporal" -ForegroundColor $ColorError
        
        $ErrorMessage = $_.Exception.Message
        try {
            if ($_.ErrorDetails.Message) {
                $ErrorData = $_.ErrorDetails.Message | ConvertFrom-Json
                if ($ErrorData.message) {
                    $ErrorMessage = $ErrorData.message
                }
            }
        } catch {}
        
        Write-Host "Error: $ErrorMessage" -ForegroundColor $ColorError
        Write-Host "`nPosibles causas:" -ForegroundColor $ColorWarning
        Write-Host "- El idPersona 1 no existe" -ForegroundColor Gray
        Write-Host "- El correo ya está en uso" -ForegroundColor Gray
        Write-Host "- El servidor no está corriendo" -ForegroundColor Gray
    }
}

Write-Host "`n╔════════════════════════════════════════════════════════════╗" -ForegroundColor $ColorInfo
Write-Host "║                      FINALIZADO                            ║" -ForegroundColor $ColorInfo
Write-Host "╚════════════════════════════════════════════════════════════╝`n" -ForegroundColor $ColorInfo

