-- ============================================
-- SCRIPT DE INICIALIZACIÓN COMPLETO - OASIS
-- ============================================
-- Este script inserta todos los datos iniciales y crea un usuario administrador
-- con rol de Seguridad y permisos correspondientes.
--
-- IMPORTANTE: Este script debe ejecutarse DESPUÉS de BD_OASIS.sql (schema)
-- ============================================

-- ============================================
-- 1. DATOS BÁSICOS
-- ============================================

INSERT INTO Pais (pais)
VALUES
    ('Bolivia');

INSERT INTO Ciudad (ciudad, Pais_idPais)
VALUES
    ('La Paz', 1),
    ('Santa Cruz de la Sierra', 1),
    ('Cochabamba', 1),
    ('Sucre', 1),
    ('Oruro', 1),
    ('Potosí', 1),
    ('Tarija', 1),
    ('Beni', 1),
    ('Pando', 1);

INSERT INTO CategoriaHotel (categoria) VALUES 
('Hotel'),
('Hostal'),
('Hotel de lujo'),
('Hotel de negocios'),
('Hotel resort'),
('Hotel Familiar'),
('Alojamiento');

INSERT INTO Hotel (hotel, puntuacion, Ciudad_idCiudad, CategoriaHotel_idCatHot, totalHabitaciones, descripcion, imagenes, ubicacion)
VALUES
    ('Hotel Plaza', 4, 1, 1, 120, 'Un lugar acogedor en el centro de la ciudad.', 'https://plaza.lapazhotelsweb.com/data/Images/1920x1080w/294/29487/29487794/image-la-paz-plaza-hotel-1.JPEG', 'Calle Principal 123, La Paz'),
    ('Hostal del Lago', 3, 2, 2, 150, 'Ideal para mochileros y viajeros de presupuesto ajustado.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0a/c6/c9/10/hostal-del-lago.jpg?w=700&h=-1&s=1', 'Avenida Lago 456, Santa Cruz de la Sierra'),
    ('Hotel Camino Real', 5, 3, 3, 160, 'Lujo y confort en el corazón de la ciudad.', 'https://caminorealaparthotel-spa.com/wp-content/uploads/2019/02/slide_001.jpg', 'Calle Real 789, Cochabamba'),
    ('Hotel Los Tajibos', 4, 4, 4, 100, 'El mejor hotel de negocios con todas las comodidades.', 'https://images.trvl-media.com/lodging/1000000/20000/18800/18758/d1cae807.jpg?impolicy=resizecrop&rw=575&rh=575&ra=fill', 'Avenida Empresarial 101, Sucre'),
    ('Hotel Rosario', 4, 5, 5, 95, 'Resort con vistas espectaculares y servicios exclusivos.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/35666575.jpg?k=0534b7f343da384d651b48f5b195ebf29210cc655adb8e676dda3b9583ec2bb8&o=&hp=1', 'Calle Vista Hermosa 202, Oruro'),
    ('Hotel Las Palmas', 3, 6, 6, 140, 'Ambiente familiar y acogedor para toda la familia.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/07/0b/3c/3f/hotel-las-palmas.jpg?w=700&h=-1&s=1', 'Avenida Familiar 303, Potosí'),
    ('Alojamiento Samay', 5, 7, 7, 130, 'Alojamiento tranquilo y confortable para una estadía perfecta.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/02/47/f2/02/the-hotel.jpg?w=700&h=-1&s=1', 'Calle Tranquila 404, Tarija'),
    ('Hotel La Siesta', 4, 8, 1, 160, 'Un hotel perfecto para descansar y relajarse.', 'https://www.alexandrehotels.com/dms/multiHotel-AlexandreHotels-New/hoteles/alexandre-la-siesta/hero-hotel-siesta-tenerife-alexandre-hotels.jpg', 'Avenida Relax 505, Beni'),
    ('Hostal Maya', 3, 9, 2, 180, 'Hostal económico con excelente servicio.', 'https://hostal-maya-inn.lapazhotelsweb.com/data/Images/OriginalPhoto/7794/779458/779458881/image-la-paz-hostal-maya-inn-2.JPEG', 'Calle Económica 606, Pando'),
    ('Hotel Alcala', 5, 1, 3, 160, 'Lujo y elegancia en cada rincón del hotel.', 'https://imgcy.trivago.com/c_limit,d_dummy.jpeg,f_auto,h_600,q_auto,w_600//hotelier-images/9b/d5/41798dc267d1d63981d14709205ce824cb98c18c91ebe91d89a2d45e668c.jpeg', 'Calle Lujosa 707, La Paz'),
    ('Hotel Regina', 4, 2, 4, 200, 'Hotel de negocios con todas las facilidades modernas.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/336993491.jpg?k=bfff4260541d1ccc41bca1b79a72c70dd12f5475a0afb218c4f1c8533536f6d5&o=&hp=1', 'Avenida Modernidad 808, Santa Cruz de la Sierra'),
    ('Hotel Colonial', 4, 4, 5, 165, 'Resort con estilo colonial y servicios de alta calidad.', 'https://www.letsbookhotel.com/img/max300/504/5046347.jpg', 'Calle Colonial 909, Cochabamba'),
    ('Hostal San Francisco', 3, 4, 6, 170, 'Hostal familiar con ambiente acogedor y seguro.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/be/51/83/photo0jpg.jpg?w=700&h=-1&s=1', 'Avenida Familiar 1010, Sucre'),
    ('Hotel Oporto', 5, 5, 7, 210, 'Alojamiento europeo con todos los servicios necesarios.', 'https://boliviamia.net/Images/Hotelpics/Victoria_Hotel_Oruro_01.jpg', 'Calle Europea 1111, Oruro'),
    ('Hotel Real', 4, 6, 1, 130, 'Hotel céntrico con todas las comodidades.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/102060692.jpg?k=3a3c500707dff48b3719cfeaa682153347a78c74a4b384097651b4d96a4dde72&o=&hp=1', 'Avenida Central 1212, Potosí');

INSERT INTO Aerolinea (aerolinea)
VALUES
    ('Boliviana de Aviacion'),
    ('Amaszonas'),
    ('Latam'),
    ('Iberia'),
    ('Avianca'),
    ('Volaris');

INSERT INTO CategoriaAtraccion (categoria)
VALUES
    ('Parque Nacional'),
    ('Sitio Arqueológico'),
    ('Mirador'),
    ('Cascada'),
    ('Mercado artesanal');

INSERT INTO Atraccion (atraccion, CategoriaAtraccion_idCatAtrac, Ciudad_idCiudad, precio, detalle)
VALUES
    ('Cataratas del Yguazú', 4, 1, 25.00,''),
    ('Ruinas de Tiwanaku', 2, 1, 10.00,''),
    ('Cristo de la Concordia', 3, 3, 5.00,''),
    ('Salar de Uyuni', 1, 6, 30.00,''),
    ('Fortaleza de Samaipata', 2, 2, 15.00,''),
    ('Mercado de las Brujas', 5, 1, 5.00,''),
    ('Catarata de Arco Iris', 4, 7, 20.00,'');

DELETE FROM CategoriaActividad;
ALTER SEQUENCE categoriaactividad_idcatacti_seq RESTART WITH 1;

INSERT INTO CategoriaActividad (categoria) VALUES
('Trekking'),
('Visita a museos'),
('Avistamiento de aves'),
('Ciclismo de montaña'),
('Tour gastronómico'),
('Conciertos');

INSERT INTO Actividad (actividad, Ciudad_idCiudad, CategoriaActividad_idCatActi, fecha, precio, detalle)
VALUES
    ('Ascenso al Huayna Potosí', 1, 1, '2024-05-10', 40.00, 'Horario: 12:00pm'),
    ('Visita al Museo de la Coca', 1, 2, '2024-05-23', 15.00, 'Horario: 12:00pm'),
    ('Avistamiento de aves en el Jardín Botánico', 3, 3, '2024-06-07', 5.00, 'Horario: 12:00pm'),
    ('Ciclismo de montaña en la Ruta de las Yungas', 1, 4, '2024-06-21', 20.00, 'Horario: 12:00pm'),
    ('Tour gastronómico por la calle Jaén', 1, 5, '2024-05-02', 10.00, 'Horario: 12:00pm'),
    ('Concierto en el Estadio Hernando Siles', 1, 6, '2024-04-28', 30.00, 'Horario: 12:00pm'),
    ('Recital en el Teatro Municipal de Santa Cruz', 2, 6, '2024-06-17', 25.00, 'Horario: 12:00pm');

INSERT INTO Auto (modelo, marca, tipo)
VALUES
    ('Civic', 'Honda', 'Sedán'),
    ('Corolla', 'Toyota', 'Sedán'),
    ('Accord', 'Honda', 'Sedán'),
    ('Camry', 'Toyota', 'Sedán'),
    ('Mustang', 'Ford', 'Deportivo'),
    ('Camaro', 'Chevrolet', 'Deportivo'),
    ('F-150', 'Ford', 'Camioneta'),
    ('Ranger', 'Ford', 'Camioneta'),
    ('Wrangler', 'Jeep', 'SUV'),
    ('RAV4', 'Toyota', 'SUV'),
    ('CR-V', 'Honda', 'SUV');

INSERT INTO Seguro (seguro, cobertura, precio)
VALUES
    ('Seguro Básico', 'Atención médica básica', 50.00),
    ('Seguro Estándar', 'Atención médica y repatriación', 75.00),
    ('Seguro Premium', 'Atención médica y pérdida de equipaje', 100.00),
    ('Seguro de Viaje Aventura', 'Atención médica especializada', 120.00),
    ('Seguro Familiar', 'Cobertura para toda la familia', 150.00);

INSERT INTO FormaPago (formapago)
VALUES
    ('Efectivo'),
    ('Tarjeta de crédito'),
    ('Tarjeta de débito'),
    ('Transferencia bancaria'),
    ('Pago en línea');

-- ============================================
-- 2. ROLES Y PERMISOS
-- ============================================

INSERT INTO Rol (rol) VALUES
('Gerente'),          -- ID: 1
('Tecnología'),       -- ID: 2
('Seguridad'),        -- ID: 3
('Contador'),         -- ID: 4
('Auditor'),          -- ID: 5
('Pasante TI'),       -- ID: 6
('Agente de viajes'), -- ID: 7
('Usuario');          -- ID: 8

INSERT INTO Permiso (permiso) VALUES
('Editar usuario'),              -- ID: 1
('Desactivar usuario'),          -- ID: 2
('Ver lista de usuarios'),       -- ID: 3
('Asignar roles'),               -- ID: 4
('Revisar accesos de red'),      -- ID: 5
('Subir documentos internos'),   -- ID: 6
('Editar documentos internos'),  -- ID: 7
('Ver documentos internos'),     -- ID: 8
('Registrar cuentas'),           -- ID: 9
('Editar cuentas'),              -- ID: 10
('Eliminar comprobantes'),       -- ID: 11
('Ver reportes contables'),      -- ID: 12
('Crear solicitud de viaje');    -- ID: 13

-- Asignación de permisos por rol
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
-- ROL 1: GERENTE
(1, 2), (1, 3), (1, 5), (1, 12),
-- ROL 2: TECNOLOGÍA
(2, 1), (2, 2), (2, 3), (2, 5),
-- ROL 3: SEGURIDAD (Asignar roles)
(3, 4),
-- ROL 4: CONTADOR
(4, 9), (4, 10), (4, 11), (4, 12),
-- ROL 5: AUDITOR
(5, 6), (5, 7), (5, 8),
-- ROL 6: PASANTE TI
(6, 8),
-- ROL 7: AGENTE DE VIAJES
(7, 13);

-- ============================================
-- 3. NOTA SOBRE CREACIÓN DE ADMINISTRADORES
-- ============================================
-- 
-- IMPORTANTE: Los administradores NO se crean directamente en SQL.
-- Deben crearse usando el endpoint del backend: POST /api/v1/admin/create
-- 
-- El backend automáticamente:
-- 1. Genera el correo: nombre.apellidoP@tuguia.bo
-- 2. Genera la contraseña: nombre + apellidoP[0] + telefono
-- 3. Hashea la contraseña con BCrypt + salt "Aqm,24Dla"
-- 4. Asigna permisos según el rol
--
-- Para crear admins, usa el script PowerShell: crear-admins-prueba.ps1
-- Este script crea múltiples admins con diferentes roles usando los endpoints.
--
-- ============================================

-- ============================================
-- 4. RESUMEN DE DATOS INSERTADOS
-- ============================================

DO $$
DECLARE
    v_paises INT;
    v_ciudades INT;
    v_hoteles INT;
    v_aerolineas INT;
    v_autos INT;
    v_roles INT;
    v_permisos INT;
    v_admins INT;
    v_admin_correo VARCHAR(255);
    v_admin_password_plain VARCHAR(255) := 'Seguridad@2024Oasis!';
BEGIN
    -- Contar registros
    SELECT COUNT(*) INTO v_paises FROM Pais;
    SELECT COUNT(*) INTO v_ciudades FROM Ciudad;
    SELECT COUNT(*) INTO v_hoteles FROM Hotel;
    SELECT COUNT(*) INTO v_aerolineas FROM Aerolinea;
    SELECT COUNT(*) INTO v_autos FROM Auto;
    SELECT COUNT(*) INTO v_roles FROM Rol;
    SELECT COUNT(*) INTO v_permisos FROM Permiso;
    SELECT COUNT(*) INTO v_admins FROM admin;
    
    -- Obtener correo del admin de seguridad
    SELECT correo INTO v_admin_correo 
    FROM admin 
    WHERE correo LIKE '%@tuguia.bo' AND rol_idrol = 3
    ORDER BY idadmin DESC 
    LIMIT 1;
    
    -- Mostrar resumen
    RAISE NOTICE '';
    RAISE NOTICE '╔════════════════════════════════════════════════════════════╗';
    RAISE NOTICE '║          RESUMEN DE INICIALIZACIÓN - OASIS                  ║';
    RAISE NOTICE '╚════════════════════════════════════════════════════════════╝';
    RAISE NOTICE '';
    RAISE NOTICE '📊 DATOS INSERTADOS:';
    RAISE NOTICE '   • Países:              %', v_paises;
    RAISE NOTICE '   • Ciudades:            %', v_ciudades;
    RAISE NOTICE '   • Hoteles:             %', v_hoteles;
    RAISE NOTICE '   • Aerolíneas:          %', v_aerolineas;
    RAISE NOTICE '   • Autos:               %', v_autos;
    RAISE NOTICE '   • Roles:               %', v_roles;
    RAISE NOTICE '   • Permisos:            %', v_permisos;
    RAISE NOTICE '';
    
        IF v_admins > 0 THEN
            RAISE NOTICE '👤 USUARIOS ADMINISTRADORES EXISTENTES:';
            RAISE NOTICE '   • Total de admins:   %', v_admins;
            RAISE NOTICE '';
            RAISE NOTICE '⚠️  IMPORTANTE:';
            RAISE NOTICE '   Los administradores deben crearse usando el endpoint del backend:';
            RAISE NOTICE '   POST /api/v1/admin/create';
            RAISE NOTICE '';
            RAISE NOTICE '   El backend automáticamente:';
            RAISE NOTICE '   1. Genera el correo: nombre.apellidoP@tuguia.bo';
            RAISE NOTICE '   2. Genera la contraseña: nombre + apellidoP[0] + telefono';
            RAISE NOTICE '   3. Hashea la contraseña con BCrypt + salt "Aqm,24Dla"';
            RAISE NOTICE '   4. Asigna permisos según el rol';
            RAISE NOTICE '';
            RAISE NOTICE '   Usa el script: crear-admins-prueba.ps1';
            RAISE NOTICE '';
        ELSE
            RAISE NOTICE '⚠️  No se encontraron usuarios administradores';
            RAISE NOTICE '   Crea admins usando el endpoint del backend o el script PowerShell';
            RAISE NOTICE '';
        END IF;
    
    RAISE NOTICE '╔════════════════════════════════════════════════════════════╗';
    RAISE NOTICE '║              INICIALIZACIÓN COMPLETADA                    ║';
    RAISE NOTICE '╚════════════════════════════════════════════════════════════╝';
    RAISE NOTICE '';
END $$;

-- Mostrar datos insertados en tablas principales
SELECT 'Paises' as tabla, COUNT(*) as registros FROM Pais
UNION ALL
SELECT 'Ciudades', COUNT(*) FROM Ciudad
UNION ALL
SELECT 'Hoteles', COUNT(*) FROM Hotel
UNION ALL
SELECT 'Aerolineas', COUNT(*) FROM Aerolinea
UNION ALL
SELECT 'Autos', COUNT(*) FROM Auto
UNION ALL
SELECT 'Roles', COUNT(*) FROM Rol
UNION ALL
SELECT 'Permisos', COUNT(*) FROM Permiso
UNION ALL
SELECT 'Admins', COUNT(*) FROM admin
ORDER BY tabla;

-- Mostrar información de todos los admins existentes
SELECT 
    a.idadmin as "ID Admin",
    a.correo as "Correo",
    r.rol as "Rol",
    p.Nombre || ' ' || p.ApellidoP || ' ' || p.ApellidoM as "Nombre Completo",
    p.Telefono as "Teléfono",
    a.estadoCuenta as "Cuenta Activa"
FROM admin a
JOIN Rol r ON a.rol_idrol = r.idrol
JOIN Persona p ON a.persona_idpersona = p.idPersona
WHERE a.correo LIKE '%@tuguia.bo'
ORDER BY a.idadmin DESC;

