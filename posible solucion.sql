select * from detalle_pedido dp, cabecera_pedidos cp, productos pro,categorias cat
where (dp.cabecera_pedido = cp.codigo_cabecera and pro.codigo_producto = dp.producto and cat.codigo_cat=pro.categoria )
and cp.proveedor='1792222323' 

select dp.codigo_pedido,dp.cabecera_pedido,
cp.proveedor,prov.nombre,prov.tipo_documento,prov.telefono,prov.correo,prov.direccion,
cp.fecha, cp.estado, ep.descripcion,
dp.producto,pro.nombre as nombreProducto,pro.udm,um.descripcion as descripcionUDM, um.categoria_udm,pro.precio_de_venta,pro.tiene_iva,pro.coste,pro.categoria,cat.nombre,cat.categoria_padre,pro.stock,
dp.cantidad,dp.subtotal,dp.cantidad_recibida
from detalle_pedido dp, cabecera_pedidos cp, productos pro, proveedor prov,
	categorias cat,estado_pedidos ep, unidades_medidas um
where (dp.cabecera_pedido = cp.codigo_cabecera and pro.codigo_producto = dp.producto 
	   and cat.codigo_cat=pro.categoria and cp.estado= ep.codigo_estado 
	   and pro.udm= um.codigo_unidades_categoria and prov.ruc_cedula=cp.proveedor )
and cp.proveedor='1792222323' 
select * from detalle_pedido dp
select * from cabecera_pedidos cp
select * from categoria_unidad_medida