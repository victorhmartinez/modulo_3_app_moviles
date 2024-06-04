package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.intenvarios.bdd.CategoriaBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakedevException;
@Path("categorias")
public class ServicioCategoria {
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
public Response crear(Categoria categoria){
	CategoriaBDD catBDD = new CategoriaBDD();
	
	try {
		catBDD.crear(categoria);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)	
public Response actulizar(Categoria categoria){
	CategoriaBDD catBDD = new CategoriaBDD();
	
	try {
		catBDD.actualizar(categoria);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
	@Path("recuperarTodos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
public Response recuperarTodos(){
	CategoriaBDD catBDD = new CategoriaBDD();
	ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	try {
		categorias=catBDD.recuperar();
		return Response.ok(categorias).build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
	
}
