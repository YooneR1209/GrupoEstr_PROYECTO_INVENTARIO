// package controller.dao;
// import java.util.HashMap;
// import controller.tda.list.LinkedList;
// import models.DetalleVenta;
// import models.Producto;
// public class DetalleVentaServices {
//     private DetalleVentaDao obj;
//     public Object[] listShowAll() throws Exception {
//         if (!obj.getlistAll().isEmpty()) {
//             DetalleVenta[] lista = (DetalleVenta[]) obj.getlistAll().toArray();
//             Object[] respuesta = new Object[lista.length];
//             for (int i = 0; i < lista.length; i++) {
//                 Producto p = new ProductoServicies().get(lista[i].getId_Producto());
//                 HashMap mapa = new HashMap<>();
//                 mapa.put("id", lista[i].getIdDetalleVenta());
//                 mapa.put("cantidadProducto", lista[i].getCantidadProductos());
//                 mapa.put("precioUnitario", lista[i].getPrecioUnitario());
//                 mapa.put("precioTotal", lista[i].getPrecioTotal());
//                 mapa.put("producto", p);
//                 respuesta[i] = mapa;
//             }
//             return respuesta;
//         }
//         return new Object[]{};
//     }
//     public DetalleVentaServices() { // Constructor de la clase
//         obj = new DetalleVentaDao(); // Instancia un objeto de la clase DetalleVentaDao
//     }
//     public Boolean update() throws Exception { // Guarda la variable DetalleVenta en la lista de objetos
//         return obj.update(); // Invoca el método save() de la clase DetalleVentaDao
//     }
//     public Boolean save() throws Exception { // Guarda la variable DetalleVenta en la lista de objetos
//         return obj.save(); // Invoca el método save() de la clase DetalleVentaDao
//     }
//     public LinkedList<DetalleVenta> listAll() { // Obtiene la lista de objetos LinkedList<T>
//         return obj.getlistAll(); // Invoca el método getlistAll() de la clase DetalleVentaDao
//     }
//     public DetalleVenta getDetalleVenta() { // Obtiene la DetalleVenta
//         return obj.getDetalleVenta(); // Invoca el método getDetalleVenta() de la clase DetalleVentaDao
//     }
//     public void setDetalleVenta(DetalleVenta DetalleVenta) { // Recibe un objeto DetalleVenta
//         obj.setDetalleVenta(DetalleVenta); // Invoca el método setLote() de la clase DetalleVentaDao y envía el objeto
//         // DetalleVenta
//     }
//     public DetalleVenta get(Integer id) throws Exception { // Obtiene un objeto DetalleVenta por su id
//         return obj.get(id); // Invoca el método get() de la clase DetalleVentaDao y envía el id
//     }
//     public Boolean delete(int index) throws Exception { // Elimina un objeto DetalleVenta por su índice
//         return obj.delete(index); // Invoca el método delete() de la clase DetalleVentaDao y envía el índice
//     }
// }