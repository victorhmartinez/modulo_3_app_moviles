package com.krakedev.inventarios.entidades;

public class TipoDocumentos {
private String codigoDocumento;
private String descripcion;

public TipoDocumentos() {

}
public TipoDocumentos(String codigoDocumento, String descripcion) {
	super();
	this.codigoDocumento = codigoDocumento;
	this.descripcion = descripcion;
}


public String getCodigoDocumento() {
	return codigoDocumento;
}

public void setCodigoDocumento(String codigoDocumento) {
	this.codigoDocumento = codigoDocumento;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}


@Override
public String toString() {
	return "TipoDocumentos [codigoDocumento=" + codigoDocumento + ", Descripcion=" + descripcion + ", getClass()="
			+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
}

}
