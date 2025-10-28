# 📊 Matriz de Permisos - Sistema Oasis

## 🎯 Roles del Sistema

| ID | Rol | Descripción |
|----|-----|-------------|
| 1 | Gerente | Supervisión general y reportes |
| 2 | Tecnología | Gestión de usuarios y accesos técnicos |
| 3 | Seguridad | Asignación de roles y control de accesos |
| 4 | Contador | Gestión contable y financiera |
| 5 | Auditor | Gestión de documentos internos |
| 6 | Pasante TI | Consulta de documentos (limitado) |
| 7 | Usuario | Gestión básica de usuarios |
| 8 | Agente de viajes | Creación de solicitudes de viaje |

---

## 🔐 Permisos del Sistema

| ID | Permiso | Descripción |
|----|---------|-------------|
| 1 | Editar usuario | Modificar información de usuarios |
| 2 | Desactivar usuario | Desactivar cuentas de usuario |
| 3 | Ver lista de usuarios | Consultar listado de usuarios |
| 4 | Asignar roles | Asignar y modificar roles |
| 5 | Revisar accesos de red | Auditar accesos al sistema |
| 6 | Subir documentos internos | Cargar documentos al sistema |
| 7 | Editar documentos internos | Modificar documentos existentes |
| 8 | Ver documentos internos | Consultar documentos |
| 9 | Registrar cuentas | Crear nuevas cuentas contables |
| 10 | Editar cuentas | Modificar cuentas existentes |
| 11 | Eliminar comprobantes | Dar de baja comprobantes |
| 12 | Ver reportes contables | Consultar reportes financieros |
| 13 | Crear solicitud de viaje | Generar solicitudes de viaje |

---

## 📋 Matriz de Permisos por Rol

| Permiso | Gerente | Tecnología | Seguridad | Contador | Auditor | Pasante TI | Usuario | Agente de viajes |
|---------|---------|------------|-----------|----------|---------|------------|---------|------------------|
| **Editar usuario** | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| **Desactivar usuario** | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| **Ver lista de usuarios** | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| **Asignar roles** | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| **Revisar accesos de red** | ✅ | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| **Subir documentos internos** | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ |
| **Editar documentos internos** | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ |
| **Ver documentos internos** | ❌ | ❌ | ❌ | ❌ | ✅ | ✅ | ❌ | ❌ |
| **Registrar cuentas** | ❌ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ |
| **Editar cuentas** | ❌ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ |
| **Eliminar comprobantes** | ❌ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ |
| **Ver reportes contables** | ✅ | ❌ | ❌ | ✅ | ❌ | ❌ | ❌ | ❌ |
| **Crear solicitud de viaje** | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ | ✅ |

---

## 📝 Detalle de Permisos por Rol

### 1️⃣ **GERENTE** (4 permisos)
```
✅ Desactivar usuario
✅ Ver lista de usuarios
✅ Revisar accesos de red
✅ Ver reportes contables
```
**Perfil:** Supervisión general, control de usuarios y acceso a reportes financieros.

---

### 2️⃣ **TECNOLOGÍA** (4 permisos)
```
✅ Editar usuario
✅ Desactivar usuario
✅ Ver lista de usuarios
✅ Revisar accesos de red
```
**Perfil:** Gestión completa de usuarios y auditoría de accesos técnicos.

---

### 3️⃣ **SEGURIDAD** (2 permisos)
```
✅ Asignar roles
✅ Revisar accesos de red
```
**Perfil:** Control de roles y seguridad de accesos.

---

### 4️⃣ **CONTADOR** (4 permisos)
```
✅ Registrar cuentas
✅ Editar cuentas
✅ Eliminar comprobantes
✅ Ver reportes contables
```
**Perfil:** Gestión completa del área contable y financiera.

---

### 5️⃣ **AUDITOR** (3 permisos)
```
✅ Subir documentos internos
✅ Editar documentos internos
✅ Ver documentos internos
```
**Perfil:** Gestión completa de documentación interna.

---

### 6️⃣ **PASANTE TI** (1 permiso)
```
✅ Ver documentos internos
```
**Perfil:** Acceso de solo lectura a documentos, rol de aprendizaje.

---

### 7️⃣ **USUARIO** (3 permisos)
```
✅ Editar usuario
✅ Desactivar usuario
✅ Ver lista de usuarios
```
**Perfil:** Gestión básica de usuarios del sistema.

---

### 8️⃣ **AGENTE DE VIAJES** (1 permiso)
```
✅ Crear solicitud de viaje
```
**Perfil:** Especializado en gestión de viajes corporativos.

---

## 🔄 Cómo se Implementa

### **Permisos de Rol (Predeterminados)**
Los permisos listados arriba son **automáticos** cuando se asigna un rol a un administrador.

**Ejemplo:**
```sql
-- Un admin con rol "Gerente" automáticamente tiene:
SELECT * FROM RolPermiso WHERE rol_idrol = 1;
-- Resultado:
-- (1, 2)  - Desactivar usuario
-- (1, 3)  - Ver lista de usuarios
-- (1, 5)  - Revisar accesos de red
-- (1, 12) - Ver reportes contables
```

### **Permisos Adicionales**
Puedes agregar permisos extra a un admin específico sin afectar a otros del mismo rol:

```sql
-- Agregar permiso adicional a un admin específico
INSERT INTO AdminPermiso (admin_idAdmin, permiso_idPermiso, tipoPermiso, activo)
VALUES (5, 1, 'ADICIONAL', true);
-- Ahora el admin 5 (Gerente) también puede "Editar usuario"
```

### **Permisos Temporales**
Puedes dar permisos temporales con fecha de expiración:

```sql
-- Dar permiso temporal por 7 días
INSERT INTO PermisoTemporal (admin_idAdmin, permiso_idPermiso, fechaInicio, fechaFin, motivo)
VALUES (
    5, 
    4, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP + INTERVAL '7 days',
    'Reemplazo de personal de seguridad'
);
```

---

## 🎯 Uso en el Frontend

### **Verificar Permiso de un Admin**
```javascript
GET /api/v1/gestion-permisos/admin/{adminId}/tiene-permiso/{permisoId}

// Respuesta:
{
  "code": "200-OK",
  "result": {
    "tienePermiso": true,
    "tipoPermiso": "ROL",  // o "ADICIONAL" o "TEMPORAL"
    "origen": "Gerente"
  }
}
```

### **Obtener Todos los Permisos Efectivos**
```javascript
GET /api/v1/gestion-permisos/admin/{adminId}/permisos-efectivos

// Respuesta incluye permisos de ROL + ADICIONALES + TEMPORALES activos
```

---

## 📊 Resumen de Conteo

| Rol | Cantidad de Permisos |
|-----|---------------------|
| Gerente | 4 |
| Tecnología | 4 |
| Seguridad | 2 |
| Contador | 4 |
| Auditor | 3 |
| Pasante TI | 1 |
| Usuario | 3 |
| Agente de viajes | 1 |

**Total de Permisos Únicos:** 13

---

## 🚀 Comandos Útiles

### **Insertar Roles y Permisos:**
```bash
psql -U postgres -d oasis -f BDD/datos.sql
```

### **Verificar Permisos de un Rol:**
```sql
-- Ver permisos del rol "Gerente"
SELECT p.permiso 
FROM RolPermiso rp
JOIN Permiso p ON rp.permiso_idpermiso = p.idPermiso
WHERE rp.rol_idrol = 1;
```

### **Ver Permisos Efectivos de un Admin:**
```sql
-- Admin con ID 5
SELECT DISTINCT p.permiso, ap.tipoPermiso
FROM AdminPermiso ap
JOIN Permiso p ON ap.permiso_idPermiso = p.idPermiso
WHERE ap.admin_idAdmin = 5 AND ap.activo = true;
```

---

✅ **Sistema de Permisos Completo y Listo para Usar**

