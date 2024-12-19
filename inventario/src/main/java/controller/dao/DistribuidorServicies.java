package controller.dao;

import java.util.HashMap;

import controller.tda.list.LinkedList;
import models.Lote;
import models.Distribuidor;

public class DistribuidorServicies {
    private DistribuidorDao obj;

    // public HashMap buscar_Id_Lotes(Integer id) throws Exception {
    // Distribuidor distribuidor = obj.buscar_IdDistribuidor(id);
    // HashMap mapa = new HashMap<>();
    // if (distribuidor != null) {

    // try {
    // mapa.put("id", distribuidor.getId());
    // mapa.put("tipoDistribuidor", distribuidor.getTipoDistribuidor());
    // mapa.put("nombre", distribuidor.getNombre());
    // mapa.put("marca", distribuidor.getMarca());
    // mapa.put("descripcion", distribuidor.getDescripcion());
    // LoteServicies ls = new LoteServicies();
    // LinkedList<Lote> listaAux = ls.search_By_Distribuidor(distribuidor.getId());
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

    public DistribuidorServicies() { // Constructor de la clase
        obj = new DistribuidorDao(); // Instancia un objeto de la clase DistribuidorDao
    }

    public Boolean update() throws Exception { // Guarda la variable Distribuidor en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase DistribuidorDao
    }

    public Boolean save() throws Exception { // Guarda la variable Distribuidor en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase DistribuidorDao
    }

    public LinkedList<Distribuidor> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase DistribuidorDao

    }

    public Distribuidor getDistribuidor() { // Obtiene la Distribuidor
        return obj.getDistribuidor(); // Invoca el método getDistribuidor() de la clase DistribuidorDao
    }

    public void setDistribuidor(Distribuidor Distribuidor) { // Recibe un objeto Distribuidor
        obj.setDistribuidor(Distribuidor); // Invoca el método setDistribuidor() de la clase DistribuidorDao y envía el
                                           // objeto
        // Distribuidor
    }

    public Distribuidor get(Integer id) throws Exception { // Obtiene un objeto Distribuidor por su id
        return obj.get(id); // Invoca el método get() de la clase DistribuidorDao y envía el id
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Distribuidor por su índice
        return obj.delete(index); // Invoca el método delete() de la clase DistribuidorDao y envía el índice
    }

}
