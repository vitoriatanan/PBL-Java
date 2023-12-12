package Model.DAO.Livro;

import FileData.Entidade.Serializador;
import Model.Entidade.Bibliotecario;
import Model.Entidade.Livro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LivroDAOImpl implements LivroDAO {
    private List<Livro> livros;

    public LivroDAOImpl() {
        this.livros = new ArrayList<>();
    }

    /**
     * Responsável por adicionar um novo livro ao sistema.
     *
     * Adiciona o livro à lista de livross e salva a lista atualizada
     * de livros no arquivo "livro.dat".
     *
     * @param livro     livro a ser adicionado
     * @return          lista de livros
     * */
    @Override
    public List<Livro> create(Livro livro) throws IOException {
        livros.add(livro);
        Serializador.salvarDados("livro.dat", livros);
        return livros;
    }

    /**
     * Responsável por procurar um livro com um id específico
     * na lista de livros.
     *
     * Faz a leitura do arquivo "livro.dat", itera sobre a lista
     * e verifica se cada livro possui o titulo.
     *
     * @param titulo    titulo do usuario a ser procurado
     * @return          livro que possui o titulo ou nulo se o livro não foi encontrado
     * */
    @Override
    public Livro read(String titulo) throws IOException, ClassNotFoundException {

        /* Faz a leitura do arquivo "livro.dat" */
        ArrayList<Livro> listaLivro = new ArrayList<Livro>();
        listaLivro = (ArrayList<Livro>) Serializador.leituraDados("livro.dat");

        for (Livro livro : listaLivro) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                return livro;
            }
        }
        return null;
    }


    /**
     * Responsável por atualizar informações de um livro no sistema.
     *
     * Faz a leitura do arquivo para obter a lista atual de livros,
     * remove o livro antigo, adiciona o novo livro à lista e salva
     * no arquivo "livro.dat".
     *
     * @param titulo          título do livro
     * @param novoLivro       dados do livro a serem atualizados
     * */
    @Override
    public void update(String titulo, Livro novoLivro) throws IOException, ClassNotFoundException {
        ArrayList<Livro> listaLivro = (ArrayList<Livro>) Serializador.leituraDados("livro.dat");

        for (int i = 0; i < listaLivro.size(); i++) {
            if (listaLivro.get(i).getTitulo().equals(titulo)) {
                // Atualiza apenas os campos necessários
                listaLivro.get(i).setAutor(novoLivro.getAutor());
                listaLivro.get(i).setEditora(novoLivro.getEditora());
                listaLivro.get(i).setIsbn(novoLivro.getIsbn());
                listaLivro.get(i).setAnoPublicacao(novoLivro.getAnoPublicacao());
                listaLivro.get(i).setCategoria(novoLivro.getCategoria());

                Serializador.salvarDados("livro.dat", listaLivro);
                break;
            }
        }
    }


    /**
     * Responsável por remover um livro do sistema.
     *
     * Faz a leitura do arquivo para obter a lista atual de livros,
     * cria uma cópia dessa lista para iterar sobre a copia, verifica
     * o livro a ser removido, remove o livro e salva a lista
     * modificada no arquivo "livro.dat".
     *
     * @param livro     livro a ser removido.
     * @return          lista atualizada.
     * */
    @Override
    public List<Livro> delete(Livro livro) throws IOException, ClassNotFoundException {

        /* Faz a leitura do arquivo "livro.dat" */
        ArrayList<Livro> listaLivro = new ArrayList<Livro>();
        listaLivro = (ArrayList<Livro>) Serializador.leituraDados("livro.dat");

        /* Cria uma cópia da lista de listaLivros */
        List<Livro> copiaLista = new ArrayList<>(listaLivro);

        for (Livro liv : copiaLista) {
            if (liv.equals(livro)) {
                listaLivro.remove(livro);
            }
        }
        Serializador.salvarDados("livro.dat", listaLivro);
        return listaLivro;
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

