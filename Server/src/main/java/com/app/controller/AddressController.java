package com.app.controller;

import com.app.model.UserModel;
import com.app.model.AddressModel;
import com.app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Controlador responsável pela gestão das rotas relacionadas aos endereços.
 *
 * Este controlador manipula a adição e busca de endereços associados a um
 * usuário autenticado. As rotas incluem a adição de novos endereços e busca
 * da lista de endereços do usuário.
 * 
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-20
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    /**
     * Serviço de endereços utilizado para lógica de negócios relacionada a endereços.
     */
    @Autowired
    private AddressService addressService;

    /**
     * Adiciona um novo endereço para o usuário autenticado.
     *
     * Este método recebe um objeto AddressModel a ser adicionado ao usuário
     * autenticado. Se o usuário não estiver autenticado, retorna um erro 401.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão do usuário.
     * @param address O objeto AddressModel que representa o endereço a ser adicionado.
     * @return Um ResponseEntity com o endereço adicionado ou um erro 401 se o usuário não estiver autenticado.
     */
    @PostMapping
    public ResponseEntity<AddressModel> addAddress(HttpServletRequest request, @RequestBody AddressModel address) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");
        
        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build(); 
        }

        AddressModel newAddress = addressService.addAddressToUser(authenticatedUser.getId(), address);
        return ResponseEntity.ok(newAddress); 
    }

    /**
     * Busca todos os endereços do usuário autenticado.
     *
     * Este método retorna uma lista de endereços associados ao usuário autenticado.
     * Se o usuário não estiver autenticado, retorna um erro 401.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão do usuário.
     * @return Um ResponseEntity com a lista de endereços do usuário ou um erro 401 se o usuário não estiver autenticado.
     */
    @GetMapping
    public ResponseEntity<List<AddressModel>> getAddressesByUser(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build(); 
        }

        List<AddressModel> addresses = addressService.getAddressesByUserId(authenticatedUser.getId());
        return ResponseEntity.ok(addresses); 
    }
}