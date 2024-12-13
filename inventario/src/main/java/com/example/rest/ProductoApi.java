package com.example.rest;

import controller.dao.ProductoServicies;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("producto")
public class ProductoApi {  

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {

        HashMap map = new HashMap<>();
        ProductoServicies ps = new ProductoServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());

        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[]{});
           
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        ProductoServicies ps = new ProductoServicies();
        try {
            ps.setProducto(ps.get(id));
        } catch (Exception e) {
            System.out.println("Error "+e);        
        }
        map.put("msg", "Ok");
        map.put("data", ps.getProducto());
        if (ps.getProducto().getId() == null) {
            map.put("data", "No existe la Producto con ese identificador");
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

            ProductoServicies ps = new ProductoServicies();
            ps.getProducto().setNombre(map.get("nombre").toString());
            ps.getProducto().setTipoProducto(map.get("tipoProducto").toString());
            ps.getProducto().setMarca(map.get("marca").toString());
            ps.getProducto().setDescripcion(map.get("descripcion").toString());


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

            ProductoServicies ps = new ProductoServicies();
            ps.setProducto(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getProducto().setNombre(map.get("nombre").toString());
            ps.getProducto().setTipoProducto(map.get("tipoProducto").toString());
            ps.getProducto().setMarca(map.get("marca").toString());
            ps.getProducto().setDescripcion(map.get("descripcion").toString());
    
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
        ProductoServicies ps = new ProductoServicies();

        map.put("msg", "Ok");
        map.put("data", ps.getProducto());
        return Response.ok(map).build();
    }

    @Path("/delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProducto(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();
    
        try {
            ProductoServicies fs = new ProductoServicies();
            
            boolean ProductoDeleted = fs.delete(id);       // Intentamos eliminar el Producto

            if (ProductoDeleted) {
                res.put("message", "Producto y Generador eliminados exitosamente");
                return Response.ok(res).build();

            } else {
            
                res.put("message", "Producto no encontrada o no eliminada");     // Si no se eliminó, enviar un error 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            }
        } catch (Exception e) {
            
            res.put("message", "Error al intentar eliminar la Producto"); // En caso de error, devolver una respuesta de error interno
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    
    // @Path("/list/order/{attribute}/{type}/{metodo}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type, @PathParam("metodo") Integer metodo) {
    //     HashMap map = new HashMap<>();
    //     ProductoServicies ps = new ProductoServicies();

    //     map.put("msg", "Ok");
    //     map.put("data", ps.order(attribute, type, metodo).toArray());

    //     if (ps.order(attribute, type, metodo).isEmpty()) {
    //         map.put("data", new Object[]{});
    //        return Response.status(Status.BAD_REQUEST).entity(map).build();
    //     }

    //     return Response.ok(map).build();
    // }


}
