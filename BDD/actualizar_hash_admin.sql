-- ============================================
-- SCRIPT PARA ACTUALIZAR HASH DE CONTRASEÑA DEL ADMIN
-- ============================================
-- Usa este script DESPUÉS de ejecutar datos_completo.sql
-- para actualizar el hash de la contraseña con el hash BCrypt correcto
--
-- PASOS:
-- 1. Genera el hash BCrypt de "Seguridad@2024Oasis!Aqm,24Dla"
-- 2. Reemplaza <TU_HASH_BCRYPT_AQUI> con el hash generado
-- 3. Ejecuta este script
-- ============================================

-- ⚠️ REEMPLAZA ESTE HASH CON EL HASH BCrypt REAL
UPDATE admin 
SET password = '<TU_HASH_BCRYPT_AQUI>'
WHERE correo = 'Admin.Seguridad@tuguia.bo';

-- Verificar que se actualizó
SELECT 
    correo,
    SUBSTRING(password, 1, 20) || '...' as hash_preview,
    estadoCuenta,
    rol_idrol
FROM admin 
WHERE correo = 'Admin.Seguridad@tuguia.bo';

-- Mostrar mensaje
DO $$
BEGIN
    RAISE NOTICE '✅ Hash de contraseña actualizado para Admin.Seguridad@tuguia.bo';
    RAISE NOTICE '📧 Correo: Admin.Seguridad@tuguia.bo';
    RAISE NOTICE '🔑 Contraseña: Seguridad@2024Oasis!';
    RAISE NOTICE '';
    RAISE NOTICE 'Ahora puedes iniciar sesión con estas credenciales.';
    RAISE NOTICE 'NOTA: El correo tiene formato @tuguia.bo para que el frontend lo reconozca como admin.';
END $$;

