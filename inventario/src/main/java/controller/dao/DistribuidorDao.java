package controller.dao;

import models.Distribuidor;
import models.OrdenCompra;

import com.google.gson.Gson;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class DistribuidorDao extends AdapterDao<Distribuidor> {
    private Distribuidor distribuidor = new Distribuidor();
    private LinkedList<Distribuidor> listAll;

    public DistribuidorDao() {
        super(Distribuidor.class);
    }

    public Distribuidor getDistribuidor() { // Obtiene la distribuidor
        if (distribuidor == null) {
            distribuidor = new Distribuidor(); // En caso de que la distribuidor sea nula, crea una nueva instancia de
                                               // Distribuidor
        }
        return this.distribuidor; // Devuelve la distribuidor
    }

    public void setDistribuidor(Distribuidor distribuidor) { // Establece la distribuidor con un objeto Distribuidor
        this.distribuidor = distribuidor; // Asigna el objeto Distribuidor a la variable distribuidor
    }

    public LinkedList<Distribuidor> getlistAll() { // Obtiene la lista de objetos
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
        distribuidor.setId(id + 1); // Asigna el id a distribuidor
        this.persist(this.distribuidor); // Guarda la lote en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Lote en la lista de objetos
        this.getlistAll();
        this.mergeA(getDistribuidor(), recuperoIndex(distribuidor.getId())); // Envia la distribuidor a actualizar con
                                                                             // su index
        System.out.println("valor" + recuperoIndex(distribuidor.getId()));
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
        Distribuidor[] lista = listAll.toArray();
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

    public Distribuidor buscar_IdDistribuidor(int id) {
        this.listAll = listAll();
        Distribuidor p = new Distribuidor();
        Distribuidor[] lista = listAll.toArray();
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

    public Boolean isUnique(String cedula) throws ListEmptyException {
        LinkedList<Distribuidor> list = listAll();

        if (list.isEmpty()) {
            return true;
        }

        Distribuidor[] ordenes = list.toArray();

        // Recorrer el array de órdenes de compra
        for (Distribuidor orden : ordenes) {
            // Comparar el número de orden de compra
            if (orden.getCedula().equals(cedula)) {
                return false; // Si encuentra una coincidencia, retorna false
            }
        }

        return true; // Si no encuentra coincidencias, retorna true
    }

}
