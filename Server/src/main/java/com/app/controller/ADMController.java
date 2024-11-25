package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.model.UserModel;
import com.app.service.UserService;

import lombok.RequiredArgsConstructor;

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
    // @PutMapping("/user/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<String> updateUser(@PathVariable("id") Long userId, @RequestBody UserModel updatedUser) {
    //     try {
    //         userService.updateUser(userId, updatedUser);
    //         return ResponseEntity.ok("Usuário atualizado com sucesso.");
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body("Erro ao atualizar o usuário: " + e.getMessage());
    //     }
    // }
}