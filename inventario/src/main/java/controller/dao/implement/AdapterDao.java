package controller.dao.implement;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Scanner;
import com.google.gson.Gson;
import controller.tda.list.LinkedList;

public class AdapterDao<T> implements InterfazDao<T> {
    private Class<T> clazz;
    private Gson g;

    // Cambiamos a una ruta absoluta en la raíz del proyecto
    public static String filePath = "data/"; // src/main/java/Data/

    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.g = new Gson();
        // Crear el directorio data si no existe
        File dataDir = new File(filePath);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public T get2(Integer id) throws Exception {
        LinkedList<T> list = listAll(); // Implementar según sea necesario
        if (!list.isEmpty()) {
            T[] matriz = list.toArray();
            for (int i = 0; i < matriz.length; i++) {
                if (getIdent(matriz[i]).intValue() == id.intValue()) {
                    return matriz[i];
                }
            }
        }
        return null;
    }

    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll(); // Implementar según sea necesario
        if(!list.isEmpty()){
            T[] matriz = list.toArray();
            return matriz[id - 1];
        }
        return null;
    }

    private Integer getIdent(T obj) {
        try {
            Method method = null;
            for (Method m : clazz.getMethods()) {
                if (m.getName().equalsIgnoreCase("getId")) {
                    method = m;
                    break;
                }
            }
            if (method == null) {
                for (Method m : clazz.getSuperclass().getMethods()) {
                    if (m.getName().equalsIgnoreCase("getId")) {
                        method = m;
                        break;
                    }
                }
            }
            if (method != null)
                return (Integer) method.invoke(obj);
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    // to_list
    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile();
            T[] matrix = g.fromJson(data, com.google.gson.reflect.TypeToken.getArray(clazz).getType());
            list.toList(matrix);
        } catch (Exception e) {
            System.out.println("Error al leer la lista listAll: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void merge(T object, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, index);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public void mergeA(T object, Integer index) throws Exception {
        LinkedList<T> list = listAll();
        list.updateA(object, index);
        String info = g.toJson(list.toArray());
        saveFile(info);
    }

    public void persist(T object) throws Exception {
        System.out.println("Persisting object: " + object);
        LinkedList<T> list = listAll();
        if (list == null) {
            System.out.println("La lista es null. Asegúrate de que el archivo JSON se esté leyendo correctamente.");
            return;
        }
        list.add(object);
        String info = g.toJson(list.toArray());
        System.out.println("Escribiendo datos al archivo: " + info);
        saveFile(info);
    }

    private String readFile() throws Exception {
        File file = new File(filePath + clazz.getSimpleName() + ".json");

        if (!file.exists()) {
            System.out.println("El archivo no existe, creando uno nuevo: " + file.getAbsolutePath());
            file.getParentFile().mkdirs();
            saveFile("[]");
        }

        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        }
        return sb.toString().trim();
    }

    protected void saveFile(String data) throws Exception {
        File file = new File(filePath + clazz.getSimpleName() + ".json");
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            System.out.println("Creando el archivo JSON: " + file.getAbsolutePath());
            file.createNewFile();
        }

        try (FileWriter f = new FileWriter(file)) {
            f.write(data);
            f.flush();
            System.out.println("Datos guardados exitosamente en: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Boolean supreme(int index) throws Exception {
        LinkedList<T> list = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        list.remove(index); // Elimina el objeto en la posición index
        System.out.println("lista después de eliminar " + list.toString());
        String info = g.toJson(list.toArray()); // Convierte la lista en un String JSON
        saveFile(info); // Guarda el String JSON en un archivo
        return true; // Retorna verdadero si se eliminó correctamente
    }
    
}
