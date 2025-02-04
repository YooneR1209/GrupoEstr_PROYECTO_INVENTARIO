package controller.dao;

import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.OrdenVenta;

public class OrdenVentaServicies {
    private OrdenVentaDao obj;

    public OrdenVentaServicies() { // Constructor de la clase
        obj = new OrdenVentaDao(); // Instancia un objeto de la clase OrdenVentaDao
    }

    public Boolean save(OrdenVenta ordenFront) throws Exception {

        return obj.save(ordenFront);
    }

    public Boolean updateLotes(OrdenVenta ordenVenta) throws Exception {

        return obj.updateLotes(ordenVenta);
    }

    public LinkedList<OrdenVenta> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase OrdenVentaDao

    }

    public OrdenVenta getOrdenVenta() { // Obtiene la OrdenVenta
        return obj.getOrdenVenta(); // Invoca el método getOrdenVenta() de la clase OrdenVentaDao
    }

    public void setOrdenVenta(OrdenVenta OrdenVenta) { // Recibe un objeto OrdenVenta
        obj.setOrdenVenta(OrdenVenta); // Invoca el método setOrdenVenta() de la clase OrdenVentaDao y envía el
                                       // objeto
        // OrdenVenta
    }

    public OrdenVenta get(Integer id) throws Exception { // Obtiene un objeto OrdenVenta por su id
        return obj.buscar_IdOrdenVenta(id); // Invoca el método get() de la clase OrdenVentaDao y envía el id
    }

    public Boolean isUnique(String nroOrdenVenta) throws ListEmptyException {
        return obj.isUnique(nroOrdenVenta);
    }

    public OrdenVenta buscar_nro_OrdenVenta(String texto) {
        return obj.buscar_nro_OrdenVenta(texto);
    }

    public LinkedList<OrdenVenta> order(String attribute, Integer type) {
        return obj.order(attribute, type);
    }

    public boolean isFechaVentaValida(String fechaCompra) {
        return obj.isFechaVentaValida(fechaCompra);
    }
}
