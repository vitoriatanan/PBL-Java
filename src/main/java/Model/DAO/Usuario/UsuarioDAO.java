package Model.DAO.Usuario;

import Model.Entidade.Usuario;

import java.util.List;

public interface UsuarioDAO {
    List<Usuario> create(Usuario usuario);
    Usuario read(String id);
    void update(String id, Usuario novoUsuario);
    List<Usuario> delete(Usuario usuario);
}

