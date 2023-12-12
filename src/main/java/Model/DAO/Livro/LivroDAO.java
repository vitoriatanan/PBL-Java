package Model.DAO.Livro;

import Model.Entidade.Livro;

import java.io.IOException;
import java.util.List;
public interface LivroDAO {
    List<Livro> create(Livro livro) throws IOException;
    Livro read(String titulo) throws IOException, ClassNotFoundException;
    void update(String titulo, Livro novoLivro) throws IOException, ClassNotFoundException;
    List<Livro> delete(Livro livro) throws IOException, ClassNotFoundException;
}
