package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVentas {
private int codigoDetalle;
private CabeceraVentas cabecera;
private Producto producto;
private int cantidad;
private BigDecimal precioVenta;
private BigDecimal subtotal;
private BigDecimal subtotalConIva;

public DetalleVentas() {
	
}

public DetalleVentas(int codigoDetalle, CabeceraVentas cabecera, Producto producto, int cantidad,
		BigDecimal precioVenta, BigDecimal subtotal, BigDecimal subtotalConIva) {
	super();
	this.codigoDetalle = codigoDetalle;
	this.cabecera = cabecera;
	this.producto = producto;
	this.cantidad = cantidad;
	this.precioVenta = precioVenta;
	this.subtotal = subtotal;
	this.subtotalConIva = subtotalConIva;
}

public int getCodigoDetalle() {
	return codigoDetalle;
}

public void setCodigoDetalle(int codigoDetalle) {
	this.codigoDetalle = codigoDetalle;
}

public CabeceraVentas getCabecera() {
	return cabecera;
}

public void setCabecera(CabeceraVentas cabecera) {
	this.cabecera = cabecera;
}

public Producto getProducto() {
	return producto;
}

public void setProducto(Producto producto) {
	this.producto = producto;
}

public int getCantidad() {
	return cantidad;
}

public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
}

public BigDecimal getPrecioVenta() {
	return precioVenta;
}

public void setPrecioVenta(BigDecimal precioVenta) {
	this.precioVenta = precioVenta;
}

public BigDecimal getSubtotal() {
	return subtotal;
}

public void setSubtotal(BigDecimal subtotal) {
	this.subtotal = subtotal;
}

public BigDecimal getSubtotalConIva() {
	return subtotalConIva;
}

public void setSubtotalConIva(BigDecimal subtotalConIva) {
	this.subtotalConIva = subtotalConIva;
}

@Override
public String toString() {
	return "DetalleVentas [codigoDetalle=" + codigoDetalle + ", cabecera=" + cabecera + ", producto=" + producto
			+ ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + ", subtotal=" + subtotal + ", subtotalConIva="
			+ subtotalConIva + "]";
}


}
