package com.cd.evaluation.users.controller.user;

import com.cd.evaluation.users.exception.InternalException;
import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.service.user.UserService;
import com.cd.evaluation.users.view.users.UserDTO;
import com.cd.evaluation.users.view.users.search.UserSearchDTO;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint to retrieve a user by id
     * @param userId users ID
     */
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId) throws InternalException {
        log.info("/api/v1/users/{userId} - endpoint accessed");
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    /**
     * Endpoint to retrieve all the users
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserModel>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(name = "field", defaultValue = "") String field,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestBody @Nullable UserSearchDTO userSearchDTO
            ) throws InternalException {
        log.info("/api/v1/users/ - getAllUsers - endpoint accessed");
        return ResponseEntity.ok().body(userService.getAllUsers(page, pageSize, field, sortDirection, userSearchDTO));
    }

    /**
     * Endpoint to save the user in the database
     * @param userDTO user payload to persist
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) throws InternalException {
        log.info("/api/v1/users/ - saveUser - endpoint accessed");
        return ResponseEntity.ok().body(userService.saveUser(userDTO));
    }

    /**
     * Endpoint to update an existing user in the database
     * @param userDTO user payload to update
     */
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
