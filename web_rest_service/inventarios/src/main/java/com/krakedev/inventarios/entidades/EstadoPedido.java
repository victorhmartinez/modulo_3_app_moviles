package com.krakedev.inventarios.entidades;

public class EstadoPedido {
private String estadoPedido;
private String descripcion;
public EstadoPedido() {
	
}
public EstadoPedido(String estadoPedido, String descripcion) {
	super();
	this.estadoPedido = estadoPedido;
	this.descripcion = descripcion;
}

public String getEstadoPedido() {
	return estadoPedido;
}

public void setEstadoPedido(String estadoPedido) {
	this.estadoPedido = estadoPedido;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

@Override
public String toString() {
	return "EstadoPedido [estadoPedido=" + estadoPedido + ", descripcion=" + descripcion + "]";
}

}
