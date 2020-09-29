package net.seensin.springdockerswarmmanagementapi.common.security.model.repository;

import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findAllByIsEnabled(Boolean enable);
}
