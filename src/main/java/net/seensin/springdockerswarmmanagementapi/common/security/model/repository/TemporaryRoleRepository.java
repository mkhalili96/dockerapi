package net.seensin.springdockerswarmmanagementapi.common.security.model.repository;


import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.TemporaryRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryRoleRepository extends PagingAndSortingRepository<TemporaryRole, Long> {
    public Optional<Role> findByRoleName(String role);
}
