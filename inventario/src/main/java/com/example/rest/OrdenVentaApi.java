package com.example.rest;

import controller.dao.OrdenVentaServicies;
import models.OrdenVenta;

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

@Path("ordenVenta")
public class OrdenVentaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {

        HashMap map = new HashMap<>();
        OrdenVentaServicies ocs = new OrdenVentaServicies();
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
        OrdenVentaServicies ocs = new OrdenVentaServicies();
        try {
            ocs.setOrdenVenta(ocs.get(id));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        map.put("msg", "Ok");
        map.put("data", ocs.getOrdenVenta());
        if (ocs.getOrdenVenta().getId() == null) {
            map.put("data", "No existe la OrdenVenta con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(OrdenVenta ordenVenta) {
        HashMap<String, Object> res = new HashMap<>();
        System.out.println("La venta se ve así:" + ordenVenta.toString());

        try {
            OrdenVentaServicies ocs = new OrdenVentaServicies();
            // Guardar la orden de venta
            ocs.save(ordenVenta);

            // Actualizar los lotes asociados
            ocs.updateLotes(ordenVenta);

            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.getMessage()); // Devuelve el mensaje de la excepción
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        OrdenVentaServicies ocs = new OrdenVentaServicies();

        map.put("msg", "Ok");
        map.put("data", ocs.getOrdenVenta());
        return Response.ok(map).build();
    }

    @Path("/list/search/nro_OrdenVenta/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdenVenta(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();

        try {
            OrdenVentaServicies fs = new OrdenVentaServicies();
            fs.setOrdenVenta(fs.buscar_nro_OrdenVenta(texto));
            map.put("msg", "Ok");
            map.put("data", fs.getOrdenVenta());
            if (fs.getOrdenVenta().getId() == null) {
                map.put("data", "No existe familia con ese id");
                return Response.status(Status.BAD_REQUEST).entity(map).build();
            }
        } catch (Exception e) {
            System.out.println("Error en search codigoLote" + e);
        }
        return Response.ok(map).build();

    }

    @Path("/list/order/{attribute}/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type) {
        HashMap map = new HashMap<>();
        OrdenVentaServicies ps = new OrdenVentaServicies();
        try {
            map.put("msg", "Ok");
            map.put("data", ps.order(attribute, type).toArray());
        } catch (Exception e) {
            System.out.println("Error en order OrdenVenta" + e);
        }

        if (ps.order(attribute, type).isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleOptions() {
        return Response.ok().build();
    }

}
