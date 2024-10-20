package com.app.service;

import com.app.model.AddressModel;
import com.app.model.UserModel;
import com.app.repository.AddressRepository;
import com.app.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public AddressModel addAddressToUser(Long userId, AddressModel address) {
        Optional<UserModel> user = userRepository.findById(userId);
        if (user.isPresent()) {
            addressRepository.save(address);
            UserModel userModel = user.get();
            userModel.getAddresses().add(address);
            userRepository.save(userModel);  
            return address;
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public List<AddressModel> getAddressesByUserId(Long userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getAddresses();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public List<AddressModel> getAllAddresses() {
        return addressRepository.findAll();
    }
}
