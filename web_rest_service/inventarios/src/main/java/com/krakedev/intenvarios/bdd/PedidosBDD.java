package com.krakedev.intenvarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.CategoriaUDM;
import com.krakedev.inventarios.entidades.DetallePedidos;
import com.krakedev.inventarios.entidades.EstadoPedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedores;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {
	public void insertar(Pedido pedido) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet rsClave=null;
		int codigoGenerado=0;
		
		Date fechaActual = new Date();
		java.sql.Date fechaSql = new java.sql.Date(fechaActual.getTime());
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into cabecera_pedidos (proveedor,fecha,estado) "
					+ "values (?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSql);
			ps.setString(3, "S");
			
			ps.executeUpdate();
			
			rsClave=ps.getGeneratedKeys();
			
			if(rsClave.next()) {
				codigoGenerado= rsClave.getInt(1);
			}
			
			ArrayList<DetallePedidos> detallesPedido=pedido.getDetalles();
			DetallePedidos det;
			
			for (int i = 0; i < detallesPedido.size(); i++) {
				det=detallesPedido.get(i);
				psDet=con.prepareStatement("insert into detalle_pedido(cabecera_pedido,producto,cantidad,subtotal,cantidad_recibida)"
											+ "values (?,?,?,?,?);");
				psDet.setInt(1, codigoGenerado);
				psDet.setInt(2,det.getProducto().getCodigoProducto() );
				psDet.setInt(3,det.getCantidad() );	
				psDet.setInt(5,0 );
				BigDecimal pv=det.getProducto().getPrecioDeVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				BigDecimal subtototal= pv.multiply(cantidad);
				psDet.setBigDecimal(4,subtototal );
				psDet.executeUpdate();
			}
			
		System.out.println("CODIGO GENERADO >>>"+codigoGenerado);
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar el pedido: "+e.getMessage());
		}
	}

public void actualizarPedido(Pedido pedido) throws KrakedevException {
	Connection con = null;
	PreparedStatement ps = null;
	PreparedStatement psDet = null;
	PreparedStatement psHis = null;
	
	Date fechaActual = new Date();
	Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
	
	try {
		con = ConexionBDD.obtenerConexion();
		ps = con.prepareStatement("update cabecera_pedidos set "
				+ "estado = ? where codigo_cabecera =?;");
		ps.setString(1, "R");
		ps.setInt(2,pedido.getCodigo());
		
		ps.executeUpdate();
				
		ArrayList<DetallePedidos> detallesPedido=pedido.getDetalles();
		
		DetallePedidos det;
		for (int i = 0; i < detallesPedido.size(); i++) {
			 det = detallesPedido.get(i);
			psDet = con.prepareStatement("update detalle_pedido "
					+ "set cantidad_recibida=?, subtotal=? "
					+ "where codigo_pedido=?;");
			psDet.setInt(1, det.getCantidadRecibida());
			BigDecimal pv=det.getProducto().getPrecioDeVenta();
			BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
			BigDecimal subtototal= pv.multiply(cantidad);
			psDet.setBigDecimal(2, subtototal);
			psDet.setInt(3, det.getCodigo());
			psDet.executeUpdate();
			/*
			 * Insertamos el historial
			 */
			psHis = con.prepareStatement("insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) "
					+ "values (?,?,?,?);");
			psHis.setTimestamp(1, fechaHoraActual);
			psHis.setString(2, "Pedido "+pedido.getCodigo());
			psHis.setInt(3, det.getProducto().getCodigoProducto());
			psHis.setInt(4, det.getCantidadRecibida());
			
			psHis.executeUpdate();
		}
		
	
		
	} catch (KrakedevException e) {
		e.printStackTrace();
		throw e;
	} catch (SQLException e) {
		e.printStackTrace();
		throw new KrakedevException("Error al actulizar el pedido: "+e.getMessage());
	}
}

public ArrayList<Pedido> buscarPorProveedor (String identificador) throws KrakedevException{
	ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
	Connection con = null;
	PreparedStatement ps = null;
	PreparedStatement psAux = null;
	ResultSet rs= null;
	ResultSet rsAux= null;
	Pedido p= null;
	try {
		con = ConexionBDD.obtenerConexion();
		ps = con.prepareStatement("select pro.ruc_cedula, pro.nombre, pro.telefono, pro.correo, pro.direccion, "
				+ "td.codigo_documento, td.descripcion AS descipcion_doc, "
				+ "cp.codigo_cabecera, cp.fecha, cp.estado, ep.descripcion AS descripcion_estado "
				+ "from proveedor pro,cabecera_pedidos cp, estado_pedidos ep, tipo_documento td "
				+ "Where cp.proveedor = pro.ruc_cedula AND ep.codigo_estado = cp.estado AND td.codigo_documento = pro.tipo_documento "
				+ "AND cp.proveedor =?;");
		ps.setString(1, identificador);
		rs = ps.executeQuery();
		while (rs.next()) {			
			String codigoDoc = rs.getString("codigo_documento");
			String descripcionDoc = rs.getString("descipcion_doc");
			TipoDocumentos td = new TipoDocumentos(codigoDoc,descripcionDoc);
			
			String nombre = rs.getString("nombre");
			String telefono = rs.getString("telefono");
			String correo = rs.getString("correo");
			String direccion = rs.getString("direccion");
			Proveedores pro = new Proveedores(identificador, td, nombre, telefono, correo, direccion);
			
			String codigoEstado = rs.getString("estado");
			String descripcionEstado = rs.getString("descripcion_estado");
			EstadoPedido ep = new EstadoPedido(codigoEstado,descripcionEstado);
			
			int codigo = rs.getInt("codigo_cabecera");
			Date fecha = rs.getDate("fecha");
			p = new Pedido(codigo, pro, fecha, ep);
			System.out.println("select dp.codigo_pedido, dp.cabecera_pedido, pro.codigo_producto, pro.nombre, "
					+ "udm.codigo_unidades_categoria, udm.descripcion AS descripcion_udm, udm.categoria_udm, cat_udm.descripcion_unidad,cast(pro.precio_de_venta as decimal(6,2)), "
					+ "pro.tiene_iva, cast(pro.coste as decimal(5,4)), pro.categoria, c.nombre As nombre_categoria, pro.stock, "
					+ "dp.cantidad As cantidad_solicitada,cast(dp.subtotal as decimal(10,4)),dp.cantidad_recibida "
					+ " from detalle_pedido dp, productos pro, categorias c, unidades_medidas udm, categoria_unidad_medida cat_udm"
					+ " where dp.producto = pro.codigo_producto and pro.categoria = c.codigo_cat and pro.udm = udm.codigo_unidades_categoria"
					+ "and udm.categoria_udm = cat_udm.codigo_unidad_medida"
					+ "and dp.cabecera_pedido=?;");
			psAux = con.prepareStatement("select dp.codigo_pedido, dp.cabecera_pedido, pro.codigo_producto, pro.nombre, "
					+ "udm.codigo_unidades_categoria, udm.descripcion AS descripcion_udm, udm.categoria_udm, cat_udm.descripcion_unidad,cast(pro.precio_de_venta as decimal(6,2)), "
					+ "pro.tiene_iva, cast(pro.coste as decimal(5,4)), pro.categoria, c.nombre As nombre_categoria, pro.stock, "
					+ "dp.cantidad As cantidad_solicitada,cast(dp.subtotal as decimal(10,4)),dp.cantidad_recibida "
					+ " from detalle_pedido dp, productos pro, categorias c, unidades_medidas udm, categoria_unidad_medida cat_udm"
					+ " where dp.producto = pro.codigo_producto and pro.categoria = c.codigo_cat and pro.udm = udm.codigo_unidades_categoria "
					+ "and udm.categoria_udm = cat_udm.codigo_unidad_medida "
					+ "and dp.cabecera_pedido=?;");
			psAux.setInt(1, codigo);
			
			
			rsAux = psAux.executeQuery();
			ArrayList<DetallePedidos> listDetalle = new ArrayList<DetallePedidos>();
			while(rsAux.next()) {
				String codigoUdm = rsAux.getString("codigo_unidades_categoria");
				String descripcion = rsAux.getString("descripcion_udm");
				String codigoUnidadCategoria=rsAux.getString("categoria_udm");
				String descripcionCategoriaUnidad=rsAux.getString("descripcion_unidad");
				CategoriaUDM categoriaUdm = new CategoriaUDM(codigoUnidadCategoria, descripcionCategoriaUnidad);
				UnidadDeMedida udm = new UnidadDeMedida(codigoUdm, descripcion, categoriaUdm);
				
				int codigoCat = rsAux.getInt("categoria");
				String nombreCategoria = rsAux.getString("nombre_categoria");
				Categoria cate = new Categoria(codigoCat, nombreCategoria, null);
				
				int codigoProducto = rsAux.getInt("codigo_producto");
				String nombreProducto = rsAux.getString("nombre");
				BigDecimal precioVenta = rsAux.getBigDecimal("precio_de_venta");
				boolean tieneIva = rsAux.getBoolean("tiene_iva");
				BigDecimal coste = rsAux.getBigDecimal("coste");;
				int stock = rsAux.getInt("stock");
				Producto producto = new Producto(codigoProducto, nombreProducto, udm, precioVenta, tieneIva, coste, cate, stock);
				
				int codigoDetalle = rsAux.getInt("codigo_pedido");
				int cantidadSolicitada = rsAux.getInt("cantidad_solicitada");
				BigDecimal Subtotal = rsAux.getBigDecimal("subtotal");
				int cantidadRecibida = rsAux.getInt("cantidad_recibida");
				DetallePedidos  detalle = new DetallePedidos(codigoDetalle, null, producto, cantidadSolicitada, Subtotal, cantidadRecibida);
				listDetalle.add(detalle);
				
			}
			p.setDetalles(listDetalle);
			pedidos.add(p);
		}
	} catch (KrakedevException e) {
		e.printStackTrace();
		throw e;
	} catch (SQLException e) {
		e.printStackTrace();
		throw new KrakedevException("Error al consultar. Detalle:"+e.getMessage());
	}
	
	
	
	return pedidos;
	
}
}
