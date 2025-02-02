package models;

public class OrdenVenta {

    private String fechaVenta;
    private String n_OrdenVenta;
    private Object detalle; // Puede ser más específico si conoces el tipo exacto

    // Constructor sin argumentos
    public OrdenVenta() {
    }

    // Constructor con argumentos
    public OrdenVenta(String fechaVenta, String n_OrdenVenta) {

        this.fechaVenta = fechaVenta;

        this.n_OrdenVenta = n_OrdenVenta;
    }

    // Getters y setters para los atributos
    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getN_OrdenVenta() {
        return n_OrdenVenta;
    }

    public void setN_OrdenVenta(String n_OrdenVenta) {
        this.n_OrdenVenta = n_OrdenVenta;
    }

    public Object getDetalle() {
        return detalle;
    }

    public void setDetalle(Object detalle) {
        this.detalle = detalle;
    }

}
