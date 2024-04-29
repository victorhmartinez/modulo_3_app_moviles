package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.intenvarios.bdd.VentasBDD;
import com.krakedev.inventarios.entidades.CabeceraVentas;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("ventas")
public class ServicioVentas {
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
public Response guardar(CabeceraVentas ventas){
	VentasBDD venBDD = new VentasBDD();
	
	try {
		venBDD.insertar(ventas);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
}
