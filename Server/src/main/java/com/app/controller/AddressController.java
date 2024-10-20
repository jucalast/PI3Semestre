package com.app.controller;

import com.app.model.UserModel;
import com.app.model.AddressModel;
import com.app.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * Adiciona um novo endereço para o usuário autenticado.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão.
     * @param address O endereço a ser adicionado.
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
     * Recupera todos os endereços do usuário autenticado.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão.
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