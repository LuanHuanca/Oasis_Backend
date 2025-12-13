# 🐳 Docker - Despliegue en EC2

## Archivos Creados

He creado los siguientes archivos para facilitar el despliegue:

1. **`Dockerfile`** - Imagen Docker para el backend Spring Boot
2. **`docker-compose.yml`** - Orquestación de servicios (Backend + PostgreSQL)
3. **`.dockerignore`** - Archivos a excluir del build
4. **`env.example`** - Plantilla de variables de entorno
5. **`deploy.sh`** - Script automatizado de despliegue
6. **`DEPLOY.md`** - Guía completa de despliegue

## 🚀 Inicio Rápido

### 1. En tu servidor EC2:

```bash
# Clonar el repositorio
git clone <tu-repo> Oasis_Backend
cd Oasis_Backend

# Copiar y editar variables de entorno
cp env.example .env
nano .env  # Edita con tus valores reales

# Ejecutar despliegue
chmod +x deploy.sh
./deploy.sh
```

### 2. Verificar que funciona:

```bash
# Ver logs
docker-compose logs -f

# Verificar estado
docker-compose ps

# Probar backend
curl http://localhost:9999/actuator/health
```

## 📋 Información Necesaria del Frontend

Para completar el despliegue del frontend, necesito:

1. **Tecnología del frontend:**
   - ¿React, Vue, Angular, Next.js, u otro?
   - ¿Versión de Node.js requerida?

2. **Archivos de configuración:**
   - `package.json` o `package-lock.json`
   - Archivo de configuración de build (si existe)
   - Variables de entorno que usa el frontend

3. **Puerto y configuración:**
   - ¿En qué puerto corre el frontend?
   - ¿Necesita alguna configuración especial?

4. **URL del API:**
   - ¿Cómo se configura la URL del backend en el frontend?

## 🔧 Configuración Actual

### Backend
- **Puerto:** 9999
- **Base de datos:** PostgreSQL (puerto 5432)
- **Framework:** Spring Boot 3.2.4
- **Java:** 17

### Variables de Entorno Requeridas

Edita el archivo `.env` con:

```bash
# Base de datos
POSTGRES_PASSWORD=tu-password-seguro

# Email (Gmail)
SPRING_MAIL_USERNAME=tu-email@gmail.com
SPRING_MAIL_PASSWORD=tu-app-password  # No la contraseña normal, sino App Password

# Google AI
GOOGLE_GENERATIVEAI_API_KEY=tu-api-key
```

## 📝 Próximos Pasos

1. **Proporciona información del frontend** para crear su Dockerfile
2. **Actualiza el docker-compose.yml** con el frontend
3. **Configura un dominio** (opcional pero recomendado)
4. **Configura SSL** con Let's Encrypt

## 🆘 Solución de Problemas

Ver `DEPLOY.md` para una guía completa de solución de problemas.

## 📚 Documentación Completa

Consulta `DEPLOY.md` para:
- Instalación paso a paso en EC2
- Configuración de Nginx
- Configuración de SSL
- Comandos útiles
- Troubleshooting detallado

