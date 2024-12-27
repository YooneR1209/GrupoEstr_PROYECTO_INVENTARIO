package controller.dao;

import models.OrdenCompra;

import com.google.gson.Gson;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

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

    public Boolean update() throws Exception { // Actualiza el nodo Lote en la lista de objetos
        this.getlistAll();
        this.mergeA(getOrdenCompra(), recuperoIndex(ordenCompra.getId())); // Envia la ordenCompra a actualizar con su
                                                                           // index
        System.out.println("valor" + recuperoIndex(ordenCompra.getId()));
        this.listAll = listAll(); // Actualiza la lista de objetos
        System.out.println("listaaa = " + listAll.toString());
        ;
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Lote por su índice
        Gson g = new Gson();
        System.out.println("intentamos eliminar el elemento con id " + index);
        this.listAll = listAll();
        System.out.println("lista:" + this.listAll.toString());
        ;
        this.listAll.remove(recuperoIndex(index));
        String info = g.toJson(this.listAll.toArray()); // Convierte la lista en un String JSON
        super.saveFile(info);
        return true; // Retorna verdadero si se eliminó correctamente
    }

    public Integer recuperoIndex(Integer id) {
        OrdenCompra[] lista = listAll.toArray();
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

}
