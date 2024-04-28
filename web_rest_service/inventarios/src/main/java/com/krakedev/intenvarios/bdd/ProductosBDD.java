package com.krakedev.intenvarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;

import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
	public ArrayList<Producto> buscar (String subcadena) throws KrakedevException{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con= null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		Producto producto=null;
		try {
			con= ConexionBDD.obtenerConexion();
		
			ps=con.prepareStatement("select prod.codigo_producto,prod.nombre as nombre_producto,udm.descripcion,udm,"
					+ "cast(precio_de_venta as decimal(6,2)),"
					+ "tiene_iva,cast(coste as decimal(5,4)),cat.nombre as nombre_categoria,categoria,stock "
					+ "from productos prod,unidades_medidas udm, categorias cat "
					+ "where prod.udm = udm.codigo_unidades_categoria "
					+ "and  cat.codigo_cat=prod.categoria "
					+ "and upper(prod.nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
		while(rs.next()) {
			int codigoProducto=rs.getInt("codigo_producto");
			String nombreProducto=rs.getString("nombre_producto");
			BigDecimal precioDeVenta=rs.getBigDecimal("precio_de_venta");
			BigDecimal coste=rs.getBigDecimal("coste");
			boolean tieneIva=rs.getBoolean("tiene_iva");
			int stock=rs.getInt("stock");
			
			//Informacion unidad medida
			String nombreUDM=rs.getString("udm");
			String descripcioUDM=rs.getString("descripcion");
			UnidadDeMedida udm = new UnidadDeMedida();
			udm.setNombre(nombreUDM);
			udm.setDescirpcion(descripcioUDM);
			
			//Informacion de categoria
			String codigoCategoria=rs.getString("categoria");
			String nombreCategoria=rs.getString("nombre_categoria");
			Categoria categoria = new Categoria();
			categoria.setCodigoCategoria(codigoCategoria);
			categoria.setNombre(nombreCategoria);
			//Creamos el producto con la informacion recuperada
			producto= new Producto(codigoProducto, nombreProducto, udm, precioDeVenta, tieneIva, coste, categoria, stock);
			productos.add(producto);

		}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			throw new KrakedevException("Error al consultar el producto. Detalle:"+e.getMessage());
		}
		return productos;
	}
}
