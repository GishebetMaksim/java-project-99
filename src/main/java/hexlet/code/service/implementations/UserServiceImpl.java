package hexlet.code.service.implementations;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public UserDTO findById(Long id) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id = " + id + " not found."));
        return mapper.map(user);
    }

    public UserDTO create(UserCreateDTO data) {
        var user = mapper.map(data);
        repository.save(user);
        return mapper.map(user);
    }

    public UserDTO update(Long id, UserUpdateDTO data) {
        var user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id = " + id + " not found."));
        mapper.update(data, user);
        repository.save(user);
        return  mapper.map(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
