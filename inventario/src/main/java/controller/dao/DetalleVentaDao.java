// package controller.dao;
// import controller.dao.implement.AdapterDao;
// import controller.tda.list.LinkedList;
// import models.DetalleVenta;

// public class DetalleVentaDao extends AdapterDao<DetalleVenta> {
//     private DetalleVenta detalleVenta = new DetalleVenta();
//     private LinkedList<DetalleVenta> listAll;
//     public DetalleVentaDao() {
//         super(DetalleVenta.class);
//     }
//     public DetalleVenta getDetalleVenta() { // Obtiene la detalleVenta
//         if (detalleVenta == null) {
//             detalleVenta = new DetalleVenta(); // En caso de que la detalleVenta sea nula, crea una nueva instancia de DetalleVenta
//         }
//         return this.detalleVenta; // Devuelve la detalleVenta
//     }
//     public void setDetalleVenta(DetalleVenta detalleVenta) { // Establece la detalleVenta con un objeto DetalleVenta
//         this.detalleVenta = detalleVenta; // Asigna el objeto DetalleVenta a la variable detalleVenta
//     }
//     public LinkedList<DetalleVenta> getlistAll() { // Obtiene la lista de objetos
//         if (listAll == null) { // Si la lista es nula
//             this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
//         }
//         return listAll; // Devuelve la lista de objetos de la variable listAll
//     }
//     public Boolean save() throws Exception { // Guarda la variable detalleVenta en la lista de objetos
//         Integer id = getlistAll().getSize() + 1; // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
//         detalleVenta.setIdDetalleVenta(id); // Asigna el id a detalleVenta
//         this.persist(this.detalleVenta); // Guarda la detalleVenta en la lista de objetos LinkedList y en el archivo JSON
//         this.listAll = listAll(); // Actualiza la lista de objetos
//         return true; // Retorna verdadero si se guardó correctamente
//     }
//     public Boolean update() throws Exception { // Actualiza el nodo DetalleVenta en la lista de objetos
//         this.merge(getDetalleVenta(), getDetalleVenta().getIdDetalleVenta() - 1); // Envia la detalleVenta a actualizar con su index
//         this.listAll = listAll(); // Actualiza la lista de objetos
//         return true;
//     }
//     public Boolean delete(int index) throws Exception { // Elimina un objeto DetalleVenta por su índice
//         this.supreme(index);
//         this.listAll = listAll(); // Actualiza la lista de objetos
//         return true; // Retorna verdadero si se eliminó correctamente
//     }
// }