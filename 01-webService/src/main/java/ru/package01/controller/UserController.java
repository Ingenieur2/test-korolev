package ru.package01.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.package01.core.model.User;
import ru.package01.core.service.DbServiceUser;

import java.util.List;

@Controller
public class UserController {
    private final SimpMessagingTemplate messagingTemplate;
    private final DbServiceUser dbServiceUser;


    public UserController(SimpMessagingTemplate messagingTemplate, DbServiceUser dbServiceUser) {
        this.messagingTemplate = messagingTemplate;
        this.dbServiceUser = dbServiceUser;
    }

    @MessageMapping("/chat.addUser")
    public void userSave(String userString) {
        long id = dbServiceUser.saveUser(userString);
        if (id != 0) {
            messagingTemplate.convertAndSend("/topic/users", dbServiceUser.getUser(id));
        }
    }

    @MessageMapping("/chat.updateUser")
    public void userUpdate(String userString) {
        long id = dbServiceUser.updateUser(userString);
        if (id != 0) {
            messagingTemplate.convertAndSend("/topic/users", dbServiceUser.getUser(id));
        }
    }

    @MessageMapping("/chat.deleteUser")
    public void userDelete(String userString) {
        long id = dbServiceUser.deleteUser(userString);
        if (id != 0) {
            messagingTemplate.convertAndSend("/topic/users", dbServiceUser.getUser(id));
        }
    }

    @MessageMapping("/chat.getAllUsers")
    public void getAll() {
        List<User> users = dbServiceUser.getAll();
        messagingTemplate.convertAndSend("/topic/getAllUsers", users);
    }
}
