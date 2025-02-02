package com.example.rest;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Path("ordenVenta")
public class OrdenVentaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrdenes() {
        HashMap<String, Object> map = new HashMap<>();

        try {
            // Ruta del archivo JSON de órdenes de venta
            String rutaBase = System.getProperty("user.dir");
            String rutaArchivo = rutaBase + "/data/OrdenVenta.json";
            File archivo = new File(rutaArchivo);

            if (archivo.exists()) {
                String contenido = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode listaOrdenes = (ArrayNode) mapper.readTree(contenido);

                map.put("msg", "Ok");
                map.put("data", listaOrdenes);
            } else {
                map.put("msg", "Error");
                map.put("data", "No se encontraron órdenes de venta.");
            }
        } catch (Exception e) {
            map.put("msg", "Error al obtener los datos.");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

        return Response.ok(map).build();
    }

    @Path("/get/{n_OrdenVenta}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdenVenta(@PathParam("n_OrdenVenta") String nOrdenVenta) {
        try {
            File archivo = new File(System.getProperty("user.dir") + "/data/OrdenVenta.json");

            if (!archivo.exists()) {
                return Response.status(Status.NOT_FOUND).entity(Map.of("msg", "Error", "data", "No se encontraron órdenes de venta.")).build();
            }

            String contenido = Files.readString(archivo.toPath(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode listaOrdenes = (ArrayNode) mapper.readTree(contenido);

            for (JsonNode orden : listaOrdenes) {
                if (orden.has("n_OrdenVenta") && orden.get("n_OrdenVenta").asText().equals(nOrdenVenta)) {
                    return Response.ok(Map.of("msg", "Ok", "data", orden)).build();
                }
            }

            return Response.status(Status.NOT_FOUND).entity(Map.of("msg", "Error", "data", "No existe una orden de venta con ese identificador.")).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("msg", "Error al obtener los datos.", "data", e.toString())).build();
        }
    }

}
