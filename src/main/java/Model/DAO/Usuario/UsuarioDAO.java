package Model.DAO.Usuario;

import Model.Entidade.Usuario;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;

public interface UsuarioDAO {
    List<Usuario> create(Usuario usuario) throws IOException;
    Usuario read(String id) throws IOException, ClassNotFoundException;
    void update(String id, Usuario novoUsuario) throws IOException, ClassNotFoundException;
    List<Usuario> delete(Usuario usuario) throws IOException, ClassNotFoundException;
}

