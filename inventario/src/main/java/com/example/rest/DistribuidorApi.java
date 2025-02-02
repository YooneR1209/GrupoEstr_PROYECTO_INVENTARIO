package com.example.rest;

import controller.dao.DistribuidorServicies;

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

@Path("distribuidor")
public class DistribuidorApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {

        HashMap map = new HashMap<>();
        DistribuidorServicies ps = new DistribuidorServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());

        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});

        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        DistribuidorServicies ps = new DistribuidorServicies();
        try {
            ps.setDistribuidor(ps.get(id));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        map.put("msg", "Ok");
        map.put("data", ps.getDistribuidor());
        if (ps.getDistribuidor().getId() == null) {
            map.put("data", "No existe la Distribuidor con ese identificador");
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

            DistribuidorServicies ps = new DistribuidorServicies();
            ps.getDistribuidor().setNombre(map.get("nombre").toString());
            ps.getDistribuidor().setCedula(map.get("cedula").toString());
            ps.getDistribuidor().setCelular(map.get("celular").toString());
            ps.getDistribuidor().setDescripcion(map.get("descripcion").toString());

            if (!ps.isUnique(ps.getDistribuidor().getCedula())) {
                res.put("msg", "Error");
                res.put("data", "Error: Ya existe un distribuidor registrado con ésta cédula");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
            ps.save();

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

            DistribuidorServicies ps = new DistribuidorServicies();
            ps.setDistribuidor(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getDistribuidor().setNombre(map.get("nombre").toString());
            ps.getDistribuidor().setCedula(map.get("cedula").toString());
            ps.getDistribuidor().setCelular(map.get("celular").toString());
            ps.getDistribuidor().setDescripcion(map.get("descripcion").toString());

            ps.update();

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
        DistribuidorServicies ps = new DistribuidorServicies();

        map.put("msg", "Ok");
        map.put("data", ps.getDistribuidor());
        return Response.ok(map).build();
    }

    @Path("/delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDistribuidor(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            DistribuidorServicies fs = new DistribuidorServicies();

            boolean DistribuidorDeleted = fs.delete(id); // Intentamos eliminar el Distribuidor

            if (DistribuidorDeleted) {
                res.put("message", "Distribuidor y Generador eliminados exitosamente");
                return Response.ok(res).build();

            } else {

                res.put("message", "Distribuidor no encontrada o no eliminada"); // Si no se eliminó, enviar un error
                                                                                 // 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            }
        } catch (Exception e) {

            res.put("message", "Error al intentar eliminar la Distribuidor"); // En caso de error, devolver una
                                                                              // respuesta de
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
    // DistribuidorServicies ps = new DistribuidorServicies();

    // map.put("msg", "Ok");
    // map.put("data", ps.order(attribute, type, metodo).toArray());

    // if (ps.order(attribute, type, metodo).isEmpty()) {
    // map.put("data", new Object[]{});
    // return Response.status(Status.BAD_REQUEST).entity(map).build();
    // }

    // return Response.ok(map).build();
    // }

}
