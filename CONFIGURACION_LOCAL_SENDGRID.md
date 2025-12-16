# 🔧 Configurar SendGrid para Desarrollo Local

## 📋 Variables Necesarias

Para que el envío de correos funcione localmente con SendGrid, necesitas configurar estas 3 variables:

1. **`mail.provider`** = `sendgrid`
2. **`sendgrid.api.key`** = Tu API Key de SendGrid (empieza con `SG.`)
3. **`sendgrid.from.email`** = El email que verificaste en SendGrid

---

## ✅ Opción 1: Configurar en `application.properties` (MÁS FÁCIL)

### Paso 1: Abrir el archivo
Abre: `Oasis_Backend/src/main/resources/application.properties`

### Paso 2: Buscar la sección de SendGrid
Busca esta sección (debería estar alrededor de la línea 47):

```properties
# ============================================
# CONFIGURACIÓN DE SENDGRID (Para Railway y Local)
# ============================================
mail.provider=${MAIL_PROVIDER:sendgrid}
sendgrid.api.key=${SENDGRID_API_KEY:}
sendgrid.from.email=${SENDGRID_FROM_EMAIL:}
```

### Paso 3: Agregar tus valores
Reemplaza los valores vacíos con tus datos reales:

```properties
mail.provider=sendgrid
sendgrid.api.key=SG.tu_api_key_completo_aqui
sendgrid.from.email=agencia.viajes.oasis@gmail.com
```

**Ejemplo completo:**
```properties
mail.provider=sendgrid
sendgrid.api.key=SG.abc123XYZ456def789ghi012jkl345mno678pqr901stu234vwx567yz
sendgrid.from.email=agencia.viajes.oasis@gmail.com
```

### Paso 4: Reiniciar la aplicación
- Detén tu aplicación Spring Boot
- Inicia nuevamente
- Prueba el endpoint

---

## ✅ Opción 2: Usar Variables de Entorno (MÁS SEGURO)

Esta opción es mejor porque no guardas el API Key en el código.

### En Windows (PowerShell):

```powershell
$env:MAIL_PROVIDER="sendgrid"
$env:SENDGRID_API_KEY="SG.tu_api_key_completo_aqui"
$env:SENDGRID_FROM_EMAIL="luanhuancam@gmail.com"
```

Luego ejecuta tu aplicación normalmente.

### En Windows (CMD):

```cmd
set MAIL_PROVIDER=sendgrid
set SENDGRID_API_KEY=SG.tu_api_key_completo_aqui
set SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
```

### En Linux/Mac:

```bash
export MAIL_PROVIDER=sendgrid
export SENDGRID_API_KEY=SG.tu_api_key_completo_aqui
export SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
```

---

## ✅ Opción 3: Crear archivo `.env` (Recomendado para desarrollo)

### Paso 1: Crear archivo `.env` en la raíz del proyecto
Crea un archivo llamado `.env` en: `Oasis_Backend/.env`

### Paso 2: Agregar las variables
```env
MAIL_PROVIDER=sendgrid
SENDGRID_API_KEY=SG.tu_api_key_completo_aqui
SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
```

### Paso 3: Cargar el archivo `.env`
Si usas IntelliJ IDEA:
1. Ve a **Run** → **Edit Configurations**
2. Selecciona tu configuración de Spring Boot
3. En **Environment variables**, click en el ícono de carpeta
4. Click en **+** y selecciona **Load from .env file**
5. Selecciona tu archivo `.env`

Si usas VS Code:
- Instala la extensión "DotENV"
- El archivo se cargará automáticamente

---

## 🔍 Verificar que Funciona

### Paso 1: Verificar en los logs
Cuando inicies la aplicación, deberías ver en los logs algo como:
```
Usando SendGrid API REST (HTTPS) para envío de correo
```

### Paso 2: Probar el endpoint
Usa el archivo `Mail.http` que creamos:

```http
POST http://localhost:9999/mail/send/tu_email@gmail.com
Content-Type: application/json
Accept: application/json

{
  "subject": "Prueba Local",
  "message": "Este es un correo de prueba desde localhost"
}
```

### Paso 3: Verificar respuesta
Si todo está bien, deberías recibir:
```json
{
  "message": "Correo enviado exitosamente"
}
```

---

## ⚠️ Errores Comunes

### Error: "SendGrid no está configurado"
**Causa:** Las variables no están configuradas o están vacías.

**Solución:**
1. Verifica que `mail.provider=sendgrid` esté configurado
2. Verifica que `sendgrid.api.key` tenga un valor (no esté vacío)
3. Verifica que `sendgrid.from.email` tenga un valor (no esté vacío)
4. Reinicia la aplicación después de cambiar los valores

### Error: "The from address does not match a verified Sender Identity"
**Causa:** El email en `sendgrid.from.email` no está verificado en SendGrid.

**Solución:**
1. Ve a SendGrid → Settings → Sender Authentication
2. Verifica que el email esté como **✅ Verified**
3. Usa **exactamente** ese email en `sendgrid.from.email` (mismo formato, mayúsculas/minúsculas)

### Error: "Invalid API Key"
**Causa:** El API Key está mal copiado o es inválido.

**Solución:**
1. Ve a SendGrid → Settings → API Keys
2. Crea un nuevo API Key
3. Copia el API Key completo (es muy largo, empieza con `SG.`)
4. Actualiza `sendgrid.api.key` con el nuevo valor

---

## 📝 Checklist

Antes de probar, verifica:

- [ ] Tienes cuenta en SendGrid
- [ ] Tienes un API Key creado (empieza con `SG.`)
- [ ] Tienes un email verificado en SendGrid
- [ ] Configuraste `mail.provider=sendgrid`
- [ ] Configuraste `sendgrid.api.key` con tu API Key completo
- [ ] Configuraste `sendgrid.from.email` con el email verificado
- [ ] Reiniciaste la aplicación después de configurar
- [ ] Probaste el endpoint y recibiste respuesta exitosa

---

## 🎯 Resumen Rápido

**Para empezar rápido:**

1. Abre `application.properties`
2. Busca la sección de SendGrid
3. Agrega:
   ```properties
   mail.provider=sendgrid
   sendgrid.api.key=SG.tu_api_key_aqui
   sendgrid.from.email=tu_email_verificado@gmail.com
   ```
4. Reinicia la aplicación
5. Prueba con `Mail.http`

---

## 🆘 ¿Necesitas ayuda?

Si sigues teniendo problemas:
1. Revisa los logs de la aplicación para ver el error exacto
2. Verifica que el API Key esté completo (no cortado)
3. Verifica que el email esté verificado en SendGrid
4. Asegúrate de haber reiniciado la aplicación después de cambiar la configuración

