package net.seensin.springdockerswarmmanagementapi.common.security.model.service;

import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.User;

import java.util.List;

public interface RoleService {

    public List<Role> getAllRoles() throws Exception;

    public User addRole(Long userId, Long roleId) throws Exception;

    public User removeRole(Long userId, Long roleId) throws Exception;
}
