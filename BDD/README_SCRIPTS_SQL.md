# 📋 Guía de Scripts SQL - Oasis

## Archivos SQL Disponibles

### 1. `BD_OASIS.sql` - Schema (Estructura)
- **Propósito**: Crea todas las tablas de la base de datos
- **Cuándo usar**: Primera vez que creas la base de datos
- **Orden**: 1️⃣ Primero

### 2. `datos.sql` - Datos Básicos (Original)
- **Propósito**: Inserta datos básicos sin usuarios
- **Cuándo usar**: Si solo necesitas datos de prueba sin usuarios
- **Orden**: 2️⃣ Después de BD_OASIS.sql

### 3. `datos_completo.sql` - Datos Completos + Usuario Admin (NUEVO)
- **Propósito**: Inserta todos los datos Y crea un usuario administrador con rol Seguridad
- **Cuándo usar**: Para inicialización completa del sistema
- **Orden**: 2️⃣ Después de BD_OASIS.sql
- **Incluye**:
  - ✅ Todos los datos básicos
  - ✅ Usuario administrador con rol Seguridad
  - ✅ Permisos asignados
  - ✅ Resumen de datos insertados

## 🚀 Uso Recomendado

### Para Docker (Automático)
Los scripts se ejecutan automáticamente en este orden:
1. `BD_OASIS.sql` → Crea las tablas
2. `datos.sql` → Inserta datos (o `datos_completo.sql` si lo reemplazas)

### Para Uso Manual

```sql
-- 1. Ejecutar schema
\i BD_OASIS.sql

-- 2. Ejecutar datos completos (incluye usuario admin)
\i datos_completo.sql
```

## ⚠️ IMPORTANTE: Hash de Contraseña

El script `datos_completo.sql` crea un usuario admin con:
- **Correo**: `admin.seguridad@oasis.com`
- **Contraseña**: `Seguridad@2024Oasis!` (16 caracteres, cumple estándares)
- **Rol**: Seguridad (ID: 3)
- **Permisos**: Asignar roles

**PERO**: El hash de la contraseña en el script es un **placeholder**. Debes:

1. **Generar el hash BCrypt correcto**:
   - Usa el backend para crear un admin temporal
   - O usa: https://bcrypt-generator.com/
   - Texto a hashear: `Seguridad@2024Oasis!Aqm,24Dla`

2. **Actualizar el hash en `datos_completo.sql`**:
   - Busca la línea: `v_password_hash VARCHAR(255) := '$2a$10$...'`
   - Reemplaza con tu hash generado

3. **O actualizar después de ejecutar el script**:
   ```sql
   UPDATE admin 
   SET password = '<TU_HASH_BCRYPT_AQUI>' 
   WHERE correo = 'admin.seguridad@oasis.com';
   ```

## 📊 Resumen que Muestra el Script

Al finalizar, el script muestra:
- Cantidad de registros insertados por tabla
- Información del usuario administrador creado
- Credenciales de acceso (correo y contraseña en texto plano)

## 🔄 Reemplazar datos.sql en Docker

Si quieres usar `datos_completo.sql` en lugar de `datos.sql`:

1. Edita `docker-compose.yml`:
   ```yaml
   volumes:
     - ${BACKEND_PATH:-./Oasis_Backend}/BDD/BD_OASIS.sql:/docker-entrypoint-initdb.d/01-schema.sql:ro
     - ${BACKEND_PATH:-./Oasis_Backend}/BDD/datos_completo.sql:/docker-entrypoint-initdb.d/02-data.sql:ro
   ```

2. O renombra los archivos:
   ```bash
   mv datos.sql datos_backup.sql
   mv datos_completo.sql datos.sql
   ```

