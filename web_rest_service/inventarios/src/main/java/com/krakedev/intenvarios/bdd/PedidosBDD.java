package com.krakedev.intenvarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedidos;
import com.krakedev.inventarios.entidades.Pedido;
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
	
	try {
		con = ConexionBDD.obtenerConexion();
		ps = con.prepareStatement("update cabecera_pedidos set "
				+ "estado = ? where codigo_cabecera =?;");
		ps.setString(1, "R");
		ps.setInt(2,pedido.getCodigo());
		
		ps.executeUpdate();
				
		ArrayList<DetallePedidos> detallesPedido=pedido.getDetalles();
		
		
		for (int i = 0; i < detallesPedido.size(); i++) {
			DetallePedidos det = detallesPedido.get(i);
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
		}
		
	
		
	} catch (KrakedevException e) {
		e.printStackTrace();
		throw e;
	} catch (SQLException e) {
		e.printStackTrace();
		throw new KrakedevException("Error al actulizar el pedido: "+e.getMessage());
	}
}
}
