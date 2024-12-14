package controller.dao;
import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.OrdenVenta;
public class OrdenVentaDao extends AdapterDao<OrdenVenta> {
    private OrdenVenta orden_Venta = new OrdenVenta();
    private LinkedList<OrdenVenta> listAll;
    public OrdenVentaDao() {
        super(OrdenVenta.class);
    }
    public OrdenVenta getOrdenVenta() {
        if (orden_Venta == null) {
            orden_Venta = new OrdenVenta();
        }
        return this.orden_Venta;
    }
    public void setOrdenVenta(OrdenVenta orden_Venta) {
        this.orden_Venta = orden_Venta;
    }
    public LinkedList<OrdenVenta> getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }
    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        orden_Venta.setIdVenta(id);
        this.persist(this.orden_Venta);
        this.listAll = listAll();
        return true;
    }
    public Boolean update() throws Exception {
        this.merge(getOrdenVenta(), getOrdenVenta().getIdVenta() - 1);
        this.listAll = listAll();
        return true;
    }
    public Boolean delete(int index) throws Exception { // Elimina un objeto Lote por su índice
        this.supreme(index);
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se eliminó correctamente
    }
}