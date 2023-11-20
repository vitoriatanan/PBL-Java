package Livro;

import java.util.List;

public interface LivroDAO {
    List<Livro> create(Livro livro); //adiciona um Livro
    Livro read(String titulo);
    void update(String titulo, Livro novoLivro);
    List<Livro> delete(Livro livro);
}
