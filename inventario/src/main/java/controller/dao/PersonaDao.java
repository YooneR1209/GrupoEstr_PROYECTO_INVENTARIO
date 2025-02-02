package controller.dao;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;
import models.Persona;
import org.mindrot.jbcrypt.BCrypt;

public class PersonaDao extends AdapterDao<Persona> {
    private Persona persona; 
    private LinkedList<Persona> listAll; 

   
    public PersonaDao() {
        super(Persona.class);
    }

    
    public Persona getPersona() {
        if(persona == null) {
            persona = new Persona();
        }
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LinkedList<Persona> getListAll() {
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll();
    }

    public Boolean save() throws Exception {
        if(!isFirstPerson()) {
            throw new Exception("Ya se ha registrado una persona, no se pueden guardar más.");
        }
        if (!isUnique(getPersona().getCorreo(), getPersona().getDni())) {
            throw new Exception("Correo o DNI ya existen.");
        }
        try {
            Integer id = getListAll().getSize() + 1;
            getPersona().setIdPersona(id);
            String encryclave = getPersona().getClave();
            getPersona().setClave(encryclave(encryclave));
            String token = TokenUtil.generateToken(getPersona().getIdPersona(), getPersona().getCorreo());
            getPersona().setToken(token);
            persist(getPersona());
            System.out.println("Persona guardada exitosamente.");
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar la persona: " + e.getMessage());
                return false;
            }
        }

        public Boolean isFirstPerson() {
            LinkedList<Persona> list = getListAll();
            return list.getSize() == 0; 
        }
    public Boolean update() throws Exception {
        String newtoken = TokenUtil.generateToken(getPersona().getIdPersona(), getPersona().getCorreo());
        getPersona().setToken(newtoken);
        String encryclave = getPersona().getClave();
        getPersona().setClave(encryclave(encryclave));
        this.merge(getPersona(), getPersona().getIdPersona() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean iniciosesion(String correo, String clave) throws ListEmptyException {
        LinkedList<Persona> list = getListAll();
        
        // Búsqueda binaria de la persona por correo
        Persona persona = binarySearch(correo);
    
        if (persona != null && BCrypt.checkpw(clave, persona.getClave())) {
            String token = TokenUtil.generateToken(persona.getIdPersona(), persona.getCorreo());
            persona.setToken(token);
    
            // Actualizar la persona en la lista
            for (int i = 0; i < list.getSize(); i++) {
                if (list.get(i).getIdPersona().equals(persona.getIdPersona())) {
                    list.update(persona, i);  
                    break;
                }
            }

            setPersona(persona);
            return true; 
        }
    
        return false;  
    }

    public static String encryclave(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt());
    }
    
    public Boolean isUnique(String correo, String dni) throws ListEmptyException {
        LinkedList<Persona> list = getListAll();

        // Búsqueda binaria para correo
        if (binarySearch(correo) != null) {
            return false; 
        }

        // Búsqueda binaria para DNI (suponiendo que también se debe ordenar por DNI)
        for (int i = 0; i < list.getSize(); i++) {
            if (list.get(i).getDni().equals(dni)) {
                return false;
            }
        }

        return true;  
    }

    public Boolean recuperarClave(String correo, String nuevaClave) throws ListEmptyException {
        LinkedList<Persona> list = listAll(); // Obtén la lista de todas las personas.
        Persona[] personas = list.toArray(); // Convierte la lista en un array para iterar.
    
        for (int i = 0; i < personas.length; i++) {
            Persona persona = personas[i];
    
            if (persona.getCorreo().equals(correo)) {
                // Generar un nuevo token y cifrar la nueva clave.
                String token = TokenUtil.generateToken(persona.getIdPersona(), persona.getCorreo());
                persona.setToken(token);
    
                String encryclave = encryclave(nuevaClave); // Cifra la nueva clave.
                persona.setClave(encryclave); // Actualiza la clave cifrada.
    
                // Actualizar la lista con la nueva información.
                list.update(persona, i); // Actualiza el objeto Persona en la lista.
    
                // Persistir los cambios directamente con el método `merge`.
                try {
                    merge(persona, i); // `merge` sobrescribe el archivo JSON en la posición correspondiente.
                } catch (Exception e) {
                    System.err.println("Error al persistir la nueva contraseña: " + e.getMessage());
                    return false;
                }
                setPersona(persona);
                return true; // Retorna true si todo fue exitoso.
            }
        }
    
        return false; // Retorna false si el correo no se encuentra.
    }

    public Boolean existeCorreo(String correo) throws ListEmptyException {
        LinkedList<Persona> list = listAll();
        for (Persona persona : list.toArray()) {
            if (persona.getCorreo().equals(correo)) {
                setPersona(persona);
                return true;

                    

            }
        }
        return null;

    }


    @SuppressWarnings("unused")
    private void sortListByCorreo() throws ListEmptyException {
        // Implementar el algoritmo de ordenación, por ejemplo, un algoritmo de ordenación de burbuja.
        for (int i = 0; i < listAll.getSize(); i++) {
            for (int j = i + 1; j < listAll.getSize(); j++) {
                if (listAll.get(i).getCorreo().compareTo(listAll.get(j).getCorreo()) > 0) {
                    // Intercambiar las personas
                    Persona temp = listAll.get(i);
                    listAll.update(listAll.get(j), i);
                    listAll.update(temp, j);
                }
            }
        }
    }

    private Persona binarySearch(String correo) throws IndexOutOfBoundsException, ListEmptyException {
        int left = 0;
        int right = listAll.getSize() - 1;
        
        while (left <= right) {
            int middle = left + (right - left) / 2;
            Persona persona = listAll.get(middle);
            
            // Si el correo coincide
            if (persona.getCorreo().equals(correo)) {
                return persona;
            }
            
            if (persona.getCorreo().compareTo(correo) < 0) {
                left = middle + 1;
            }
            else {
                right = middle - 1;
            }
        }
        
        // Si no se encuentra
        return null;
    }

    public Persona getPersonaByToken(String token) throws ListEmptyException {
        LinkedList<Persona> list = getListAll();
        
        for (Persona persona : list.toArray()) {
            if (persona.getToken().equals(token)) {
                return persona;
            }
        }
        return null; // Si no se encuentra la persona con ese token
    }

    // Agregado al final de PersonaDao

public LinkedList<String> getAllDnis() {
    LinkedList<String> dnis = new LinkedList<>();
    for (Persona persona : getListAll().toArray()) {
        dnis.add(persona.getDni());
    }
    return dnis;
}

    
    
    
    



    
    
    
}
