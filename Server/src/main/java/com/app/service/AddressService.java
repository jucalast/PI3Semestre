package com.app.service;

import com.app.model.AddressModel;
import com.app.model.UserModel;
import com.app.repository.AddressRepository;
import com.app.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de negócios relacionada aos endereços.
 *
 * Este serviço fornece métodos para adicionar endereços a um usuário e buscar
 * os endereços associados a um usuário. Ele interage com os repositórios de
 * endereços e usuários para realizar operações de persistência.
 *
 * @author Giovanni
 * @version 1.0
 * @since 2024-10-20
 */
@Service
public class AddressService {

    /**
     * Repositório de endereços utilizado para realizar operações de
     * persistência dos endereços no banco de dados.
     */
    @Autowired
    private AddressRepository addressRepository;

    /**
     * Repositório de usuários utilizado para realizar operações de persistência
     * dos usuários no banco de dados.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Adiciona um novo endereço a um usuário existente.
     *
     * Este método associa um novo endereço a um usuário com base no seu ID. Se
     * o usuário não for encontrado, uma exceção é lançada.
     *
     * @param userId O ID do usuário ao qual o endereço será associado.
     * @param address O objeto AddressModel que representa o endereço a ser
     * adicionado.
     * @return O objeto AddressModel adicionado ao usuário.
     * @throws RuntimeException Se o usuário não for encontrado.
     */
    public AddressModel addAddressToUser(Long userId, AddressModel address) {
        userService.addAddressToUser(userId, address);
        return address;
    }

    /**
     * Busca todos os endereços de um usuário pelo seu ID.
     *
     * Este método retorna uma lista de endereços associados ao usuário. Se o
     * usuário não for encontrado, uma exceção é lançada.
     *
     * @param userId O ID do usuário cujos endereços serão recuperados.
     * @return Uma lista de objetos AddressModel associados ao usuário.
     * @throws RuntimeException Se o usuário não for encontrado.
     */
    public List<AddressModel> getAddressesByUserId(Long userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getAddresses();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    /**
     * Busca todos os endereços do usuário autenticado.
     *
     * @param userId O ID do usuário autenticado.
     * @return Uma lista de objetos AddressModel associados ao usuário.
     */
    public List<AddressModel> getAddressesByAuthenticatedUser(Long userId) {
        return getAddressesByUserId(userId);
    }

    /**
     * Recupera todos os endereços cadastrados no sistema.
     *
     * Este método retorna uma lista de todos os endereços presentes no banco de
     * dados.
     *
     * @return Uma lista de todos os objetos AddressModel cadastrados.
     */
    public List<AddressModel> getAllAddresses() {
        return addressRepository.findAll();
    }

    /**
     * Cria um novo endereço.
     *
     * Este método salva um novo endereço no banco de dados.
     *
     * @param address O objeto AddressModel que representa o endereço a ser
     * criado.
     * @return O objeto AddressModel criado.
     */
    public AddressModel createAddress(AddressModel address) {
        System.out.println("Creating address: " + address);
        AddressModel savedAddress = addressRepository.save(address);
        System.out.println("Saved address: " + savedAddress);
        return savedAddress;
    }
}
