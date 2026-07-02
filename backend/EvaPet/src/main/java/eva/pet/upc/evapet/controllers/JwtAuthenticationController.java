package eva.pet.upc.evapet.controllers;

import eva.pet.upc.evapet.dtos.securities.JwtRequestDTO;
import eva.pet.upc.evapet.dtos.securities.JwtResponseDTO;
import eva.pet.upc.evapet.securities.JwtTokenUtil;
import eva.pet.upc.evapet.serviceImplements.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody JwtRequestDTO req) throws  Exception {
        authenticate(req.getMail(), req.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getMail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    private void authenticate(String mail, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(mail, password));
        } catch (DisabledException e) {
            throw new Exception("Usuario inactivo");
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales inválidas");
        }
    }
}
