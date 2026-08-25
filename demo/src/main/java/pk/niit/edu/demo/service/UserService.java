package pk.niit.edu.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pk.niit.edu.demo.SecurityConfig.SecurityConfig;
import pk.niit.edu.demo.dto.UserRequestDTO;
import pk.niit.edu.demo.dto.UserResponseDTO;
import pk.niit.edu.demo.entity.UserEntity;
import pk.niit.edu.demo.exception.EmailAlreadyExistsException;
import pk.niit.edu.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // create user
    public UserResponseDTO createUser(UserRequestDTO request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists!");
        }
        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());

        user.setRole("USER");

        UserEntity savedUser = userRepository.save(user);

        return convertToResponseDTO(savedUser);

    }

    // Get all users
    public List<UserResponseDTO> getAllUser(){

        List<UserEntity> users = userRepository.findAll();
        List<UserResponseDTO> responseList = new ArrayList<>();

        for(UserEntity user: users){
            responseList.add(convertToResponseDTO(user));
        }
        return responseList;
    }

    // Get user by ID
    public Optional<UserResponseDTO> getUserByID(Integer id, String loggedInEmail){

        Optional<UserEntity> user = userRepository.findById(id);

        if(user.isEmpty()){
            return Optional.empty();
        }
        Optional<UserEntity> loggedInUser = userRepository.findByEmail(loggedInEmail);

        if(loggedInUser.isEmpty()){
            return Optional.empty();
        }
        UserEntity requested = user.get();
        UserEntity current = loggedInUser.get();

        if(current.getRole().equals("ADMIN")
            || requested.getEmail().equals(loggedInEmail)){
                return Optional.of(convertToResponseDTO(requested));
        }
        return Optional.empty();
    }

    // Delete user
    public boolean deleteUser(Integer id){
        if(!userRepository.existsById(id)){
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    // update a user
    public UserResponseDTO updateUser(Integer id, UserRequestDTO request){
        Optional<UserEntity> existing = userRepository.findById(id);

        if(existing.isPresent()){
            UserEntity user = existing.get();

            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setPhone(request.getPhone());

            UserEntity updatedUser = userRepository.save(user);

            return convertToResponseDTO(updatedUser);
        }
        return null;
    }

    // helper method

    private UserResponseDTO convertToResponseDTO(UserEntity user){
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        return response;
    }
}
