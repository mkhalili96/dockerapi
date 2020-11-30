package net.seensin.springdockerswarmmanagementapi.modules.security.model.repository;


import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.TemporaryRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryRoleRepository extends PagingAndSortingRepository<TemporaryRole, Long> {
    public Optional<Role> findByRoleName(String role);
}
