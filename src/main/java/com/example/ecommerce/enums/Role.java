package com.example.ecommerce.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.ADMIN_CREATE,
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_UPDATE,
            Permission.EMPLOYEE_DELETE,
            Permission.EMPLOYEE_CREATE
    )),
    EMPLOYEE(Set.of(
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_UPDATE,
            Permission.EMPLOYEE_DELETE,
            Permission.EMPLOYEE_CREATE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = permissions
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList();

        return List.copyOf(authorities);
    }
}
