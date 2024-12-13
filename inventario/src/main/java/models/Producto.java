package models;

public class Producto {
    private Integer id;
    private String tipoProducto;
    private String nombre;
    private String marca;
    private String descripcion;

    public Producto() {
    }
    
    public Producto(Integer id, String tipoProducto, String nombre, String marca , String descripcion) {
        this.id = id;
        this.tipoProducto = tipoProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.descripcion = descripcion;
    }

    public Producto(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }
    
    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    // public String toString() {
    //     return "Producto{id='"+ id + "', nombre='" + nombre + "', marca='" + marca + "', descripcion='"+ descripcion + "}";
    // }

}
