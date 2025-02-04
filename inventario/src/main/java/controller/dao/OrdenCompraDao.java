package controller.dao;

import models.OrdenCompra;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.Lote;

public class OrdenCompraDao extends AdapterDao<OrdenCompra> {
    private OrdenCompra ordenCompra = new OrdenCompra();
    private LinkedList<OrdenCompra> listAll;

    public OrdenCompraDao() {
        super(OrdenCompra.class);
    }

    public OrdenCompra getOrdenCompra() { // Obtiene la ordenCompra
        if (ordenCompra == null) {
            ordenCompra = new OrdenCompra(); // En caso de que la ordenCompra sea nula, crea una nueva instancia de
                                             // OrdenCompra
        }
        return this.ordenCompra; // Devuelve la ordenCompra
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) { // Establece la ordenCompra con un objeto OrdenCompra
        this.ordenCompra = ordenCompra; // Asigna el objeto OrdenCompra a la variable ordenCompra
    }

    public LinkedList<OrdenCompra> getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save(OrdenCompra ordenFront) throws Exception { // Guarda la variable lote en la lista de objetos
        this.ordenCompra = ordenFront;
        Integer id = 0;
        if (!getlistAll().isEmpty()) {
            id = getlistAll().getLast().getId(); // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        }
        ordenCompra.setId(id + 1); // Asigna el id a ordenCompra
        this.persist(this.ordenCompra); // Guarda la lote en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean updateLotes(OrdenCompra ordenCompra) throws Exception {
        Lote[] lotelist = ordenCompra.getLoteList();
        LoteDao ld = new LoteDao();

        try {
            for (Lote lote : lotelist) {
                int cantidad = lote.getCantidad();
                String codigo = lote.getCodigoLote();
                System.out.println("Lote: " + codigo + " Cantidad:" + cantidad);
                Lote lotesito = this.buscarCodigoLote(codigo);
                if (lotesito.getCodigoLote() != null) {
                    lotesito.setCantidad(lotesito.getCantidad() + cantidad);
                    ld.setLote(lotesito);
                    ld.update();
                } else {
                    System.out.println("El lote " + lote.getCodigoLote() + " No existe");
                    ld.setLote(lote);
                    ld.save();
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

    public OrdenCompra buscar_IdOrdenCompra(int id) {
        this.listAll = listAll();
        OrdenCompra p = new OrdenCompra();
        OrdenCompra[] lista = listAll.toArray();
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

    public Boolean isUnique(String nroOrdenCompra) throws ListEmptyException {
        LinkedList<OrdenCompra> list = listAll();

        if (list.isEmpty()) {
            return true;
        }

        OrdenCompra[] ordenes = list.toArray();

        for (OrdenCompra orden : ordenes) {
            if (orden.getNro_OrdenCompra().equals(nroOrdenCompra)) {
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

    public OrdenCompra buscar_nro_OrdenCompra(String texto) {
        System.out.println("Estamos buscando por Metodo Binario");
        OrdenCompra resultado = null;

        try {
            this.getlistAll();

            if (!listAll.isEmpty()) {

                listAll.mergeSort("nro_OrdenCompra", 0); // Asumimos que la lista ya está ordenada

                int derecha = 0;
                int izquierda = listAll.getSize() - 1;

                while (derecha <= izquierda) {
                    int mid = (derecha + izquierda) / 2;
                    OrdenCompra midOrdenCompra = listAll.get(mid);
                    String codigoOrdenCompra = midOrdenCompra.getNro_OrdenCompra().toLowerCase();

                    if (codigoOrdenCompra.startsWith(texto.toLowerCase())) {
                        resultado = midOrdenCompra; // Se encuentra el primer OrdenCompra que coincide
                        break; // Terminamos la búsqueda al encontrar la coincidencia
                    } else if (codigoOrdenCompra.compareTo(texto.toLowerCase()) < 0) {
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

    public LinkedList<OrdenCompra> order(String attribute, Integer type) {
        try {
            getlistAll();
            System.out.println("Lista antes de ordenar " + listAll.toString());
            return this.listAll.mergeSort(attribute, type);

        } catch (Exception e) {
            return null;
        }
    }

}
