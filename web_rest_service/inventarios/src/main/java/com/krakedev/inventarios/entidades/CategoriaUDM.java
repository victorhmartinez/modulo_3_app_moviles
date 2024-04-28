package com.krakedev.inventarios.entidades;

public class CategoriaUDM {
private String codigoCategoria;
private String nombre;
public CategoriaUDM(String codigoCategoria, String nombre) {
	super();
	this.codigoCategoria = codigoCategoria;
	this.nombre = nombre;
}
public CategoriaUDM() {
	
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
@Override
public String toString() {
	return "CategoriaUDM [codigoCategoria=" + codigoCategoria + ", nombre=" + nombre + "]";
}

}
