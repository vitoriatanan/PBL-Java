package Model.DAO.Bibliotecario;

import Model.Entidade.Bibliotecario;

import java.io.IOException;
import java.util.List;

public interface BibliotecarioDAO {
    List<Bibliotecario> create(Bibliotecario bibliotecario) throws IOException;
    Bibliotecario read(String id) throws IOException, ClassNotFoundException;
    void update(String id, Bibliotecario novoBibliotecario) throws IOException, ClassNotFoundException;
    List<Bibliotecario> delete(Bibliotecario bibliotecario) throws IOException, ClassNotFoundException;
}
