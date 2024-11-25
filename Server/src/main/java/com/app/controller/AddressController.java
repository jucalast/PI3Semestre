package com.app.controller;

import com.app.model.AddressModel;
import com.app.model.UserModel;
import com.app.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador responsável pela gestão das rotas relacionadas aos endereços.
 *
 * Este controlador manipula a adição e busca de endereços associados a um
 * usuário autenticado. As rotas incluem a adição de novos endereços e busca da
 * lista de endereços do usuário.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-20
 */
@Slf4j
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    /**
     * Serviço de endereços utilizado para lógica de negócios relacionada a
     * endereços.
     */
    @Autowired
    private AddressService addressService;

    /**
     * Adiciona um novo endereço para o usuário autenticado.
     *
     * Este método recebe um objeto AddressModel a ser adicionado ao usuário
     * autenticado. Se o usuário não estiver autenticado, retorna um erro 401.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão do
     * usuário.
     * @param address O objeto AddressModel que representa o endereço a ser
     * adicionado.
     * @param addressType O tipo de endereço a ser adicionado.
     * @return Um ResponseEntity com o endereço adicionado ou um erro 401 se o
     * usuário não estiver autenticado.
     */
    @PostMapping
    public ResponseEntity<AddressModel> addAddress(HttpServletRequest request, @RequestBody AddressModel address, @RequestParam String addressType) {

        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        AddressModel newAddress = addressService.addAddressToUser(authenticatedUser.getId(), address, addressType);
        return ResponseEntity.ok(newAddress);
    }

    /**
     * Busca todos os endereços do usuário autenticado.
     *
     * Este método retorna uma lista de endereços associados ao usuário
     * autenticado. Se o usuário não estiver autenticado, retorna um erro 401.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão do
     * usuário.
     * @return Um ResponseEntity com a lista de endereços do usuário ou um erro
     * 401 se o usuário não estiver autenticado.
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

    /**
     * Busca todos os endereços do usuário autenticado, incluindo o tipo de endereço.
     *
     * @param request O objeto HttpServletRequest para acessar a sessão do usuário.
     * @return Um ResponseEntity com a lista de endereços do usuário ou um erro 401 se o usuário não estiver autenticado.
     */
    @GetMapping("/user")
    public ResponseEntity<List<Map<String, Object>>> getAddressesByAuthenticatedUser(HttpServletRequest request) {
        UserModel authenticatedUser = (UserModel) request.getSession().getAttribute("user");

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        List<Map<String, Object>> addresses = addressService.getAddressesByUserId(authenticatedUser.getId());
        return ResponseEntity.ok(addresses);
    }

    /**
     * Busca todos os endereços cadastrados.
     *
     * Este método retorna uma lista de todos os endereços presentes no banco de
     * dados.
     *
     * @return Um ResponseEntity com a lista de endereços.
     */
    @GetMapping("/all")
    public ResponseEntity<List<AddressModel>> getAllAddresses() {
        List<AddressModel> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    /**
     * Rota que cria um novo endereço e o associa ao usuário autenticado.
     *
     * @param addressData Os dados do endereço fornecidos pelo usuário.
     * @param request O objeto HttpServletRequest para acessar a sessão e obter o ID do usuário autenticado.
     * @return Uma resposta indicando o sucesso ou falha da operação.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@RequestBody Map<String, String> addressData, HttpServletRequest request) {
        log.info("Recebendo dados do endereço: {}", addressData);

        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        AddressModel address = new AddressModel();
        address.setStreet(addressData.get("street"));
        address.setNumber(addressData.get("number"));
        address.setNeighborhood(addressData.get("neighborhood"));
        address.setCity(addressData.get("city"));
        address.setState(addressData.get("state"));
        address.setZipCode(addressData.get("zipCode"));
        String addressType = addressData.get("addressType");

        addressService.addAddressToUser(userId, address, addressType);

        return ResponseEntity.ok("Endereço adicionado com sucesso");
    }

    // ...existing code...
}
