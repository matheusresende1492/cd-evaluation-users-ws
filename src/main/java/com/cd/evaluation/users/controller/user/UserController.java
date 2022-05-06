package com.cd.evaluation.users.controller.user;

import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.service.user.UserService;
import com.cd.evaluation.users.view.users.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/status")
    public HttpStatus isServerUp() throws InternalException {
        return HttpStatus.OK;
    }

    @GetMapping("/throw-exception")
    public HttpStatus throwInternalException() throws InternalException {
        throw new InternalException(InternalException.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) throws InternalException {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws InternalException {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping()
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) throws InternalException {
        return ResponseEntity.ok().body(userService.saveUser(userDTO));
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) throws InternalException {
        return ResponseEntity.ok().body(userService.updateUser(userDTO));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) throws InternalException {
        try {
            userService.deleteUser(userId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new InternalException(InternalException.DELETE_CONSTRAINT_EXCEPTION, HttpStatus.CONFLICT);
        }
    }
}
