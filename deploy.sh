#!/bin/bash

# Script de despliegue para Oasis en EC2
# Uso: ./deploy.sh

set -e

echo "🚀 Iniciando despliegue de Oasis..."

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Verificar que Docker está instalado
if ! command -v docker &> /dev/null; then
    echo -e "${RED}❌ Docker no está instalado. Por favor instálalo primero.${NC}"
    exit 1
fi

# Verificar que Docker Compose está instalado
if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}❌ Docker Compose no está instalado. Por favor instálalo primero.${NC}"
    exit 1
fi

# Verificar que existe el archivo .env
if [ ! -f .env ]; then
    echo -e "${YELLOW}⚠️  Archivo .env no encontrado. Creando desde env.example...${NC}"
    if [ -f env.example ]; then
        cp env.example .env
        echo -e "${YELLOW}⚠️  Por favor, edita el archivo .env con tus valores reales antes de continuar.${NC}"
        echo -e "${YELLOW}   Ejecuta: nano .env${NC}"
        exit 1
    else
        echo -e "${RED}❌ Archivo env.example no encontrado.${NC}"
        exit 1
    fi
fi

# Verificar que los archivos SQL existen
if [ ! -f "BDD/BD_OASIS.sql" ]; then
    echo -e "${YELLOW}⚠️  Advertencia: BDD/BD_OASIS.sql no encontrado.${NC}"
fi

if [ ! -f "BDD/datos.sql" ]; then
    echo -e "${YELLOW}⚠️  Advertencia: BDD/datos.sql no encontrado.${NC}"
fi

# Detener contenedores existentes
echo -e "${GREEN}📦 Deteniendo contenedores existentes...${NC}"
docker-compose down

# Construir imágenes
echo -e "${GREEN}🔨 Construyendo imágenes Docker...${NC}"
docker-compose build --no-cache

# Iniciar servicios
echo -e "${GREEN}🚀 Iniciando servicios...${NC}"
docker-compose up -d

# Esperar a que los servicios estén listos
echo -e "${GREEN}⏳ Esperando a que los servicios estén listos...${NC}"
sleep 10

# Verificar estado
echo -e "${GREEN}✅ Verificando estado de los servicios...${NC}"
docker-compose ps

# Verificar salud del backend
echo -e "${GREEN}🏥 Verificando salud del backend...${NC}"
sleep 5
if curl -f http://localhost:9999/actuator/health &> /dev/null; then
    echo -e "${GREEN}✅ Backend está funcionando correctamente${NC}"
else
    echo -e "${YELLOW}⚠️  Backend aún no responde. Revisa los logs con: docker-compose logs backend${NC}"
fi

echo -e "${GREEN}✨ Despliegue completado!${NC}"
echo -e "${GREEN}📊 Para ver los logs: docker-compose logs -f${NC}"
echo -e "${GREEN}🛑 Para detener: docker-compose down${NC}"

