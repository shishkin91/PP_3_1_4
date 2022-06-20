package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;


    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> allRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(Long roleId) throws NoSuchElementException {
        Optional<Role> roleFromDb = roleRepository.findById(roleId);
        return roleFromDb.orElse(new Role());
    }


}
