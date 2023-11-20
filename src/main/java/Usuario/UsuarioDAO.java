package Usuario;

import java.util.List;
//
public interface UsuarioDAO {
    List<Usuario> create(Usuario usuario);
    Usuario read(Integer id);
    void update(Integer id, Usuario novoUsuario);
    List<Usuario> delete(Usuario usuario);
}

