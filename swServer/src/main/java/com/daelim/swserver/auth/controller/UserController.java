package com.daelim.swserver.auth.controller;

import com.daelim.swserver.auth.dto.UserDTO;
import com.daelim.swserver.auth.entity.User;
import com.daelim.swserver.auth.repository.UserRepository;
import com.daelim.swserver.security.SHA256;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    SHA256 sha256 = new SHA256();

    @PostMapping("signin")
    @ResponseBody
    public String signin(@RequestBody UserDTO userdto) throws NoSuchAlgorithmException {
        JsonObject response = new JsonObject();
        Optional<User> tempUser = userRepository.findById(userdto.getUserId());
        userdto.setUserPassword(sha256.encrypt(userdto.getUserPassword()));

        log.info("Request : " + userdto.toString());

        // Check Duplicate User
        if(tempUser.isPresent()) {
            response.addProperty("success", "failed");
            response.addProperty("message", "Duplicate user");
            return response.toString();
        }

        User user = userdto.toEntity();
        try {
            User saveUser = userRepository.save(user);
            response.addProperty("success", "true");
        } catch (Exception e) {
            response.addProperty("success", "failed");
            response.addProperty("message", e.getMessage());
        }

        return response.toString();
    }

    @GetMapping("signup")
    public String signup(@RequestParam(value = "id") String userId,
                         @RequestParam(value = "password") String password) throws NoSuchAlgorithmException{
        JsonObject response = new JsonObject();
        String crypto = sha256.encrypt(password);

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            if(crypto.equals(user.get().getUserPassword())) {
                response.addProperty("success", "true");
            } else {
                response.addProperty("success", "failed");
                response.addProperty("message", "Auth");
            }
        } else {
            response.addProperty("success", "failed");
            response.addProperty("message", "NoAccount");
        }

        return response.toString();
    }

    @DeleteMapping("delete")
    public String delete(@RequestParam(value = "id") String userId) {
        JsonObject response = new JsonObject();
        try {
            userRepository.deleteById(userId);
            response.addProperty("success", "true");
        } catch(Exception e) {
            response.addProperty("success", "failed");
            response.addProperty("message", e.getMessage());
        }

        return response.toString();
    }


}
