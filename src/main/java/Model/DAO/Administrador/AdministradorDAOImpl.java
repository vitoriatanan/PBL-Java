package Model.DAO.Administrador;

import Model.Entidade.Administrador;
import Model.Entidade.EmprestimoDevolucao;
import Model.Entidade.Livro;
import Model.DAO.Livro.LivroDAOImpl;
import Model.Entidade.Usuario;
import Model.DAO.Usuario.UsuarioDAOImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdministradorDAOImpl implements AdministradorDAO {
    
    private List<Administrador> adms;
    private LivroDAOImpl livroDAO;
    private UsuarioDAOImpl usuarioDAO;

    public AdministradorDAOImpl() {
        this.adms = new ArrayList<>();
        this.livroDAO = new LivroDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public List<Administrador> create(Administrador administrador) {
        adms.add(administrador);
        return adms;
    }

    @Override
    public Administrador read(Integer id) {
        for (Administrador administrador : adms) {
            if (administrador.getId().equals(id)) {
                return administrador;
            }
        }
        return null;
    }

    @Override
    public void update(Integer id, Administrador novoAdm){
        Administrador admAntigo = read(id);
        if (admAntigo != null) {
            adms.remove(admAntigo);
            adms.add(novoAdm);
        }
    }

    @Override
    public List<Administrador> delete(Administrador administrador) {
        this.adms.remove(administrador);
        return adms;
    }


    /**
     * Permite que o administrador realize pesquisa de livros
     *
     * @param tipo      Corresponde a categoria para pesquisa (autor, título, isbn ou categoria)
     * @param busca     Corresponde a busca desejada
     * */
    public boolean pesquisar(String tipo, String busca) {
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
     * Permite que o administrador tenha acesso aos empréstimos
     *
     * @param livro      Livro a ser emprestado
     * @param usuario    Usuario que fará o empréstimo
     * */
    public EmprestimoDevolucao emprestimos(Livro livro, Usuario usuario) {
        EmprestimoDevolucao emprestimo = usuarioDAO.realizarEmprestimo(livro, usuario);
        return emprestimo;
    }


    /**
     * Realiza a devolução do livro e marca o livro como disponível
     *
     * @param emprestimo           Empréstimo realizado pelo usuário
     * @param dataDevolucaoReal    Data de devolução do livro
     * */
    public void devolucao(EmprestimoDevolucao emprestimo, Date dataDevolucaoReal, Usuario usuario) {
        usuarioDAO.realizarDevolucao(emprestimo, dataDevolucaoReal, usuario);
    }


    /**
     * Permite ao administrador acessar as reservas dos livros
     *
     * @param livro       Livro a ser reservado
     * @param usuario     Usuário que está fazendo a reserva
     * @return            true se a reserva for bem-sucedida, false caso contrário
     * */
    public boolean reserva(Livro livro, Usuario usuario) {
        return usuarioDAO.realizarReserva(livro, usuario);
    }

    /**
     * Permite ao administrador acessar as renovações de empréstimos
     *
     * @param usuario         Usuário que deseja renovar o empréstimo
     * @param emprestimo      Empréstimo que deve ser renovado
     * @return                true se a renovação for bem-sucedida, false caso contrário
     * */
    public boolean renovacao(Usuario usuario, EmprestimoDevolucao emprestimo) {
        return usuarioDAO.renovarEmprestimo(usuario, emprestimo);
    }
}
