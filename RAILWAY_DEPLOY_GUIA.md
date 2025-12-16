# 🚀 Guía para Subir Cambios a Railway

## 📋 Pasos para Desplegar en Railway

### Paso 1: Verificar que `.env` NO se suba a Git

**⚠️ IMPORTANTE:** El archivo `.env` contiene información sensible (API Keys, contraseñas) y **NO debe subirse a Git**.

Verifica que `.env` esté en `.gitignore`:

```bash
# Verificar si .env está ignorado
cd Oasis_Backend
cat .gitignore | grep -i "\.env"
```

Si no está, agrega esta línea a `.gitignore`:
```
.env
```

---

### Paso 2: Agregar y Hacer Commit de los Cambios

#### 2.1 Agregar los archivos modificados y nuevos

```bash
cd Oasis_Backend

# Agregar todos los archivos modificados y nuevos (excepto .env)
git add .
```

**Nota:** Si `.env` está en `.gitignore`, no se agregará automáticamente.

#### 2.2 Verificar qué se va a subir

```bash
# Ver qué archivos se van a commitear
git status
```

**Asegúrate de que `.env` NO aparezca en la lista.**

#### 2.3 Hacer Commit

```bash
git commit -m "feat: Integrar SendGrid para envío de correos en Railway

- Agregar SendGridMailService para envío vía API REST
- Actualizar MailService para soportar SendGrid
- Configurar application-docker.properties para SendGrid
- Agregar guías de configuración de SendGrid
- Agregar endpoint HTTP para pruebas de correo"
```

---

### Paso 3: Subir a GitHub/GitLab

```bash
# Subir los cambios al repositorio remoto
git push origin main
```

**Si es la primera vez o hay conflictos:**
```bash
# Si hay cambios remotos, primero hacer pull
git pull origin main

# Resolver conflictos si los hay, luego:
git push origin main
```

---

### Paso 4: Configurar Variables de Entorno en Railway

**⚠️ CRÍTICO:** Railway necesita estas variables para que SendGrid funcione.

#### 4.1 Ir a Railway

1. Ve a https://railway.app
2. Inicia sesión
3. Selecciona tu proyecto
4. Click en tu servicio de **Backend**

#### 4.2 Agregar Variables de Entorno

En el menú lateral, click en **"Variables"** o **"Environment Variables"**

Agrega estas **3 variables**:

##### Variable 1: MAIL_PROVIDER
- **Name:** `MAIL_PROVIDER`
- **Value:** `sendgrid`
- Click en **"Add"**

##### Variable 2: SENDGRID_API_KEY
- **Name:** `SENDGRID_API_KEY`
- **Value:** `SG.tu_api_key_completo_aqui`
  - Obtén este valor de SendGrid → Settings → API Keys
  - Debe empezar con `SG.` y ser muy largo
- Click en **"Add"**

##### Variable 3: SENDGRID_FROM_EMAIL
- **Name:** `SENDGRID_FROM_EMAIL`
- **Value:** `luanhuancam@gmail.com` (o el email que verificaste en SendGrid)
  - **⚠️ IMPORTANTE:** Debe ser **exactamente** el mismo email que verificaste en SendGrid
- Click en **"Add"**

---

### Paso 5: Verificar Variables Existentes

Asegúrate de que estas variables también estén configuradas (para la base de datos):

- `PGHOST` (Railway la configura automáticamente)
- `PGPORT` (Railway la configura automáticamente)
- `PGDATABASE` (Railway la configura automáticamente)
- `PGUSER` (Railway la configura automáticamente)
- `PGPASSWORD` (Railway la configura automáticamente)

**Si no están, Railway las configura automáticamente cuando conectas la base de datos.**

---

### Paso 6: Trigger del Deploy en Railway

Railway detecta automáticamente cuando haces `git push` y comienza a desplegar.

#### 6.1 Verificar el Deploy

1. En Railway, ve a tu servicio de Backend
2. Click en la pestaña **"Deployments"** o **"Deploys"**
3. Deberías ver un nuevo deploy en progreso
4. Espera a que termine (2-5 minutos)

#### 6.2 Ver Logs del Deploy

1. Click en el deploy en progreso
2. Revisa los logs para ver si hay errores
3. Busca mensajes como:
   - ✅ "Build successful"
   - ✅ "Deployment successful"
   - ❌ Si hay errores, revísalos y corrígelos

---

### Paso 7: Verificar que Funciona

#### 7.1 Probar el Endpoint de Correo

Usa el archivo `Mail.http` que creamos:

```http
POST https://oasisbackend-production-eb4d.up.railway.app/mail/send/tu_email@gmail.com
Content-Type: application/json
Accept: application/json

{
  "subject": "Prueba desde Railway",
  "message": "Este es un correo de prueba desde Railway con SendGrid"
}
```

#### 7.2 Verificar Respuesta

Si todo está bien, deberías recibir:
```json
{
  "message": "Correo enviado exitosamente"
}
```

#### 7.3 Revisar Logs en Railway

1. En Railway, ve a tu servicio de Backend
2. Click en la pestaña **"Logs"**
3. Busca mensajes como:
   - `Usando SendGrid API REST (HTTPS) para envío de correo`
   - `=== CORREO ENVIADO EXITOSAMENTE VÍA SENDGRID ===`

---

## ⚠️ Errores Comunes y Soluciones

### Error: "SendGrid no está configurado"
**Causa:** Las variables de entorno no están configuradas en Railway.

**Solución:**
1. Ve a Railway → Variables
2. Verifica que `MAIL_PROVIDER=sendgrid`
3. Verifica que `SENDGRID_API_KEY` tenga un valor
4. Verifica que `SENDGRID_FROM_EMAIL` tenga un valor
5. Haz un redeploy

### Error: "The from address does not match a verified Sender Identity"
**Causa:** El email en `SENDGRID_FROM_EMAIL` no está verificado en SendGrid.

**Solución:**
1. Ve a SendGrid → Settings → Sender Authentication
2. Verifica que el email esté como **✅ Verified**
3. Usa **exactamente** ese email en Railway (mismo formato)

### Error: "Invalid API Key"
**Causa:** El API Key está mal configurado en Railway.

**Solución:**
1. Ve a SendGrid → Settings → API Keys
2. Crea un nuevo API Key
3. Copia el API Key completo
4. Actualiza `SENDGRID_API_KEY` en Railway
5. Haz un redeploy

### Error: Deploy falla en Railway
**Causa:** Puede ser un error de compilación o configuración.

**Solución:**
1. Revisa los logs del deploy en Railway
2. Verifica que todos los archivos estén commiteados
3. Verifica que no haya errores de sintaxis
4. Si el error persiste, revisa los logs completos

---

## 📝 Checklist Antes de Subir

Antes de hacer `git push`, verifica:

- [ ] `.env` está en `.gitignore` (no se subirá)
- [ ] Todos los archivos necesarios están agregados (`git add .`)
- [ ] No hay archivos sensibles en el commit (`git status`)
- [ ] El commit tiene un mensaje descriptivo
- [ ] Las variables de entorno están listas para configurar en Railway

---

## 📝 Checklist Después de Subir

Después de hacer `git push` y configurar Railway:

- [ ] `git push` se completó sin errores
- [ ] Railway detectó el nuevo commit
- [ ] El deploy comenzó automáticamente
- [ ] `MAIL_PROVIDER=sendgrid` está en Railway
- [ ] `SENDGRID_API_KEY` está en Railway (con el valor correcto)
- [ ] `SENDGRID_FROM_EMAIL` está en Railway (con el email verificado)
- [ ] El deploy terminó exitosamente
- [ ] Probaste el endpoint y funcionó
- [ ] Revisaste los logs y no hay errores

---

## 🎯 Resumen Rápido

**Para subir cambios a Railway:**

1. **Git:**
   ```bash
   cd Oasis_Backend
   git add .
   git commit -m "feat: Integrar SendGrid para envío de correos"
   git push origin main
   ```

2. **Railway:**
   - Ve a Railway → Tu Proyecto → Backend → Variables
   - Agrega: `MAIL_PROVIDER=sendgrid`
   - Agrega: `SENDGRID_API_KEY=SG.tu_api_key`
   - Agrega: `SENDGRID_FROM_EMAIL=tu_email_verificado@gmail.com`
   - Railway desplegará automáticamente

3. **Verificar:**
   - Revisa los logs del deploy
   - Prueba el endpoint de correo
   - Verifica que recibiste el correo

---

## 🆘 ¿Necesitas Ayuda?

Si tienes problemas:
1. Revisa los logs del deploy en Railway
2. Verifica que las variables estén correctamente configuradas
3. Asegúrate de que el email esté verificado en SendGrid
4. Revisa que el API Key sea válido y completo

