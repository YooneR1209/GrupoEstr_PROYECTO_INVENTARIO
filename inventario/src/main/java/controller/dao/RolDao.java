package controller.dao;

import controller.dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Rol;

public class RolDao extends AdapterDao<Rol> {
    private Rol rol;
    private LinkedList<Rol> listAll;

    public RolDao(){
        super(Rol.class);
    }

    public Rol getRol(){
        if (rol == null) {
            rol = new Rol();
        }
        return this.rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public LinkedList<Rol> getListAll(){
        if (this.listAll == null) {
            this.listAll = new LinkedList<>();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        if (getRol() == null) {
            throw new Exception("El rol no está configurado");
        }
        Integer id = getListAll().getSize() + 1;
        getRol().setIdRol(id); // Asigna un nuevo id al rol
        persist(getRol()); // Guarda el rol en la base de datos
        return true;
    }

    public Boolean update() throws Exception {
        if (getRol() == null) {
            throw new Exception("El rol no está configurado");
        }
        // this.update(getRol()); // Actualiza el rol
        this.listAll = getListAll(); // Refresca la lista
        return true;
    }
}
