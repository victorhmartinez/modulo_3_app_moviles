package com.krakedev.inventarios.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.intenvarios.bdd.CategoriaBDD;
import com.krakedev.intenvarios.bdd.TipoDocumentosBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;
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
	
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
public Response crear(TipoDocumentos tipoDocuemento){
		TipoDocumentosBDD tipoDocBDD = new TipoDocumentosBDD();
	
	try {	
		tipoDocBDD.crear(tipoDocuemento);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
}
