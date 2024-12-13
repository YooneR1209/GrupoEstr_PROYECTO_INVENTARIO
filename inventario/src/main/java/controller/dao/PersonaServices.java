package controller.dao;

import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.Persona;

/**
 * Clase PersonaServices: Proporciona una capa de servicio que interactúa con el DAO de Persona.
 * Gestiona operaciones relacionadas con la entidad Persona, como la creación, actualización,
 * obtención y validación de sesiones.
 */
public class PersonaServices {
    private PersonaDao obj;
    /**
     * Constructor de la clase PersonaServices. Inicializa el DAO de Persona.
     */
    public PersonaServices() {
        obj = new PersonaDao();  
    }

    /**
     * Guarda una nueva persona en la base de datos. Llama al método save() del DAO.
     * 
     * return true si la operación fue exitosa.
     * throws Exception Si ocurre un error al guardar la persona.
     */
    public Boolean save() throws Exception {
        return obj.save();  
    }

    /**
     * Actualiza los datos de la persona en la base de datos. Llama al método update() del DAO.
     * 
     * return true si la operación fue exitosa.
     * throws Exception Si ocurre un error al actualizar la persona.
     */
    public Boolean update() throws Exception {
        return obj.update();  
    }

    /**
     * Obtiene una lista de todas las personas almacenadas en la base de datos.
     * 
     * return LinkedList de objetos Persona.
     */
    public LinkedList<Persona> listAll() {
        return obj.getListAll();  
    }

    /**
     * Obtiene la instancia actual de Persona.
     * 
     * return La persona actual.
     */
    public Persona getPersona() {
        return obj.getPersona();  
    }

    /**
     * Establece la instancia de Persona.
     * 
     * param persona La nueva instancia de Persona a establecer.
     */
    public void setPersona(Persona persona) {
        obj.setPersona(persona);
    }

    /**
     * Obtiene una persona específica por su ID. Llama al método get() del DAO.
     * 
     * param id El ID de la persona a buscar.
     * return La persona con el ID proporcionado.
     * throws Exception Si no se encuentra la persona o ocurre un error.
     */
    public Persona get(Integer id) throws Exception {
        return obj.get(id); 
    }

    /**
     * Realiza el inicio de sesión para una persona validando su correo y clave.
     * Llama al método iniciosesion() del DAO para verificar las credenciales.
     * 
     * param correo El correo electrónico de la persona.
     * param clave La clave ingresada por la persona.
     * return true si el inicio de sesión es exitoso.
     * throws ListEmptyException Si la lista de personas está vacía.
     */
    public Boolean iniciosesion(String correo, String clave) throws ListEmptyException {
        return obj.iniciosesion(correo, clave); 
    }
}
