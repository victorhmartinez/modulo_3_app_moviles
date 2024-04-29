package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CabeceraVentas {
private int codigoCabecera;
private Date fechaVenta;
private BigDecimal totalSinIva;
private BigDecimal iva;
private BigDecimal total;
private  ArrayList<DetalleVentas> detalleVentas;
public CabeceraVentas(int codigoCabecera, Date fechaVenta, BigDecimal totalSinIva, BigDecimal iva, BigDecimal total) {
	super();
	this.codigoCabecera = codigoCabecera;
	this.fechaVenta = fechaVenta;
	this.totalSinIva = totalSinIva;
	this.iva = iva;
	this.total = total;
	
}
public CabeceraVentas() {
}
public int getCodigoCabecera() {
	return codigoCabecera;
}
public void setCodigoCabecera(int codigoCabecera) {
	this.codigoCabecera = codigoCabecera;
}
public Date getFechaVenta() {
	return fechaVenta;
}
public void setFechaVenta(Date fechaVenta) {
	this.fechaVenta = fechaVenta;
}
public BigDecimal getTotalSinIva() {
	return totalSinIva;
}
public void setTotalSinIva(BigDecimal totalSinIva) {
	this.totalSinIva = totalSinIva;
}
public BigDecimal getIva() {
	return iva;
}
public void setIva(BigDecimal iva) {
	this.iva = iva;
}
public BigDecimal getTotal() {
	return total;
}
public void setTotal(BigDecimal total) {
	this.total = total;
}


public ArrayList<DetalleVentas> getDetalleVentas() {
	return detalleVentas;
}
public void setDetalleVentas(ArrayList<DetalleVentas> detalleVentas) {
	this.detalleVentas = detalleVentas;
}
@Override
public String toString() {
	return "CabeceraVentas [codigoCabecera=" + codigoCabecera + ", fechaVenta=" + fechaVenta + ", totalSinIva="
			+ totalSinIva + ", iva=" + iva + ", total=" + total + "]";
}

}
