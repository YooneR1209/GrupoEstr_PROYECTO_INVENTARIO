package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import controller.dao.LoteServicies;
import controller.dao.ProductoServicies;

@Path("lote")
public class LoteApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {

        HashMap map = new HashMap<>();
        LoteServicies ls = new LoteServicies();
        map.put("msg", "Ok");

        try {
            map.put("data", ls.listShowAll());
            if (ls.listAll().isEmpty()) {

                map.put("data", new Object[] {});
            }
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("data", new Object[] {});

            // TODO: handle exception
        }
        return Response.ok(map).build();

    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        LoteServicies ls = new LoteServicies();
        try {
            ls.setLote(ls.get(id));
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        map.put("msg", "Ok");
        map.put("data", ls.getLote());
        if (ls.getLote().getId() == null) {
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
        // todo
        // Validation

        HashMap res = new HashMap<>();

        try {

            LoteServicies ls = new LoteServicies();
            ProductoServicies productoServicies = new ProductoServicies();
            productoServicies.setProducto(productoServicies.get(Integer.parseInt(map.get("producto").toString())));
            ls.getLote().setCodigoLote(map.get("codigoLote").toString());
            ls.getLote().setCantidad(Integer.parseInt(map.get("cantidad").toString()));
            ls.getLote().setPrecioCompra(Float.parseFloat(map.get("precioCompra").toString()));
            ls.getLote().setPrecioVenta(Float.parseFloat(map.get("precioVenta").toString()));
            ls.getLote().setFechaVencimiento(map.get("fechaVencimiento").toString());
            ls.getLote().setFechaCreacion(map.get("fechaCreacion").toString());
            ls.getLote().setDescripcionLote(map.get("descripcionLote").toString());
            ls.getLote().setId_Producto(productoServicies.getProducto().getId());
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

            LoteServicies ls = new LoteServicies();
            ls.setLote(ls.get(Integer.parseInt(map.get("id").toString())));
            System.out.println(ls.getLote().getId());
            ls.getLote().setCodigoLote(map.get("codigoLote").toString());
            ls.getLote().setCantidad(Integer.parseInt(map.get("cantidad").toString()));
            ls.getLote().setPrecioCompra(Float.parseFloat(map.get("precioCompra").toString()));
            ls.getLote().setPrecioVenta(Float.parseFloat(map.get("precioVenta").toString()));
            ls.getLote().setFechaVencimiento(map.get("fechaVencimiento").toString());
            ls.getLote().setFechaCreacion(map.get("fechaCreacion").toString());
            ls.getLote().setDescripcionLote(map.get("descripcionLote").toString());

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

    @Path("/list/search/codigoLote/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoteFirstApellido(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();
        LoteServicies fs = new LoteServicies();

        map.put("msg", "Ok");
        map.put("data", fs.buscar_CodigoLote(texto).toArray());
        if (fs.buscar_CodigoLote(texto).isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/code/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCode(@PathParam("code") String code) {
        HashMap<String, Object> map = new HashMap<>();
        LoteServicies ps = new LoteServicies();
        try {
            // Obtener el lote específico
            ps.setLote(ps.lote_codigo(code));
            if (ps.getLote().getId() == null) {
                map.put("msg", "No existe el lote con ese identificador");
                map.put("data", null);
                return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(map).build();
            }

            // Agregar el lote específico al mapa
            map.put("msg", "OK");
            map.put("lote", ps.getLote());

            // Obtener la lista completa de lotes (o productos relacionados) y agregarla al mapa
            LoteServicies ls = new LoteServicies();
            map.put("listaCompleta", ls.listShowAll());
          
    public Response deleteLote(@PathParam("id") int id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            LoteServicies fs = new LoteServicies();

            boolean LoteDeleted = fs.delete(id); // Intentamos eliminar el Lote

            if (!LoteDeleted) {

                res.put("message", "Lote no encontrada o no eliminada"); // Si no se eliminó, enviar un error 404
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();

            } else {

                res.put("message", "Lote eliminados exitosamente");
                return Response.ok(res).build();

        } catch (Exception e) {
            map.put("msg", "Error al procesar la solicitud");
            map.put("data", null);
            return Response.status(Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(map).build();
        }

        return Response.ok(map).header("Access-Control-Allow-Origin", "*").build();

            res.put("message", "Error al intentar eliminar la Lote"); // En caso de error, devolver una respuesta de
                                                                      // error interno
            res.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/list/search/codigoLote/{texto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoteFirstApellido(@PathParam("texto") String texto) {
        HashMap map = new HashMap<>();

        try {
            LoteServicies fs = new LoteServicies();
            fs.setLote(fs.buscar_CodigoLote(texto));
            map.put("msg", "Ok");
            map.put("data", fs.showLote(fs.getLote()));
            if (fs.getLote().getId() == null) {
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
        LoteServicies ps = new LoteServicies();
        try {
            map.put("msg", "Ok");
            map.put("data", ps.listShowAll2(ps.order(attribute, type)));
        } catch (Exception e) {
            // TODO: handle exception
        }

        if (ps.order(attribute, type).isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }

        return Response.ok(map).build();
    }
}
