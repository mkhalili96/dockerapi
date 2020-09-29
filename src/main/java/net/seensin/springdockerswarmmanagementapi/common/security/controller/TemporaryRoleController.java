package net.seensin.springdockerswarmmanagementapi.common.security.controller;

import net.seensin.springdockerswarmmanagementapi.common.security.controller.to.UserRoleTo;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.TemporaryRole;
import net.seensin.springdockerswarmmanagementapi.common.security.model.entity.User;
import net.seensin.springdockerswarmmanagementapi.common.security.model.service.TempRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sina/roles/temp")
public class TemporaryRoleController {

    @Autowired
    TempRoleService tempRoleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping()
    public ResponseEntity<User> addRole(@RequestBody UserRoleTo userRoleTo) throws Exception {
        return ResponseEntity.ok(tempRoleService.addTempRole(userRoleTo.getUserId(), userRoleTo.getRoleId(), userRoleTo.getDuration()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping()
    public ResponseEntity removeRole(@RequestBody UserRoleTo userRoleTo) throws Exception {
        tempRoleService.removeTempRole(userRoleTo.getUserId(), userRoleTo.getRoleId());
        Map map = new HashMap();
        map.put("message", "temp role deleted successfully");
        map.put("status", 200);
        return ResponseEntity.ok(map);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<TemporaryRole>> getAllRoles() throws Exception {
        return ResponseEntity.ok(tempRoleService.getAllRoles());
    }
}
