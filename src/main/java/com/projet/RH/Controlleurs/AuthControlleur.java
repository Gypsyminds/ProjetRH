package com.projet.RH.Controlleurs;

import com.projet.RH.Entities.ERole;
import com.projet.RH.Entities.Role;
import com.projet.RH.Entities.User;
import com.projet.RH.Repository.RoleRepository;
import com.projet.RH.Repository.UserRepository;
import com.projet.RH.Service.UserDetailsImpl;
import com.projet.RH.payload.request.LoginRequest;
import com.projet.RH.payload.request.SignupRequest;
import com.projet.RH.payload.response.JwtResponse;
import com.projet.RH.payload.response.MessageResponse;
import com.projet.RH.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.projet.RH.Entities.ERole.Role_Administrateur;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthControlleur {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));



    }


    @PostMapping(value="/signup" , consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerUsers(@Valid @PathParam("username") String username,
                                           @PathParam("email") String email,
                                           @PathParam("password") String password,
                                           @PathParam("role")  String role ,
                                           @PathParam("tel1")  String tel1 ,
                                           @PathParam("tel2")  String tel2 ,
                                           @PathParam("ville")  String ville ,
                                           @PathParam("code_postal")  String code_postal ,
                                           @PathParam("adresse")  String adresse ,
                                           @PathParam("nom")  String nom ,
                                           @PathParam("prenom")  String prenom ,
                                           @PathParam("etat")  String etat,
                                           @PathParam("birth_date") Date birth_date
            , @RequestParam("image") MultipartFile image) throws IOException {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(username,
               email,
                encoder.encode(password),tel1 ,tel2 ,ville ,code_postal , adresse,etat , nom ,prenom ,birth_date);
        Set<String> strRoles = Collections.singleton(role);
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.Role_DRH)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(rolee -> {
                switch (rolee) {

                    case "agent":
                        Role modRole = roleRepository.findByName(ERole.Role_agent)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    case "admin":
                        Role EntRole = roleRepository.findByName(Role_Administrateur)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(EntRole);


                        break;
                    case "RH":
                        Role RHRole = roleRepository.findByName(ERole.Role_DRH)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(RHRole);


                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.Role_DRH)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }



        user.setRoles(roles);
        user.setPhoto_profil(image.getBytes());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
