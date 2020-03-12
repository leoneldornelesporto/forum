package br.com.forum.alura.config.security;

import br.com.forum.alura.model.Usuario;
import br.com.forum.alura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(s);
        System.out.println(usuario.isPresent());
        if (usuario.isPresent()){
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados inválidos");
    }
}