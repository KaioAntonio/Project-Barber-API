package com.projectbarber.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectbarber.domain.user.dto.CreateUserDTO;
import com.projectbarber.domain.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public UserDTO cadastrar(CreateUserDTO createUserDTO) {
        User user = objectMapper.convertValue(createUserDTO, User.class);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userRepository.save(user);
        UserDTO userDTO = objectMapper.convertValue(user, UserDTO.class);
        return userDTO;
    }
}
