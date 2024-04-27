package com.krakedev.intenvarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedores;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;



public class ProveedoresBDD {
	public ArrayList<Proveedores> buscar (String subcadena) throws KrakedevException{
		ArrayList<Proveedores> proveedores = new ArrayList<Proveedores>();
		Connection con= null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		Proveedores proveedor=null;
		try {
			con= ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select ruc_cedula,tipo_documento,nombre,telefono,correo,direccion "
					+ "from proveedor "
					+ "where upper(nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
		rs=ps.executeQuery();
		while(rs.next()) {
			String identificador=rs.getString("ruc_cedula");
			String tipoDocumento=rs.getString("tipo_documento");
			String nombre=rs.getString("nombre");
			String telefono=rs.getString("telefono");
			String direccion=rs.getString("correo");
			String correo=rs.getString("direccion");

			proveedor= new Proveedores(identificador, tipoDocumento, nombre,telefono,direccion,correo);
			proveedores.add(proveedor);

		}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			throw new KrakedevException("Error al consultar. Detalle:"+e.getMessage());
		}
		return proveedores;
	}
}
