package controller.dao;

import java.util.HashMap;

import controller.tda.list.LinkedList;
import models.Lote;
import models.Producto;

public class LoteServicies {
    private LoteDao obj;
    
    public Object[] listShowAll() throws Exception {
        if (!obj.getlistAll().isEmpty()) {
            Lote[] lista = (Lote[]) obj.getlistAll().toArray();
            Object[] respuesta = new Object [lista.length];
            for(int i = 0; i< lista.length; i++) {
                Producto p = new ProductoServicies().get(lista[i].getId_Producto());
                HashMap mapa = new HashMap<>();
                mapa.put("id", lista[i].getId());
                mapa.put("codigoLote", lista[i].getCodigoLote());
                mapa.put("cantidad", lista[i].getCantidad());
                mapa.put("precioCompra", lista[i].getPrecioCompra());
                mapa.put("precioVenta", lista[i].getPrecioVenta());
                mapa.put("fechaVencimiento", lista[i].getFechaVencimiento());
                mapa.put("fechaCreacion", lista[i].getFechaCreacion());
                mapa.put("descripcionLote", lista[i].getDescripcionLote());
                mapa.put("producto", p);
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[]{};

        
    } 

    public LoteServicies() { // Constructor de la clase
        obj = new LoteDao(); // Instancia un objeto de la clase LoteDao
    }

    public Boolean update() throws Exception { // Guarda la variable Lote en la lista de objetos
        return obj.update(); // Invoca el método save() de la clase LoteDao
    }

    public Boolean save() throws Exception { // Guarda la variable Lote en la lista de objetos
        return obj.save(); // Invoca el método save() de la clase LoteDao
    }

    public LinkedList<Lote> listAll() { // Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); // Invoca el método getlistAll() de la clase LoteDao

    }

    public Lote getLote() { // Obtiene la Lote
        return obj.getLote(); // Invoca el método getLote() de la clase LoteDao
    }

    public void setLote(Lote Lote) { // Recibe un objeto Lote
        obj.setLote(Lote); // Invoca el método setLote() de la clase LoteDao y envía el objeto
                                 // Lote
    }

    public Lote get(Integer id) throws Exception { // Obtiene un objeto Lote por su id
        return obj.get(id); // Invoca el método get() de la clase LoteDao y envía el id
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Lote por su índice
        return obj.delete(index); // Invoca el método delete() de la clase LoteDao y envía el índice
    }

    public LinkedList<Lote> buscar_CodigoLote(String texto) {
        return obj.buscar_CodigoLote(texto);
    }
    
}
