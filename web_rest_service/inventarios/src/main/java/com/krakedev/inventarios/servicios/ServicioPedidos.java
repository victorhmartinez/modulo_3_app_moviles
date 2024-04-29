package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.intenvarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakedevException;

@Path("pedidos")
public class ServicioPedidos {
	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
public Response crear(Pedido pedido){
	PedidosBDD pedidosBDD = new PedidosBDD();
	
	try {
		pedidosBDD.insertar(pedido);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
}
