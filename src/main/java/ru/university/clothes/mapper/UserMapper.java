package ru.university.clothes.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import ru.university.clothes.dto.UserDto;
import ru.university.clothes.dto.UserLoggedDto;
import ru.university.clothes.model.Permission;
import ru.university.clothes.model.Role;
import ru.university.clothes.model.User;

public class UserMapper {
    public static UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                // Собираем все имена ролей (например, ["ROLE_USER", "ROLE_ADMIN"])
                getUserRoleNames(user),
                // Собираем все разрешения из всех ролей в один плоский Set
                getUserPermissions(user));
    }

    public static User userDtoToUser(UserDto dto) {
        User user = new User();
        user.setUserName(dto.username());
        // Если в будущем понадобится восстанавливать роли из DTO, логику нужно будет
        // добавить сюда
        return user;
    }

    public static UserLoggedDto userToUserLoggedDto(User user) {
        return new UserLoggedDto(
                user.getUserName(),
                getUserRoleNames(user),
                getUserPermissions(user));
    }

    // Вспомогательный метод для сборки имён всех ролей пользователя
    private static Set<String> getUserRoleNames(User user) {
        if (user.getRoles() == null)
            return Collections.emptySet();
        return user.getRoles().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());
    }

    // Вспомогательный метод для сборки всех разрешений со всех ролей (flatMapping)
    private static Set<String> getUserPermissions(User user) {
        if (user.getRoles() == null)
            return Collections.emptySet();
        return user.getRoles().stream()
                .map(Role::getPermissions)
                .filter(permissions -> permissions != null)
                .flatMap(Collection::stream)
                .map(Permission::getAuthority)
                .collect(Collectors.toSet());
    }
}