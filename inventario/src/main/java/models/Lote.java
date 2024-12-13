package models;

public class Lote {
    private Integer id;
    private String codigoLote;
    private int cantidad;
    private float precioCompra;
    private float precioVenta;
    private String fechaVencimiento;
    private String fechaCreacion;
    private String descripcionLote;
    private Integer id_Producto;

    public Lote(Integer id, int cantidad, float precioCompra, float precioVenta, String fechaVencimiento,
            String fechaCreacion, String descripcionLote, Integer id_Producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaCreacion = fechaCreacion;
        this.descripcionLote = descripcionLote;
        this.id_Producto = id_Producto;
    }

    public Lote() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcionLote() {
        return descripcionLote;
    }

    public void setDescripcionLote(String descripcionLote) {
        this.descripcionLote = descripcionLote;
    }

    public Integer getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(Integer id_Producto) {
        this.id_Producto = id_Producto;
    }
   
    public String toString() {
        return "Producto{id='"+ id + "', codigoLote='" + codigoLote + "', id_Producto='" + id_Producto +"}";
    }
}
