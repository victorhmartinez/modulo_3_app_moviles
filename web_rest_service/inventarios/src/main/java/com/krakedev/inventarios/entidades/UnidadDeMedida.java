package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
private String nombre;
private String descirpcion;
private CategoriaUDM categoriaUDM;
public UnidadDeMedida() {

}
public UnidadDeMedida(String nombre, String descirpcion, CategoriaUDM categoriaUDM) {
	super();
	this.nombre = nombre;
	this.descirpcion = descirpcion;
	this.categoriaUDM = categoriaUDM;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getDescirpcion() {
	return descirpcion;
}
public void setDescirpcion(String descirpcion) {
	this.descirpcion = descirpcion;
}
public CategoriaUDM getCategoriaUDM() {
	return categoriaUDM;
}
public void setCategoriaUDM(CategoriaUDM categoriaUDM) {
	this.categoriaUDM = categoriaUDM;
}
@Override
public String toString() {
	return "UnidadDeMedida [nombre=" + nombre + ", descirpcion=" + descirpcion + ", categoriaUDM=" + categoriaUDM + "]";
}

}
