package net.seensin.springdockerswarmmanagementapi.common.security.model.service;


import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.TemporaryRole;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.User;

import java.util.List;

public interface TempRoleService {

    public List<TemporaryRole> getAllRoles() throws Exception;

    public User addTempRole(Long userId, Long roleId, Long duration) throws Exception;

    public void removeTempRole(Long userId, Long roleId) throws Exception;
}
