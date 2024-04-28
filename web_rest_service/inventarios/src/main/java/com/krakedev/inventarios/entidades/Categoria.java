package com.krakedev.inventarios.entidades;

public class Categoria {
private String codigoCategoria;
private String nombre;
private Categoria categoriaPadre;
public Categoria(String codigoCategoria, String nombre, Categoria categoriaPadre) {
	
	this.codigoCategoria = codigoCategoria;
	this.nombre = nombre;
	this.categoriaPadre = categoriaPadre;
}
public Categoria() {
	

}
public String getCodigoCategoria() {
	return codigoCategoria;
}
public void setCodigoCategoria(String codigoCategoria) {
	this.codigoCategoria = codigoCategoria;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public Categoria getCategoriaPadre() {
	return categoriaPadre;
}
public void setCategoriaPadre(Categoria categoriaPadre) {
	this.categoriaPadre = categoriaPadre;
}
@Override
public String toString() {
	return "Categoria [codigoCategoria=" + codigoCategoria + ", nombre=" + nombre + ", categoriaPadre=" + categoriaPadre
			+ "]";
}

}
