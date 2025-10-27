-- Created by Vertabelo (http://vertabelo.com)
-- Crear la base de datos 'Oasis' en PgAdmin

-- tables
-- Table: Actividad
CREATE TABLE Actividad (
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
CREATE TABLE Aerolinea (
    idAerolinea serial NOT NULL,
    aerolinea varchar(45) NOT NULL, 
    CONSTRAINT Aerolinea_pk PRIMARY KEY (idAerolinea)
);

-- Table: AlquilerAuto
CREATE TABLE AlquilerAuto (
    idAlquiler serial NOT NULL,
    precio decimal(7,2) NOT NULL,
    dias int NOT NULL,
    empresa varchar(65) NOT NULL,
    Auto_idAuto int NOT NULL,
    Ciudad_idCiudad int NOT NULL,
    CONSTRAINT AlquilerAuto_pk PRIMARY KEY (idAlquiler)
);

-- Table: Atraccion
CREATE TABLE Atraccion (
    idAtraccion serial NOT NULL,
    atraccion varchar(45) NOT NULL,
    CategoriaAtraccion_idCatAtrac int NOT NULL,
    Ciudad_idCiudad int NOT NULL,
    precio decimal(7,2) NOT NULL,
    detalle varchar(255) NOT NULL,
    CONSTRAINT Atraccion_pk PRIMARY KEY (idAtraccion)
);

-- Table: Auditoria
CREATE TABLE Auditoria (
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
CREATE TABLE Auto (
    idAuto serial NOT NULL,
    modelo varchar(45) NOT NULL,
    marca varchar(45) NOT NULL,
    tipo varchar(45) NOT NULL,
    CONSTRAINT Auto_pk PRIMARY KEY (idAuto)
);

-- Table: CategoriaActividad
CREATE TABLE CategoriaActividad (
    idCatActi serial NOT NULL,
    categoria varchar(45) NOT NULL,
    CONSTRAINT CategoriaActividad_pk PRIMARY KEY (idCatActi)
);

-- Table: CategoriaAtraccion
CREATE TABLE CategoriaAtraccion (
    idCatAtrac serial NOT NULL,
    categoria varchar(45) NOT NULL,
    CONSTRAINT CategoriaAtraccion_pk PRIMARY KEY (idCatAtrac)
);

-- Table: CategoriaHotel
CREATE TABLE CategoriaHotel (
    idCatHot serial NOT NULL,
    categoria varchar(45) NOT NULL,
    CONSTRAINT CategoriaHotel_pk PRIMARY KEY (idCatHot)
);

-- Table: Ciudad
CREATE TABLE Ciudad (
    idCiudad serial NOT NULL,
    ciudad varchar(45) NOT NULL,
    Pais_idPais int NOT NULL,
    CONSTRAINT Ciudad_pk PRIMARY KEY (idCiudad)
);

-- Table: Cliente
CREATE TABLE Cliente (
    idCliente serial NOT NULL,
    correo varchar(45) NOT NULL,
    password varchar(255) NOT NULL,
    estadoCuenta varchar(45) NOT NULL,
    Persona_idPersona int NOT NULL,
    CONSTRAINT Cliente_pk PRIMARY KEY (idCliente)
);

ALTER TABLE Cliente
  ADD COLUMN estadoCuenta_tmp boolean;

UPDATE Cliente
  SET estadoCuenta_tmp = CASE
      WHEN LOWER(estadoCuenta) IN ('activo', 'true', '1') THEN true
      ELSE false
  END;

ALTER TABLE Cliente
  DROP COLUMN estadoCuenta;

ALTER TABLE Cliente
  RENAME COLUMN estadoCuenta_tmp TO estadoCuenta;


-- Table: Comentarios
CREATE TABLE Comentarios (
    idComentario serial NOT NULL,
    Comentario text NOT NULL,
    Cliente_idCliente int NOT NULL,
    CONSTRAINT Comentarios_pk PRIMARY KEY (idComentario)
);

-- Table: Facturacion
CREATE TABLE Facturacion (
    idFactura serial NOT NULL,
    NIT varchar(45) NOT NULL,
    fecha date NOT NULL,
    Cliente_idCliente int NOT NULL,
    ReservaViaje_idReservaViaja int NOT NULL,
    FormaPago_idFormP int NOT NULL,
    CONSTRAINT Facturacion_pk PRIMARY KEY (idFactura)
);

-- Table: FormaPago
CREATE TABLE FormaPago (
    idFormP serial NOT NULL,
    formapago varchar(45) NOT NULL,
    CONSTRAINT FormaPago_pk PRIMARY KEY (idFormP)
);

-- Table: Hotel
CREATE TABLE Hotel (
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
CREATE TABLE Pais (
    idPais serial NOT NULL,
    pais varchar(55) NOT NULL,
    CONSTRAINT Pais_pk PRIMARY KEY (idPais)
);

-- Table: Persona
CREATE TABLE Persona (
    idPersona serial NOT NULL,
    Nombre varchar(45) NOT NULL,
    ApellidoP varchar(45) NOT NULL,
    ApellidoM varchar(45) NOT NULL,
    Telefono varchar(45) NOT NULL,
    CONSTRAINT Persona_pk PRIMARY KEY (idPersona)
);

-- Table: ReservaHotel
CREATE TABLE ReservaHotel (
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
CREATE TABLE ReservaViaje (
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
CREATE TABLE Seguro (
    idSeguro serial NOT NULL,
    seguro varchar(45) NOT NULL,
    cobertura varchar(45) NOT NULL,
    precio decimal(7,2) NOT NULL,
    CONSTRAINT Seguro_pk PRIMARY KEY (idSeguro)
);

-- Table: Vuelo
CREATE TABLE Vuelo (
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
CREATE TABLE Rol (
    idRol serial NOT NULL,
    rol varchar(45) NOT NULL,
    CONSTRAINT Rol_pk PRIMARY KEY (idRol)
);

-- Table: Permiso
CREATE TABLE Permiso (
    idPermiso serial NOT NULL,
    permiso varchar(45) NOT NULL,
    CONSTRAINT Permiso_pk PRIMARY KEY (idPermiso)
);

-- Table: admin
CREATE TABLE admin (
    idadmin serial NOT NULL,
    correo varchar(45) NOT NULL,
    password varchar(255) NOT NULL,
    rol_idrol int NOT NULL,
    persona_idpersona int NOT NULL,
    CONSTRAINT admin_pk PRIMARY KEY (idadmin),
    CONSTRAINT admin_rol_fk FOREIGN KEY (rol_idrol) REFERENCES rol (idrol),
    CONSTRAINT admin_persona_fk FOREIGN KEY (persona_idpersona) REFERENCES persona (idpersona)
);

-- Table: AdminPermiso
CREATE TABLE AdminPermiso (
    idAdminPermiso serial NOT NULL,
    admin_idAdmin int NOT NULL,
    permiso_idPermiso int NOT NULL,
    CONSTRAINT AdminPermiso_pk PRIMARY KEY (idAdminPermiso),
    CONSTRAINT AdminPermiso_Admin_fk FOREIGN KEY (admin_idAdmin) REFERENCES admin (idadmin),
    CONSTRAINT AdminPermiso_Permiso_fk FOREIGN KEY (permiso_idPermiso) REFERENCES Permiso (idPermiso)
);

-- foreign keys
-- Reference: Actividad_CategoriaActividad (table: Actividad)
ALTER TABLE Actividad ADD CONSTRAINT Actividad_CategoriaActividad
    FOREIGN KEY (CategoriaActividad_idCatActi)
        REFERENCES CategoriaActividad (idCatActi)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Actividad_Ciudad (table: Actividad)
ALTER TABLE Actividad ADD CONSTRAINT Actividad_Ciudad
    FOREIGN KEY (Ciudad_idCiudad)
        REFERENCES Ciudad (idCiudad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: AlquilerAuto_Auto (table: AlquilerAuto)
ALTER TABLE AlquilerAuto ADD CONSTRAINT AlquilerAuto_Auto
    FOREIGN KEY (Auto_idAuto)
        REFERENCES Auto (idAuto)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: AlquilerAuto_Ciudad (table: AlquilerAuto)
ALTER TABLE AlquilerAuto ADD CONSTRAINT AlquilerAuto_Ciudad
    FOREIGN KEY (Ciudad_idCiudad)
        REFERENCES Ciudad (idCiudad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Atraccion_CategoriaAtraccion (table: Atraccion)
ALTER TABLE Atraccion ADD CONSTRAINT Atraccion_CategoriaAtraccion
    FOREIGN KEY (CategoriaAtraccion_idCatAtrac)
        REFERENCES CategoriaAtraccion (idCatAtrac)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Atraccion_Ciudad (table: Atraccion)
ALTER TABLE Atraccion ADD CONSTRAINT Atraccion_Ciudad
    FOREIGN KEY (Ciudad_idCiudad)
        REFERENCES Ciudad (idCiudad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Auditoria_Cliente (table: Auditoria)
ALTER TABLE Auditoria ADD CONSTRAINT Auditoria_Cliente
    FOREIGN KEY (Cliente_idCliente)
        REFERENCES Cliente (idCliente)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Auditoria_admin (table: Auditoria)
ALTER TABLE Auditoria ADD CONSTRAINT Auditoria_admin
    FOREIGN KEY (admin_idAdmin)
        REFERENCES admin (idAdmin)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Ciudad_Pais (table: Ciudad)
ALTER TABLE Ciudad ADD CONSTRAINT Ciudad_Pais
    FOREIGN KEY (Pais_idPais)
        REFERENCES Pais (idPais)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Cliente_Persona (table: Cliente)
ALTER TABLE Cliente ADD CONSTRAINT Cliente_Persona
    FOREIGN KEY (Persona_idPersona)
        REFERENCES Persona (idPersona)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Comentarios_Cliente (table: Comentarios)
ALTER TABLE Comentarios ADD CONSTRAINT Comentarios_Cliente
    FOREIGN KEY (Cliente_idCliente)
        REFERENCES Cliente (idCliente)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Facturacion_Cliente (table: Facturacion)
ALTER TABLE Facturacion ADD CONSTRAINT Facturacion_Cliente
    FOREIGN KEY (Cliente_idCliente)
        REFERENCES Cliente (idCliente)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Facturacion_FormaPago (table: Facturacion)
ALTER TABLE Facturacion ADD CONSTRAINT Facturacion_FormaPago
    FOREIGN KEY (FormaPago_idFormP)
        REFERENCES FormaPago (idFormP)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Facturacion_ReservaViaje (table: Facturacion)
ALTER TABLE Facturacion ADD CONSTRAINT Facturacion_ReservaViaje
    FOREIGN KEY (ReservaViaje_idReservaViaja)
        REFERENCES ReservaViaje (idReservaViaja)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Hotel_CategoriaHotel (table: Hotel)
ALTER TABLE Hotel ADD CONSTRAINT Hotel_CategoriaHotel
    FOREIGN KEY (CategoriaHotel_idCatHot)
        REFERENCES CategoriaHotel (idCatHot)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Hotel_Ciudad (table: Hotel)
ALTER TABLE Hotel ADD CONSTRAINT Hotel_Ciudad
    FOREIGN KEY (Ciudad_idCiudad)
        REFERENCES Ciudad (idCiudad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaHotel_Hotel (table: ReservaHotel)
ALTER TABLE ReservaHotel ADD CONSTRAINT ReservaHotel_Hotel
    FOREIGN KEY (Hotel_idHotel)
        REFERENCES Hotel (idHotel)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaViaje_Actividad (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Actividad
    FOREIGN KEY (Actividad_idActividad)
        REFERENCES Actividad (idActividad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaViaje_AlquilerAuto (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_AlquilerAuto
    FOREIGN KEY (AlquilerAuto_idAlquiler)
        REFERENCES AlquilerAuto (idAlquiler)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaViaje_Atraccion (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Atraccion
    FOREIGN KEY (Atraccion_idAtraccion)
        REFERENCES Atraccion (idAtraccion)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaViaje_ReservaHotel (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_ReservaHotel
    FOREIGN KEY (ReservaHotel_idReservaHotel)
        REFERENCES ReservaHotel (idReservaHotel)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaViaje_Seguro (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Seguro
    FOREIGN KEY (Seguro_idSeguro)
        REFERENCES Seguro (idSeguro)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ReservaViaje_Viaje (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT ReservaViaje_Viaje
    FOREIGN KEY (Viaje_idViaje)
        REFERENCES Vuelo (idViaje)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Reserva_Cliente (table: ReservaViaje)
ALTER TABLE ReservaViaje ADD CONSTRAINT Reserva_Cliente
    FOREIGN KEY (Cliente_idCliente)
        REFERENCES Cliente (idCliente)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Viaje_Aerolinea (table: Vuelo)
ALTER TABLE Vuelo ADD CONSTRAINT Viaje_Aerolinea
    FOREIGN KEY (Aerolinea_idAerolinea)
        REFERENCES Aerolinea (idAerolinea)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Viaje_Ciudad (table: Vuelo)
ALTER TABLE Vuelo ADD CONSTRAINT Viaje_Ciudad
    FOREIGN KEY (origen)
        REFERENCES Ciudad (idCiudad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Viaje_Ciudad1 (table: Vuelo)
ALTER TABLE Vuelo ADD CONSTRAINT Viaje_Ciudad1
    FOREIGN KEY (destino)
        REFERENCES Ciudad (idCiudad)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: admin_Persona (table: admin)
ALTER TABLE admin ADD CONSTRAINT admin_Persona
    FOREIGN KEY (persona_idpersona)
        REFERENCES Persona (idPersona)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

CREATE TABLE HistorialContrasena (
    idHistorial serial NOT NULL,
    idCliente int NOT NULL,
    contrasena_hash varchar(255) NOT NULL,
    fecha_cambio timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT HistorialContrasena_pk PRIMARY KEY (idHistorial),
    CONSTRAINT HistorialContrasena_Cliente_fk FOREIGN KEY (idCliente)
        REFERENCES Cliente (idCliente)
        ON DELETE CASCADE
);

ALTER TABLE HistorialContrasena
ADD COLUMN idAdmin int NULL;


-- 1️⃣ Elimina las llaves foráneas actuales (si existen)
ALTER TABLE HistorialContrasena
DROP CONSTRAINT IF EXISTS HistorialContrasena_Cliente_fk;

-- 2️⃣ Elimina las columnas que ya no se usarán
ALTER TABLE HistorialContrasena
DROP COLUMN IF EXISTS idCliente,
DROP COLUMN IF EXISTS idAdmin;

-- 3️⃣ Agrega la nueva columna idPersona
ALTER TABLE HistorialContrasena
ADD COLUMN idPersona int NOT NULL;

-- 4️⃣ Crea la relación con la tabla Persona
ALTER TABLE HistorialContrasena
ADD CONSTRAINT HistorialContrasena_Persona_fk
FOREIGN KEY (idPersona)
REFERENCES Persona (idPersona)
ON DELETE CASCADE;


-- End of file.

