package net.seensin.springdockerswarmmanagementapi.modules.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import net.seensin.springdockerswarmmanagementapi.common.exception.validator.password.PasswordConstraintValidator;
import net.seensin.springdockerswarmmanagementapi.modules.security.controller.to.UserRoleTo;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.User;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.entity.common.view.View;
import net.seensin.springdockerswarmmanagementapi.modules.security.model.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController()
@RequestMapping("/sina/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    PasswordConstraintValidator passwordConstraintValidator;


    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity save(@Valid @RequestBody User user) throws Exception {
        if (passwordConstraintValidator.isValid(user.getPassword())) {
            return ResponseEntity.ok(userService.save(user));
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping()
    public ResponseEntity<User> update(@Valid @RequestBody User user) throws Exception {
        return ResponseEntity.ok(userService.update(user));
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(value = "password")
    public ResponseEntity<User> changePassword(@Valid @RequestBody User user) throws Exception {
        if (passwordConstraintValidator.isValid(user.getPassword())) {
            return ResponseEntity.ok(userService.update(user));
        } else {
            return ResponseEntity.ok(userService.changePassword(user));
        }
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map> delete(@PathVariable Long id) throws Exception {
        userService.delete(id);
        Map map = new HashMap();
        map.put("message", "account deleted successfully");
        map.put("status", 200);
        return ResponseEntity.ok(map);
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity find(@RequestParam(required = false) String username, @RequestParam(required = false) Long id) throws Exception {

        logger.info("Looking to load Users ...");

        if (id != null) {
            return ResponseEntity.ok(userService.getUser(id));
        } else if (username != null) {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } else {
            return ResponseEntity.ok(userService.getAllUsers());
        }
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(value = "/active")
    public ResponseEntity<Map> activeAction(@RequestBody UserRoleTo to) throws Exception {
        userService.active(to.getUserId(), to.getActive());
        Map map = new HashMap();
        String message = to.getActive() ? "account activated successfully .." : "account deactivated successfully ..";
        map.put("message", message);
        map.put("status", 200);
        return ResponseEntity.ok(map);
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/active")
    public ResponseEntity<List<User>> getAllActiveUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllActiveUsers());
    }

    @JsonView(View.UserView.externalView.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/deactive")
    public ResponseEntity<List<User>> getAllDeActiveUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllDeActiveUsers());
    }


}
