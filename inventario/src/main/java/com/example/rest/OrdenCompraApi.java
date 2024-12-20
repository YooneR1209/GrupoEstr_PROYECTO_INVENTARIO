package com.example.rest;

import controller.dao.OrdenCompraServicies;

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
    public Response save(HashMap map) {
        // todo
        // Validation

        HashMap res = new HashMap<>();

        try {

            OrdenCompraServicies ocs = new OrdenCompraServicies();
            ocs.getOrdenCompra().setNro_OrdenCompra(map.get("nro_OrdenCompra").toString());
            ocs.getOrdenCompra().setFechaCompra(map.get("fechaCompra").toString());
            ocs.getOrdenCompra().setCedula_Distribuidor(map.get("cedula_Distribuidor").toString());
            ocs.getOrdenCompra().setLoteList(map.get("loteList").toString());
            ocs.getOrdenCompra().setTotalCompra(Float.parseFloat(map.get("totalCompra").toString()));

            ocs.save();

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

            OrdenCompraServicies ocs = new OrdenCompraServicies();
            ocs.setOrdenCompra(ocs.get(Integer.parseInt(map.get("id").toString())));
            ocs.getOrdenCompra().setNro_OrdenCompra(map.get("nro_OrdenCompra").toString());
            ocs.getOrdenCompra().setFechaCompra(map.get("fechaCompra").toString());
            ocs.getOrdenCompra().setCedula_Distribuidor(map.get("cedula_Distribuidor").toString());
            ocs.getOrdenCompra().setLoteList(map.get("loteList").toString());
            ocs.getOrdenCompra().setTotalCompra(Float.parseFloat(map.get("totalCompra").toString()));

            ocs.update();

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

    @Path("/delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrdenCompra(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            OrdenCompraServicies fs = new OrdenCompraServicies();

            boolean OrdenCompraDeleted = fs.delete(id); // Intentamos eliminar el OrdenCompra

            if (OrdenCompraDeleted) {
                res.put("message", "OrdenCompra y Generador eliminados exitosamente");
                return Response.ok(res).build();

            } else {

                res.put("message", "OrdenCompra no encontrada o no eliminada"); // Si no se eliminó, enviar un error 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            }
        } catch (Exception e) {

            res.put("message", "Error al intentar eliminar la OrdenCompra"); // En caso de error, devolver una respuesta
                                                                             // de
                                                                             // error interno
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
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

}
