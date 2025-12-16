# 📧 Guía Completa: Configurar SendGrid en Railway

## ✅ ¿Por qué SendGrid?

- ✅ Funciona perfectamente en Railway (usa HTTPS, no SMTP)
- ✅ 100 correos/día gratis
- ✅ Muy estable y confiable
- ✅ Excelente entregabilidad
- ✅ Fácil de configurar

---

## 📋 Paso 1: Crear Cuenta en SendGrid

1. Ve a https://sendgrid.com
2. Click en **"Start for free"** o **"Comenzar gratis"**
3. Completa el formulario de registro:
   - Email
   - Contraseña
   - Nombre
   - Empresa (opcional)
4. Verifica tu email (revisa tu bandeja)
5. Completa la verificación de identidad (puede pedir número de teléfono)

---

## 🔑 Paso 2: Crear API Key

1. Una vez dentro del dashboard de SendGrid:
2. En el menú lateral izquierdo, busca **"Settings"** (Configuración)
3. Click en **"API Keys"** o **"Claves API"**
4. Click en **"Create API Key"** o **"Crear Clave API"**
5. Configuración:
   - **API Key Name:** `Railway Oasis` (o el nombre que prefieras)
   - **API Key Permissions:** Selecciona **"Full Access"** o **"Mail Send"**
6. Click en **"Create & View"** o **"Crear y Ver"**
7. **⚠️ MUY IMPORTANTE:** Copia el API Key **INMEDIATAMENTE**
   - El API Key es una cadena larga de caracteres
   - Ejemplo: `SG.abc123XYZ456def789ghi012jkl345mno678pqr901stu234vwx567yz`
   - **NO podrás verlo de nuevo** después de cerrar la ventana
   - Guárdalo en un lugar seguro

---

## 📧 Paso 3: Verificar Sender Identity (Email o Dominio)

SendGrid requiere verificar quién envía los correos. Tienes 2 opciones:

### ✅ OPCIÓN A: Verificar un Email Individual (MÁS FÁCIL - RECOMENDADO)

**Recomendado si NO tienes dominio propio:**

1. En el menú lateral, ve a **"Settings"** → **"Sender Authentication"**
2. Click en **"Verify a Single Sender"** o **"Verificar un Remitente Individual"**
3. Completa el formulario:
   - **From Email Address:** Tu email personal (ej: `agencia.viajes.oasis@gmail.com`)
     - **⚠️ NO uses** el dominio de Railway (como `@railway.app`)
     - Usa tu email personal (Gmail, Outlook, etc.)
   - **From Name:** Nombre que aparecerá (ej: `Agencia Oasis`)
   - **Reply To:** Mismo email o diferente
   - **Company Address:** Tu dirección (puede ser personal)
   - **City, State, Zip, Country:** Tu información
4. Click en **"Create"** o **"Crear"**
5. SendGrid te enviará un correo de verificación
6. Revisa tu bandeja (y spam)
7. Click en el enlace de verificación del correo
8. ✅ El email quedará verificado

**Usa este email en:** `SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com`

**💡 IMPORTANTE:** 
- NO necesitas dominio propio para esta opción
- Solo necesitas un email personal (Gmail, Outlook, etc.)
- El email que verifiques será el remitente de todos los correos

---

### ✅ OPCIÓN B: Verificar un Dominio Propio (Más Profesional)

**Si tienes un dominio propio (ej: `tuguia.bo`):**

1. En el menú lateral, ve a **"Settings"** → **"Sender Authentication"**
2. Click en **"Authenticate Your Domain"** o **"Autenticar tu Dominio"**
3. Selecciona tu proveedor de DNS (GoDaddy, Namecheap, Cloudflare, etc.)
4. SendGrid te dará registros DNS que debes agregar:
   - Registros **CNAME**
   - Registros **TXT**
5. Ve a tu proveedor de dominio
6. Agrega los registros DNS en la zona DNS de tu dominio
7. Espera 5-30 minutos para que se propaguen
8. Vuelve a SendGrid y click en **"Verify"** o **"Verificar"**
9. ✅ El dominio quedará verificado

**Puedes usar cualquier email de ese dominio:**
- `SENDGRID_FROM_EMAIL=noreply@tudominio.com`
- `SENDGRID_FROM_EMAIL=contacto@tudominio.com`
- etc.

---

## 🚂 Paso 4: Configurar Variables en Railway

1. Ve a tu proyecto en Railway: https://railway.app
2. Selecciona tu proyecto
3. En el menú lateral, click en **"Variables"**
4. Click en **"+ New Variable"** o **"+ Nueva Variable"**

### Variable 1: MAIL_PROVIDER
- **Name:** `MAIL_PROVIDER`
- **Value:** `sendgrid`
- Click en **"Add"**

### Variable 2: SENDGRID_API_KEY
- **Name:** `SENDGRID_API_KEY`
- **Value:** `SG.abc123XYZ456def789...` (tu API Key completo de SendGrid)
- Click en **"Add"**

### Variable 3: SENDGRID_FROM_EMAIL
- **Name:** `SENDGRID_FROM_EMAIL`
- **Value:** El email que verificaste en SendGrid
  - Si verificaste email individual: `agencia.viajes.oasis@gmail.com`
  - Si verificaste dominio: `noreply@tudominio.com`
- Click en **"Add"**

---

## ✅ Paso 5: Verificar Variables en Railway

Después de agregar las 3 variables, deberías ver:

```
MAIL_PROVIDER = sendgrid
SENDGRID_API_KEY = SG.abc123XYZ456...
SENDGRID_FROM_EMAIL = agencia.viajes.oasis@gmail.com
```

---

## 🔄 Paso 6: Redeploy en Railway

**MUY IMPORTANTE:** Después de agregar las variables:

1. En Railway, ve a tu servicio (backend)
2. Click en los **3 puntos** (⋯) o el menú
3. Selecciona **"Redeploy"** o **"Redesplegar"**
4. Espera a que termine el despliegue (2-5 minutos)

---

## 🧪 Paso 7: Probar el Envío

1. Prueba enviar un correo desde tu aplicación
2. Revisa los logs de Railway:
   - Deberías ver: `"Usando SendGrid API REST (HTTPS) para envío de correo"`
   - Deberías ver: `"=== CORREO ENVIADO EXITOSAMENTE VÍA SENDGRID ==="`
3. Revisa la bandeja del destinatario

---

## 🔍 Verificar Estado en SendGrid

1. Ve a SendGrid Dashboard
2. En el menú lateral, ve a **"Activity"** o **"Actividad"**
3. Verás todos los correos enviados
4. Puedes ver el estado de cada envío:
   - ✅ **Delivered** = Entregado
   - ⏳ **Processing** = Procesando
   - ❌ **Bounced** = Rebotado
   - ❌ **Blocked** = Bloqueado

---

## ⚠️ Errores Comunes y Soluciones

### Error: "Invalid API Key"
- **Causa:** El API Key está mal copiado o incompleto
- **Solución:** 
  - Verifica que el API Key empiece con `SG.`
  - Copia el API Key completo (es muy largo)
  - Si no lo tienes, crea uno nuevo en SendGrid

### Error: "The from address does not match a verified Sender Identity"
- **Causa:** El email en `SENDGRID_FROM_EMAIL` no está verificado
- **Solución:**
  - Ve a SendGrid → Settings → Sender Authentication
  - Verifica que el email/dominio esté como **✅ Verified**
  - Usa **exactamente** ese email en `SENDGRID_FROM_EMAIL`

### Error: "Forbidden" o "401 Unauthorized"
- **Causa:** El API Key no tiene los permisos correctos
- **Solución:**
  - Ve a SendGrid → Settings → API Keys
  - Verifica que el API Key tenga permisos de **"Mail Send"** o **"Full Access"**
  - Si no, crea uno nuevo con los permisos correctos

### Error: "Connection timeout" o "Couldn't connect"
- **Causa:** Esto NO debería pasar con SendGrid (usa HTTPS)
- **Solución:** 
  - Verifica que `MAIL_PROVIDER=sendgrid` esté configurado
  - Verifica que las variables estén correctas
  - Haz un redeploy

---

## 📝 Checklist Final

Antes de probar, verifica:

- [ ] Cuenta creada en SendGrid
- [ ] API Key creada y copiada (empieza con `SG.`)
- [ ] Email o dominio verificado en SendGrid (estado: ✅ Verified)
- [ ] `MAIL_PROVIDER=sendgrid` configurado en Railway
- [ ] `SENDGRID_API_KEY` configurado en Railway (API Key completo)
- [ ] `SENDGRID_FROM_EMAIL` configurado en Railway (email verificado)
- [ ] Redeploy hecho en Railway
- [ ] Logs de Railway muestran "SendGrid API REST"

---

## 🎯 Ejemplo Completo de Configuración

### En SendGrid:
- **API Key:** `SG.abc123XYZ456def789ghi012jkl345mno678pqr901stu234vwx567yz`
- **Email Verificado:** `agencia.viajes.oasis@gmail.com`

### En Railway (Variables):
```
MAIL_PROVIDER=sendgrid
SENDGRID_API_KEY=SG.abc123XYZ456def789ghi012jkl345mno678pqr901stu234vwx567yz
SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
```

---

## 🆘 ¿Aún no funciona?

1. **Revisa los logs de Railway:**
   - Busca mensajes que empiecen con `"=== INICIO ENVÍO DE CORREO"`
   - Verifica si dice "SendGrid" o "SMTP"
   - Busca errores específicos

2. **Verifica en SendGrid:**
   - Ve a Activity → verifica si los correos aparecen ahí
   - Revisa el estado de cada envío

3. **Verifica las variables:**
   - Asegúrate de que las 3 variables estén configuradas
   - Verifica que no haya espacios extra
   - Verifica que el API Key sea completo

4. **Prueba manualmente:**
   - Puedes probar enviar un correo desde el dashboard de SendGrid
   - Si funciona desde ahí, el problema está en la configuración de Railway

---

## 📞 Soporte

Si después de seguir todos los pasos aún no funciona:
- Revisa la documentación oficial: https://docs.sendgrid.com
- Contacta al soporte de SendGrid
- Revisa los logs detallados en Railway

