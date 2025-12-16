# 📧 Configurar SendGrid SIN Dominio Propio (Solo con Email)

## ✅ NO Necesitas Dominio Propio

**Puedes usar SendGrid verificando solo un EMAIL individual.**  
**NO necesitas el dominio de Railway** ni ningún dominio propio.

---

## 🎯 Solución: Verificar un Email Individual

### Paso 1: Crear Cuenta en SendGrid

1. Ve a https://sendgrid.com
2. Click en **"Start for free"**
3. Completa el registro:
   - Email: Usa tu email personal (ej: `agencia.viajes.oasis@gmail.com`)
   - Contraseña
   - Nombre
4. Verifica tu email (revisa tu bandeja)
5. Completa la verificación de identidad (puede pedir teléfono)

---

### Paso 2: Crear API Key

1. Una vez dentro del dashboard de SendGrid
2. En el menú lateral izquierdo, ve a **"Settings"** (Configuración)
3. Click en **"API Keys"** o **"Claves API"**
4. Click en **"Create API Key"** o **"Crear Clave API"**
5. Configuración:
   - **API Key Name:** `Railway Oasis`
   - **API Key Permissions:** Selecciona **"Full Access"** o **"Mail Send"**
6. Click en **"Create & View"**
7. **⚠️ MUY IMPORTANTE:** Copia el API Key **INMEDIATAMENTE**
   - Empieza con `SG.` seguido de muchos caracteres
   - Ejemplo: `SG.abc123XYZ456def789ghi012jkl345mno678pqr901stu234vwx567yz`
   - **NO podrás verlo de nuevo** después de cerrar la ventana
   - Guárdalo en un lugar seguro

---

### Paso 3: Verificar un Email Individual (SIN Dominio)

**Esta es la parte importante - NO necesitas dominio:**

1. En el menú lateral de SendGrid, ve a **"Settings"**
2. Click en **"Sender Authentication"**
3. Verás varias opciones:
   - **"Authenticate Your Domain"** ← NO uses esta (requiere dominio)
   - **"Verify a Single Sender"** ← ✅ USA ESTA (solo email)
4. Click en **"Verify a Single Sender"** o **"Verificar un Remitente Individual"**

5. Completa el formulario:
   - **From Email Address:** 
     - Ingresa tu email personal
     - Ejemplo: `agencia.viajes.oasis@gmail.com`
     - **NO uses** `@railway.app` ni ningún dominio de Railway
   
   - **From Name:** 
     - Nombre que aparecerá como remitente
     - Ejemplo: `Agencia Oasis` o `Oasis Viajes`
   
   - **Reply To:** 
     - Puede ser el mismo email o diferente
     - Ejemplo: `agencia.viajes.oasis@gmail.com`
   
   - **Company Address:** 
     - Tu dirección física (puede ser tu dirección personal)
   
   - **City, State, Zip, Country:** 
     - Tu información de ubicación

6. Click en **"Create"** o **"Crear"**

7. SendGrid te enviará un correo de verificación a ese email

8. **Revisa tu bandeja de entrada** (y también spam):
   - Busca un correo de SendGrid
   - Asunto: "Verify your sender email" o similar
   - Click en el enlace de verificación

9. ✅ **Listo!** El email quedará verificado

10. Vuelve a SendGrid y verifica que el email aparezca como **✅ "Verified"**

---

### Paso 4: Configurar en Railway

1. Ve a tu proyecto en Railway: https://railway.app
2. Selecciona tu proyecto
3. En el menú lateral, click en **"Variables"**
4. Click en **"+ New Variable"**

#### Variable 1: MAIL_PROVIDER
- **Name:** `MAIL_PROVIDER`
- **Value:** `sendgrid`
- Click en **"Add"**

#### Variable 2: SENDGRID_API_KEY
- **Name:** `SENDGRID_API_KEY`
- **Value:** `SG.abc123XYZ456def789...` (el API Key completo que copiaste)
- Click en **"Add"**

#### Variable 3: SENDGRID_FROM_EMAIL
- **Name:** `SENDGRID_FROM_EMAIL`
- **Value:** El email que verificaste (ej: `agencia.viajes.oasis@gmail.com`)
- **⚠️ IMPORTANTE:** Debe ser **exactamente** el mismo email que verificaste
- Click en **"Add"**

---

### Paso 5: Redeploy

1. En Railway, ve a tu servicio (backend)
2. Click en los **3 puntos** (⋯)
3. Selecciona **"Redeploy"**
4. Espera a que termine (2-5 minutos)

---

## ✅ Ejemplo Completo

### Lo que tienes:
- ❌ NO tienes dominio propio
- ✅ Tienes email: `agencia.viajes.oasis@gmail.com`
- ✅ Railway te da: `oasisbackend-production-eb4d.up.railway.app` (NO lo uses)

### Lo que haces:
1. Verificas `agencia.viajes.oasis@gmail.com` en SendGrid
2. Obtienes API Key: `SG.abc123XYZ456...`
3. Configuras en Railway:
   ```
   MAIL_PROVIDER=sendgrid
   SENDGRID_API_KEY=SG.abc123XYZ456...
   SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
   ```

### Resultado:
- ✅ Puedes enviar correos desde `agencia.viajes.oasis@gmail.com`
- ✅ Funciona perfectamente en Railway
- ✅ NO necesitas dominio propio

---

## ⚠️ Errores Comunes

### Error: "The from address does not match a verified Sender Identity"
- **Causa:** El email en `SENDGRID_FROM_EMAIL` no está verificado
- **Solución:** 
  - Ve a SendGrid → Settings → Sender Authentication
  - Verifica que el email esté como **✅ Verified**
  - Usa **exactamente** ese email en Railway

### Error: "Invalid API Key"
- **Causa:** El API Key está mal copiado
- **Solución:** 
  - Verifica que empiece con `SG.`
  - Copia el API Key completo (es muy largo)
  - Si no lo tienes, crea uno nuevo

---

## 🎯 Resumen

**NO necesitas:**
- ❌ Dominio propio
- ❌ Dominio de Railway
- ❌ Configurar DNS

**Solo necesitas:**
- ✅ Un email personal (Gmail, Outlook, etc.)
- ✅ Verificar ese email en SendGrid
- ✅ API Key de SendGrid
- ✅ Configurar las 3 variables en Railway

---

## 📝 Checklist

- [ ] Cuenta creada en SendGrid
- [ ] API Key creada y copiada (empieza con `SG.`)
- [ ] Email verificado en SendGrid (estado: ✅ Verified)
- [ ] `MAIL_PROVIDER=sendgrid` en Railway
- [ ] `SENDGRID_API_KEY` en Railway (API Key completo)
- [ ] `SENDGRID_FROM_EMAIL` en Railway (email verificado)
- [ ] Redeploy hecho en Railway
- [ ] Prueba de envío realizada

---

## 🆘 ¿Aún tienes dudas?

**Pregunta común:** "¿Puedo usar el email de Railway?"
- **Respuesta:** NO. Railway no te da un email, solo un dominio. Debes usar tu email personal (Gmail, Outlook, etc.)

**Pregunta común:** "¿Qué email uso?"
- **Respuesta:** Cualquier email personal que tengas. El más común es Gmail.

**Pregunta común:** "¿El destinatario verá mi email personal?"
- **Respuesta:** Sí, el correo aparecerá como enviado desde ese email. Por eso es mejor usar un email profesional si es posible.

