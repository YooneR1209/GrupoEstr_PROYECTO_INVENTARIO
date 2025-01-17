package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import controller.dao.PersonaServices;

/**
 * Clase PersonaApi: Exposición de un API REST que proporciona operaciones relacionadas con las personas.
 * Permite listar, guardar, actualizar, obtener y realizar inicio de sesión para las personas.
 */
@Path("/persona")
public class PersonaApi {

    /**
     * Endpoint que lista todas las personas registradas.
     * 
     * @return Response con la lista de personas o un mensaje si no hay personas registradas.
     */
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getpresona(){
        HashMap<String, Object> map = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        
        map.put("personas", "Lista de personas");
        map.put("data", ps.listAll().toArray());
        
        if (ps.listAll().isEmpty()){
            map.put("message", "No hay personas registradas");
        }
        
        return Response.ok(map).build();  
    }

    /**
     * Endpoint para guardar una nueva persona.
     * 
     * param map Datos de la persona en formato JSON (nombre, apellido, telefono, etc.).
     * return Response con el resultado del registro (éxito o error).
     */
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePersona(HashMap<String , Object> map){
        HashMap<String, Object> res = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        try {
            ps.getPersona().setNombre(map.get("nombre").toString());
            ps.getPersona().setApellido(map.get("apellido").toString());
            ps.getPersona().setTelefono(map.get("telefono").toString());
            ps.getPersona().setCorreo(map.get("correo").toString());
            ps.getPersona().setDni(map.get("dni").toString());
            ps.getPersona().setClave(map.get("clave").toString());

            
            ps.save();
            res.put("message", "Persona registrada correctamente");
            res.put("data", "Guardado");
            res.put("token", ps.getPersona().getToken());  
            
            return Response.ok(res).build(); 
        } catch (Exception e) {
            
            if (e.getMessage().contains("Correo o DNI ya existen")) {
                res.put("message", "Correo o DNI ya existen");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();  
            }
            res.put("message", "Error al registrar persona");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();  
        }
    }

    /**
     * Endpoint que obtiene los datos de una persona específica por su ID.
     * 
     * param id El ID de la persona a obtener.
     * return Response con los datos de la persona o un error si no se encuentra.
     */
    @Path("/list/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersona(@PathParam("id") Integer id){
        HashMap<String, Object> map = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        try{
            ps.setPersona(ps.get(id));

        } catch (Exception e) {

        } 
        map.put("msg", "Proyecto");
        map.put("data", ps.getPersona());
        if(ps.getPersona().getIdPersona() == null){
            map.put("message", "Persona no encontrada");
            return Response.status(Response.Status.NOT_FOUND).entity(map).build();  
        }
        return Response.ok(map).build();
    }
    /**
     * Endpoint para actualizar los datos de una persona.
     * 
     * param map Datos de la persona a actualizar (ID, nombre, apellido, etc.).
     * return Response con el resultado de la actualización.
     */
    @Path("/actualizar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePersona(HashMap<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        try {
            // Asignación de los nuevos datos de la persona
            ps.getPersona().setIdPersona(Integer.parseInt(map.get("idPersona").toString()));
            ps.getPersona().setNombre(map.get("nombre").toString());
            ps.getPersona().setApellido(map.get("apellido").toString());
            ps.getPersona().setTelefono(map.get("telefono").toString());
            ps.getPersona().setCorreo(map.get("correo").toString());
            ps.getPersona().setDni(map.get("dni").toString());
            ps.getPersona().setClave(map.get("clave").toString());

            
            ps.update();
            res.put("message", "Persona actualizada correctamente");
            res.put("data", "Actualizado");
            res.put("token", ps.getPersona().getToken());
            return Response.ok(res).build();  
        } catch (Exception e) {
            res.put("message", "Error al actualizar persona");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();  
        }
    }

    /**
     * Endpoint para realizar el inicio de sesión de una persona.
     * Valida el correo y la clave, y devuelve un token si es exitoso.
     * 
     * param map Contiene el correo y la clave para el inicio de sesión.
     * return Response con el token generado o un mensaje de error.
     */
    @Path("/iniciosesion")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciar(HashMap<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        PersonaServices ps = new PersonaServices();

        try {
            String correo = map.get("correo").toString();
            String clave = map.get("clave").toString();
            
            NewCookie cookie = new NewCookie("token", ps.getPersona().getToken(), "/", null, NewCookie.DEFAULT_VERSION, null, 3600, false);

            if (ps.iniciosesion(correo, clave)) {
                res.put("msg", "Login exitoso");
                res.put("token", ps.getPersona().getToken());
                res.put("idPersona", ps.getPersona().getIdPersona());
                return Response.ok(res).cookie(cookie).build();  
            } else {
                res.put("msg", "Correo o clave incorrectos");
                return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();  
            }
        } catch (Exception e) {
            res.put("msg", "Error al iniciar sesión");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();  
        }
    }

    @Path("/recuperar_clave")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recuperarClave(HashMap<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        String correo = map.get("correo").toString();
        String nuevaClave = map.get("nuevaClave").toString();                        ;
        try {
            if (ps.recuperarClave(correo, nuevaClave)) {
                res.put("msg", "Clave recuperada");
                res.put("clave", ps.getPersona().getClave());
                return Response.ok(res).build();  
            } else {
                res.put("msg", "Correo no encontrado");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();  
            }
        } catch (Exception e) {
            res.put("msg", "Error al recuperar clave");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();  
        }
    }

    
    @Path("/correoexiste")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response correoexiste(HashMap<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        PersonaServices ps = new PersonaServices();
        String correo = map.get("correo").toString();
        try {
            if (ps.existeCorreo(correo)) {
                res.put("msg", "Correo encontrado");
                res.put("persona", ps.getPersona());	
                return Response.ok(res).build();  
            } else {
                res.put("msg", "Correo no encontrado");
                return Response.status(Response.Status.NOT_FOUND).entity(res).build();  
            }
        } catch (Exception e) {
            res.put("msg", "Error al buscar correo");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();  
        }
    }

}
