
INSERT INTO Pais (pais)
VALUES
    ('Bolivia');

INSERT INTO Ciudad (ciudad, Pais_idPais)
VALUES
    ('La Paz', 1),  -- Departamento: La Paz
    ('Santa Cruz de la Sierra', 1),  -- Departamento: Santa Cruz
    ('Cochabamba', 1),  -- Departamento: Cochabamba
    ('Sucre', 1),  -- Departamento: Chuquisaca
    ('Oruro', 1),  -- Departamento: Oruro
    ('Potosí', 1),  -- Departamento: Potosí
    ('Tarija', 1),  -- Departamento: Tarija
    ('Beni', 1),  -- Departamento: Beni
    ('Pando', 1);  -- Departamento: Pando

INSERT INTO CategoriaHotel (categoria) VALUES ('Hotel');
INSERT INTO CategoriaHotel (categoria) VALUES ('Hostal');
INSERT INTO CategoriaHotel (categoria) VALUES ('Hotel de lujo');
INSERT INTO CategoriaHotel (categoria) VALUES ('Hotel de negocios');
INSERT INTO CategoriaHotel (categoria) VALUES ('Hotel resort');
INSERT INTO CategoriaHotel (categoria) VALUES ('Hotel Familiar');
INSERT INTO CategoriaHotel (categoria) VALUES ('Alojamiento');

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


select * from Hotel;

INSERT INTO Aerolinea (aerolinea)
VALUES
    ('Boliviana de Aviacion'),
    ('Amaszonas'),
    ('Latam'),
    ('Iberia'),
    ('Avianca'),
    ('Volaris');

select * from aerolinea;


INSERT INTO CategoriaAtraccion (categoria)
VALUES
    ('Parque Nacional'),
    ('Sitio Arqueológico'),
    ('Mirador'),
    ('Cascada'),
    ('Mercado artesanal');


INSERT INTO Atraccion (atraccion, CategoriaAtraccion_idCatAtrac, Ciudad_idCiudad, precio, detalle)
VALUES
    ('Cataratas del Yguazú', 4, 1, 25.00,''),  -- Ciudad: La Paz, Categoría: Cascada
    ('Ruinas de Tiwanaku', 2, 1, 10.00,''),  -- Ciudad: La Paz, Categoría: Sitio Arqueológico
    ('Cristo de la Concordia', 3, 3, 5.00,''),  -- Ciudad: Cochabamba, Categoría: Mirador
    ('Salar de Uyuni', 1, 6, 30.00,''),  -- Ciudad: Potosí, Categoría: Parque Nacional
    ('Fortaleza de Samaipata', 2, 2, 15.00,''),  -- Ciudad: Santa Cruz de la Sierra, Categoría: Sitio Arqueológico
    ('Mercado de las Brujas', 5, 1, 5.00,''),  -- Ciudad: La Paz, Categoría: Mercado artesanal
    ('Catarata de Arco Iris', 4, 7, 20.00,'');  -- Ciudad: Tarija, Categoría: Cascada

select * from atraccion;

-- Eliminar todos los datos de la tabla Categoria Actividades
DELETE FROM CategoriaActividad;

-- Reiniciar la secuencia asociada a la columna idCiudad
ALTER SEQUENCE categoriaactividad_idcatacti_seq RESTART WITH 1;

-- Insertar categorías de actividad
INSERT INTO CategoriaActividad (categoria) VALUES
                                               ('Trekking'),
                                               ('Visita a museos'),
                                               ('Avistamiento de aves'),
                                               ('Ciclismo de montaña'),
                                               ('Tour gastronómico'),
                                               ('Conciertos');

-- Insertar actividades
INSERT INTO Actividad (actividad, Ciudad_idCiudad, CategoriaActividad_idCatActi, fecha, precio, detalle)
VALUES
    ('Ascenso al Huayna Potosí', 1, 1, '2024-05-10', 40.00, 'Horario: 12:00pm'),  -- Ciudad: La Paz
    ('Visita al Museo de la Coca', 1, 2, '2024-05-23', 15.00, 'Horario: 12:00pm'),  -- Ciudad: La Paz
    ('Avistamiento de aves en el Jardín Botánico', 3, 3, '2024-06-07', 5.00, 'Horario: 12:00pm'),  -- Ciudad: Cochabamba
    ('Ciclismo de montaña en la Ruta de las Yungas', 1, 4, '2024-06-21', 20.00, 'Horario: 12:00pm'),  -- Ciudad: La Paz
    ('Tour gastronómico por la calle Jaén', 1, 5, '2024-05-02', 10.00, 'Horario: 12:00pm'),  -- Ciudad: La Paz
    ('Concierto en el Estadio Hernando Siles', 1, 6, '2024-04-28', 30.00, 'Horario: 12:00pm'),  -- Ciudad: La Paz
    ('Recital en el Teatro Municipal de Santa Cruz', 2, 6, '2024-06-17', 25.00, 'Horario: 12:00pm');  -- Ciudad: Santa Cruz de la Sierra

select * from Actividad;

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

select * from auto;

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
	
INSERT INTO Hotel (hotel, puntuacion, Ciudad_idCiudad, CategoriaHotel_idCatHot, totalHabitaciones, descripcion, imagenes, ubicacion) VALUES
('Hotel Camino Real', 4, 2, 1, 150, 'Un hotel de lujo con todas las comodidades en el centro de Santa Cruz.', 'https://image-tc.galaxy.tf/wijpeg-b0wd83ji9cej534w24trnh898/unknown.jpg?width=1920', 'Santa Cruz de la Sierra, Bolivia'),
('Los Tajibos Hotel & Convention Center', 5, 2, 2, 200, 'El mejor hotel para convenciones y eventos en Santa Cruz.', 'https://lostajiboshotel.com/sites/default/files/2022-06/lobby.jpg', 'Santa Cruz de la Sierra, Bolivia'),
('Hotel Presidente', 3, 1, 1, 100, 'Hotel moderno en el corazón de La Paz.', 'https://www.family-travel-secrets.com/wp-content/uploads/2016/05/IMG_3589-1024x682.jpg', 'La Paz, Bolivia'),
('Gran Hotel Cochabamba', 4, 3, 2, 120, 'Lujoso hotel con vistas panorámicas de la ciudad.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/215675119.jpg?k=d1fe39a797e02f5bcab0d6a9b591c5c75e90fe4751fc7065c18ca0978468be1b&o=&hp=1', 'Cochabamba, Bolivia'),
('Hotel Parador Santa Maria La Real', 4, 4, 1, 80, 'Un hotel histórico en el centro de Sucre.', 'https://z.cdrst.com/foto/hotel-sf/1260a/granderesp/foto-hotel-1242d7a0.jpg', 'Sucre, Bolivia'),
('Hotel Eden', 3, 5, 1, 75, 'Confortable hotel en el centro de Oruro.', 'https://www.hoteledenbolivia.com/assets/images/home-1.jpg', 'Oruro, Bolivia'),
('Hostal Colonial Potosí', 2, 6, 1, 60, 'Acogedor hostal en el casco antiguo de Potosí.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/306345707.jpg?k=6d61b7cc208ea4396c2950ed8f303f4c2a85bc6180b2717d550812155f18a9fa&o=&hp=1', 'Potosí, Bolivia'),
('Hotel Los Ceibos', 3, 7, 1, 85, 'Cómodo hotel en el centro de Tarija.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/21/0d/fd/f0/vista-de-habitacion-al.jpg?w=700&h=-1&s=1', 'Tarija, Bolivia'),
('Hotel El Pantanal', 3, 8, 1, 70, 'Hotel con encanto en la región del Beni.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/336413792.jpg?k=3f90b5ba07e58b07da9714e9c0f89858975bc5348c10f163e1f45667429feb39&o=&hp=1', 'Beni, Bolivia'),
('Hotel Casa Grande', 4, 1, 1, 90, 'Moderno hotel en la zona sur de La Paz.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/16198551.jpg?k=dc2b965b142b452c2db33e48dd741cf02264273bcc5d2fd2c9b90e18a60734c1&o=&hp=1', 'La Paz, Bolivia'),
('Hotel LP Santa Cruz', 3, 2, 1, 110, 'Hotel práctico y moderno en Santa Cruz.', 'https://lphoteles.com/wp-content/uploads/2022/10/Fachada-Hotel-LP-Santa-Cruz.jpg', 'Santa Cruz de la Sierra, Bolivia'),
('Hotel Diplomat', 4, 3, 2, 100, 'Elegante hotel en el centro de Cochabamba.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/203896151.jpg?k=d30afdd8120084c68720d1a6143ed84f09cf4a690663be8a0e3e1b30319a7069&o=&hp=1', 'Cochabamba, Bolivia'),
('Hostal Sucre', 2, 4, 1, 45, 'Acogedor hostal en la ciudad blanca de Sucre.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/09/43/4c/5d/hostal-sucre.jpg?w=700&h=-1&s=1', 'Sucre, Bolivia'),
('Hotel Virgen del Socavón', 3, 5, 1, 65, 'Hotel cerca del famoso santuario de Oruro.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/164472585.jpg?k=0b761c7f499d1abf22591bafe9c82a79e986f27f9ef5ebd765ed0d7e710b7bff&o=&hp=1', 'Oruro, Bolivia'),
('Hotel Cima Argentum', 3, 6, 1, 80, 'Hotel con vistas impresionantes en Potosí.', 'https://cf.bstatic.com/xdata/images/hotel/max1024x768/38209154.jpg?k=9876270653a7d390e6f655272d7b56515324a0081376dcea47e6121dccaba02d&o=&hp=1', 'Potosí, Bolivia'),
('Hotel Victoria Plaza', 3, 7, 1, 70, 'Hotel céntrico en la ciudad de Tarija.', 'https://cf.bstatic.com/xdata/images/hotel/max500/125168201.jpg?k=dbd0cf55f89e3cf2a8ba06b166c50a71e9ce567ef19677ec5a63bef6bbc670c8&o=&hp=1', 'Tarija, Bolivia'),
('Hotel Oriental', 2, 8, 1, 60, 'Hotel básico y cómodo en Beni.', 'https://media-cdn.tripadvisor.com/media/photo-s/06/b1/ae/eb/hotel-oriental.jpg', 'Beni, Bolivia'),
('Hotel Gran Paititi', 3, 9, 1, 50, 'Hotel en la zona selvática de Pando.', 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/04/4c/0c/5d/paititi-hostal.jpg?w=700&h=-1&s=1', 'Pando, Bolivia');

INSERT INTO Aerolinea (aerolinea) VALUES
('Boliviana de Aviación'),
('AmasZonas'),
('Ecojet'),
('AirFrance KLM'),
('Star Peru'),
('GOL Linhas Aéreas Inteligentes');

INSERT INTO Auto (modelo, marca, tipo) VALUES
('Qashqai', 'Nissan', 'SUV'),
('Kicks', 'Nissan', 'SUV'),
('Frontier', 'Nissan', 'Camioneta (Pickup)'),
('X-Trail', 'Nissan', 'SUV'),
('Versa', 'Nissan', 'Sedán'),
('March', 'Nissan', 'Hatchback'),
('Pathfinder', 'Nissan', 'SUV'),
('Navara', 'Nissan', 'Camioneta (Pickup)'),
('Murano', 'Nissan', 'SUV'),
('Sentra', 'Nissan', 'Sedán'),
('Altima', 'Nissan', 'Sedán'),
('Maxima', 'Nissan', 'Sedán'),

('EcoSport', 'Ford', 'SUV'),
('Ranger', 'Ford', 'Camioneta (Pickup)'),
('Explorer', 'Ford', 'SUV'),
('Escape', 'Ford', 'SUV'),
('Mustang', 'Ford', 'Coupé'),
('F-150', 'Ford', 'Camioneta (Pickup)'),
('Focus', 'Ford', 'Sedán'),
('Edge', 'Ford', 'SUV'),
('Territory', 'Ford', 'SUV'),
('Kuga', 'Ford', 'SUV'),
('Mondeo', 'Ford', 'Sedán'),
('Fiesta', 'Ford', 'Hatchback'),

('Hilux', 'Toyota', 'Camioneta (Pickup)'),
('Corolla', 'Toyota', 'Sedán'),
('RAV4', 'Toyota', 'SUV'),
('Fortuner', 'Toyota', 'SUV'),
('Land Cruiser', 'Toyota', 'SUV'),
('Yaris', 'Toyota', 'Hatchback'),
('Prado', 'Toyota', 'SUV'),
('Avanza', 'Toyota', 'Minivan'),
('Tacoma', 'Toyota', 'Camioneta (Pickup)'),
('SWA', 'Toyota', 'SUV'),
('Etios', 'Toyota', 'Sedán'),
('Camry', 'Toyota', 'Sedán'),
('Land Cruiser Prado', 'Toyota', 'SUV'),

('Tracker', 'Chevrolet', 'SUV'),
('Spark', 'Chevrolet', 'Hatchback'),
('Onix', 'Chevrolet', 'Hatchback/Sedán'),
('Sail', 'Chevrolet', 'Sedán'),
('Colorado', 'Chevrolet', 'Camioneta (Pickup)'),
('Equinox', 'Chevrolet', 'SUV'),
('Captiva', 'Chevrolet', 'SUV'),
('Tahoe', 'Chevrolet', 'SUV'),
('Cruze', 'Chevrolet', 'Sedán'),
('Aveo', 'Chevrolet', 'Sedán'),
('Cavalier', 'Chevrolet', 'Sedán'),
('Trax', 'Chevrolet', 'SUV'),
('S10', 'Chevrolet', 'Camioneta (Pickup)'),

('Gol', 'Volkswagen', 'Hatchback'),
('T-Cross', 'Volkswagen', 'SUV'),
('Nivus', 'Volkswagen', 'SUV'),
('Virtus', 'Volkswagen', 'Sedán'),
('Saveiro', 'Volkswagen', 'Camioneta (Pickup)'),
('Amarok', 'Volkswagen', 'Camioneta (Pickup)'),
('Jetta', 'Volkswagen', 'Sedán'),
('Tiguan', 'Volkswagen', 'SUV'),
('Polo', 'Volkswagen', 'Hatchback'),
('Teramont', 'Volkswagen', 'SUV'),
('Vento', 'Volkswagen', 'Sedán'),
('Golf', 'Volkswagen', 'Hatchback'),
('Passat', 'Volkswagen', 'Sedán'),

('Strada', 'Fiat', 'Camioneta (Pickup)'),
('Toro', 'Fiat', 'Camioneta (Pickup)'),
('Argo', 'Fiat', 'Hatchback'),
('Cronos', 'Fiat', 'Sedán'),
('Mobi', 'Fiat', 'Hatchback'),
('Uno', 'Fiat', 'Hatchback'),
('Fiorino', 'Fiat', 'Furgoneta'),
('Pulse', 'Fiat', 'SUV'),
('Doblo', 'Fiat', 'Minivan'),

('Duster', 'Renault', 'SUV'),
('Logan', 'Renault', 'Sedán'),
('Sandero', 'Renault', 'Hatchback'),
('Stepway', 'Renault', 'Crossover'),
('Captur', 'Renault', 'SUV'),
('Alaskan', 'Renault', 'Camioneta (Pickup)'),
('Koleos', 'Renault', 'SUV'),
('Kwid', 'Renault', 'Hatchback'),
('Kangoo', 'Renault', 'Furgoneta'),

('Creta', 'Hyundai', 'SUV'),
('HB20', 'Hyundai', 'Hatchback'),
('Tucson', 'Hyundai', 'SUV'),
('Santa Fe', 'Hyundai', 'SUV'),
('Elantra', 'Hyundai', 'Sedán'),
('Sonata', 'Hyundai', 'Sedán'),
('Accent', 'Hyundai', 'Sedán'),
('i30', 'Hyundai', 'Hatchback'),
('Venue', 'Hyundai', 'SUV'),
('Kona', 'Hyundai', 'SUV'),

('Sportage', 'Kia', 'SUV'),
('Rio', 'Kia', 'Hatchback'),
('Forte', 'Kia', 'Sedán'),
('Seltos', 'Kia', 'SUV'),
('Sorento', 'Kia', 'SUV'),
('Soul', 'Kia', 'Hatchback'),
('Optima', 'Kia', 'Sedán'),
('Stinger', 'Kia', 'Sedán'),
('Telluride', 'Kia', 'SUV'),

('CX-5', 'Mazda', 'SUV'),
('Mazda 3', 'Mazda', 'Sedán'),
('Mazda 2', 'Mazda', 'Hatchback'),
('BT-50', 'Mazda', 'Camioneta (Pickup)'),
('CX-9', 'Mazda', 'SUV'),
('Mazda 6', 'Mazda', 'Sedán'),
('MX-5', 'Mazda', 'Roadster'),
('CX-3', 'Mazda', 'SUV');

-- Insertar datos en la tabla Rol
INSERT INTO Rol (rol) VALUES
('Gerente'),          -- ID: 1
('Tecnología'),       -- ID: 2
('Seguridad'),        -- ID: 3
('Contador'),         -- ID: 4
('Auditor'),          -- ID: 5
('Pasante TI'),       -- ID: 6
('Usuario'),          -- ID: 7
('Agente de viajes'); -- ID: 8
-- Insertar datos en la tabla Permiso
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

-- ============================================
-- ASIGNACIÓN DE PERMISOS POR ROL
-- ============================================
-- Basado en la matriz de permisos del sistema

-- ============================================
-- ROL 1: GERENTE
-- ============================================
-- Permisos: Desactivar usuario, Ver lista usuarios, Revisar accesos red, Ver reportes contables
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(1, 2),  -- Desactivar usuario
(1, 3),  -- Ver lista de usuarios
(1, 5),  -- Revisar accesos de red
(1, 12); -- Ver reportes contables

-- ============================================
-- ROL 2: TECNOLOGÍA
-- ============================================
-- Permisos: Editar usuario, Desactivar usuario, Ver lista usuarios, Revisar accesos red
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(2, 1),  -- Editar usuario
(2, 2),  -- Desactivar usuario
(2, 3),  -- Ver lista de usuarios
(2, 5);  -- Revisar accesos de red

-- ============================================
-- ROL 3: SEGURIDAD
-- ============================================
-- Permisos: Asignar roles, Revisar accesos red
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(3, 4),  -- Asignar roles
(3, 5);  -- Revisar accesos de red

-- ============================================
-- ROL 4: CONTADOR
-- ============================================
-- Permisos: Registrar cuentas, Editar cuentas, Eliminar comprobantes, Ver reportes contables
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(4, 9),   -- Registrar cuentas
(4, 10),  -- Editar cuentas
(4, 11),  -- Eliminar comprobantes
(4, 12);  -- Ver reportes contables

-- ============================================
-- ROL 5: AUDITOR
-- ============================================
-- Permisos: Subir documentos internos, Editar documentos internos, Ver documentos internos
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(5, 6),  -- Subir documentos internos
(5, 7),  -- Editar documentos internos
(5, 8);  -- Ver documentos internos

-- ============================================
-- ROL 6: PASANTE TI
-- ============================================
-- Permisos: Ver documentos internos
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(6, 8);  -- Ver documentos internos

-- ============================================
-- ROL 7: USUARIO
-- ============================================
-- Permisos: Editar usuario, Desactivar usuario, Ver lista usuarios
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(7, 1),  -- Editar usuario
(7, 2),  -- Desactivar usuario
(7, 3);  -- Ver lista de usuarios

-- ============================================
-- ROL 8: AGENTE DE VIAJES
-- ============================================
-- Permisos: Crear solicitud de viaje
INSERT INTO RolPermiso (rol_idrol, permiso_idpermiso) VALUES
(8, 13); -- Crear solicitud de viaje