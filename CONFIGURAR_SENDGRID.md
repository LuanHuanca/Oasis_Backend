# 📧 Configuración de SendGrid para Envío de Correos

## Problema Actual

El error `401: The provided authorization grant is invalid, expired, or revoked` indica que la API key de SendGrid no está configurada correctamente o es inválida.

## Solución: Configurar SendGrid

### Paso 1: Crear una Cuenta en SendGrid

1. Ve a [https://signup.sendgrid.com/](https://signup.sendgrid.com/)
2. Crea una cuenta gratuita (permite enviar hasta 100 correos/día)
3. Verifica tu email

### Paso 2: Verificar el Email Remitente

1. Inicia sesión en [https://app.sendgrid.com/](https://app.sendgrid.com/)
2. Ve a **Settings** → **Sender Authentication**
3. Selecciona **Verify a Single Sender** (para pruebas) o **Domain Authentication** (para producción)
4. Sigue las instrucciones para verificar tu email o dominio

### Paso 3: Crear una API Key

1. En SendGrid, ve a **Settings** → **API Keys**
2. Haz clic en **Create API Key**
3. Configura:
   - **Name**: `Oasis Backend` (o el nombre que prefieras)
   - **API Key Permissions**: Selecciona **Full Access** (o al menos **Mail Send**)
4. Haz clic en **Create & View**
5. **IMPORTANTE**: Copia la API key inmediatamente, ya que solo se muestra una vez
   - Formato: `SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`

### Paso 4: Configurar Variables de Entorno

#### Si estás usando Docker Compose:

Edita el archivo `.env` (o créalo desde `env.example`):

```bash
# Proveedor de correo
MAIL_PROVIDER=sendgrid

# API Key de SendGrid (la que copiaste en el paso 3)
SENDGRID_API_KEY=SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Email remitente (debe estar verificado en SendGrid)
SENDGRID_FROM_EMAIL=tu-email@tudominio.com
```

#### Si estás usando Railway:

1. Ve a tu proyecto en Railway
2. Selecciona el servicio del backend
3. Ve a la pestaña **Variables**
4. Agrega las siguientes variables:

```
MAIL_PROVIDER = sendgrid
SENDGRID_API_KEY = SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
SENDGRID_FROM_EMAIL = tu-email@tudominio.com
```

#### Si estás usando EC2 u otro servidor:

```bash
export MAIL_PROVIDER=sendgrid
export SENDGRID_API_KEY=SG.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
export SENDGRID_FROM_EMAIL=tu-email@tudominio.com
```

O agrégalas al archivo `.env` si usas docker-compose.

### Paso 5: Reiniciar el Servicio

Después de configurar las variables de entorno:

```bash
# Si usas Docker Compose
docker-compose restart backend

# Si usas Railway, las variables se aplican automáticamente al hacer deploy
```

## Verificación

### Probar la Configuración

Puedes usar el endpoint de prueba:

```http
GET /mail/test
```

O enviar un correo de prueba:

```http
POST /mail/send/tu-email@ejemplo.com
Content-Type: application/json

{
  "subject": "Prueba de Correo",
  "message": "Este es un correo de prueba desde Oasis"
}
```

## Troubleshooting

### Error 401: API Key Inválida

- Verifica que la API key esté correctamente copiada (sin espacios)
- Asegúrate de que la API key tenga permisos de "Mail Send"
- Genera una nueva API key si es necesario

### Error 403: Acceso Denegado

- Verifica que el email remitente (`SENDGRID_FROM_EMAIL`) esté verificado en SendGrid
- Asegúrate de que la API key tenga los permisos correctos

### El correo no llega

- Revisa la carpeta de spam
- Verifica que el email destinatario sea válido
- Revisa los logs del backend para más detalles
- En SendGrid, ve a **Activity** para ver el estado de los correos enviados

## Límites de SendGrid

- **Plan Gratuito**: 100 correos/día
- **Plan Essentials**: 40,000 correos/mes
- **Plan Pro**: 100,000 correos/mes

## Seguridad

⚠️ **NUNCA** commits la API key en el código o en repositorios públicos.

- Usa siempre variables de entorno
- No compartas la API key públicamente
- Rota las API keys periódicamente
- Usa diferentes API keys para desarrollo y producción

## Alternativas a SendGrid

Si prefieres usar otro servicio:

1. **Resend** (similar a SendGrid)
2. **Mailgun**
3. **Amazon SES**
4. **SMTP tradicional** (solo para desarrollo local, no funciona en Railway)

Para cambiar el proveedor, modifica `MAIL_PROVIDER` en las variables de entorno.

