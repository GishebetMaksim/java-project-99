package hexlet.code.service.interfaces;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO findById(Long id);
    UserDTO create(UserCreateDTO data);
    UserDTO update(Long id, UserUpdateDTO data);
    void delete(Long id);
}
