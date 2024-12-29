package controller.dao;

import controller.tda.list.LinkedList;
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
        return obj.get(id); // Invoca el método get() de la clase OrdenCompraDao y envía el id
    }

}
