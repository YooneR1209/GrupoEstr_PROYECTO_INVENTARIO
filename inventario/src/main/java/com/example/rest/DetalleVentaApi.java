package com.example.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import models.OrdenVenta;

@Path("detalle")
public class DetalleVentaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            // Ruta del archivo donde se guardan los detalles de venta
            String rutaBase = System.getProperty("user.dir"); // Directorio actual
            String rutaArchivo = rutaBase + "/data/DetalleVenta.json"; // Ruta completa del archivo

            File archivo = new File(rutaArchivo);

            // Si el archivo existe, leer los datos
            if (archivo.exists()) {
                StringBuilder contenido = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        contenido.append(linea);
                    }
                }

                // Procesar el contenido JSON
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode arrayExistente = (ArrayNode) mapper.readTree(contenido.toString());

                // Preparar la respuesta
                map.put("msg", "Ok");
                map.put("data", arrayExistente);  // Aquí, en lugar de convertirlo a un String, usamos directamente el ArrayNode

                // Si no hay datos, devolver un array vacío
                if (arrayExistente.isEmpty()) {
                    map.put("data", new Object[]{});
                }

            } else {
                map.put("msg", "Error");
                map.put("data", "No se encontró el archivo de detalles de venta.");
            }

        } catch (Exception e) {
            map.put("msg", "Error al obtener los datos.");
            map.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

        return Response.ok(map).build();
    }

    // Método para obtener el próximo idVenta incremental
    private int getNextIdVenta() {
        int idVenta = 0; // Valor predeterminado

        // Ruta del archivo donde se guarda el último idVenta usado
        String rutaBase = System.getProperty("user.dir");
        String rutaArchivoIdVenta = rutaBase + "/data/lastIdVenta.txt";

        // Intentamos leer el último idVenta desde el archivo
        try {
            File archivo = new File(rutaArchivoIdVenta);

            // Si el archivo existe, leer el último idVenta
            if (archivo.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(archivo));
                String lastId = reader.readLine();
                reader.close();
                if (lastId != null) {
                    idVenta = Integer.parseInt(lastId);
                }
            }

            // Incrementar el idVenta
            idVenta++;

            // Guardar el nuevo idVenta en el archivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(String.valueOf(idVenta));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return idVenta;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDetalleVenta(HashMap<String, Object> detalleVenta) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            System.out.println("Detalle recibido: " + detalleVenta);

            // Formateador de fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Rutas de archivos
            String rutaBase = System.getProperty("user.dir");
            String rutaOrdenes = rutaBase + "/data/OrdenVenta.json";
            String rutaDetalles = rutaBase + "/data/DetalleVenta.json";

            File archivoOrdenes = new File(rutaOrdenes);
            File archivoDetalles = new File(rutaDetalles);

            ObjectMapper mapper = new ObjectMapper();

            // Cargar detalles de venta existentes
            ArrayNode listaDetalles;
            if (archivoDetalles.exists()) {
                String contenido = new String(Files.readAllBytes(archivoDetalles.toPath()), StandardCharsets.UTF_8);
                listaDetalles = (ArrayNode) mapper.readTree(contenido);
            } else {
                listaDetalles = mapper.createArrayNode();
            }

            // Agregar nuevo detalle a DetalleVenta.json
            listaDetalles.add(mapper.valueToTree(detalleVenta));
            Files.write(archivoDetalles.toPath(), mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(listaDetalles));

            // Cargar órdenes de venta existentes
            ArrayNode listaOrdenes;
            if (archivoOrdenes.exists()) {
                String contenido = new String(Files.readAllBytes(archivoOrdenes.toPath()), StandardCharsets.UTF_8);
                listaOrdenes = (ArrayNode) mapper.readTree(contenido);
            } else {
                listaOrdenes = mapper.createArrayNode();
            }

            // Crear nueva orden de venta
            OrdenVenta ordenVenta = new OrdenVenta();
            ordenVenta.setN_OrdenVenta(UUID.randomUUID().toString()); // ID único para la orden
            ordenVenta.setFechaVenta(sdf.format(new Date())); // Fecha en formato personalizado
            ordenVenta.setDetalle(detalleVenta); // Asignar detalle de venta

            // Guardar en OrdenVenta.json
            JsonNode nuevaOrdenJson = mapper.valueToTree(ordenVenta);
            listaOrdenes.add(nuevaOrdenJson);
            Files.write(archivoOrdenes.toPath(), mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(listaOrdenes));

            res.put("msg", "Orden de venta y detalle guardados correctamente.");
            res.put("id", ordenVenta.getN_OrdenVenta());
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al guardar la orden y detalle.");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
