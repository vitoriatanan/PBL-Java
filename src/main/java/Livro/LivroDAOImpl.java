package Livro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//
public class LivroDAOImpl implements LivroDAO {
    private List<Livro> livros;

    public LivroDAOImpl() {
        this.livros = new ArrayList<>();
    }

    @Override
    public List<Livro> create(Livro livro) {
        livros.add(livro);
        return livros;
    }

    @Override
    public Livro read(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                return livro;
            }
        }
        return null;
    }

    @Override
    public void update(String titulo, Livro novoLivro) {
        Livro livroAntigo = read(titulo);
        if (livroAntigo != null) {
            livros.remove(livroAntigo);
            livros.add(novoLivro);
        }
    }

    @Override
    public List<Livro> delete(Livro livro) {
        this.livros.remove(livro);
        return livros;
    }

    public boolean pesquisarLivro(String tipo, String busca) {
        for (Livro livro : livros) {
            if (Objects.equals(tipo, "autor") && livro.getAutor().contains(busca)) {
                return true;
            } else if (Objects.equals(tipo, "titulo") && livro.getTitulo().contains(busca)) {
                return true;
            } else if (Objects.equals(tipo, "categoria") && livro.getCategoria().contains(busca)) {
                return true;
            } else if (Objects.equals(tipo, "isbn") && livro.getIsbn().contains(busca)) {
                return true;
            } else if (Objects.equals(tipo, "editora") && livro.getEditora().contains(busca)) {
                return true;
            }
        }

        return false;
    }

    public List<Livro> getLivros() {
        return livros;
    }
}

