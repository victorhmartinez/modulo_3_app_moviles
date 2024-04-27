package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.intenvarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Proveedores;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("proveedores")
public class ServiciosProveedores {
	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
public Response buscar(@PathParam("sub") String subcadena){
	ProveedoresBDD provBDD = new ProveedoresBDD();
	ArrayList<Proveedores> proveedores = null;
	try {
		proveedores = provBDD.buscar(subcadena);
		return Response.ok(proveedores).build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
public Response crear(Proveedores proveedor){
	ProveedoresBDD provBDD = new ProveedoresBDD();
	
	try {
		provBDD.crear(proveedor);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
}
