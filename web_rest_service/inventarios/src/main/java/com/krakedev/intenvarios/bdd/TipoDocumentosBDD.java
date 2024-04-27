package com.krakedev.intenvarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDocumentosBDD {
	public ArrayList<TipoDocumentos> recuperar () throws KrakedevException{
		ArrayList<TipoDocumentos> tiposDocumentos = new ArrayList<TipoDocumentos>();
		Connection con= null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		TipoDocumentos tipoDocumento=null;
		try {
			con= ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select codigo_documento, descripcion "
					+ "from tipo_documento " );
			
		rs=ps.executeQuery();
		while(rs.next()) {
			String codigo_documento=rs.getString("codigo_documento");
			String descripcion=rs.getString("descripcion");
			tipoDocumento= new TipoDocumentos(codigo_documento,descripcion);
			tiposDocumentos.add(tipoDocumento);

		}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			throw new KrakedevException("Error al consultar. Detalle:"+e.getMessage());
		}
		return tiposDocumentos;
	}
}
