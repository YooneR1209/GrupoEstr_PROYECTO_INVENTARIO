package models;

public class DetalleVenta {

    private Integer idDetalleVenta;
    private String nombreProducto;
    private String descripcionVenta;
    private Integer cantidadProductos;

    public DetalleVenta() {
    }

    public DetalleVenta(Integer idDetalleVenta,
            String nombreProducto,
            String descripcionVenta,
            Integer cantidadProductos
    ) {
        this.idDetalleVenta = idDetalleVenta;
        this.nombreProducto = nombreProducto;
        this.descripcionVenta = descripcionVenta;
        this.cantidadProductos = cantidadProductos;

    }

    public Integer getId() {
        return idDetalleVenta;
    }

    public void setId(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getdescripcionVenta() {
        return descripcionVenta;
    }

    public void setdescripcionVenta(String descripcionVenta) {
        this.descripcionVenta = descripcionVenta;
    }

    public Integer getcantidadProductos() {
        return cantidadProductos;
    }

    public void setcantidadProductos(Integer cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

}
