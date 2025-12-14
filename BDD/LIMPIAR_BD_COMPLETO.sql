-- ============================================
-- SCRIPT DE LIMPIEZA COMPLETA DE LA BASE DE DATOS
-- ============================================
-- Este script elimina TODAS las tablas, secuencias y datos
-- de la base de datos Oasis.
-- 
-- ⚠️ ADVERTENCIA: Este script eliminará TODO el contenido de la BD
-- ============================================

-- Método más seguro: eliminar todo el esquema y recrearlo
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- Otorgar permisos
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;

-- Mensaje de confirmación
DO $$
BEGIN
    RAISE NOTICE 'Base de datos limpiada completamente. Todas las tablas y datos han sido eliminados.';
    RAISE NOTICE 'El esquema public ha sido recreado. Ahora puedes ejecutar BD_OASIS_COMPLETO_FINAL.sql';
END $$;

