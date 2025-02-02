package controller.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Lote;
import models.OrdenVenta;

public class OrdenVentaDao extends AdapterDao<OrdenVenta> {

    private OrdenVenta orden_Venta = new OrdenVenta();
    private LinkedList<OrdenVenta> listAll;

    public OrdenVentaDao() {
        super(OrdenVenta.class);
    }

    public OrdenVenta getOrdenVenta() {
        if (orden_Venta == null) {
            orden_Venta = new OrdenVenta();
        }
        return this.orden_Venta;
    }

    public void setOrdenVenta(OrdenVenta orden_Venta) {
        this.orden_Venta = orden_Venta;
    }

    public LinkedList<OrdenVenta> getlistAll() {
        if (listAll == null) {
            listAll = new LinkedList<>();
            try {
                // Leer el archivo JSON
                String rutaBase = System.getProperty("user.dir");
                String rutaArchivo = rutaBase + "/data/DetalleVenta.json";
                File archivo = new File(rutaArchivo);

                if (archivo.exists()) {
                    StringBuilder contenido = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            contenido.append(linea);
                        }
                    }

                    // Parsear el contenido JSON
                    ObjectMapper mapper = new ObjectMapper();
                    OrdenVenta[] ordenes = mapper.readValue(contenido.toString(), OrdenVenta[].class);

                    // Añadir las órdenes al LinkedList
                    for (OrdenVenta orden : ordenes) {
                        listAll.add(orden);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listAll;
    }

    public Integer recuperoIndex(Integer id) {
        LoteDao ld = new LoteDao();
        Lote[] lista = ld.listAll().toArray();
        Integer count = 0;
        System.out.println("Entramos acá " + id);
        try {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getId() != id) {
                    count++;
                } else if (lista[i].getId() == id) {
                    return count;
                }
            }
        } catch (Exception e) {
            System.out.println("borrado fallido");
        }
        return null;
    }

    public String getJsonOrdenVenta() {
        // Crear el objeto Gson con el formato de fecha
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd") // Establecer el formato de fecha deseado
                .create();

        // Serializar el objeto orden_Venta a JSON
        return gson.toJson(this.orden_Venta); // Esto devolverá el objeto en formato JSON
    }

    public Lote buscarCodigoLote(String codigo) {
        Lote l = new Lote();
        LoteDao ld = new LoteDao();
        Lote[] lista = ld.listAll().toArray();
        if (lista != null) {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getCodigoLote().equals(codigo)) {
                    l = lista[i];
                    break;
                }
            }
        }
        return l;
    }

}
