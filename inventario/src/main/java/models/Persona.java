package models;

import java.util.regex.Pattern;

public class Persona {
    private Integer idPersona;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String dni;
    private String clave;
    private String token;

    // Constructor completo
    public Persona(Integer idPersona, String nombre, String apellido, String telefono, String correo, String dni, String clave, String token) {
        this.idPersona = idPersona;
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
        setCorreo(correo);
        setDni(dni);
        setClave(clave);
        this.token = token;
    }

    // Constructor vacío
    public Persona() {
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && nombre.length() <= 30) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre debe tener un máximo de 30 caracteres.");
        }
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido != null && apellido.length() <= 30) {
            this.apellido = apellido;
        } else {
            throw new IllegalArgumentException("El apellido debe tener un máximo de 30 caracteres.");
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && telefono.matches("\\d{10}")) {
            this.telefono = telefono;
        } else {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos.");
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (correo != null && Pattern.matches(emailRegex, correo)) {
            this.correo = correo;
        } else {
            throw new IllegalArgumentException("El correo electrónico debe tener un formato válido.");
        }
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni != null && dni.matches("\\d{10}")) {
            this.dni = dni;
        } else {
            throw new IllegalArgumentException("El DNI debe tener exactamente 10 dígitos.");
        }
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        String claveRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (clave != null && Pattern.matches(claveRegex, clave)) {
            this.clave = clave;
        } else {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula y un número.");
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
