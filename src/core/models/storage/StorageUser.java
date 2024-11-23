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
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        this.users.add(user);
        return true;
    }

    // Método para obtener un usuario por ID
    public User getUser(int id) {
        for (User user : this.users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    // Método para eliminar un usuario por ID
    public boolean delUser(int id) {
        for (User user : this.users) {
            if (user.getId() == id) {
                this.users.remove(user);
                return true;
            }
        }
        return false;
    }
    
    // Método para obtener todas las cuentas (si es necesario)
    public ArrayList<User> getUsers() {
        return users;
    }
    
    
}
