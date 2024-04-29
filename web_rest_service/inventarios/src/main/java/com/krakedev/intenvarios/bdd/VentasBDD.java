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

import com.krakedev.inventarios.entidades.CabeceraVentas;
import com.krakedev.inventarios.entidades.DetalleVentas;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {
	public void insertar(CabeceraVentas ventas) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psActualizar = null;
		PreparedStatement psHistorial = null;
		ResultSet rsClave=null;
		int codigoGenerado=0;
		
		Date fechaActual = new Date();
		java.sql.Date fechaSql = new java.sql.Date(fechaActual.getTime());
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into cabecera_ventas (fecha_venta,total_sin_iva,iva,total) "
					+ "values (?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, fechaSql);
			BigDecimal totalSinIva= new BigDecimal(0);
			BigDecimal iva= new BigDecimal(0);
			BigDecimal total= new BigDecimal(0);
			ps.setBigDecimal(2, totalSinIva);
			ps.setBigDecimal(3, iva);
			ps.setBigDecimal(4, total);
			
			ps.executeUpdate();
			
			rsClave=ps.getGeneratedKeys();
			
			if(rsClave.next()) {
				codigoGenerado= rsClave.getInt(1);
			}
			System.out.println("CODIGO GENERADO >>>"+codigoGenerado);
		
			ArrayList<DetalleVentas> detalleVentas=ventas.getDetalleVentas();
			DetalleVentas detVent;
			BigDecimal totalSinIvaCabecera=new BigDecimal(0);
			BigDecimal ivaCabecera=new BigDecimal(0);
			BigDecimal totalCabecera=new BigDecimal(0);
			for (int i = 0; i < detalleVentas.size(); i++) {
				detVent=detalleVentas.get(i);
				psDet=con.prepareStatement("insert into detalle_ventas (cabecera,producto,cantidad,precio_venta,subtotal,subtotal_con_iva) "
				+ "values (?,?,?,?,?,?);");
				psDet.setInt(1, codigoGenerado);
				psDet.setInt(2,detVent.getProducto().getCodigoProducto() );
				psDet.setInt(3,detVent.getCantidad() );
				//Precio venta
				
				BigDecimal pv=detVent.getProducto().getPrecioDeVenta();
				psDet.setBigDecimal(4, pv);
				BigDecimal cantidad = new BigDecimal(detVent.getCantidad());
				//Subtotal
				BigDecimal subtototal= pv.multiply(cantidad);
				psDet.setBigDecimal(5,subtototal );
				//Calculamos el iva solo si es que tiene
					if(detVent.getProducto().isTieneIva()) {
						BigDecimal ivaTransformado = new BigDecimal(1.12);
						BigDecimal subtotalConIva=subtototal.multiply(ivaTransformado);
						psDet.setBigDecimal(6,subtotalConIva );
						ivaCabecera=ivaCabecera.add(subtotalConIva);
					}else {
						psDet.setBigDecimal(6,subtototal );
						totalSinIvaCabecera=totalSinIvaCabecera.add(subtototal);
					}
				psDet.executeUpdate();
			
				
				/*
				 * Actualizamos el historial
				 * */
				psHistorial = con.prepareStatement("insert into historial_stock (fecha,referencia_c_pedido,producto,cantidad) "
						+ "values (?,?,?,?);");
				psHistorial.setTimestamp(1, fechaHoraActual);
				psHistorial.setString(2, "Vendido "+codigoGenerado);
				psHistorial.setInt(3, detVent.getProducto().getCodigoProducto());
				psHistorial.setInt(4, detVent.getCantidad()*-1);
				
				psHistorial.executeUpdate();
				
			}
			//Calculamos los parametros a actualizar en la cabecera
			totalCabecera=totalSinIvaCabecera.add(ivaCabecera);
			/*
			 * Actualizamos la cabecera
			 * 
			 * */
			psActualizar=con.prepareStatement("update cabecera_ventas set total_sin_iva=?,iva=?,total=? "
					+ "where codigo_cab_ventas = ?");
			psActualizar.setBigDecimal(1, totalSinIvaCabecera);
			psActualizar.setBigDecimal(2, ivaCabecera);
			psActualizar.setBigDecimal(3, totalCabecera);
			psActualizar.setInt(4, codigoGenerado);
			psActualizar.executeUpdate();
			
			
			
		
			
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar la venta: "+e.getMessage());
		}
	}
}
