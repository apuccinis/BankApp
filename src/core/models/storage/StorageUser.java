/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.User;
import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class StorageUser {

    // (Del Template MVC)
    // Instancia Singleton
    private static StorageUser instance;

    // Atributos del Storage
    private ArrayList<User> users;

    private StorageUser() {
        this.users = new ArrayList<>();
    }

    public static StorageUser getInstance() {
        if (instance == null) {
            instance = new StorageUser();
        }
        return instance;
    }

    // Método para agregar un usuario
    public boolean addUser(User user) {
        for (User u : this.users) {
            if (u.getId() == user.getId()) { // Comparación de enteros
                return false; // Usuario ya existe
            }
        }
        this.users.add(user);
        return true; // Usuario agregado correctamente
    }

    // Método para obtener un usuario por ID
    public User getUser(int id) { // Ajuste: el parámetro debe ser int
        for (User user : this.users) {
            if (user.getId() == id) { // Comparación de enteros
                return user;
            }
        }
        return null; // Usuario no encontrado
    }

    // Método para eliminar un usuario por ID
    public boolean delUser(int id) { // Ajuste: el parámetro debe ser int
        for (User user : this.users) {
            if (user.getId() == id) { // Comparación de enteros
                this.users.remove(user);
                return true; // Usuario eliminado
            }
        }
        return false; // Usuario no encontrado
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
