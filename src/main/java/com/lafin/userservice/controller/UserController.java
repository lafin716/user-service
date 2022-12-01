package com.lafin.userservice.controller;

import com.lafin.userservice.dto.UserDto;
import com.lafin.userservice.repository.UserEntity;
import com.lafin.userservice.service.UserService;
import com.lafin.userservice.vo.Greeting;
import com.lafin.userservice.vo.RequestUser;
import com.lafin.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Greeting greeting;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in User Service on PORT: %s, SERVER PORT: %s, SECRET: %s, EXPIRE: %s ",
                env.getProperty("local.server.port"),
                env.getProperty("server.port"),
                env.getProperty("token.secret"),
                env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser user = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
