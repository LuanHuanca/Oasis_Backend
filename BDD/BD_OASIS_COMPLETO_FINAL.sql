-- ============================================
-- SCRIPT COMPLETO DE BASE DE DATOS OASIS
-- ============================================
-- Este script crea el esquema completo y llena todos los datos iniciales
-- Incluye: tablas, relaciones, roles, permisos y datos de ejemplo
-- a
-- IMPORTANTE: Ejecutar este script en una base de datos limpia
-- ============================================

-- ============================================
-- PARTE 1: CREACIÓN DE TABLAS (ESQUEMA)
-- ============================================

-- Table: Actividad
CREATE TABLE IF NOT EXISTS Actividad (
    idActividad serial NOT NULL,
    actividad varchar(45) NOT NULL,
    Ciudad_idCiudad int NOT NULL,
    CategoriaActividad_idCatActi int NOT NULL,
    fecha date NOT NULL,
    precio decimal(7,2) NOT NULL,
    Detalle varchar(255) NOT NULL,
    CONSTRAINT Actividad_pk PRIMARY KEY (idActividad)
);

-- Table: Aerolinea
CREATE TABLE IF NOT EXISTS Aerolinea (
    idAerolinea serial NOT NULL,
    aerolinea varchar(45) NOT NULL, 
    CONSTRAINT Aerolinea_pk PRIMARY KEY (idAerolinea)
);

-- Table: AlquilerAuto
CREATE TABLE IF NOT EXISTS AlquilerAuto (
    idAlquiler serial NOT NULL,
    precio decimal(7,2) NOT NULL,
    dias int NOT NULL,
    empresa varchar(65) NOT NULL,
    Auto_idAuto int NOT NULL,
    Ciudad_idCiudad int NOT NULL,
    CONSTRAINT AlquilerAuto_pk PRIMARY KEY (idAlquiler)
);

-- Table: Atraccion
CREATE TABLE IF NOT EXISTS Atraccion (
    idAtraccion serial NOT NULL,
    atraccion varchar(45) NOT NULL,
    CategoriaAtraccion_idCatAtrac int NOT NULL,
    Ciudad_idCiudad int NOT NULL,
    precio decimal(7,2) NOT NULL,
    detalle varchar(255) NOT NULL,
    CONSTRAINT Atraccion_pk PRIMARY KEY (idAtraccion)
);

-- Table: Auditoria
CREATE TABLE IF NOT EXISTS Auditoria (
    idAudit serial NOT NULL,
    actividad varchar(250) NOT NULL,
    fecha date NOT NULL,
    hora time NOT NULL,
    fechaInicio timestamp NULL,
    fechaFin timestamp NULL,
    ip varchar(50) NOT NULL,
    admin_idAdmin int NULL,
    Cliente_idCliente int NULL,
    CONSTRAINT Auditoria_pk PRIMARY KEY (idAudit)
);

-- Table: Auto
CREATE TABLE IF NOT EXISTS Auto (
    idAuto serial NOT NULL,
    modelo varchar(45) NOT NULL,
    marca varchar(45) NOT NULL,
    tipo varchar(45) NOT NULL,
    CONSTRAINT Auto_pk PRIMARY KEY (idAuto)
);

-- Table: CategoriaActividad
CREATE TABLE IF NOT EXISTS CategoriaActividad (
    idCatActi serial NOT NULL,
    categoria varchar(45) NOT NULL,
    CONSTRAINT CategoriaActividad_pk PRIMARY KEY (idCatActi)
);

-- Table: CategoriaAtraccion
CREATE TABLE IF NOT EXISTS CategoriaAtraccion (
    idCatAtrac serial NOT NULL,
    categoria varchar(45) NOT NULL,
    CONSTRAINT CategoriaAtraccion_pk PRIMARY KEY (idCatAtrac)
);

-- Table: CategoriaHotel
CREATE TABLE IF NOT EXISTS CategoriaHotel (
    idCatHot serial NOT NULL,
    categoria varchar(45) NOT NULL,
    CONSTRAINT CategoriaHotel_pk PRIMARY KEY (idCatHot)
);

-- Table: Ciudad
CREATE TABLE IF NOT EXISTS Ciudad (
    idCiudad serial NOT NULL,
    ciudad varchar(45) NOT NULL,
    Pais_idPais int NOT NULL,
    CONSTRAINT Ciudad_pk PRIMARY KEY (idCiudad)
);

-- Table: Cliente
CREATE TABLE IF NOT EXISTS Cliente (
    idCliente serial NOT NULL,
    correo varchar(45) NOT NULL,
    password varchar(255) NOT NULL,
    estadoCuenta boolean NOT NULL DEFAULT true,
    intentosFallidos int NOT NULL DEFAULT 0,
    fechaBloqueo timestamp NULL,
    motivoBloqueo varchar(255) NULL,
    Persona_idPersona int NOT NULL,
    CONSTRAINT Cliente_pk PRIMARY KEY (idCliente)
);

-- Table: Comentarios
CREATE TABLE IF NOT EXISTS Comentarios (
    idComentario serial NOT NULL,
    Comentario text NOT NULL,
    Cliente_idCliente int NOT NULL,
    CONSTRAINT Comentarios_pk PRIMARY KEY (idComentario)
);

-- Table: Facturacion
CREATE TABLE IF NOT EXISTS Facturacion (
    idFactura serial NOT NULL,
    NIT varchar(45) NOT NULL,
    fecha date NOT NULL,
    Cliente_idCliente int NOT NULL,
    ReservaViaje_idReservaViaja int NOT NULL,
    FormaPago_idFormP int NOT NULL,
    CONSTRAINT Facturacion_pk PRIMARY KEY (idFactura)
);

-- Table: FormaPago
CREATE TABLE IF NOT EXISTS FormaPago (
    idFormP serial NOT NULL,
    formapago varchar(45) NOT NULL,
    CONSTRAINT FormaPago_pk PRIMARY KEY (idFormP)
);

-- Table: Hotel
CREATE TABLE IF NOT EXISTS Hotel (
    idHotel serial NOT NULL,
    hotel varchar(255) NOT NULL,
    puntuacion int NOT NULL,
    Ciudad_idCiudad int NOT NULL,
    CategoriaHotel_idCatHot int NOT NULL,
    totalHabitaciones int NOT NULL,
    descripcion varchar(255) NOT NULL,
    imagenes text NOT NULL,
    ubicacion varchar(255) NOT NULL,
    CONSTRAINT Hotel_pk PRIMARY KEY (idHotel)
);

-- Table: Pais
CREATE TABLE IF NOT EXISTS Pais (
    idPais serial NOT NULL,
    pais varchar(55) NOT NULL,
    CONSTRAINT Pais_pk PRIMARY KEY (idPais)
);

-- Table: Persona
CREATE TABLE IF NOT EXISTS Persona (
    idPersona serial NOT NULL,
    Nombre varchar(45) NOT NULL,
    ApellidoP varchar(45) NOT NULL,
    ApellidoM varchar(45) NOT NULL,
    Telefono varchar(45) NOT NULL,
    CONSTRAINT Persona_pk PRIMARY KEY (idPersona)
);

-- Table: ReservaHotel
CREATE TABLE IF NOT EXISTS ReservaHotel (
    idReservaHotel serial NOT NULL,
    fechaInicio date NOT NULL,
    fechaFin date NOT NULL,
    precio decimal(7,2) NOT NULL,
    personas int NOT NULL,
    Hotel_idHotel int NOT NULL,
    NroReservaHotel varchar(45) NOT NULL,
    habitaciones int NOT NULL,
    Detalle varchar(255) NOT NULL,
    CONSTRAINT ReservaHotel_pk PRIMARY KEY (idReservaHotel)
);

-- Table: ReservaViaje
CREATE TABLE IF NOT EXISTS ReservaViaje (
    idReservaViaja serial NOT NULL,
    fecha date NOT NULL,
    Cliente_idCliente int NOT NULL,
    Viaje_idViaje int NOT NULL,
    Seguro_idSeguro int NOT NULL,
    AlquilerAuto_idAlquiler int NOT NULL,
    Atraccion_idAtraccion int NOT NULL,
    Actividad_idActividad int NOT NULL,
    ReservaHotel_idReservaHotel int NOT NULL,
    CONSTRAINT ReservaViaje_pk PRIMARY KEY (idReservaViaja)
);

-- Table: Seguro
CREATE TABLE IF NOT EXISTS Seguro (
    idSeguro serial NOT NULL,
    seguro varchar(45) NOT NULL,
    cobertura varchar(45) NOT NULL,
    precio decimal(7,2) NOT NULL,
    CONSTRAINT Seguro_pk PRIMARY KEY (idSeguro)
);

-- Table: Vuelo
CREATE TABLE IF NOT EXISTS Vuelo (
    idViaje serial NOT NULL,
    origen int NOT NULL,
    destino int NOT NULL,
    Aerolinea_idAerolinea int NOT NULL,
    fechaInicio date NOT NULL,
    fechaFin date NOT NULL,
    precio decimal(7,2) NOT NULL,
    personas int NOT NULL,
    nroReserva varchar(45) NOT NULL,
    CONSTRAINT Vuelo_pk PRIMARY KEY (idViaje)
);

-- Table: Rol
CREATE TABLE IF NOT EXISTS Rol (
    idRol serial NOT NULL,
    rol varchar(45) NOT NULL,
    CONSTRAINT Rol_pk PRIMARY KEY (idRol)
);

-- Table: Permiso
CREATE TABLE IF NOT EXISTS Permiso (
    idPermiso serial NOT NULL,
    permiso varchar(45) NOT NULL,
    CONSTRAINT Permiso_pk PRIMARY KEY (idPermiso)
);

-- Table: admin
CREATE TABLE IF NOT EXISTS admin (
    idadmin serial NOT NULL,
    correo varchar(45) NOT NULL,
    password varchar(255) NOT NULL,
    estadoCuenta boolean NOT NULL DEFAULT true,
    intentosFallidos int NOT NULL DEFAULT 0,
    fechaBloqueo timestamp NULL,
    motivoBloqueo varchar(255) NULL,
    rol_idrol int NOT NULL,
    persona_idpersona int NOT NULL,
    CONSTRAINT admin_pk PRIMARY KEY (idadmin)
);

-- Table: AdminPermiso
CREATE TABLE IF NOT EXISTS AdminPermiso (
    idAdminPermiso serial NOT NULL,
    admin_idAdmin int NOT NULL,
    permiso_idPermiso int NOT NULL,
    tipoPermiso varchar(20) NOT NULL DEFAULT 'ADICIONAL',
    fechaAsignacion timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo boolean NOT NULL DEFAULT true,
    CONSTRAINT AdminPermiso_pk PRIMARY KEY (idAdminPermiso),
    CONSTRAINT AdminPermiso_tipo_check CHECK (tipoPermiso IN ('ROL', 'ADICIONAL', 'TEMPORAL'))
);

-- Table: HistorialContrasena
CREATE TABLE IF NOT EXISTS HistorialContrasena (
    idHistorial serial NOT NULL,
    idCliente int NULL,
    idAdmin int NULL,
    contrasena_hash varchar(255) NOT NULL,
    fecha_cambio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT HistorialContrasena_pk PRIMARY KEY (idHistorial),
    CONSTRAINT HistorialContrasena_check_user CHECK (
        (idCliente IS NOT NULL AND idAdmin IS NULL) OR 
        (idCliente IS NULL AND idAdmin IS NOT NULL)
    )
);

-- Table: RolPermiso
CREATE TABLE IF NOT EXISTS RolPermiso (
    idRolPermiso serial NOT NULL,
    rol_idRol int NOT NULL,
    permiso_idPermiso int NOT NULL,
    fechaAsignacion timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT RolPermiso_pk PRIMARY KEY (idRolPermiso),
    CONSTRAINT RolPermiso_unique UNIQUE (rol_idRol, permiso_idPermiso)
);

-- Table: PermisoTemporal
CREATE TABLE IF NOT EXISTS PermisoTemporal (
    idPermisoTemporal serial NOT NULL,
    admin_idAdmin int NOT NULL,
    permiso_idPermiso int NOT NULL,
    fechaInicio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fechaFin timestamp NOT NULL,
    motivo varchar(255) NULL,
    activo boolean NOT NULL DEFAULT true,
    fechaCreacion timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PermisoTemporal_pk PRIMARY KEY (idPermisoTemporal),
    CONSTRAINT PermisoTemporal_fecha_check CHECK (fechaFin > fechaInicio)
);

-- Table: HistorialBloqueo
CREATE TABLE IF NOT EXISTS HistorialBloqueo (
    idHistorialBloqueo serial NOT NULL,
    tipoUsuario varchar(20) NOT NULL,
    idUsuario int NOT NULL,
    accion varchar(20) NOT NULL,
    motivo varchar(255) NULL,
    fecha timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    realizadoPor int NULL,
    ipOrigen varchar(50) NULL,
    CONSTRAINT HistorialBloqueo_pk PRIMARY KEY (idHistorialBloqueo),
    CONSTRAINT HistorialBloqueo_tipo_check CHECK (tipoUsuario IN ('CLIENTE', 'ADMIN')),
    CONSTRAINT HistorialBloqueo_accion_check CHECK (accion IN ('BLOQUEO', 'DESBLOQUEO'))
);

-- ============================================
-- PARTE 2: CLAVES FORÁNEAS
-- ============================================

-- Foreign keys
ALTER TABLE Actividad ADD CONSTRAINT Actividad_CategoriaActividad
    FOREIGN KEY (CategoriaActividad_idCatActi) REFERENCES CategoriaActividad (idCatActi);

ALTER TABLE Actividad ADD CONSTRAINT Actividad_Ciudad
    FOREIGN KEY (Ciudad_idCiudad) REFERENCES Ciudad (idCiudad);

-- Foreign keys
ALTER TABLE Actividad ADD CONSTRAINT Actividad_CategoriaActividad
    FOREIGN KEY (CategoriaActividad_idCatActi) REFERENCES CategoriaActividad (idCatActi);

ALTER TABLE Actividad ADD CONSTRAINT Actividad_Ciudad
    FOREIGN KEY (Ciudad_idCiudad) REFERENCES Ciudad (idCiudad);

ALTER TABLE AlquilerAuto ADD CONSTRAINT AlquilerAuto_Auto
    FOREIGN KEY (Auto_idAuto) REFERENCES Auto (idAuto);

ALTER TABLE AlquilerAuto ADD CONSTRAINT AlquilerAuto_Ciudad
    FOREIGN KEY (Ciudad_idCiudad) REFERENCES Ciudad (idCiudad);

ALTER TABLE Atraccion ADD CONSTRAINT Atraccion_CategoriaAtraccion
    FOREIGN KEY (CategoriaAtraccion_idCatAtrac) REFERENCES CategoriaAtraccion (idCatAtrac);

ALTER TABLE Atraccion ADD CONSTRAINT Atraccion_Ciudad
    FOREIGN KEY (Ciudad_idCiudad) REFERENCES Ciudad (idCiudad);

ALTER TABLE Auditoria ADD CONSTRAINT Auditoria_Cliente
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente);

ALTER TABLE Auditoria ADD CONSTRAINT Auditoria_admin
    FOREIGN KEY (admin_idAdmin) REFERENCES admin (idadmin);

ALTER TABLE Ciudad ADD CONSTRAINT Ciudad_Pais
    FOREIGN KEY (Pais_idPais) REFERENCES Pais (idPais);

ALTER TABLE Cliente ADD CONSTRAINT Cliente_Persona
    FOREIGN KEY (Persona_idPersona) REFERENCES Persona (idPersona);

ALTER TABLE Comentarios ADD CONSTRAINT Comentarios_Cliente
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente);

ALTER TABLE Facturacion ADD CONSTRAINT Facturacion_Cliente
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente);

ALTER TABLE Facturacion ADD CONSTRAINT Facturacion_FormaPago
    FOREIGN KEY (FormaPago_idFormP) REFERENCES FormaPago (idFormP);

ALTER TABLE Facturacion ADD CONSTRAINT Facturacion_ReservaViaje
    FOREIGN KEY (ReservaViaje_idReservaViaja) REFERENCES ReservaViaje (idReservaViaja);

ALTER TABLE Hotel ADD CONSTRAINT Hotel_CategoriaHotel
    FOREIGN KEY (CategoriaHotel_idCatHot) REFERENCES CategoriaHotel (idCatHot);

ALTER TABLE Hotel ADD CONSTRAINT Hotel_Ciudad
    FOREIGN KEY (Ciudad_idCiudad) REFERENCES Ciudad (idCiudad);

ALTER TABLE ReservaHotel ADD CONSTRAINT ReservaHotel_Hotel
    FOREIGN KEY (Hotel_idHotel) REFERENCES Hotel (idHotel);

ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Actividad
    FOREIGN KEY (Actividad_idActividad) REFERENCES Actividad (idActividad);

ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_AlquilerAuto
    FOREIGN KEY (AlquilerAuto_idAlquiler) REFERENCES AlquilerAuto (idAlquiler);

ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Atraccion
    FOREIGN KEY (Atraccion_idAtraccion) REFERENCES Atraccion (idAtraccion);

ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_ReservaHotel
    FOREIGN KEY (ReservaHotel_idReservaHotel) REFERENCES ReservaHotel (idReservaHotel);

ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Seguro
    FOREIGN KEY (Seguro_idSeguro) REFERENCES Seguro (idSeguro);

ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Viaje
    FOREIGN KEY (Viaje_idViaje) REFERENCES Vuelo (idViaje);

ALTER TABLE ReservaViaje ADD CONSTRAINT Reserva_Cliente
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente);

ALTER TABLE Vuelo ADD CONSTRAINT Viaje_Aerolinea
    FOREIGN KEY (Aerolinea_idAerolinea) REFERENCES Aerolinea (idAerolinea);

ALTER TABLE Vuelo ADD CONSTRAINT Viaje_Ciudad
    FOREIGN KEY (origen) REFERENCES Ciudad (idCiudad);

ALTER TABLE Vuelo ADD CONSTRAINT Viaje_Ciudad1
    FOREIGN KEY (destino) REFERENCES Ciudad (idCiudad);

ALTER TABLE admin ADD CONSTRAINT admin_rol_fk
    FOREIGN KEY (rol_idrol) REFERENCES Rol (idRol);

ALTER TABLE admin ADD CONSTRAINT admin_persona_fk
    FOREIGN KEY (persona_idpersona) REFERENCES Persona (idPersona);

ALTER TABLE AdminPermiso ADD CONSTRAINT AdminPermiso_Admin_fk
    FOREIGN KEY (admin_idAdmin) REFERENCES admin (idadmin);

ALTER TABLE AdminPermiso ADD CONSTRAINT AdminPermiso_Permiso_fk
    FOREIGN KEY (permiso_idPermiso) REFERENCES Permiso (idPermiso);

ALTER TABLE HistorialContrasena ADD CONSTRAINT HistorialContrasena_Cliente_fk
    FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente) ON DELETE CASCADE;

ALTER TABLE HistorialContrasena ADD CONSTRAINT HistorialContrasena_Admin_fk
    FOREIGN KEY (idAdmin) REFERENCES admin (idadmin) ON DELETE CASCADE;

ALTER TABLE RolPermiso ADD CONSTRAINT RolPermiso_Rol_fk
    FOREIGN KEY (rol_idRol) REFERENCES Rol (idRol) ON DELETE CASCADE;

ALTER TABLE RolPermiso ADD CONSTRAINT RolPermiso_Permiso_fk
    FOREIGN KEY (permiso_idPermiso) REFERENCES Permiso (idPermiso) ON DELETE CASCADE;

ALTER TABLE PermisoTemporal ADD CONSTRAINT PermisoTemporal_Admin_fk
    FOREIGN KEY (admin_idAdmin) REFERENCES admin (idadmin) ON DELETE CASCADE;

ALTER TABLE PermisoTemporal ADD CONSTRAINT PermisoTemporal_Permiso_fk
    FOREIGN KEY (permiso_idPermiso) REFERENCES Permiso (idPermiso) ON DELETE CASCADE;

-- Índices
CREATE INDEX idx_historial_bloqueo_usuario ON HistorialBloqueo(tipoUsuario, idUsuario);
CREATE INDEX idx_historial_bloqueo_fecha ON HistorialBloqueo(fecha DESC);

-- Comentarios
COMMENT ON COLUMN Cliente.estadoCuenta IS 'Estado de la cuenta: true = activa, false = bloqueada';
COMMENT ON COLUMN Cliente.intentosFallidos IS 'Número de intentos de login fallidos consecutivos';
COMMENT ON COLUMN Cliente.fechaBloqueo IS 'Fecha y hora en que se bloqueó la cuenta';
COMMENT ON COLUMN Cliente.motivoBloqueo IS 'Motivo del bloqueo de cuenta';
COMMENT ON COLUMN admin.estadoCuenta IS 'Estado de la cuenta: true = activa, false = bloqueada';
COMMENT ON COLUMN admin.intentosFallidos IS 'Número de intentos de login fallidos consecutivos';
COMMENT ON COLUMN admin.fechaBloqueo IS 'Fecha y hora en que se bloqueó la cuenta';
COMMENT ON COLUMN admin.motivoBloqueo IS 'Motivo del bloqueo de cuenta';
COMMENT ON TABLE HistorialBloqueo IS 'Historial de bloqueos y desbloqueos de cuentas para auditoría';

-- ============================================
-- PARTE 3: DATOS INICIALES
-- ============================================

-- 1. DATOS BÁSICOS
-- Limpiar datos existentes primero
TRUNCATE TABLE Pais CASCADE;
TRUNCATE TABLE Ciudad CASCADE;
TRUNCATE TABLE CategoriaHotel CASCADE;
TRUNCATE TABLE Hotel CASCADE;
TRUNCATE TABLE Aerolinea CASCADE;
TRUNCATE TABLE CategoriaAtraccion CASCADE;
TRUNCATE TABLE Atraccion CASCADE;
TRUNCATE TABLE CategoriaActividad CASCADE;
TRUNCATE TABLE Actividad CASCADE;
TRUNCATE TABLE Auto CASCADE;
TRUNCATE TABLE Seguro CASCADE;
TRUNCATE TABLE FormaPago CASCADE;
TRUNCATE TABLE Rol CASCADE;
TRUNCATE TABLE Permiso CASCADE;
TRUNCATE TABLE RolPermiso CASCADE;

-- Reiniciar secuencias
ALTER SEQUENCE IF EXISTS pais_idpais_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS ciudad_idciudad_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS categoriahotel_idcathot_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS hotel_idhotel_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS aerolinea_idaerolinea_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS categoriaatraccion_idcatatrac_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS atraccion_idatraccion_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS categoriaactividad_idcatacti_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS actividad_idactividad_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS auto_idauto_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS seguro_idseguro_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS formapago_idformp_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS rol_idrol_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS permiso_idpermiso_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS rolpermiso_idrolpermiso_seq RESTART WITH 1;

INSERT INTO Pais (pais) VALUES ('Bolivia');

INSERT INTO Ciudad (ciudad, Pais_idPais) VALUES
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

INSERT INTO Hotel (hotel, puntuacion, Ciudad_idCiudad, CategoriaHotel_idCatHot, totalHabitaciones, descripcion, imagenes, ubicacion) VALUES
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

INSERT INTO Aerolinea (aerolinea) VALUES
    ('Boliviana de Aviacion'),
    ('Amaszonas'),
    ('Latam'),
    ('Iberia'),
    ('Avianca'),
    ('Volaris');

INSERT INTO CategoriaAtraccion (categoria) VALUES
    ('Parque Nacional'),
    ('Sitio Arqueológico'),
    ('Mirador'),
    ('Cascada'),
    ('Mercado artesanal');

INSERT INTO Atraccion (atraccion, CategoriaAtraccion_idCatAtrac, Ciudad_idCiudad, precio, detalle) VALUES
    ('Cataratas del Yguazú', 4, 1, 25.00,''),
    ('Ruinas de Tiwanaku', 2, 1, 10.00,''),
    ('Cristo de la Concordia', 3, 3, 5.00,''),
    ('Salar de Uyuni', 1, 6, 30.00,''),
    ('Fortaleza de Samaipata', 2, 2, 15.00,''),
    ('Mercado de las Brujas', 5, 1, 5.00,''),
    ('Catarata de Arco Iris', 4, 7, 20.00,'');

INSERT INTO CategoriaActividad (categoria) VALUES
('Trekking'),
('Visita a museos'),
('Avistamiento de aves'),
('Ciclismo de montaña'),
('Tour gastronómico'),
('Conciertos');

INSERT INTO Actividad (actividad, Ciudad_idCiudad, CategoriaActividad_idCatActi, fecha, precio, detalle) VALUES
    ('Ascenso al Huayna Potosí', 1, 1, '2024-05-10', 40.00, 'Horario: 12:00pm'),
    ('Visita al Museo de la Coca', 1, 2, '2024-05-23', 15.00, 'Horario: 12:00pm'),
    ('Avistamiento de aves en el Jardín Botánico', 3, 3, '2024-06-07', 5.00, 'Horario: 12:00pm'),
    ('Ciclismo de montaña en la Ruta de las Yungas', 1, 4, '2024-06-21', 20.00, 'Horario: 12:00pm'),
    ('Tour gastronómico por la calle Jaén', 1, 5, '2024-05-02', 10.00, 'Horario: 12:00pm'),
    ('Concierto en el Estadio Hernando Siles', 1, 6, '2024-04-28', 30.00, 'Horario: 12:00pm'),
    ('Recital en el Teatro Municipal de Santa Cruz', 2, 6, '2024-06-17', 25.00, 'Horario: 12:00pm');

INSERT INTO Auto (modelo, marca, tipo) VALUES
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

INSERT INTO Seguro (seguro, cobertura, precio) VALUES
    ('Seguro Básico', 'Atención médica básica', 50.00),
    ('Seguro Estándar', 'Atención médica y repatriación', 75.00),
    ('Seguro Premium', 'Atención médica y pérdida de equipaje', 100.00),
    ('Seguro de Viaje Aventura', 'Atención médica especializada', 120.00),
    ('Seguro Familiar', 'Cobertura para toda la familia', 150.00)
ON CONFLICT DO NOTHING;

INSERT INTO FormaPago (formapago) VALUES
    ('Efectivo'),
    ('Tarjeta de crédito'),
    ('Tarjeta de débito'),
    ('Transferencia bancaria'),
    ('Pago en línea');

-- ============================================
-- 2. ROLES Y PERMISOS (MUY IMPORTANTE)
-- ============================================

INSERT INTO Rol (rol) VALUES
('Gerente'),          -- ID: 1
('Tecnología'),       -- ID: 2
('Seguridad'),        -- ID: 3
('Contador'),         -- ID: 4
('Auditor'),          -- ID: 5
('Pasante TI'),       -- ID: 6
('Agente de viajes'), -- ID: 7
('Usuario');           -- ID: 8

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
('Crear solicitud de viaje');     -- ID: 13

-- Asignación de permisos por rol
INSERT INTO RolPermiso (rol_idRol, permiso_idPermiso) VALUES
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
-- FIN DEL SCRIPT
-- ============================================
-- La base de datos está lista con:
-- - Todas las tablas creadas
-- - Todas las relaciones establecidas
-- - Todos los roles y permisos configurados
-- - Datos iniciales insertados
-- ============================================

