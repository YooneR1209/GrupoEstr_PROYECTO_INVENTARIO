package controller.dao;

import controller.tda.list.LinkedList;
import models.Producto;

public class ProductoServicies {
    private ProductoDao obj;

    public ProductoServicies() { // Constructor de la clase
        obj = new ProductoDao(); // Instancia un objeto de la clase ProductoDao
    }

    public Boolean update() throws Exception { // Guarda la variable Producto en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase ProductoDao
    }

    public Boolean save() throws Exception { // Guarda la variable Producto en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase ProductoDao
    }

    public LinkedList<Producto> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase ProductoDao

    }

    public Producto getProducto() { // Obtiene la Producto
        return obj.getProducto(); // Invoca el método getProducto() de la clase ProductoDao
    }

    public void setProducto(Producto Producto) { // Recibe un objeto Producto
        obj.setProducto(Producto); // Invoca el método setProducto() de la clase ProductoDao y envía el objeto
                                 // Producto
    }

    public Producto get(Integer id) throws Exception { // Obtiene un objeto Producto por su id
        return obj.get(id); // Invoca el método get() de la clase ProductoDao y envía el id
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Producto por su índice
        return obj.delete(index); // Invoca el método delete() de la clase ProductoDao y envía el índice
    }
    
}
