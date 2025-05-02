package by.evgen.task_traker_api.mapper;

import by.evgen.task_traker_api.database.entity.Role;
import by.evgen.task_traker_api.database.entity.User;
import by.evgen.task_traker_api.dto.security.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "roles", expression = "java(mapRoleNames(user.getRoles()))")
    UserResponse toResponse(User user);

    default Set<String> mapRoleNames(Set<Role> roles) {
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles.stream()
                .map(r -> r.getRole().name()) // Предполагая, что у Role есть метод getName()
                .collect(Collectors.toSet());
    }
}
