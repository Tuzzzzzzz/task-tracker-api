package by.evgen.task_traker_api.service;

import by.evgen.task_traker_api.database.entity.User;
import by.evgen.task_traker_api.database.repository.UserRepo;
import by.evgen.task_traker_api.dto.security.UserResponse;
import by.evgen.task_traker_api.exception.UserNotFoundException;
import by.evgen.task_traker_api.exception.UsernameAlreadyExistsException;
import by.evgen.task_traker_api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse create(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        return userMapper.toResponse(userRepo.save(user));
    }

    @Transactional
    public boolean delete(Long id){
        return userRepo.findById(id)
                .map(user -> {
                    userRepo.delete(user);
                    userRepo.flush();
                    return true;
                })
                .orElse(false);
    }

    public UserResponse findByUsername(String username) {
        return userRepo.findByUsername(username).map(userMapper::toResponse)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s not found".formatted(username)));
    }

    public UserResponse findById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
