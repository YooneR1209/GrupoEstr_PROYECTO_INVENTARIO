package controller.dao;
import controller.tda.list.LinkedList;
import models.OrdenVenta;
public class OrdenVentaServices {
    private OrdenVentaDao obj;
    /*
    public Object[] listShowAll() throws Exception {
        if (!obj.getListAll().isEmpty()) {
            OrdenVenta[] lista = (OrdenVenta[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                DetalleVenta d = new DetalleVentaServices().get(lista[i].getIdDetalleVenta());
                HashMap mapa = new HashMap<>();
                mapa.put("idOrdenVenta", lista[i].getIdVenta());
                mapa.put("fecha", lista[i].getFechaVenta());
                mapa.put("subTotal", lista[i].getSubtotalVenta());
                mapa.put("iva", lista[i].getIva());
                mapa.put("precioTotal", lista[i].getTotalVenta());
                mapa.put("n_ordenVenta", lista[i].getN_OrdenVenta());
                mapa.put("detalle", d);
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[]{};
    }
     */
    public OrdenVentaServices() { // Constructor de la clase
        obj = new OrdenVentaDao(); // Instancia un objeto de la clase OrdenVentaDao
    }
    public Boolean update() throws Exception { // Guarda la variable OrdenVenta en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase OrdenVentaDao
    }
    public Boolean save() throws Exception { // Guarda la variable OrdenVenta en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase OrdenVentaDao
    }
    public LinkedList<OrdenVenta> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getListAll(); // Invoca el método getlistAll() de la clase OrdenVentaDao
    }
    public OrdenVenta getOrdenVenta() { // Obtiene la OrdenVenta
        return obj.getOrdenVenta(); // Invoca el método getOrdenVenta() de la clase OrdenVentaDao
    }
    public void setOrdenVenta(OrdenVenta OrdenVenta) { // Recibe un objeto OrdenVenta
        obj.setOrdenVenta(OrdenVenta); // Invoca el método setOrdenVenta() de la clase OrdenVentaDao y envía el objeto
        // OrdenVenta
    }
    public OrdenVenta get(Integer id) throws Exception { // Obtiene un objeto OrdenVenta por su id
        return obj.get(id); // Invoca el método get() de la clase OrdenVentaDao y envía el id
    }
    public Boolean delete(int index) throws Exception { // Elimina un objeto OrdenVenta por su índice
        return obj.delete(index); // Invoca el método delete() de la clase OrdenVentaDao y envía el índice
    }
}