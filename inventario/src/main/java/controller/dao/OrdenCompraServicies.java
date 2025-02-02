package controller.dao;

import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.OrdenCompra;

public class OrdenCompraServicies {
    private OrdenCompraDao obj;

    public OrdenCompraServicies() { // Constructor de la clase
        obj = new OrdenCompraDao(); // Instancia un objeto de la clase OrdenCompraDao
    }

    public Boolean save(OrdenCompra ordenFront) throws Exception {

        return obj.save(ordenFront);
    }

    public Boolean updateLotes(OrdenCompra ordenCompra) throws Exception {

        return obj.updateLotes(ordenCompra);
    }

    public LinkedList<OrdenCompra> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase OrdenCompraDao

    }

    public OrdenCompra getOrdenCompra() { // Obtiene la OrdenCompra
        return obj.getOrdenCompra(); // Invoca el método getOrdenCompra() de la clase OrdenCompraDao
    }

    public void setOrdenCompra(OrdenCompra OrdenCompra) { // Recibe un objeto OrdenCompra
        obj.setOrdenCompra(OrdenCompra); // Invoca el método setOrdenCompra() de la clase OrdenCompraDao y envía el
                                         // objeto
        // OrdenCompra
    }

    public OrdenCompra get(Integer id) throws Exception { // Obtiene un objeto OrdenCompra por su id
        return obj.buscar_IdOrdenCompra(id); // Invoca el método get() de la clase OrdenCompraDao y envía el id
    }

    public Boolean isUnique(String nroOrdenCompra) throws ListEmptyException {
        return obj.isUnique(nroOrdenCompra);
    }

    public boolean isFechaCompraValida(String fechaCompra) {
        return obj.isFechaCompraValida(fechaCompra);
    }

    public OrdenCompra buscar_nro_OrdenCompra(String texto) {
        return obj.buscar_nro_OrdenCompra(texto);
    }

    public LinkedList<OrdenCompra> order(String attribute, Integer type) {
        return obj.order(attribute, type);
    }

}
