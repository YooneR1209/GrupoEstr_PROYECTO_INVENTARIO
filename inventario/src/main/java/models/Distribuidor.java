package models;

public class Distribuidor {
    Integer id;
    String nombre;
    String cedula;
    String celular;
    String descripcion;

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
        if (nombre != null && nombre.matches(".*[a-zA-Z].*")) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre del distribuidor debe contener al menos una letra.");
        }
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula != null && cedula.matches("\\d{10}")) {
            this.cedula = cedula;
        } else {
            throw new IllegalArgumentException("La cédula debe tener exactamente 10 dígitos numéricos.");
        }
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
