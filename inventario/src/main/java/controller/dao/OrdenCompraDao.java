package controller.dao;

import models.OrdenCompra;

import controller.dao.implement.AdapterDao;
import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Distribuidor;
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

}
