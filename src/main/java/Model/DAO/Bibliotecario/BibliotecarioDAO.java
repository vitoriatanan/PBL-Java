package Model.DAO.Bibliotecario;

import Model.Entidade.Bibliotecario;

import java.util.List;

public interface BibliotecarioDAO {
    List<Bibliotecario> create(Bibliotecario bibliotecario);
    Bibliotecario read(Integer id);
    void update(Integer id, Bibliotecario novoBibliotecario);
    List<Bibliotecario> delete(Bibliotecario bibliotecario);
}
