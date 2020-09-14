package br.com.forum.config.security;

import br.com.forum.modelo.Usuario;
import br.com.forum.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Usuario> usuario = repository.findByEmail((username));

        if(usuario.isPresent()){
            return usuario.get();
        }

        throw new UsernameNotFoundException("invalid data!");
    }
}
