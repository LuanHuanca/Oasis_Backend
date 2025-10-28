# Ejemplos Prácticos de Uso del Sistema de Contraseñas

## ¿Por qué dos endpoints diferentes?

### 1. `/password` - Cambiar contraseña
**Propósito**: Cambiar la contraseña actual por una nueva
**Cuándo usar**: Cuando el usuario quiere actualizar su contraseña

### 2. `/validate-password` - Validar contraseña actual  
**Propósito**: Verificar que la contraseña ingresada es correcta
**Cuándo usar**: Para confirmar la identidad antes de hacer cambios sensibles

## Ejemplos desde el Frontend

### Ejemplo 1: Cambio Simple de Contraseña (Cliente)
```javascript
// Frontend: Usuario quiere cambiar su contraseña
async function cambiarContrasenaCliente(clienteId, nuevaContrasena) {
    try {
        const response = await fetch(`/api/v1/cliente/${clienteId}/password`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password: nuevaContrasena
            })
        });
        
        const result = await response.json();
        
        if (result.success) {
            alert('Contraseña cambiada exitosamente');
        } else {
            alert('Error: ' + result.message);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Uso:
cambiarContrasenaCliente(1, "miNuevaContrasena123");
```

### Ejemplo 2: Flujo Completo con Validación (Cliente)
```javascript
// Frontend: Cliente quiere cambiar contraseña con validación previa
async function cambiarContrasenaClienteConValidacion(clienteId, contrasenaActual, nuevaContrasena) {
    try {
        // Paso 1: Validar contraseña actual
        const validateResponse = await fetch(`/api/v1/cliente/${clienteId}/validate-password`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password: contrasenaActual
            })
        });
        
        const validateResult = await validateResponse.json();
        
        if (!validateResult.data) {
            alert('La contraseña actual es incorrecta');
            return;
        }
        
        // Paso 2: Si la validación es exitosa, cambiar la contraseña
        const changeResponse = await fetch(`/api/v1/cliente/${clienteId}/password`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password: nuevaContrasena
            })
        });
        
        const changeResult = await changeResponse.json();
        
        if (changeResult.success) {
            alert('Contraseña cambiada exitosamente');
        } else {
            alert('Error: ' + changeResult.message);
        }
        
    } catch (error) {
        console.error('Error:', error);
    }
}

// Uso:
cambiarContrasenaClienteConValidacion(1, "miContrasenaActual", "miNuevaContrasena");
```

### Ejemplo 3: Flujo Completo con Validación (Admin)
```javascript
// Frontend: Admin quiere cambiar contraseña con validación previa
async function cambiarContrasenaAdminConValidacion(adminId, contrasenaActual, nuevaContrasena) {
    try {
        // Paso 1: Validar contraseña actual
        const validateResponse = await fetch(`/api/v1/admin/${adminId}/validate-password`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password: contrasenaActual
            })
        });
        
        const validateResult = await validateResponse.json();
        
        if (!validateResult.data) {
            alert('La contraseña actual es incorrecta');
            return;
        }
        
        // Paso 2: Si la validación es exitosa, cambiar la contraseña
        const changeResponse = await fetch(`/api/v1/admin/${adminId}/password`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password: nuevaContrasena
            })
        });
        
        const changeResult = await changeResponse.json();
        
        if (changeResult.success) {
            alert('Contraseña cambiada exitosamente');
        } else {
            alert('Error: ' + changeResult.message);
        }
        
    } catch (error) {
        console.error('Error:', error);
    }
}

// Uso:
cambiarContrasenaAdminConValidacion(1, "miContrasenaActual", "miNuevaContrasena");
```

### Ejemplo 3: Obtener Historial de Contraseñas
```javascript
// Frontend: Ver historial de cambios de contraseña
async function obtenerHistorialContrasenas(tipoUsuario, usuarioId) {
    try {
        const endpoint = tipoUsuario === 'cliente' 
            ? `/api/v1/historial-contrasena/cliente/${usuarioId}`
            : `/api/v1/historial-contrasena/admin/${usuarioId}`;
            
        const response = await fetch(endpoint);
        const result = await response.json();
        
        if (result.success) {
            console.log('Historial de contraseñas:', result.data);
            // Mostrar en la interfaz
            mostrarHistorialEnUI(result.data);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Uso:
obtenerHistorialContrasenas('admin', 1);
obtenerHistorialContrasenas('cliente', 1);
```

## Casos de Uso Reales

### Caso 1: Usuario olvidó su contraseña
```javascript
// Solo necesitas el endpoint de cambio directo
cambiarContrasenaCliente(1, "nuevaContrasenaSegura");
```

### Caso 2: Usuario quiere cambiar contraseña por seguridad
```javascript
// Usar validación previa para mayor seguridad
cambiarContrasenaAdminConValidacion(1, "contraseñaActual", "nuevaContrasenaSegura");
```

### Caso 3: Cliente quiere verificar identidad antes de cambios sensibles
```javascript
// Solo validar sin cambiar
async function verificarIdentidadCliente(clienteId, contrasena) {
    const response = await fetch(`/api/v1/cliente/${clienteId}/validate-password`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ password: contrasena })
    });
    
    const result = await response.json();
    return result.data; // true si es correcta, false si no
}
```

### Caso 4: Administrador quiere verificar identidad antes de cambios sensibles
```javascript
// Solo validar sin cambiar
async function verificarIdentidadAdmin(adminId, contrasena) {
    const response = await fetch(`/api/v1/admin/${adminId}/validate-password`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ password: contrasena })
    });
    
    const result = await response.json();
    return result.data; // true si es correcta, false si no
}
```

## Ventajas del DTO vs Map

### Antes (con Map):
```java
@RequestBody Map<String, String> body
String password = body.get("password"); // Puede ser null
```

### Ahora (con DTO):
```java
@RequestBody PasswordChangeDTO passwordChangeDTO
String password = passwordChangeDTO.getPassword(); // Más claro y tipado
```

**Beneficios del DTO:**
- ✅ **Tipado fuerte**: El IDE te ayuda con autocompletado
- ✅ **Validación automática**: Spring valida automáticamente
- ✅ **Documentación**: Es más claro qué campos espera el endpoint
- ✅ **Mantenibilidad**: Si necesitas agregar más campos, es más fácil

## Respuestas del Servidor

### Cambio exitoso:
```json
{
    "success": true,
    "data": {
        "idAdmin": 1,
        "correo": "admin@tuguia.bo",
        "password": null, // No se devuelve la contraseña por seguridad
        "rol": {...}
    },
    "message": null
}
```

### Error de validación:
```json
{
    "success": false,
    "data": null,
    "message": "La nueva contraseña ya fue usada anteriormente. Elija otra."
}
```

### Validación de contraseña:
```json
{
    "success": true,
    "data": true, // true = contraseña correcta, false = incorrecta
    "message": null
}
```
