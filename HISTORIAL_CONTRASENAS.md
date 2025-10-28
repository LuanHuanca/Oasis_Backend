# Sistema de Historial de Contraseñas - Oasis Backend

## Descripción
Este sistema implementa un historial de contraseñas para tanto clientes como administradores, permitiendo:
- Cambio seguro de contraseñas
- Validación contra contraseñas anteriores (últimas 5)
- Historial completo de cambios de contraseña
- Prevención de reutilización de contraseñas

## Estructura de la Base de Datos

### Tabla HistorialContrasena
```sql
CREATE TABLE HistorialContrasena (
    idHistorial serial NOT NULL,
    idCliente int NULL,
    idAdmin int NULL,
    contrasena_hash varchar(255) NOT NULL,
    fecha_cambio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT HistorialContrasena_pk PRIMARY KEY (idHistorial),
    CONSTRAINT HistorialContrasena_Cliente_fk FOREIGN KEY (idCliente)
        REFERENCES Cliente (idCliente)
        ON DELETE CASCADE,
    CONSTRAINT HistorialContrasena_Admin_fk FOREIGN KEY (idAdmin)
        REFERENCES admin (idadmin)
        ON DELETE CASCADE,
    CONSTRAINT HistorialContrasena_check_user CHECK (
        (idCliente IS NOT NULL AND idAdmin IS NULL) OR 
        (idCliente IS NULL AND idAdmin IS NOT NULL)
    )
);
```

**Características importantes:**
- Solo puede tener referencia a un cliente O un admin (no ambos)
- Se elimina automáticamente cuando se elimina el usuario
- Almacena el hash de la contraseña, no la contraseña en texto plano

## Endpoints Disponibles

### Para Clientes

#### Cambiar Contraseña
```
PUT /api/v1/cliente/{id}/password
Content-Type: application/json

{
    "password": "nuevaContrasena123"
}
```

#### Validar Contraseña Actual
```
POST /api/v1/cliente/{id}/validate-password
Content-Type: application/json

{
    "password": "contraseñaActual"
}
```

#### Obtener Historial de Contraseñas
```
GET /api/v1/historial-contrasena/cliente/{idCliente}
```

### Para Administradores

#### Cambiar Contraseña
```
PUT /api/v1/admin/{id}/password
Content-Type: application/json

{
    "password": "nuevaContrasenaAdmin123"
}
```

#### Validar Contraseña Actual
```
POST /api/v1/admin/{id}/validate-password
Content-Type: application/json

{
    "password": "contraseñaActual"
}
```

#### Obtener Historial de Contraseñas
```
GET /api/v1/historial-contrasena/admin/{idAdmin}
```

## Reglas de Negocio

### Validaciones Implementadas
1. **No puede ser igual a la contraseña actual**: Se verifica que la nueva contraseña no sea igual a la actual
2. **No puede reutilizar las últimas 5 contraseñas**: Se verifica contra el historial de las últimas 5 contraseñas
3. **Salt personalizado**: Se aplica el salt "Aqm,24Dla" a todas las contraseñas antes del hashing
4. **Hashing seguro**: Se utiliza BCrypt para el hashing de contraseñas

### Flujo de Cambio de Contraseña
1. Validar que la nueva contraseña no sea igual a la actual
2. Verificar contra el historial (últimas 5 contraseñas)
3. Guardar la contraseña actual en el historial
4. Hashear la nueva contraseña con salt
5. Actualizar la contraseña en la base de datos

## Servicios Principales

### HistorialContrasenaService
- `findHistoryForCliente(Long idCliente)`: Obtiene historial de un cliente
- `findHistoryForAdmin(Long idAdmin)`: Obtiene historial de un admin
- `saveHistory(Long idCliente, String passwordHash)`: Guarda historial para cliente
- `saveHistoryForAdmin(Long idAdmin, String passwordHash)`: Guarda historial para admin
- `isPasswordUsedByCliente(Long idCliente, String saltedPassword, int checkLast)`: Valida reutilización para cliente
- `isPasswordUsedByAdmin(Long idAdmin, String saltedPassword, int checkLast)`: Valida reutilización para admin

### ClienteBl
- `updatePassword(Long id, String newPasswordRaw)`: Cambia contraseña de cliente
- `validateCurrentPassword(Long id, String currentPassword)`: Valida contraseña actual de cliente

### AdminBl
- `updatePassword(Long id, String newPasswordRaw)`: Cambia contraseña de admin
- `validateCurrentPassword(Long id, String currentPassword)`: Valida contraseña actual de admin

## Instalación y Configuración

### 1. Regenerar la Base de Datos
```bash
# Ejecutar el script de regeneración
psql -U postgres -f BDD/regenerate_database.sql
```

### 2. Verificar la Instalación
```bash
# Compilar el proyecto
./gradlew build

# Ejecutar las pruebas
./gradlew test
```

### 3. Probar los Endpoints
Usar el archivo `http/HistorialContrasena.http` para probar los endpoints.

## Seguridad

### Medidas Implementadas
- **Salt personalizado**: Previene ataques de diccionario
- **BCrypt**: Algoritmo de hashing seguro y lento
- **Historial limitado**: Solo se verifican las últimas 5 contraseñas
- **Validación de entrada**: Se valida que las contraseñas no estén vacías
- **Logging**: Se registran todos los cambios de contraseña

### Recomendaciones Adicionales
- Implementar políticas de complejidad de contraseñas
- Agregar límites de intentos de cambio de contraseña
- Implementar notificaciones por email cuando se cambie la contraseña
- Considerar implementar autenticación de dos factores

## Manejo de Errores

### Códigos de Error Comunes
- `TASK-1000`: Error general del sistema
- `TASK-1001`: Usuario no encontrado
- `TASK-1002`: Contraseña vacía o inválida

### Mensajes de Error Específicos
- "La nueva contraseña no puede ser igual a la actual"
- "La nueva contraseña ya fue usada anteriormente. Elija otra."
- "Administrador no existe"
- "Cliente no existe"

## Testing

### Casos de Prueba Recomendados
1. Cambio exitoso de contraseña
2. Intento de usar contraseña actual
3. Intento de reutilizar contraseña anterior
4. Validación de contraseña actual correcta
5. Validación de contraseña actual incorrecta
6. Obtener historial de contraseñas
7. Manejo de errores con IDs inexistentes
