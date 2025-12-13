-- ============================================
-- SCRIPT PARA GENERAR HASH DE CONTRASEÑA
-- ============================================
-- Este script NO puede generar BCrypt directamente en PostgreSQL
-- Debes usar una de las siguientes opciones:
--
-- OPCIÓN 1: Usar el backend (RECOMENDADO)
-- 1. Inicia el backend
-- 2. Crea un admin temporal usando la API:
--    POST http://localhost:9999/api/v1/admin/create
--    {
--      "correo": "temp@test.com",
--      "password": "Seguridad@2024Oasis!",
--      "rolId": 3,
--      "idPersona": 1
--    }
-- 3. Consulta el hash:
--    SELECT password FROM admin WHERE correo = 'temp@test.com';
-- 4. Copia el hash y úsalo en datos_completo.sql
-- 5. Elimina el admin temporal:
--    DELETE FROM admin WHERE correo = 'temp@test.com';
--
-- OPCIÓN 2: Usar herramienta online
-- 1. Ve a: https://bcrypt-generator.com/
-- 2. Introduce: Seguridad@2024Oasis!Aqm,24Dla
-- 3. Rounds: 10
-- 4. Copia el hash generado
--
-- OPCIÓN 3: Usar script Java/Python
-- Ver: Oasis_Backend/tests/generar_hash_password.ps1
-- ============================================

-- Contraseña a hashear: Seguridad@2024Oasis!
-- Texto completo con salt: Seguridad@2024Oasis!Aqm,24Dla
-- 
-- Características de la contraseña:
-- ✓ Más de 12 caracteres (16 caracteres)
-- ✓ Incluye mayúsculas (S, O)
-- ✓ Incluye minúsculas (eguridad, asis)
-- ✓ Incluye números (2024)
-- ✓ Incluye caracteres especiales (@, !)
-- ✓ Cumple estándares de seguridad

-- Una vez tengas el hash, actualiza datos_completo.sql en la línea:
-- v_password_hash VARCHAR(255) := '<TU_HASH_AQUI>';

SELECT 'Para generar el hash, sigue las instrucciones en los comentarios de este archivo' as instrucciones;

