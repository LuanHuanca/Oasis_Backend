# 📧 Guía de Configuración de Correo en Railway

## ⚠️ Problema
Railway **bloquea todas las conexiones SMTP salientes** (puertos 25, 587, 465) para prevenir spam. 
**Por lo tanto, Gmail SMTP NO funciona en Railway**, ni ningún otro servicio SMTP tradicional.

## ✅ Solución: SendGrid API REST (HTTPS)
La solución es usar **SendGrid API REST** que funciona sobre **HTTPS (puerto 443)**, que Railway **NO bloquea**.

### ✅ SendGrid (RECOMENDADO)

SendGrid usa **API REST (HTTPS)** en lugar de SMTP, por lo que funciona perfectamente en Railway.

#### Paso 1: Crear cuenta en SendGrid
1. Ve a https://sendgrid.com
2. Click en **"Start for free"** o **"Comenzar gratis"**
3. Completa el registro y verifica tu email
4. Completa la verificación de identidad (puede pedir teléfono)

#### Paso 2: Crear API Key
1. En el dashboard de SendGrid, ve a **Settings** → **API Keys**
2. Click en **Create API Key**
3. Nombre: `Railway Oasis`
4. Permisos: **Full Access** o **Mail Send**
5. Click en **Create & View**
6. **⚠️ MUY IMPORTANTE:** Copia el API Key **INMEDIATAMENTE**
   - El API Key empieza con `SG.` seguido de muchos caracteres
   - Ejemplo: `SG.abc123XYZ456def789...`
   - **NO podrás verlo de nuevo** después de cerrar la ventana

#### Paso 3: Verificar Sender Identity (Email o Dominio)
**IMPORTANTE:** Tienes 2 opciones:

**OPCIÓN A: Verificar un EMAIL Individual (MÁS FÁCIL)**
1. En SendGrid, ve a **Settings** → **Sender Authentication**
2. Click en **Verify a Single Sender**
3. Completa el formulario con tu email (ej: `agencia.viajes.oasis@gmail.com`)
4. SendGrid te enviará un correo de verificación
5. Click en el enlace del correo para verificar
6. ✅ Listo! Usa ese email en `SENDGRID_FROM_EMAIL`

**OPCIÓN B: Verificar un DOMINIO Propio (Más profesional)**
1. En SendGrid, ve a **Settings** → **Sender Authentication**
2. Click en **Authenticate Your Domain**
3. Selecciona tu proveedor de DNS
4. Agrega los registros DNS que SendGrid te proporciona
5. Espera 5-30 minutos y verifica en SendGrid
6. ✅ Listo! Usa `noreply@tudominio.com` en `SENDGRID_FROM_EMAIL`

**💡 RECOMENDACIÓN:** Para empezar rápido, usa la OPCIÓN A (email individual).

#### Paso 4: Configurar en Railway
1. Ve a tu proyecto en Railway
2. Abre la pestaña **Variables**
3. Agrega estas variables:

```
MAIL_PROVIDER=sendgrid
SENDGRID_API_KEY=SG.abc123XYZ456def789...
SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
```

**⚠️ IMPORTANTE:** 
- `SENDGRID_FROM_EMAIL` debe ser **exactamente** el email que verificaste en SendGrid
- Si verificaste `agencia.viajes.oasis@gmail.com`, usa ese mismo email
- Si verificaste un dominio `tuguia.bo`, puedes usar `noreply@tuguia.bo`
- **NO uses** el dominio de Railway (como `railway.app`)

#### Paso 5: Redeploy
Después de agregar las variables, haz un **redeploy** del servicio en Railway.

**📖 Ver guía detallada:** `SENDGRID_SETUP_GUIA.md`

---

### ⚠️ SMTP (NO funciona en Railway - Solo para desarrollo local)

**NO USAR SMTP EN RAILWAY** - Railway bloquea todos los puertos SMTP.

Solo funciona para desarrollo local:

```
MAIL_PROVIDER=smtp
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=tu-email@gmail.com
SPRING_MAIL_PASSWORD=<APP_PASSWORD>
```

---

## Configuración de Variables en Railway

### Cómo agregar variables:
1. Ve a tu proyecto en Railway
2. Click en **Variables** (en el menú lateral)
3. Click en **+ New Variable**
4. Agrega cada variable una por una
5. **IMPORTANTE:** Después de agregar las variables, debes hacer un **redeploy** del servicio

### Variables necesarias (para SendGrid):
```
MAIL_PROVIDER=sendgrid
SENDGRID_API_KEY=SG.abc123XYZ456def789...
SENDGRID_FROM_EMAIL=agencia.viajes.oasis@gmail.com
```

**Nota:** 
- `SENDGRID_API_KEY` empieza con `SG.` y es muy largo
- `SENDGRID_FROM_EMAIL` debe ser el email que verificaste en SendGrid

---

## Verificación

Después de configurar:
1. Haz un redeploy en Railway
2. Revisa los logs para ver si la conexión SMTP es exitosa
3. Prueba enviando un correo desde tu aplicación

---

## Troubleshooting

### Error: "Connection timed out"
- **Causa:** Railway bloquea el puerto SMTP
- **Solución:** Usa SendGrid, Resend o Mailgun (no Gmail)

### Error: "Authentication failed"
- **Causa:** Credenciales incorrectas
- **Solución:** Verifica que las variables de entorno estén correctas

### Error: "Sender not verified"
- **Causa:** El remitente no está verificado en SendGrid
- **Solución:** Verifica el dominio o email en SendGrid

---

## Recomendación Final

**Usa SendGrid** porque:
- ✅ Funciona perfectamente en Railway (usa HTTPS, no SMTP)
- ✅ 100 correos/día gratis
- ✅ Muy estable y confiable
- ✅ Excelente entregabilidad
- ✅ Ampliamente usado y bien documentado
- ✅ No requiere abrir puertos (usa puerto 443 HTTPS)

**¿Por qué funciona?**
- SendGrid usa **API REST** sobre **HTTPS (puerto 443)**
- Railway **NO bloquea HTTPS**, solo bloquea SMTP (puertos 25, 587, 465)
- Por eso SendGrid funciona mientras que Gmail SMTP no

**📖 Guía detallada:** Ver `SENDGRID_SETUP_GUIA.md` para instrucciones paso a paso

