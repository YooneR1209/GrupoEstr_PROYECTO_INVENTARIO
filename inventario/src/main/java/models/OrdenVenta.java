package models;
public class OrdenVenta {
    private Integer idVenta;
    private String fechaVenta;
    private Float subtotalVenta;
    private Float iva;
    private Float totalVenta;
    private String n_OrdenVenta;
    private Integer IdDetalleVenta;
    public OrdenVenta() {
    }
    public OrdenVenta(Integer idVenta, String fechaVenta, Float subtotalVenta, Float iva, Float totalVenta, String n_OrdenVenta, Integer IdDetalleVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.subtotalVenta = subtotalVenta;
        this.iva = iva;
        this.totalVenta = totalVenta;
        this.n_OrdenVenta = n_OrdenVenta;
        this.IdDetalleVenta = IdDetalleVenta;
    }
    public Integer getIdVenta() {
        return idVenta;
    }
    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }
    public String getFechaVenta() {
        return fechaVenta;
    }
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    public Float getSubtotalVenta() {
        return subtotalVenta;
    }
    public void setSubtotalVenta(Float subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }
    public Float getIva() {
        return iva;
    }
    public void setIva(Float iva) {
        this.iva = iva;
    }
    public Float getTotalVenta() {
        return totalVenta;
    }
    public void setTotalVenta(Float totalVenta) {
        this.totalVenta = totalVenta;
    }
    public String getN_OrdenVenta() {
        return n_OrdenVenta;
    }
    public void setN_OrdenVenta(String n_OrdenVenta) {
        this.n_OrdenVenta = n_OrdenVenta;
    }
    public Integer getIdDetalleVenta() {
        return IdDetalleVenta;
    }
    public void setIdDetalleVenta(Integer IdDetalleVenta) {
        this.IdDetalleVenta = IdDetalleVenta;
    }
}