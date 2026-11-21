package ru.university.clothes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Permission;
import ru.university.clothes.model.Role;
import ru.university.clothes.model.User;
import ru.university.clothes.repository.PermissionRepository;
import ru.university.clothes.repository.RoleRepository;
import ru.university.clothes.repository.UserRepository;

@EnableCaching
@EnableAsync
@SpringBootApplication
@RequiredArgsConstructor
public class ClothesApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ClothesApplication.class, args);
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createPermissionsIfNotExists();
        createRolesIfNotExists();
        createUsersIfNotExists();
    }

    public void createUsersIfNotExists() {
        if (!userRepository.findAll().isEmpty())
            return;

        Role roleAdmin = roleRepository.findByTitle("ADMIN").orElseThrow();
        Role roleUser = roleRepository.findByTitle("USER").orElseThrow();
        Role roleManager = roleRepository.findByTitle("MANAGER").orElseThrow();

        // Исправлено: используем метод билдера .roles() и передаем Set.of(...)
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(Set.of(roleAdmin))
                .build();
        userRepository.save(admin);

        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles(Set.of(roleUser))
                .build();
        userRepository.save(user);

        User manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("manager"))
                .roles(Set.of(roleManager))
                .build();
        userRepository.save(manager);
    }

    private void createRolesIfNotExists() {
        if (roleRepository.count() == 0) {
            Role userRole = Role.builder().title("USER").build();
            Role moderatorRole = Role.builder().title("MODERATOR").build();
            Role managerRole = Role.builder().title("MANAGER").build();
            Role adminRole = Role.builder().title("ADMIN").build();

            // Сохраняем роли
            roleRepository.save(userRole);
            roleRepository.save(managerRole);
            roleRepository.save(moderatorRole);
            roleRepository.save(adminRole);

            // Получаем разрешения из БД
            Permission productRead = permissionRepository.findByPermissionAndOperation("clothes", "read").orElseThrow();
            Permission productWrite = permissionRepository.findByPermissionAndOperation("clothes", "write").orElseThrow();
            Permission productDelete = permissionRepository.findByPermissionAndOperation("clothes", "delete").orElseThrow();

            // Назначаем разрешения ролям
            userRole.setPermissions(new HashSet<>(Set.of(productRead)));
            managerRole.setPermissions(new HashSet<>(Set.of(productRead, productWrite)));
            adminRole.setPermissions(new HashSet<>(Set.of(productRead, productWrite, productDelete)));

            // Сохраняем обновлённые роли с разрешениями
            roleRepository.save(userRole);
            roleRepository.save(managerRole);
            roleRepository.save(adminRole);
        }
    }

    private void createPermissionsIfNotExists() {
        if (permissionRepository.count() == 0) {
            permissionRepository.saveAll(List.of(
                    Permission.builder().permission("clothes").operation("read").build(),
                    Permission.builder().permission("clothes").operation("write").build(),
                    Permission.builder().permission("clothes").operation("delete").build()
            ));
        }
    }

}
