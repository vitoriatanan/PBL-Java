package Model.DAO.Bibliotecario;

import Model.Entidade.Bibliotecario;

import java.util.List;

public interface BibliotecarioDAO {
    List<Bibliotecario> create(Bibliotecario bibliotecario);
    Bibliotecario read(String id);
    void update(String id, Bibliotecario novoBibliotecario);
    List<Bibliotecario> delete(Bibliotecario bibliotecario);
}
