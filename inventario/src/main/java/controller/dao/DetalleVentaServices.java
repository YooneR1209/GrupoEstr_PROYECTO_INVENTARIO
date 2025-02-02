package controller.dao;

import controller.tda.list.LinkedList;
import models.DetalleVenta;

public class DetalleVentaServices { // Constructor de la clase

    private DetalleVentaDao obj; // Instancia un objeto de la clase DetalleVentaDao

    public DetalleVentaServices() {
        obj = new DetalleVentaDao();
    }

    public Boolean update() throws Exception {
        ///Guarda la variable DetalleVenta en la lista de objetos
         return obj.update();  //Invoca el método save() de la clase DetalleVentaDao
    }

    public Boolean save() throws Exception { // Guarda la variable DetalleVenta en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase DetalleVentaDao
    }

    public LinkedList<DetalleVenta> listAll() {  //Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll();  //Invoca el método getlistAll() de la clase DetalleVentaDao
    }

    public DetalleVenta getDetalleVenta() { // Obtiene la DetalleVenta
        return obj.getDetalleVenta(); // Invoca el método getDetalleVenta() de la clase DetalleVentaDao
    }

    public void setDetalleVenta(DetalleVenta DetalleVenta) { // Recibe un objeto DetalleVenta
        obj.setDetalleVenta(DetalleVenta);  //Invoca el método setLote() de la clase DetalleVentaDao y envía el objeto

    }

    public DetalleVenta get(Integer id) throws Exception {  //Obtiene un objeto DetalleVenta por su id
        return obj.get(id);  //Invoca el método get() de la clase DetalleVentaDao y envía el id
    }

    public Boolean delete(int index) throws Exception {  //Elimina un objeto DetalleVenta por su índice
        return obj.delete(index);  //Invoca el método delete() de la clase DetalleVentaDao y envía el índice
    }
}
