package eva.pet.upc.evapet.serviceImplements;

import eva.pet.upc.evapet.models.User;
import eva.pet.upc.evapet.repositories.IUsersRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// Me hiciste perder 1 hora intentando ver donde estaba mi User malditoooo >:
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsersRepository repo;

    @Override
    public UserDetails loadUserByUsername(@NonNull String mail) throws UsernameNotFoundException {
        Optional<User> user = repo.findUserByMail(mail);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + mail);
        }

        User u = user.get();

        if (!u.isActive()) {
            throw new UsernameNotFoundException("Usuario inactivo: " + mail);
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        //Acá recordar que estamos usando un ENUM por lo tanto accedemos a el en getNameRol() para despues acceder a su estring en name
        roles.add(new SimpleGrantedAuthority(u.getRol().getNameRol().name()));

        return new org.springframework.security.core.userdetails.User(
                u.getMail(),
                u.getPassword(),
                u.isActive(),   // enabled
                true,           // accountNonExpired
                true,           // credentialsNonExpired
                true,           // accountNonLocked
                roles
        );
    }
}
