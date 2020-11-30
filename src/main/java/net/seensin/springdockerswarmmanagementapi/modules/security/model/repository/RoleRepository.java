package net.seensin.springdockerswarmmanagementapi.modules.security.model.repository;

import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    public Boolean existsByRoleName(String role);
    public Role findByRoleName(String role);
}
