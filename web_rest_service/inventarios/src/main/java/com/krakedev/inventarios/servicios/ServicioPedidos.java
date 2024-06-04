package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)	
public Response actualizar(Pedido pedido){
	PedidosBDD pedidosBDD = new PedidosBDD();
	
	try {
		pedidosBDD.actualizarPedido(pedido);
		return Response.ok().build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
	@Path("buscar-por-proveedor/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
public Response buscar(@PathParam("sub") String identificador){
	PedidosBDD pedidosBDD = new PedidosBDD();
	ArrayList<Pedido> pedidos = null;
	try {
		pedidos = pedidosBDD.buscarPorProveedor(identificador);
		return Response.ok(pedidos).build();
	} catch (KrakedevException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return Response.serverError().build();
	}
}
	
}
