package models;

import controller.tda.list.LinkedList;

public class DetalleVenta {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private Float precioUnitario;
    private Float precioTotal;
    private LinkedList<DetalleVenta> detalle;

    public DetalleVenta() {
    }

    /*cantidad
precio unitario
precio total
id orden
id service */
    public DetalleVenta(Integer id,
            String nombre,
            String descripcion,
            Integer cantidad,
            Float precioUnitario,
            Float precioTotal
    ) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getNombreProducto() {
        return cantidad;
    }

    public void setNombreProducto(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LinkedList<DetalleVenta> getDetalle() {
        return this.detalle;
    }

    public void setDetalle(LinkedList<DetalleVenta> detalle) {
        this.detalle = detalle;
    }

}
