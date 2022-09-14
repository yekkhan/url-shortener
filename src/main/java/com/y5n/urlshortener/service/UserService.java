package com.y5n.urlshortener.service;

import com.y5n.urlshortener.entity.Role;
import com.y5n.urlshortener.entity.User;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    User getUser(String userName);
}
