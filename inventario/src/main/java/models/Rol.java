package models;

public class Rol {
        private int idRol;
        private String nombre;
        private String descripcion;
        private String tipo;
    
        // Constructor vacío
        public Rol() {}
    
        // Constructor con parámetros
        public Rol(int idRol, String nombre, String descripcion, String tipo) {
            this.idRol = idRol;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.tipo = tipo;
        }
    
        // Getters y Setters
        public int getIdRol() {
            return idRol;
        }
    
        public void setIdRol(int idRol) {
            this.idRol = idRol;
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
    
        public String getTipo() {
            return tipo;
        }
    
        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    
        // Método toString para representar el objeto como una cadena
        @Override
        public String toString() {
            return "Rol{" +
                    "idRol=" + idRol +
                    ", nombre='" + nombre + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    ", tipo='" + tipo + '\'' +
                    '}';
        }
    }
