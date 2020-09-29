package net.seensin.springdockerswarmmanagementapi.common.security.model.service.impl;


import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.CantAssignTempRoleUserAlreadyHaveException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.RoleIdNotFoundException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.TempRoleIdNotFoundException;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.security.UserIdNotFoundException;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.TemporaryRole;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.User;
import net.seensin.springdockerswarmmanagementapi.common.security.model.repository.RoleRepository;
import net.seensin.springdockerswarmmanagementapi.common.security.model.repository.TemporaryRoleRepository;
import net.seensin.springdockerswarmmanagementapi.common.security.model.repository.UserRepository;
import net.seensin.springdockerswarmmanagementapi.common.security.model.service.TempRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TempRoleServiceImpl implements TempRoleService {

    @Autowired
    TemporaryRoleRepository temporaryRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<TemporaryRole> getAllRoles() throws Exception {
        return (List<TemporaryRole>) temporaryRoleRepository.findAll();
    }

    @Override
    @Transactional
    public User addTempRole(Long userId, Long roleId, Long duration) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException(userId));
        Long expireTimeStamp = new Date().getTime() + duration * 60 * 1000;
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleIdNotFoundException(roleId));
        if (role.getUserEntities().contains(user))
            throw new CantAssignTempRoleUserAlreadyHaveException(user.getUsername(), role.getRoleName());
        TemporaryRole temporaryRole = new TemporaryRole(role.getRoleName(), expireTimeStamp);
        user.getUserTempRolesSet().add(temporaryRole);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeTempRole(Long userId, Long roleId) throws Exception {

//        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException(userId));
//        TemporaryRole role = user.getUserTempRolesSet().stream().filter(r -> r.getId().equals(roleId)).findFirst().orElseThrow(() -> new TempRoleIdNotFoundException(roleId));
//        user.getUserRolesSet().remove(role);
        if (temporaryRoleRepository.existsById(roleId)) {
            temporaryRoleRepository.deleteById(roleId);
        } else {
            throw new TempRoleIdNotFoundException(roleId);
        }
    }

    // check spring security : how read user roles on UserPrincipal ?
    //todo scheduler fixedDelay ??
    @Scheduled(fixedDelay = 900000)
    @Transactional
    public void removeExpiredTempRoles() {
        Long currentTimeStamp = new Date().getTime();
        Iterator<TemporaryRole> iterator = temporaryRoleRepository.findAll().iterator();
        while (iterator.hasNext()) {
            TemporaryRole temporaryRole = iterator.next();

            System.out.println("current date : " + currentTimeStamp + " expiredate : " + temporaryRole.getExpireDate());
            if (temporaryRole.getExpireDate() <= currentTimeStamp) {
                System.out.println("deleted");

                temporaryRoleRepository.delete(temporaryRole);
            }
        }
    }
}
