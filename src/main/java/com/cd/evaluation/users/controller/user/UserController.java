package com.cd.evaluation.users.controller.user;

import com.cd.evaluation.users.model.user.UserModel;
import com.cd.evaluation.users.service.user.UserService;
import lombok.RequiredArgsConstructor;
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
    public HttpStatus isServerUp(){
        return HttpStatus.OK;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping()
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserModel userModel) {
        UserModel savedUserModel = userService.saveUser(userModel);
        return ResponseEntity.ok().body(savedUserModel);
    }
}
