package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.dao.OrdenVentaServices;

@Path("ordenVenta")
public class OrdenVentaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrder() {

        HashMap map = new HashMap<>();
        OrdenVentaServices ls = new OrdenVentaServices();
        map.put("msg", "Ok");

        map.put("data", ls.listAll().toArray());
        if (ls.listAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();

    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        OrdenVentaServices ls = new OrdenVentaServices();
        try {
            ls.setOrdenVenta(ls.get(id));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        map.put("msg", "Ok");
        map.put("data", ls.getOrdenVenta());
        if (ls.getOrdenVenta().getIdVenta() == null) {
            map.put("data", "No existe la Lote con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        //todo
        //Validation

        HashMap res = new HashMap<>();

        try {

            OrdenVentaServices ls = new OrdenVentaServices();/*
            DetalleVentaServices detalleVentaServices = new DetalleVentaServices();
            detalleVentaServices.setDetalleVenta(detalleVentaServices.get(Integer.parseInt(map.get("detalle").toString()))); */
            ls.getOrdenVenta().setN_OrdenVenta(map.get("n_ordenVenta").toString());
            ls.getOrdenVenta().setSubtotalVenta(Float.parseFloat(map.get("subtotalVenta").toString()));
            ls.getOrdenVenta().setTotalVenta(Float.parseFloat(map.get("totalVenta").toString()));
            ls.getOrdenVenta().setIva(Float.parseFloat(map.get("iva").toString()));
            ls.getOrdenVenta().setFechaVenta(map.get("fechaVenta").toString());/*
            ls.getOrdenVenta().setIdDetalleVenta(detalleVentaServices.getDetalleVenta().getIdDetalleVenta()); */

            ls.save();

            res.put("msg", "Ok");
            res.put("data", "Guardado correctamente");
            return Response.ok(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) {

        HashMap res = new HashMap<>();

        try {

            OrdenVentaServices ls = new OrdenVentaServices();
            ls.getOrdenVenta().setN_OrdenVenta(map.get("n_ordenVenta").toString());
            ls.getOrdenVenta().setSubtotalVenta(Float.parseFloat(map.get("subtotalVenta").toString()));
            ls.getOrdenVenta().setTotalVenta(Float.parseFloat(map.get("totalVenta").toString()));
            ls.getOrdenVenta().setIva(Float.parseFloat(map.get("iva").toString()));
            ls.getOrdenVenta().setFechaVenta(map.get("fechaVenta").toString());

            ls.update();

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
        OrdenVentaServices ls = new OrdenVentaServices();

        map.put("msg", "Ok");
        map.put("data", ls.getOrdenVenta());
        return Response.ok(map).build();
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLote(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            OrdenVentaServices fs = new OrdenVentaServices();

            boolean LoteDeleted = fs.delete(id - 1);       // Intentamos eliminar el Lote

            if (LoteDeleted) {
                res.put("message", "Lote y Generador eliminados exitosamente");
                return Response.ok(res).build();

            } else {

                res.put("message", "Lote no encontrada o no eliminada");     // Si no se eliminó, enviar un error 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            }
        } catch (Exception e) {

            res.put("message", "Error al intentar eliminar la Lote"); // En caso de error, devolver una respuesta de error interno
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    // @Path("/list/order/{attribute}/{type}/{metodo}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type, @PathParam("metodo") Integer metodo) {
    //     HashMap map = new HashMap<>();
    //     OrdenVentaServices ls = new OrdenVentaServices();
    //     map.put("msg", "Ok");
    //     map.put("data", ls.order(attribute, type, metodo).toArray());
    //     if (ls.order(attribute, type, metodo).isEmpty()) {
    //         map.put("data", new Object[]{});
    //        return Response.status(Status.BAD_REQUEST).entity(map).build();
    //     }
    //     return Response.ok(map).build();
    // }
    //IMPLEMENTAR BUSQEDA POR N DE ORDEN DE VENTA////
}
