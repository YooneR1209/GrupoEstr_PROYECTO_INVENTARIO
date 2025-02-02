package controller.dao;

import controller.tda.list.LinkedList;
import models.OrdenVenta;

public class OrdenVentaServices {

    private OrdenVentaDao obj;

    // Constructor
    public OrdenVentaServices() {
        obj = new OrdenVentaDao();
    }

    // Métodos de CRUD y acceso a datos
    public LinkedList<OrdenVenta> listAll() {
        return obj.getlistAll();
    }

    public OrdenVenta getOrdenVenta() {
        return obj.getOrdenVenta();
    }

    public void setOrdenVenta(OrdenVenta ordenVenta) {
        obj.setOrdenVenta(ordenVenta);
    }

}
