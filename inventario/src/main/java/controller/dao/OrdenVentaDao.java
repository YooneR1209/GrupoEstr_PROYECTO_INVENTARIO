package controller.dao;

import models.OrdenVenta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.Lote;

public class OrdenVentaDao extends AdapterDao<OrdenVenta> {
    private OrdenVenta ordenVenta = new OrdenVenta();
    private LinkedList<OrdenVenta> listAll;

    public OrdenVentaDao() {
        super(OrdenVenta.class);
    }

    public OrdenVenta getOrdenVenta() { // Obtiene la ordenVenta
        if (ordenVenta == null) {
            ordenVenta = new OrdenVenta(); // En caso de que la ordenVenta sea nula, crea una nueva instancia de
                                           // OrdenVenta
        }
        return this.ordenVenta; // Devuelve la ordenVenta
    }

    public void setOrdenVenta(OrdenVenta ordenVenta) { // Establece la ordenVenta con un objeto OrdenVenta
        this.ordenVenta = ordenVenta; // Asigna el objeto OrdenVenta a la variable ordenVenta
    }

    public LinkedList<OrdenVenta> getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save(OrdenVenta ordenFront) throws Exception { // Guarda la variable lote en la lista de objetos
        this.ordenVenta = ordenFront;
        Integer id = 0;
        if (!getlistAll().isEmpty()) {
            id = getlistAll().getLast().getId(); // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        }
        ordenVenta.setId(id + 1); // Asigna el id a ordenVenta
        this.persist(this.ordenVenta); // Guarda la lote en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean updateLotes(OrdenVenta ordenVenta) throws Exception {
        Lote[] lotelist = ordenVenta.getLoteList();
        LoteDao ld = new LoteDao();

        try {
            for (Lote lote : lotelist) {
                int cantidad = lote.getCantidad();
                String codigo = lote.getCodigoLote();
                System.out.println("Lote: " + codigo + " Cantidad:" + cantidad);
                Lote lotesito = this.buscarCodigoLote(codigo);
                if (lotesito.getCodigoLote() != null) {
                    lotesito.setCantidad(lotesito.getCantidad() - cantidad);
                    ld.setLote(lotesito);
                    ld.update();
                } else {
                    System.out.println("El lote " + lote.getCodigoLote() + " No existe");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR updteLotes: " + e);
        }

        return true;
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

    public OrdenVenta buscar_IdOrdenVenta(int id) {
        this.listAll = listAll();
        OrdenVenta p = new OrdenVenta();
        OrdenVenta[] lista = listAll.toArray();
        if (!listAll.isEmpty()) {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getId().intValue() == id) {
                    p = lista[i];
                    break;
                }
            }
        }
        return p;
    }

    public Boolean isUnique(String nroOrdenVenta) throws ListEmptyException {
        LinkedList<OrdenVenta> list = listAll();

        if (list.isEmpty()) {
            throw new ListEmptyException("La lista de órdenes de compra está vacía.");
        }

        OrdenVenta[] ordenes = list.toArray();

        for (OrdenVenta orden : ordenes) {
            if (orden.getNro_OrdenVenta().equals(nroOrdenVenta)) {
                return false;
            }
        }

        return true; // Si no encuentra coincidencias, retorna true
    }

    public boolean isFechaCompraValida(String fechaCompra) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate fechaCompraLocalDate = LocalDate.parse(fechaCompra, formatter);

            LocalDate fechaActual = LocalDate.now();

            return !fechaCompraLocalDate.isAfter(fechaActual);

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public OrdenVenta buscar_nro_OrdenVenta(String texto) {
        System.out.println("Estamos buscando por Metodo Binario");
        OrdenVenta resultado = null;

        try {
            this.getlistAll();

            if (!listAll.isEmpty()) {

                listAll.mergeSort("nro_OrdenVenta", 0); // Asumimos que la lista ya está ordenada

                int derecha = 0;
                int izquierda = listAll.getSize() - 1;

                while (derecha <= izquierda) {
                    int mid = (derecha + izquierda) / 2;
                    OrdenVenta midOrdenVenta = listAll.get(mid);
                    String codigoOrdenVenta = midOrdenVenta.getNro_OrdenVenta().toLowerCase();

                    if (codigoOrdenVenta.startsWith(texto.toLowerCase())) {
                        resultado = midOrdenVenta; // Se encuentra el primer OrdenVenta que coincide
                        break; // Terminamos la búsqueda al encontrar la coincidencia
                    } else if (codigoOrdenVenta.compareTo(texto.toLowerCase()) < 0) {
                        derecha = mid + 1;
                    } else {
                        izquierda = mid - 1;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        return resultado; // Devuelve el Lote encontrado o null si no se encuentra
    }

    public LinkedList<OrdenVenta> order(String attribute, Integer type) {
        try {
            getlistAll();
            System.out.println("Lista antes de ordenar " + listAll.toString());
            return this.listAll.mergeSort(attribute, type);

        } catch (Exception e) {
            return null;
        }
    }

}
