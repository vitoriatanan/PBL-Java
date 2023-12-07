package Model.DAO.Bibliotecario;

import Model.Entidade.Bibliotecario;
import Model.Entidade.EmprestimoDevolucao;
import Model.Entidade.Livro;
import Model.Entidade.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Model.DAO.Livro.LivroDAOImpl;
import Model.DAO.Usuario.UsuarioDAOImpl;

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
        bibliotecario.setId(gerarIdBibliotecario());
        bibliotecarios.add(bibliotecario);
        return bibliotecarios;
    }

    @Override
    public Bibliotecario read(String id) {
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getId().equals(id)) {
                return bibliotecario;
            }
        }
        return null;
    }

    @Override
    public void update(String id, Bibliotecario novoBibliotecario) {
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
     * Método que cria id aleatórios para o bibliotecário
     *
     * @return   id do bibliotecário
     * */
    public String gerarIdBibliotecario() {
        Random rand = new Random();
        String novoID;

        // Loop para gerar um novo ID e verificar se já existe na lista de bibliotecários
        do {
            int numeroAleatorio = rand.nextInt(1000);
            novoID = "b" + numeroAleatorio;
        } while (existeId(novoID));

        return novoID;
    }

    /**
     * Método auxiliar para verificar a existência de um ID na lista de bibliotecários
     *
     * @param id     id a ser verificado
     * @return       true se já existir um id e false caso contrário
     * */
    private boolean existeId(String id) {
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getId().equals(id)) {
                return true; // ID encontrado, já existe
            }
        }
        return false; // ID não encontrado, é único
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

