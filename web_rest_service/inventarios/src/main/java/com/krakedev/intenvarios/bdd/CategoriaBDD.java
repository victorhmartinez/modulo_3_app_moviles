package com.krakedev.intenvarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;

import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriaBDD {
	public void crear(Categoria categoria) throws KrakedevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			if (categoria.getCategoriaPadre() == null) {
				ps = con.prepareStatement("insert into categorias (nombre,categoria_padre) " + "values (?,null) ");
				ps.setString(1, categoria.getNombre());
			} else {
				ps = con.prepareStatement("insert into categorias (nombre,categoria_padre) " + "values (?,?) ");
				ps.setString(1, categoria.getNombre());
				ps.setInt(2, categoria.getCategoriaPadre().getCodigoCategoria());
			}

			ps.executeUpdate();

		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			throw new KrakedevException("Error al insertar una categoria. Detalle:" + e.getMessage());
		}
	}

	public void actualizar(Categoria categoria) throws KrakedevException {
		Connection con = null;

		try {
			con = ConexionBDD.obtenerConexion();
			PreparedStatement ps = null;
			if (categoria.getCategoriaPadre() == null) {
				ps = con.prepareStatement(
						"update categorias set nombre=?, categoria_padre= null" + " where codigo_cat=?");
				ps.setString(1, categoria.getNombre());
				ps.setInt(2, categoria.getCodigoCategoria());
			} else {
				ps = con.prepareStatement("update categorias set nombre=?, categoria_padre=?" + " where codigo_cat=?");
				ps.setString(1, categoria.getNombre());
				ps.setInt(2, categoria.getCategoriaPadre().getCodigoCategoria());
				ps.setInt(3, categoria.getCodigoCategoria());
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al actualizar la categoria Detalle:" + e.getMessage());
		} catch (KrakedevException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public ArrayList<Categoria> recuperar() throws KrakedevException {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Categoria categoria = null;
		try {
			con = ConexionBDD.obtenerConexion();

			ps = con.prepareStatement("select cat.codigo_cat, cat.nombre, cat.categoria_padre, "
					+ "(select nombre from categorias where codigo_cat = cat.categoria_padre) AS nombre_padre "
					+ "from categorias cat;");

			rs = ps.executeQuery();
			while (rs.next()) {
				int codigoCategoria = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");
				int categoriaPadreCodigo = rs.getInt("categoria_padre");
				String nombreCategoriaPadre = rs.getString("nombre_padre");
				if(categoriaPadreCodigo<=0 || nombreCategoriaPadre.equals(null) ) {
					categoria = new Categoria(codigoCategoria, nombre, null);
					categorias.add(categoria);

				}else {
					Categoria categoriaPadre = new Categoria(categoriaPadreCodigo, nombreCategoriaPadre, null);
					categoria = new Categoria(codigoCategoria, nombre, categoriaPadre);
					categorias.add(categoria);
				}
				

			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			throw new KrakedevException("Error al consultar. Detalle:" + e.getMessage());
		}
		return categorias;
	}
}
