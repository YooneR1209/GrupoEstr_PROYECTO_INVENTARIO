package com.example.rest;

import controller.dao.LoteServicies;
import controller.dao.OrdenCompraServicies;
import models.OrdenCompra;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        HashMap<String, Object> res = new HashMap<>();

        try {
            OrdenCompraServicies ocs = new OrdenCompraServicies();

            System.out.println("Recibimos la peticion de compraApi.");

            // Validar si la fecha de compra es válida
            if (!ocs.isFechaCompraValida(ordenCompra.getFechaCompra())) {
                res.put("msg", "Error");
                res.put("data",
                        "La fecha de compra no puede ser mayor que la fecha actual o tiene un formato inválido.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Verificar si el número de orden de compra es único antes de guardar
            if (!ocs.isUnique(ordenCompra.getNro_OrdenCompra())) {
                res.put("msg", "Error");
                res.put("data", "El número de orden de compra '" + ordenCompra.getNro_OrdenCompra() + "' ya existe.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            if (ordenCompra.getLoteList() == null || ordenCompra.getLoteList().length == 0) {
                res.put("msg", "Error");
                res.put("data", "La lista de lotes no puede estar vacía.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Guardar la orden de compra
            ocs.save(ordenCompra);

            // Actualizar los lotes asociados
            ocs.updateLotes(ordenCompra);

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
        OrdenCompraServicies ocs = new OrdenCompraServicies();

        map.put("msg", "Ok");
        map.put("data", ocs.getOrdenCompra());
        return Response.ok(map).build();
    }

    @Path("/list/search/nro_OrdenCompra/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdenCompra(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();

        try {
            OrdenCompraServicies fs = new OrdenCompraServicies();
            fs.setOrdenCompra(fs.buscar_nro_OrdenCompra(texto));
            map.put("msg", "Ok");
            map.put("data", fs.getOrdenCompra());
            if (fs.getOrdenCompra().getId() == null) {
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
        OrdenCompraServicies ps = new OrdenCompraServicies();
        try {
            map.put("msg", "Ok");
            map.put("data", ps.order(attribute, type).toArray());
        } catch (Exception e) {
            System.out.println("Error en order OrdenCompra" + e);
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
