package models;

public class OrdenCompra {
    private Integer id;
    private String nro_OrdenCompra;
    private String fechaCompra;
    private String cedula_Distribuidor;
    private Lote[] loteList;
    private float totalCompra;

    public OrdenCompra(Integer id, String nro_OrdenCompra, String fechaCompra, String cedula_Distribuidor,
            Lote[] loteList, float totalCompra) {
        this.id = id;
        this.nro_OrdenCompra = nro_OrdenCompra;
        this.fechaCompra = fechaCompra;
        this.cedula_Distribuidor = cedula_Distribuidor;
        this.loteList = loteList;
        this.totalCompra = totalCompra;
    }

    public OrdenCompra() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNro_OrdenCompra() {
        return nro_OrdenCompra;
    }

    public void setNro_OrdenCompra(String nro_OrdenCompra) {
        this.nro_OrdenCompra = nro_OrdenCompra;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getCedula_Distribuidor() {
        return cedula_Distribuidor;
    }

    public void setCedula_Distribuidor(String cedula_Distribuidor) {
        this.cedula_Distribuidor = cedula_Distribuidor;
    }

    public Lote[] getLoteList() {
        return loteList;
    }

    public void setLoteList(Lote[] loteList) {
        this.loteList = loteList;
    }

    public float getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(float totalCompra) {
        this.totalCompra = totalCompra;
    }

}
