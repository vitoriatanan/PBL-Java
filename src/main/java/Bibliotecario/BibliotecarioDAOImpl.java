package Bibliotecario;

import EmprestimoDevolucao.EmprestimoDevolucao;
import Livro.Livro;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Livro.LivroDAOImpl;
import Usuario.UsuarioDAOImpl;

public class BibliotecarioDAOImpl implements BibliotecarioDAO {

    private List<Bibliotecario> bibliotecarios;
    private LivroDAOImpl livroDAO;
    private UsuarioDAOImpl usuarioDAO;

    public BibliotecarioDAOImpl() {
        this.bibliotecarios = new ArrayList<>();
        this.livroDAO = new LivroDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public List<Bibliotecario> create(Bibliotecario bibliotecario) {
        bibliotecarios.add(bibliotecario);
        return bibliotecarios;
    }

    @Override
    public Bibliotecario read(Integer id) {
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getId().equals(id)) {
                return bibliotecario;
            }
        }
        return null;
    }

    @Override
    public void update(Integer id, Bibliotecario novoBibliotecario) {
        Bibliotecario bibliotecarioAntigo = read(id);
        if (bibliotecarioAntigo != null) {
            bibliotecarios.remove(bibliotecarioAntigo);
            bibliotecarios.add(novoBibliotecario);
        }
    }

    @Override
    public List<Bibliotecario> delete(Bibliotecario bibliotecario) {
        this.bibliotecarios.remove(bibliotecario);
        return bibliotecarios;
    }

    /**
     * Permite que o biliotecário realize pesquisa de livros
     *
     * @param tipo      Corresponde a categoria para pesquisa (autor, título, isbn ou categoria)
     * @param busca     Corresponde a busca desejada
     * @return          Resultado da pesquisa
     * */
    public boolean pesquisarLivros(String tipo, String busca) {
        return livroDAO.pesquisarLivro(tipo, busca);
    }


    /**
     * Registra novo livro na biblioteca
     *
     * @param livro    Livro a ser registrado
     * @return         Lista de livros após o registro
     * */
    public List<Livro> registrarLivros(Livro livro) {
        livroDAO.create(livro);
        return livroDAO.getLivros();
    }

    /**
     * Permite que o bibliotecário tenha acesso aos empréstimos
     * @param livro      Livro a ser emprestado
     * @param usuario    Usuario que fará o empréstimo
     * */
    public EmprestimoDevolucao realizarEmprestimo(Livro livro, Usuario usuario) {
        EmprestimoDevolucao emprestimo = usuarioDAO.realizarEmprestimo(livro, usuario);
        return emprestimo;
    }


    /**
     * Realiza a devolução do livro e marca o livro como disponível
     *
     * @param emprestimo           Empréstimo realizado pelo usuário
     * @param dataDevolucaoReal    Data de devolução do livro
     * */
    public void realizarDevolucao(EmprestimoDevolucao emprestimo, Date dataDevolucaoReal, Usuario usuario) {
        usuarioDAO.realizarDevolucao(emprestimo, dataDevolucaoReal, usuario);
    }
}

