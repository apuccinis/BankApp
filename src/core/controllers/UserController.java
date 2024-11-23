/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;
/**
 *
 * @author User
 */
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.User;
import core.models.storage.StorageUser;
import java.util.ArrayList;

public class UserController {

    // Método para crear un usuario (Botón Register)
    public static Response createUser(String id, String firstname, String lastname, String age) {
        try {
            int idInt, ageInt;

            // Validación del id
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0 || String.valueOf(idInt).length() > 9) {
                    return new Response("Id must be a positive integer with a maximum of 9 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            // Validación del firstname
            if (firstname.isEmpty()) {
                return new Response("Firstname must not be empty", Status.BAD_REQUEST);
            }

            // Validación del lastname
            if (lastname.isEmpty()) {
                return new Response("Lastname must not be empty", Status.BAD_REQUEST);
            }

            // Validación de la edad
            try {
                ageInt = Integer.parseInt(age);
                if (ageInt < 18) {
                    return new Response("Age must be at least 18", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Age must be numeric", Status.BAD_REQUEST);
            }

            // Creación del usuario y almacenamiento
            User user = new User(idInt, firstname, lastname, ageInt);
            StorageUser storageUser = StorageUser.getInstance();
            if (!storageUser.addUser(user)) {
                return new Response("User with this ID already exists", Status.BAD_REQUEST);
            }

            return new Response("User created successfully", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para leer un usuario (Template Del ejemplo MVC)
    public static Response readUser(String id) {
        try {
            int idInt;

            // Validación del id
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0 || String.valueOf(idInt).length() > 9) {
                    return new Response("Id must be a positive integer with a maximum of 9 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            // Buscar el usuario
            StorageUser storageUser = StorageUser.getInstance();
            User user = storageUser.getUser(idInt);
            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            return new Response("User found", Status.OK, user);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Método para listar usuarios RESPONSE RESPONSE RESFRESH de BankFrame
    public static Response getAllUsers() {
        try {
            StorageUser storageUser = StorageUser.getInstance();

            ArrayList<User> users = storageUser.getUsers();
            
            users.sort((obj1, obj2) -> (obj1.getId() - obj2.getId()));
            
            return new Response("Users retrieved successfully", Status.OK, users);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}
