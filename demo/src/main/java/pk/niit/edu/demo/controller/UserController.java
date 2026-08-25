package pk.niit.edu.demo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.niit.edu.demo.dto.UserRequestDTO;
import pk.niit.edu.demo.dto.UserResponseDTO;
import pk.niit.edu.demo.service.UserService;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create user
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request){
        UserResponseDTO response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // get all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = userService.getAllUser();

        return ResponseEntity.ok(users);
    }

    // get one user
    @GetMapping("id/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id, Authentication authentication){

        String loggedInEmail = authentication.getName();
        Optional<UserResponseDTO> user = userService.getUserByID(id,loggedInEmail);

        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("id/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer id, @RequestBody UserRequestDTO request){
        UserResponseDTO updatedUser = userService.updateUser(id, request);

        if(updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id){
        boolean deletedUser = userService.deleteUser(id);

        if(deletedUser){
            return ResponseEntity.ok("User Deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<String> haseebUser(@PathVariable Integer id){
        boolean deletedUser = userService.deleteUser(id);

        if(deletedUser){
            return ResponseEntity.ok("User Deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");

    }



}
