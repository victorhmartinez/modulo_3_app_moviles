package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.intenvarios.bdd.TipoDocumentosBDD;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakedevException;
@Path("tiposdocumentos")
public class ServicioTipoDocumentos {
	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
public Response recuperar(){
	TipoDocumentosBDD tipoDocBDD = new TipoDocumentosBDD();
	ArrayList<TipoDocumentos> tiposDocumentos = null;
	try {
		tiposDocumentos = tipoDocBDD.recuperar();
		return Response.ok(tiposDocumentos).build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
}
