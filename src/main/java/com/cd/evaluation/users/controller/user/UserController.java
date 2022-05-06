package com.cd.evaluation.users.controller.user;

import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.service.user.UserService;
import com.cd.evaluation.users.view.users.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint to retrieve the server status
     */
    @GetMapping("/status")
    public HttpStatus isServerUp() throws InternalException {
        log.info("/api/v1/users/status - endpoint accessed");
        return HttpStatus.OK;
    }

    //TODO remove this code
    @GetMapping("/throw-exception")
    public HttpStatus throwInternalException() throws InternalException {
        throw new InternalException(InternalException.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Endpoint to retrieve a user by id
     * @param userId users ID
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) throws InternalException {
        log.info("/api/v1/users/{userId} - endpoint accessed");
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    /**
     * Endpoint to retrieve all the users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws InternalException {
        log.info("/api/v1/users/ - getAllUsers - endpoint accessed");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    /**
     * Endpoint to save the user in the database
     * @param userDTO user payload to persist
     */
    @PostMapping()
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) throws InternalException {
        log.info("/api/v1/users/ - saveUser - endpoint accessed");
        return ResponseEntity.ok().body(userService.saveUser(userDTO));
    }

    /**
     * Endpoint to update an existing user in the database
     * @param userDTO user payload to update
     */
    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) throws InternalException {
        log.info("/api/v1/users/ - updateUser - endpoint accessed");
        return ResponseEntity.ok().body(userService.updateUser(userDTO));
    }

    /**
     * Endpoint to delete an existing user from the database
     * @param userId user IDs for deletion
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) throws InternalException {
        try {
            log.info("/api/v1/users/{userId} - deleteUser - endpoint accessed");
            userService.deleteUser(userId);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new InternalException(InternalException.DELETE_CONSTRAINT_EXCEPTION, HttpStatus.CONFLICT);
        }
    }
}
