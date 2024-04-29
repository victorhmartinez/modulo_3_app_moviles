package com.krakedev.inventarios.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
private int codigo;
private Proveedores proveedor;
private Date fecha;
private EstadoPedido estado;
private ArrayList<DetallePedidos> detalles;


public Pedido(int codigo, Proveedores proveedor, Date fecha, EstadoPedido estado) {
	super();
	this.codigo = codigo;
	this.proveedor = proveedor;
	this.fecha = fecha;
	this.estado = estado;
}
public Pedido() {
	
}

public int getCodigo() {
	return codigo;
}


public void setCodigo(int codigo) {
	this.codigo = codigo;
}


public Proveedores getProveedor() {
	return proveedor;
}


public void setProveedor(Proveedores proveedor) {
	this.proveedor = proveedor;
}


public Date getFecha() {
	return fecha;
}


public void setFecha(Date fecha) {
	this.fecha = fecha;
}


public EstadoPedido getEstado() {
	return estado;
}


public void setEstado(EstadoPedido estado) {
	this.estado = estado;
}
public ArrayList<DetallePedidos> getDetalles() {
	return detalles;
}
public void setDetalles(ArrayList<DetallePedidos> detalles) {
	this.detalles = detalles;
}

@Override
public String toString() {
	return "Pedido [codigo=" + codigo + ", proveedor=" + proveedor + ", fecha=" + fecha + ", estado=" + estado + "]";
}


}
