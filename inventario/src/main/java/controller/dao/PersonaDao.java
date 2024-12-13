package controller.dao;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.Persona;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase PersonaDao: Maneja las operaciones de persistencia y lógica para la entidad Persona.
 * Hereda de AdapterDao para aprovechar las operaciones CRUD genéricas.
 */
public class PersonaDao extends AdapterDao<Persona> {
    private Persona persona; 
    private LinkedList<Persona> listAll; 

    /**
     * Constructor que inicializa el DAO con la clase Persona.
     */
    public PersonaDao() {
        super(Persona.class);
    }

    /**
     * Obtiene la instancia actual de Persona. Si no existe, crea una nueva.
     * 
     * return La instancia de Persona actual.
     */
    public Persona getPersona() {
        if(persona == null) {
            persona = new Persona();
        }
        return this.persona;
    }

    /**
     * Establece la instancia actual de Persona.
     * 
     * param persona La nueva instancia de Persona.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * Obtiene una lista de todas las personas almacenadas. Si la lista no está inicializada,
     * la carga desde la json.
     * 
     * return LinkedList con todas las personas.
     */
    public LinkedList<Persona> getListAll() {
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll();
    }

    /**
     * Guarda la instancia actual de Persona en la json.
     * Verifica unicidad del correo y DNI antes de guardar.
     * Genera una clave cifrada y un token para la Persona.
     * 
     * return true si la operación fue exitosa.
     * throws Exception Si el correo o DNI ya existen.
     */
    public Boolean save() throws Exception {
        if (!isUnique(getPersona().getCorreo(), getPersona().getDni())) {
            throw new Exception("Correo o DNI ya existen.");
        }
        Integer id = getListAll().getSize() + 1;
        getPersona().setIdPersona(id);
        String encryclave = getPersona().getClave();
        getPersona().setClave(encryclave(encryclave));
        String token = TokenUtil.generateToken(getPersona().getIdPersona(), getPersona().getCorreo());
        getPersona().setToken(token);
        persist(getPersona());
        return true;
    }

    /**
     * Actualiza la información de la instancia actual de Persona en la json.
     * 
     * return true si la operación fue exitosa.
     *throws Exception Si ocurre un error en la actualización.
     */
    public Boolean update() throws Exception {
        String newtoken = TokenUtil.generateToken(getPersona().getIdPersona(), getPersona().getCorreo());
        getPersona().setToken(newtoken);
        String encryclave = getPersona().getClave();
        getPersona().setClave(encryclave(encryclave));
        this.merge(getPersona(), getPersona().getIdPersona() - 1);
        this.listAll = listAll();
        return true;
    }

    /**
     * Inicia sesión para un usuario. Valida el correo y la clave encriptada,
     * y genera un nuevo token si las credenciales son correctas.
     * 
     * param correo El correo electrónico del usuario.
     * param clave La clave ingresada.
     * return true si las credenciales son válidas.
     *throws ListEmptyException Si la lista de personas está vacía.
     */
    public Boolean iniciosesion(String correo, String clave) throws ListEmptyException {
        LinkedList<Persona> list = listAll();
        Persona[] personas = list.toArray();
    
        for (Persona persona : personas) {
            if (persona.getCorreo().equals(correo)) {
                if (BCrypt.checkpw(clave, persona.getClave())) {
                    String token = TokenUtil.generateToken(persona.getIdPersona(), persona.getCorreo());
                    persona.setToken(token);
    
                    for (int i = 0; i < list.getSize(); i++) {
                        if (list.get(i).getIdPersona().equals(persona.getIdPersona())) {
                            list.update(persona, i);  
                            break;
                        }
                    }

                    setPersona(persona);
                    return true; 
                }
            }
        }
    
        return false;  
    }

    /**
     * Cifra una clave utilizando el algoritmo BCrypt.
     * 
     * param clave La clave en texto plano.
     * return La clave cifrada.
     */
    public static String encryclave(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt());
    }
    
    /**
     * Verifica si el correo y el DNI de una persona son únicos en la lista de personas.
     * 
     * param correo El correo a verificar.
     * param dni El DNI a verificar.
     * return true si ambos son únicos, false de lo contrario.
     * throws ListEmptyException Si la lista de personas está vacía.
     */
    public Boolean isUnique(String correo, String dni) throws ListEmptyException {
        LinkedList<Persona> list = listAll();
        Persona[] personas = list.toArray();

        for (Persona persona : personas) {
            if (persona.getCorreo().equals(correo) || persona.getDni().equals(dni)) {
                return false; 
            }
        }

        return true;  
    }
}
