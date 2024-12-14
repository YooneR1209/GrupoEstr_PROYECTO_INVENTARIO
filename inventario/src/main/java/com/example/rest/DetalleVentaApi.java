// package com.example.rest;

// import java.util.HashMap;

// import javax.ws.rs.Consumes;
// import javax.ws.rs.DELETE;
// import javax.ws.rs.GET;
// import javax.ws.rs.POST;
// import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.Response.Status;

// import controller.dao.DetalleVentaServices;
// import controller.dao.ProductoServicies;

// @Path("detalleVenta")
// public class DetalleVentaApi {

//     @Path("/list")
//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response getAllDetalleVenta() {

//         HashMap map = new HashMap<>();
//         DetalleVentaServices ls = new DetalleVentaServices();
//         map.put("msg", "Ok");

//         try {
//             map.put("data", ls.listShowAll());
//             if (ls.listAll().isEmpty()) {
//                 map.put("data", new Object[]{});

//             }
//             return Response.ok(map).build();
//         } catch (Exception e) {
//             map.put("data", new Object[]{});

//             // TODO: handle exception
//         }
//         return Response.ok(map).build();

//     }

//     @Path("/get/{id}")
//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response getDetalleVenta(@PathParam("id") Integer id) {
//         HashMap map = new HashMap<>();
//         DetalleVentaServices ls = new DetalleVentaServices();
//         try {
//             ls.setDetalleVenta(ls.get(id));
//         } catch (Exception e) {
//             System.out.println("Error " + e);
//         }
//         map.put("msg", "Ok");
//         map.put("data", ls.getDetalleVenta());
//         if (ls.getDetalleVenta().getIdDetalleVenta() == null) {
//             map.put("data", "No existe la Lote con ese identificador");
//             return Response.status(Status.BAD_REQUEST).entity(map).build();
//         }

//         return Response.ok(map).build();
//     }

//     @Path("/save")
//     @POST
//     @Consumes(MediaType.APPLICATION_JSON)
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response save(HashMap map) {
//         //todo
//         //Validation

//         HashMap res = new HashMap<>();

//         try {

//             DetalleVentaServices ls = new DetalleVentaServices();
//             ProductoServicies productoServicies = new ProductoServicies();
//             productoServicies.setProducto(productoServicies.get(Integer.parseInt(map.get("producto").toString())));
//             ls.getDetalleVenta().setCantidadProductos(Integer.parseInt(map.get("cantidadProducto").toString()));
//             ls.getDetalleVenta().setPrecioUnitario(Float.parseFloat(map.get("precioUnitario").toString()));
//             ls.getDetalleVenta().setPrecioTotal(Float.parseFloat(map.get("precioTotal").toString()));
//             ls.getDetalleVenta().setId_Producto(productoServicies.getProducto().getId());

//             ls.save();

//             res.put("msg", "Ok");
//             res.put("data", "Guardado correctamente");
//             return Response.ok(res).build();

//         } catch (Exception e) {
//             res.put("msg", "Error");
//             res.put("data", e.toString());
//             return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
//         }
//     }

//     @Path("/update")
//     @POST
//     @Consumes(MediaType.APPLICATION_JSON)
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response update(HashMap map) {

//         HashMap res = new HashMap<>();

//         try {

//             DetalleVentaServices ls = new DetalleVentaServices();
//             ls.getDetalleVenta().setCantidadProductos(Integer.parseInt(map.get("cantidadProductos").toString()));
//             ls.getDetalleVenta().setPrecioUnitario(Float.parseFloat(map.get("precioLote").toString()));
//             ls.getDetalleVenta().setPrecioTotal(Float.parseFloat(map.get("precioCompra").toString()));

//             ls.update();

//             res.put("msg", "Ok");
//             res.put("data", "Guardado correctamente");
//             return Response.ok(res).build();

//         } catch (Exception e) {
//             res.put("msg", "Error");
//             res.put("data", e.toString());
//             return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
//         }
//     }

//     @Path("/listType")

//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response getType() {
//         HashMap map = new HashMap<>();
//         DetalleVentaServices ls = new DetalleVentaServices();

//         map.put("msg", "Ok");
//         map.put("data", ls.getDetalleVenta());
//         return Response.ok(map).build();
//     }

//     @Path("/delete/{id}")
//     @DELETE
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response deleteLote(@PathParam("id") int id) {
//         HashMap<String, Object> res = new HashMap<>();

//         try {
//             DetalleVentaServices fs = new DetalleVentaServices();

//             boolean LoteDeleted = fs.delete(id - 1);       // Intentamos eliminar el Lote

//             if (LoteDeleted) {
//                 res.put("message", "Lote y Generador eliminados exitosamente");
//                 return Response.ok(res).build();

//             } else {

//                 res.put("message", "Lote no encontrada o no eliminada");     // Si no se eliminó, enviar un error 404
//                 return Response.status(Response.Status.NOT_FOUND).entity(res).build();

//             }
//         } catch (Exception e) {

//             res.put("message", "Error al intentar eliminar la Lote"); // En caso de error, devolver una respuesta de error interno
//             res.put("error", e.getMessage());
//             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
//         }
//     }

//     // @Path("/list/order/{attribute}/{type}/{metodo}")
//     // @GET
//     // @Produces(MediaType.APPLICATION_JSON)
//     // public Response getOrder(@PathParam("attribute") String attribute, @PathParam("type") Integer type, @PathParam("metodo") Integer metodo) {
//     //     HashMap map = new HashMap<>();
//     //     DetalleVentaServices ls = new DetalleVentaServices();
//     //     map.put("msg", "Ok");
//     //     map.put("data", ls.order(attribute, type, metodo).toArray());
//     //     if (ls.order(attribute, type, metodo).isEmpty()) {
//     //         map.put("data", new Object[]{});
//     //        return Response.status(Status.BAD_REQUEST).entity(map).build();
//     //     }
//     //     return Response.ok(map).build();
//     // }
// }
