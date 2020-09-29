package net.seensin.springdockerswarmmanagementapi.common.security.controller;

import net.seensin.springdockerswarmmanagementapi.common.security.controller.to.UserRoleTo;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.Role;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.User;
import net.seensin.springdockerswarmmanagementapi.common.security.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/sina/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping()
    public ResponseEntity<User> addRole(@RequestBody UserRoleTo userRoleTo) throws Exception {
        return ResponseEntity.ok(roleService.addRole(userRoleTo.getUserId(), userRoleTo.getRoleId()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<User> removeRole(@RequestBody UserRoleTo userRoleTo) throws Exception {
        return ResponseEntity.ok(roleService.removeRole(userRoleTo.getUserId(), userRoleTo.getRoleId()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles() throws Exception {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
