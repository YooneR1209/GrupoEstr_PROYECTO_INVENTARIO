package controller.dao;

import java.util.HashMap;

import controller.tda.list.LinkedList;
import models.Lote;
import models.Producto;

public class ProductoServicies {
    private ProductoDao obj;

    public HashMap buscar_Id_Lotes(Integer id) throws Exception {
        Producto producto = obj.buscar_IdProducto(id);
        HashMap mapa = new HashMap<>();
        if (producto != null) {

            try {
                mapa.put("id", producto.getId());
                mapa.put("tipoProducto", producto.getTipoProducto());
                mapa.put("nombre", producto.getNombre());
                mapa.put("marca", producto.getMarca());
                mapa.put("descripcion", producto.getDescripcion());
                LoteServicies ls = new LoteServicies();
                LinkedList<Lote> listaAux = ls.search_By_Producto(producto.getId());
                if (!listaAux.isEmpty()) {
                    Lote[] listaL = listaAux.toArray();
                    HashMap[] detalist = new HashMap[listaL.length];
                    for (int j = 0; j < detalist.length; j++) {
                        HashMap map_aux = new HashMap<>();
                        map_aux.put("id", listaL[j].getId());
                        map_aux.put("codigoLote", listaL[j].getCodigoLote());
                        map_aux.put("cantidad", listaL[j].getCantidad());
                        map_aux.put("precioCompra", listaL[j].getPrecioCompra());
                        map_aux.put("precioVenta", listaL[j].getPrecioVenta());
                        map_aux.put("fechaVencimiento", listaL[j].getFechaVencimiento());
                        map_aux.put("fechaCreacion", listaL[j].getFechaCreacion());
                        map_aux.put("descripcionLote", listaL[j].getDescripcionLote());
                        detalist[j] = map_aux;
                    }
                    mapa.put("lotes", detalist);
                } else {
                    mapa.put("lotes", new Object[] {});
                }

            } catch (Exception e) {
                // TODO: handle exception
                return new HashMap<>();
            }
        }
        return mapa;
    }

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
