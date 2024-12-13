package models;

public class Venta {

    private Integer idVenta;
    private String venta;
    private Integer cantidad;
    private String fechaVenta;

    private String metodoPago;

    public Venta() {
    }

    public Venta(Integer idVenta, String venta, Integer cantidad, String fechaVenta, String telefono,
            String metodoPago) {
        this.idVenta = idVenta;
        this.venta = venta;
        this.cantidad = cantidad;
        this.fechaVenta = fechaVenta;

        this.metodoPago = metodoPago;
    }

    public Integer getId() {
        return idVenta;
    }

    public void setId(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getventa() {
        return venta;
    }

    public void setventa(String venta) {
        this.venta = venta;
    }

    public Integer getcantidad() {
        return cantidad;
    }

    public void setcantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getfechaVenta() {
        return fechaVenta;
    }

    public void setfechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getmetodoPago() {
        return metodoPago;
    }

    public void setmetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
