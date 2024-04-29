drop table if exists detalle_ventas;
drop table if exists detalle_pedido;
drop table if exists historial_stock;
drop table if exists productos;
drop table if exists categorias;
drop table if exists unidades_medidas;
drop table if exists categoria_unidad_medida;
drop table if exists cabecera_pedidos;
drop table if exists proveedor;
drop table if exists tipo_documento;
drop table if exists estado_pedidos;
drop table if exists cabecera_ventas;
create table categorias(
	codigo_cat serial not null,
	nombre varchar (100) not null,
	categoria_padre int ,
	constraint categorias_PK PRIMARY KEY (codigo_cat),
	constraint categorias_padre_FK foreign KEY (categoria_padre) 
	references categorias (codigo_cat)
);
insert into  categorias (nombre,categoria_padre) values ('Materia prima',null);
insert into  categorias (nombre,categoria_padre) values ('Proteina',1);
insert into  categorias (nombre,categoria_padre) values ('Salsas',1);
insert into  categorias (nombre,categoria_padre) values ('Punto de venta',null);
insert into  categorias (nombre,categoria_padre) values ('Bebidas',4);
insert into  categorias (nombre,categoria_padre) values ('Con Alcohol',5);
insert into  categorias (nombre,categoria_padre) values ('Sin Alcohol',5);
select * from categorias;
--Undida_medida categoria
create table categoria_unidad_medida(
	codigo_unidad_medida char(1) not null,
	descripcion_unidad varchar(20) not null,
	constraint categoria_unidad_PK PRIMARY KEY(codigo_unidad_medida)
);
insert into categoria_unidad_medida(codigo_unidad_medida,descripcion_unidad )
values('U','Unidades');
insert into categoria_unidad_medida(codigo_unidad_medida,descripcion_unidad )
values('V','Volumen');
insert into categoria_unidad_medida(codigo_unidad_medida,descripcion_unidad )
values('P','Peso');
insert into categoria_unidad_medida(codigo_unidad_medida,descripcion_unidad )
values('L','Longuitud');
select * from categoria_unidad_medida;
--Undidades de medida
create table unidades_medidas (
	codigo_unidades_categoria char(2) not null,
	descripcion varchar(100)not null,
	categoria_udm char (1) not null,
	constraint unidades_medida_PK PRIMARY KEY (codigo_unidades_categoria),
	constraint categoria_udm_FK foreign KEY (categoria_udm) 
	references categoria_unidad_medida (codigo_unidad_medida)	
);
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('ml','Mililitros','V');
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('l','Litros','V');
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('u','Unidad','U');
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('d','Docenas','U');
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('g','Gramos','P');
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('kg','Kilogramos','P');
insert into unidades_medidas(codigo_unidades_categoria,descripcion,categoria_udm )
values('lb','Libras','P');
select * from unidades_medidas;
--Productos
create table productos(
	codigo_producto serial not null,
	nombre varchar(50) not null,
	UDM char(2) not null,
	precio_de_venta money not null,
	tiene_iva boolean not null,
	coste money not null,
	categoria int not null,
	stock int not null,
	constraint productos_PK PRIMARY KEY (codigo_producto),
	constraint unidad_medida_FK foreign KEY (UDM) 
	references unidades_medidas (codigo_unidades_categoria),
	constraint categoria_FK foreign KEY (categoria) 
	references categorias (codigo_cat)	
);
insert into productos (nombre, UDM, precio_de_venta, tiene_iva, coste, categoria, stock)
values ('Coca cola pequeña','u',0.5804,true,0.3729,7,110);
insert into productos (nombre, UDM, precio_de_venta, tiene_iva, coste, categoria, stock)
values ('Salsa de tomate','kg',0.95,true,0.8736,3,0);
insert into productos (nombre, UDM, precio_de_venta, tiene_iva, coste, categoria, stock)
values ('Mostaza','kg',0.95,true,0.8736,3,0);
insert into productos (nombre, UDM, precio_de_venta, tiene_iva, coste, categoria, stock)
values ('Fuze tea','u',0.8,true,0.7,7,49);
select * from  productos;
create table tipo_documento(
	codigo_documento char(1) not null,
	descripcion varchar(100) not null,
	constraint tipo_documento_PK PRIMARY KEY(codigo_documento)
);
insert into tipo_documento (codigo_documento, descripcion)values ('C','Cedula');
insert into tipo_documento (codigo_documento, descripcion)values ('R','RUC');
select * from tipo_documento;
--Proveedor
create table proveedor(
	ruc_cedula varchar(13) not null,
	nombre varchar(100) not null,
	tipo_documento char (1) not null,
	telefono varchar(10) not null,
	correo varchar(20) not null,
	direccion varchar(20) not null,
	constraint proveedor_PK PRIMARY KEY(ruc_cedula),
	constraint tipo_documento_FK foreign KEY (tipo_documento) 
	references tipo_documento (codigo_documento)	
);
insert into proveedor(ruc_cedula,nombre,tipo_documento,telefono, correo,direccion)
	values ('1792222323','Victor Martinez','C','0992545450','fcorreo@correo.com','Loja');
insert into proveedor(ruc_cedula,nombre,tipo_documento,telefono, correo,direccion)
	values ('1900056001','SNACK.SA','R','0992545450','fcorreo@correo.com','Loja');
	
select * from proveedor;
--Estado pedidos
create table estado_pedidos(
	codigo_estado char(1) not null,
	descripcion varchar(50) not null,
	constraint descripcion_PK PRIMARY KEY(codigo_estado)
);
insert into estado_pedidos(codigo_estado,descripcion)values ('S','Solicitado');
insert into estado_pedidos(codigo_estado,descripcion)values ('R','Recibido');
select * from estado_pedidos;
--Cabecera_pedidos
create table cabecera_pedidos(
	codigo_cabecera serial not null,
	proveedor varchar(13) not null,
	fecha timestamp not null,
	estado char(1) not null,
	constraint cabecera_pedidos_PK PRIMARY KEY(codigo_cabecera),
	constraint proveedor_FK foreign KEY (proveedor) 
	references proveedor (ruc_cedula),	
	constraint estado_FK foreign KEY (estado) 
	references estado_pedidos (codigo_estado)
);

insert into cabecera_pedidos (proveedor,fecha,estado) 
values ('1792222323','30/11/2023 22:30:30','R');
insert into cabecera_pedidos (proveedor,fecha,estado) 
values ('1792222323','20/11/2023 22:30:30','R');
select * from cabecera_pedidos;
--Detalle pedidos
create table detalle_pedido(
	codigo_pedido serial not null,
	cabecera_pedido int not null,
	producto int not null,
	cantidad int not null,
	subtotal money not null,
	cantidad_recibida int not null,
	constraint detalle_pedido_PK PRIMARY KEY(codigo_pedido),
	constraint cabecera_pedido_FK foreign KEY (cabecera_pedido) 
	references cabecera_pedidos (codigo_cabecera),	
	constraint producto_FK foreign KEY (producto) 
	references productos (codigo_producto)
);
insert into detalle_pedido(cabecera_pedido,producto,cantidad,subtotal,cantidad_recibida) 
values (1,1,100,37.9,100);
insert into detalle_pedido(cabecera_pedido,producto,cantidad,subtotal,cantidad_recibida) 
values (1,4,50,11.8,50);
insert into detalle_pedido(cabecera_pedido,producto,cantidad,subtotal,cantidad_recibida) 
values (2,1,10,3.73,10);
select * from detalle_pedido;

-- Historial Stock
create table historial_stock(
	codigo_historial serial not null,
	fecha timestamp not null,
	referencia_c_pedido varchar(100)  not null,
	producto int not null,
	cantidad int not null,
	constraint historial_stock_PK PRIMARY KEY(codigo_historial),
	constraint producto_FK foreign KEY (producto) 
	references productos (codigo_producto)
);

insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) 
values ('20/11/2023','1',1,100);
insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) 
values ('20/11/2023','1',4,50);
insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) 
values ('20/11/2023','2',1,10);
insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) 
values ('20/11/2023','1',1,-5);
insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) 
values ('20/11/2023','1',4,-1);
select * from historial_stock;

--Cabecera venta
create table cabecera_ventas (
	codigo_cab_ventas serial not null,
	fecha_venta timestamp not null,
	total_sin_iva money not null,
	iva money not null,
	total money not null,
	constraint cabecera_ventas_PK PRIMARY KEY(codigo_cab_ventas)
);

insert into cabecera_ventas (fecha_venta,total_sin_iva,iva,total) 
values ('20/11/2023 23:36:40',3.26,0.39,3.65);
select * from cabecera_ventas;
--Detalle ventas
create table detalle_ventas (
	codigo_detalle serial not null,
	cabecera int not null,
	producto int not null,
	cantidad  int not null,
	precio_venta money not null,
	subtotal money not null,
	subtotal_con_iva  money not null,
	constraint detalle_ventas_PK PRIMARY KEY(codigo_detalle),
	constraint cabecera_ventas_FK foreign KEY (cabecera) 
	references cabecera_ventas (codigo_cab_ventas),	
	constraint producto_FK foreign KEY (producto) 
	references productos (codigo_producto)
);
insert into detalle_ventas (cabecera,producto,cantidad,precio_venta,subtotal,subtotal_con_iva) 
values (1,1,5,0.58,2.9,3.25);
insert into detalle_ventas (cabecera,producto,cantidad,precio_venta,subtotal,subtotal_con_iva) 
values (1,4,1,0.36,0.36,0.4);
select * from detalle_ventas