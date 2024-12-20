package controller.dao;

import java.util.HashMap;

import controller.tda.list.LinkedList;
import models.Lote;
import models.OrdenCompra;

public class OrdenCompraServicies {
    private OrdenCompraDao obj;

    // public HashMap buscar_Id_Lotes(Integer id) throws Exception {
    // OrdenCompra ordenCompra = obj.buscar_IdOrdenCompra(id);
    // HashMap mapa = new HashMap<>();
    // if (ordenCompra != null) {

    // try {
    // mapa.put("id", ordenCompra.getId());
    // mapa.put("tipoOrdenCompra", ordenCompra.getTipoOrdenCompra());
    // mapa.put("nombre", ordenCompra.getNombre());
    // mapa.put("marca", ordenCompra.getMarca());
    // mapa.put("descripcion", ordenCompra.getDescripcion());
    // LoteServicies ls = new LoteServicies();
    // LinkedList<Lote> listaAux = ls.search_By_OrdenCompra(ordenCompra.getId());
    // if (!listaAux.isEmpty()) {
    // Lote[] listaL = listaAux.toArray();
    // HashMap[] detalist = new HashMap[listaL.length];
    // for (int j = 0; j < detalist.length; j++) {
    // HashMap map_aux = new HashMap<>();
    // map_aux.put("id", listaL[j].getId());
    // map_aux.put("codigoLote", listaL[j].getCodigoLote());
    // map_aux.put("cantidad", listaL[j].getCantidad());
    // map_aux.put("precioCompra", listaL[j].getPrecioCompra());
    // map_aux.put("precioVenta", listaL[j].getPrecioVenta());
    // map_aux.put("fechaVencimiento", listaL[j].getFechaVencimiento());
    // map_aux.put("fechaCreacion", listaL[j].getFechaCreacion());
    // map_aux.put("descripcionLote", listaL[j].getDescripcionLote());
    // detalist[j] = map_aux;
    // }
    // mapa.put("lotes", detalist);
    // } else {
    // mapa.put("lotes", new Object[] {});
    // }

    // } catch (Exception e) {
    // // TODO: handle exception
    // return new HashMap<>();
    // }
    // }
    // return mapa;
    // }

    public OrdenCompraServicies() { // Constructor de la clase
        obj = new OrdenCompraDao(); // Instancia un objeto de la clase OrdenCompraDao
    }

    public Boolean update() throws Exception { // Guarda la variable OrdenCompra en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase OrdenCompraDao
    }

    public Boolean save() throws Exception { // Guarda la variable OrdenCompra en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase OrdenCompraDao
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

    public Boolean delete(int index) throws Exception { // Elimina un objeto OrdenCompra por su índice
        return obj.delete(index); // Invoca el método delete() de la clase OrdenCompraDao y envía el índice
    }

}
