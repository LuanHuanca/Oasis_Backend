# Guía de Despliegue en EC2

Esta guía te ayudará a desplegar Oasis Backend y Frontend en un servidor EC2 usando Docker.

## Requisitos Previos

1. **Instancia EC2** con:
   - Ubuntu 20.04 LTS o superior (recomendado)
   - Mínimo 2GB RAM, 2 vCPU
   - Puerto 22 (SSH) abierto
   - Puertos 80, 443, 9999 (backend) y 3000 (frontend) abiertos en Security Groups

2. **Acceso SSH** a la instancia EC2

## Paso 1: Preparar la Instancia EC2

### 1.1 Conectarse a EC2

```bash
ssh -i tu-clave.pem ubuntu@tu-ip-ec2
```

### 1.2 Instalar Docker y Docker Compose

```bash
# Actualizar el sistema
sudo apt update && sudo apt upgrade -y

# Instalar Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Agregar usuario al grupo docker
sudo usermod -aG docker $USER

# Instalar Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Verificar instalación
docker --version
docker-compose --version

# Reiniciar sesión para aplicar cambios de grupo
exit
# Vuelve a conectarte
```

### 1.3 Instalar Git (si no está instalado)

```bash
sudo apt install git -y
```

## Paso 2: Clonar y Preparar el Proyecto

### 2.1 Clonar el repositorio Backend

```bash
cd ~
git clone <url-de-tu-repositorio-backend> Oasis_Backend
cd Oasis_Backend
```

### 2.2 Configurar variables de entorno

```bash
# Copiar el archivo de ejemplo
cp .env.example .env

# Editar con tus valores reales
nano .env
```

**Importante:** Actualiza los siguientes valores en `.env`:
- `POSTGRES_PASSWORD`: Contraseña segura para PostgreSQL
- `SPRING_MAIL_USERNAME`: Tu email de Gmail
- `SPRING_MAIL_PASSWORD`: Tu App Password de Gmail (no la contraseña normal)
- `GOOGLE_GENERATIVEAI_API_KEY`: Tu API key de Google

### 2.3 Preparar scripts SQL (si existen)

Asegúrate de que los archivos SQL en `BDD/` estén listos:
- `BDD/BD_OASIS.sql`
- `BDD/datos.sql`

## Paso 3: Construir y Ejecutar con Docker Compose

### 3.1 Construir las imágenes

```bash
docker-compose build
```

### 3.2 Iniciar los servicios

```bash
docker-compose up -d
```

### 3.3 Verificar que todo esté funcionando

```bash
# Ver logs
docker-compose logs -f

# Ver estado de los contenedores
docker-compose ps

# Verificar salud del backend
curl http://localhost:9999/actuator/health
```

## Paso 4: Configurar el Frontend

### 4.1 Clonar el repositorio Frontend

```bash
cd ~
git clone <url-de-tu-repositorio-frontend> Oasis_Frontend
cd Oasis_Frontend
```

### 4.2 Crear Dockerfile para Frontend

Necesitarás crear un `Dockerfile` en el repositorio frontend. Dependiendo de tu tecnología:

**Para React/Next.js:**
```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

**Para Vue.js:**
```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### 4.3 Actualizar docker-compose.yml

Edita el `docker-compose.yml` en el backend y descomenta la sección del frontend, ajustando la ruta:

```yaml
frontend:
  build:
    context: ../Oasis_Frontend
    dockerfile: Dockerfile
  container_name: oasis-frontend
  ports:
    - "80:80"
  environment:
    - REACT_APP_API_URL=http://tu-ip-ec2:9999
  depends_on:
    - backend
  networks:
    - oasis-network
  restart: unless-stopped
```

### 4.4 Reconstruir y reiniciar

```bash
cd ~/Oasis_Backend
docker-compose up -d --build
```

## Paso 5: Configurar Nginx como Reverse Proxy (Opcional pero Recomendado)

### 5.1 Instalar Nginx

```bash
sudo apt install nginx -y
```

### 5.2 Crear configuración de Nginx

```bash
sudo nano /etc/nginx/sites-available/oasis
```

Contenido:

```nginx
server {
    listen 80;
    server_name tu-dominio.com www.tu-dominio.com;

    # Frontend
    location / {
        proxy_pass http://localhost:80;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }

    # Backend API
    location /api {
        proxy_pass http://localhost:9999;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_cache_bypass $http_upgrade;
    }
}
```

### 5.3 Habilitar el sitio

```bash
sudo ln -s /etc/nginx/sites-available/oasis /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

## Paso 6: Configurar SSL con Let's Encrypt (Opcional)

```bash
sudo apt install certbot python3-certbot-nginx -y
sudo certbot --nginx -d tu-dominio.com -d www.tu-dominio.com
```

## Comandos Útiles

### Ver logs
```bash
docker-compose logs -f backend
docker-compose logs -f postgres
docker-compose logs -f frontend
```

### Reiniciar servicios
```bash
docker-compose restart backend
docker-compose restart postgres
```

### Detener servicios
```bash
docker-compose down
```

### Detener y eliminar volúmenes (CUIDADO: elimina la base de datos)
```bash
docker-compose down -v
```

### Actualizar el código
```bash
cd ~/Oasis_Backend
git pull
docker-compose up -d --build
```

## Solución de Problemas

### El backend no se conecta a PostgreSQL
- Verifica que PostgreSQL esté saludable: `docker-compose ps`
- Revisa los logs: `docker-compose logs postgres`
- Verifica las variables de entorno en `.env`

### Puerto ya en uso
- Cambia los puertos en `.env` o detén el servicio que está usando el puerto

### Error de permisos
- Asegúrate de que los archivos SQL tengan permisos de lectura: `chmod 644 BDD/*.sql`

### Verificar conectividad
```bash
# Desde dentro del contenedor backend
docker exec -it oasis-backend sh
wget -O- http://postgres:5432
```

## Seguridad

1. **Cambia todas las contraseñas por defecto** en producción
2. **Usa variables de entorno** para información sensible
3. **Configura un firewall** (ufw) en EC2
4. **Habilita SSL/HTTPS** para producción
5. **Mantén actualizado** Docker y las imágenes base

## Monitoreo

Para monitorear los recursos:

```bash
# Uso de recursos
docker stats

# Espacio en disco
df -h
docker system df
```

