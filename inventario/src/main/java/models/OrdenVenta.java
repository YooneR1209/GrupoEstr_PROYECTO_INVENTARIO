package models;

public class OrdenVenta {
    private Integer id;
    private String nro_OrdenVenta;
    private String fechaVenta;
    private Lote[] loteList;
    private float totalVenta;

    public OrdenVenta(Integer id, String nro_OrdenVenta, String fechaVenta,
            Lote[] loteList, float totalVenta) {
        this.id = id;
        this.nro_OrdenVenta = nro_OrdenVenta;
        this.fechaVenta = fechaVenta;
        this.loteList = loteList;
        this.totalVenta = totalVenta;
    }

    public OrdenVenta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNro_OrdenVenta() {
        return nro_OrdenVenta;
    }

    public void setNro_OrdenVenta(String nro_OrdenVenta) {
        this.nro_OrdenVenta = nro_OrdenVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Lote[] getLoteList() {
        return loteList;
    }

    public void setLoteList(Lote[] loteList) {
        this.loteList = loteList;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
    }

    @Override
    public String toString() {
        StringBuilder loteInfo = new StringBuilder();

        if (loteList != null && loteList.length > 0) {
            loteInfo.append("[\n");
            for (Lote lote : loteList) {
                loteInfo.append("  ").append(lote).append(",\n");
            }
            loteInfo.append("]");
        } else {
            loteInfo.append("Sin lotes");
        }

        return "OrdenVenta {" +
                "id=" + id +
                ", nro_OrdenVenta='" + nro_OrdenVenta + '\'' +
                ", fechaVenta='" + fechaVenta + '\'' +
                ", totalVenta=" + totalVenta +
                ", loteList=" + loteInfo +
                '}';
    }

}
