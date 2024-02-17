package springboot.poscarrent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springboot.poscarrent.model.User;
import springboot.poscarrent.repository.UserRepository;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin

public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        ResponseEntity responseEntity = null;
        try {
            String hasPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hasPassword);
            user.setRole("ROLE_"+user.getRole());

            User saveUser = userRepository.save(user);
            if(saveUser.getId()>0){

                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body("use saved");
            }

        }catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("use Not saved"+e.getMessage());
        }
        return responseEntity;
    }
}
