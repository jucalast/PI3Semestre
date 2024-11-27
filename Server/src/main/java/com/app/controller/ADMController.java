package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.model.UserModel;
import com.app.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/protected")
public class ADMController {

    private final UserService userService;

    /**
     * Rota protegida para atualizar as informações de um usuário.
     *
     * Apenas usuários com a role ADMIN podem acessar essa rota.
     *
     * @param userId O ID do usuário a ser atualizado.
     * @param updatedUser O objeto UserModel com os dados atualizados do
     * usuário.
     * @return Uma resposta indicando o sucesso da atualização.
     */
    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long userId, @RequestBody UserModel updatedUser) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("userName", updatedUser.getUserName());
        updates.put("emailId", updatedUser.getEmailId());
        updates.put("cpf", updatedUser.getCpf());
        updates.put("mobileNumber", updatedUser.getMobileNumber());
        updates.put("password", updatedUser.getPassword());
        updates.put("roles", updatedUser.getRoles());
        updates.put("profilePic", updatedUser.getProfilePic());

        try {
            userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar o usuário: " + e.getMessage());
        }
    }

}