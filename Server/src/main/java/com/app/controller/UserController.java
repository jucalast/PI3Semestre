package com.app.controller;

import com.app.model.Usuario;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador API REST para gerenciar operações relacionadas aos usuários.
 *
 * <p>Esta classe fornece endpoints para realizar operações CRUD (Create, Read, Update, Delete)
 * sobre os usuários do sistema de e-commerce.</p>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Construtor para injeção de dependência do serviço de usuários.
     *
     * @param userService O serviço de usuários a ser injetado.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtém todos os usuários.
     *
     * @return Uma lista contendo todos os usuários.
     */
    @GetMapping
    public List<Usuario> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Obtém um usuário específico pelo seu identificador.
     *
     * @param id O identificador do usuário.
     * @return Um {@link ResponseEntity} contendo o usuário se encontrado, ou um status 404 Not Found se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Optional<Usuario> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo usuário.
     *
     * @param usuario O usuário a ser criado.
     * @return Um {@link ResponseEntity} contendo o usuário criado e um status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
        Usuario savedUsuario = userService.createUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id O identificador do usuário a ser atualizado.
     * @param usuario O usuário com as novas informações.
     * @return Um {@link ResponseEntity} contendo o usuário atualizado se o identificador existir, ou um status 404 Not Found se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updatedUser = userService.updateUser(id, usuario);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Exclui um usuário pelo seu identificador.
     *
     * @param id O identificador do usuário a ser excluído.
     * @return Um {@link ResponseEntity} com status 204 No Content se a exclusão for bem-sucedida, ou um status 404 Not Found se o usuário não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
