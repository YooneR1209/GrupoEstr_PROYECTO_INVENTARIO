package controller.dao;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Venta;

public class VentaDao extends AdapterDao<Venta> {

    private Venta persona;
    private LinkedList listAll;

    public VentaDao() {
        super(Venta.class);
    }

    public Venta getVenta() {
        if (persona == null) {
            persona = new Venta();
        }
        return this.persona;
    }

    public void setVenta(Venta venta) {
        this.persona = persona;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        persona.setId(id);
        this.persist(this.persona);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getVenta(), getVenta().getId() - 1);
        this.listAll = listAll();
        return true;
    }

}
