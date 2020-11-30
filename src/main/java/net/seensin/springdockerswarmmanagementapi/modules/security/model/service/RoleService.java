package net.seensin.springdockerswarmmanagementapi.modules.security.model.service;

import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.User;

import java.util.List;

public interface RoleService {

    public List<Role> getAllRoles() throws Exception;

    public User addRole(Long userId, Long roleId) throws Exception;

    public User removeRole(Long userId, Long roleId) throws Exception;
}
