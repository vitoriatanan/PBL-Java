package Model.DAO.Livro;

import Model.Entidade.Livro;

import java.util.List;
public interface LivroDAO {
    List<Livro> create(Livro livro);
    Livro read(String titulo);
    void update(String titulo, Livro novoLivro);
    List<Livro> delete(Livro livro);
}
