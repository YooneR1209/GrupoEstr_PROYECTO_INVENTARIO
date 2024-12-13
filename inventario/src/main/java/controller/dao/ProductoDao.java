package controller.dao;

import models.Lote;
import models.Producto;

import com.google.gson.Gson;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class ProductoDao extends AdapterDao<Producto> {
    private Producto producto = new Producto();
    private LinkedList<Producto> listAll;

    public ProductoDao() {
        super(Producto.class);
    }

    public Producto getProducto() { // Obtiene la producto
        if (producto == null) {
            producto = new Producto(); // En caso de que la producto sea nula, crea una nueva instancia de Producto
        }
        return this.producto; // Devuelve la producto
    }

    public void setProducto(Producto producto) { // Establece la producto con un objeto Producto
        this.producto = producto; // Asigna el objeto Producto a la variable producto
    }

    public LinkedList<Producto> getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save() throws Exception { // Guarda la variable lote en la lista de objetos
        Integer id = 0;
        if (!getlistAll().isEmpty()) {
            id = getlistAll().getLast().getId(); // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        }
        producto.setId(id + 1); // Asigna el id a producto
        this.persist(this.producto); // Guarda la lote en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Lote en la lista de objetos
        this.getlistAll();
        this.mergeA(getProducto(), recuperoIndex(producto.getId()) ); // Envia la producto a actualizar con su index
        System.out.println("valor" + recuperoIndex(producto.getId()));
        this.listAll = listAll(); // Actualiza la lista de objetos
        System.out.println("listaaa = " + listAll.toString());;
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Lote por su índice
        Gson g = new Gson();
        System.out.println("intentamos eliminar el elemento con id " +index);
        this.listAll = listAll();
        System.out.println("lista:" + this.listAll.toString());;
        this.listAll.remove(recuperoIndex(index));
        String info = g.toJson(this.listAll.toArray()); //Convierte la lista en un String JSON
        super.saveFile(info);
        return true; // Retorna verdadero si se eliminó correctamente
    }

    public Integer recuperoIndex (Integer id) {
        Producto[] lista = listAll.toArray();
        Integer count = 0;
        System.out.println("Entramos acá " +id);
        try {
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getId() != id) {
                count++;
                }else if (lista[i].getId() == id) {
                    return count;
                }
            }
        } catch (Exception e) {
            System.out.println("borrado fallido");
        }
        return null;
    }

}
