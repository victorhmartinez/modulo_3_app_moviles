package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class Producto {
private int codigoProducto;
private String nombre;
private UnidadDeMedida unidadMedida;
private BigDecimal precioDeVenta;
private boolean tieneIva;
private BigDecimal coste;
private Categoria categoriaProducto;
private int stock;


public Producto() {
	super();
}
public Producto(int codigoProducto, String nombre, UnidadDeMedida unidadMedida, BigDecimal precioDeVenta,
		boolean tieneIva, BigDecimal coste, Categoria categoriaProducto, int stock) {
	super();
	this.codigoProducto = codigoProducto;
	this.nombre = nombre;
	this.unidadMedida = unidadMedida;
	this.precioDeVenta = precioDeVenta;
	this.tieneIva = tieneIva;
	this.coste = coste;
	this.categoriaProducto = categoriaProducto;
	this.stock = stock;
}
public int getCodigoProducto() {
	return codigoProducto;
}
public void setCodigoProducto(int codigo_producto) {
	this.codigoProducto = codigo_producto;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public UnidadDeMedida getUnidadMedida() {
	return unidadMedida;
}
public void setUnidadMedida(UnidadDeMedida unidadMedida) {
	this.unidadMedida = unidadMedida;
}
public BigDecimal getPrecioDeVenta() {
	return precioDeVenta;
}
public void setPrecioDeVenta(BigDecimal precioDeVenta) {
	this.precioDeVenta = precioDeVenta;
}
public boolean isTieneIva() {
	return tieneIva;
}
public void setTieneIva(boolean tieneIva) {
	this.tieneIva = tieneIva;
}
public BigDecimal getCoste() {
	return coste;
}
public void setCoste(BigDecimal coste) {
	this.coste = coste;
}
public Categoria getCategoriaProducto() {
	return categoriaProducto;
}
public void setCategoriaProducto(Categoria categoriaProducto) {
	this.categoriaProducto = categoriaProducto;
}
public int getStock() {
	return stock;
}
public void setStock(int stock) {
	this.stock = stock;
}
@Override
public String toString() {
	return "Producto [codigo_producto=" + codigoProducto + ", nombre=" + nombre + ", unidadMedida=" + unidadMedida
			+ ", precioDeVenta=" + precioDeVenta + ", tieneIva=" + tieneIva + ", coste=" + coste
			+ ", categoriaProducto=" + categoriaProducto + ", stock=" + stock + "]";
}


}
