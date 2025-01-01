package com.example.rest;

import controller.dao.OrdenCompraServicies;
import models.OrdenCompra;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("ordenCompra")
public class OrdenCompraApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {

        HashMap map = new HashMap<>();
        OrdenCompraServicies ocs = new OrdenCompraServicies();
        map.put("msg", "Ok");
        map.put("data", ocs.listAll().toArray());

        if (ocs.listAll().isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        OrdenCompraServicies ocs = new OrdenCompraServicies();
        try {
            ocs.setOrdenCompra(ocs.get(id));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        map.put("msg", "Ok");
        map.put("data", ocs.getOrdenCompra());
        if (ocs.getOrdenCompra().getId() == null) {
            map.put("data", "No existe la OrdenCompra con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(OrdenCompra ordenCompra) {
        // todo
        // Validation

        HashMap res = new HashMap<>();

        try {

            OrdenCompraServicies ocs = new OrdenCompraServicies();
            ocs.save(ordenCompra); // Guardamos la factura

            ocs.updateLotes(ordenCompra);

            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        OrdenCompraServicies ocs = new OrdenCompraServicies();

        map.put("msg", "Ok");
        map.put("data", ocs.getOrdenCompra());
        return Response.ok(map).build();
    }

    // @Path("/list/order/{attribute}/{type}/{metodo}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getOrder(@PathParam("attribute") String attribute,
    // @PathParam("type") Integer type, @PathParam("metodo") Integer metodo) {
    // HashMap map = new HashMap<>();
    // OrdenCompraServicies ocs = new OrdenCompraServicies();

    // map.put("msg", "Ok");
    // map.put("data", ocs.order(attribute, type, metodo).toArray());

    // if (ocs.order(attribute, type, metodo).isEmpty()) {
    // map.put("data", new Object[]{});
    // return Response.status(Status.BAD_REQUEST).entity(map).build();
    // }

    // return Response.ok(map).build();
    // }

    // @Path("/list/search/id/lote/{ordenCompraId}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getLotesIdOrdenCompra(@PathParam("ordenCompraId") Integer id)
    // {
    // HashMap map = new HashMap<>();
    // OrdenCompraServicies ocs = new OrdenCompraServicies();
    // try {
    // map.put("msg", "Ok");
    // map.put("data", ocs.buscar_Id_Lotes(id));
    // if (map.isEmpty()) {
    // map.put("data", "No existe el OrdenCompra");
    // return
    // Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin",
    // "*").entity(map)
    // .build();
    // }
    // } catch (Exception e) {
    // System.out.println("Error" + e);

    // }
    // return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();
    // }

    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleOptions() {
        return Response.ok().build();
    }

}
