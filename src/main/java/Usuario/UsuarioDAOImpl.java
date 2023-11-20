package Usuario;

import Livro.LivroDAOImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import Livro.Livro;
import EmprestimoDevolucao.EmprestimoDevolucao;


public class UsuarioDAOImpl implements UsuarioDAO {
    private List<Usuario> usuarios;
    private LivroDAOImpl livroDAO;

    public UsuarioDAOImpl() {
        this.usuarios = new ArrayList<>();
        this.livroDAO = new LivroDAOImpl();
    }

    @Override
    public List<Usuario> create(Usuario usuario) {
        usuarios.add(usuario);
        return usuarios;
    }

    @Override
    public Usuario read(Integer id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void update(Integer id, Usuario novoUsuario) {
        Usuario usuarioAntigo = read(id);
        if (usuarioAntigo != null) {
            usuarios.remove(usuarioAntigo);
            usuarios.add(novoUsuario);
        }
    }

    @Override
    public List<Usuario> delete(Usuario usuario) {
        this.usuarios.remove(usuario);
        return usuarios;
    }


    /**
     * Permite que o usuário realize pesquisa de livros
     *
     * @param tipo      Corresponde a categoria para pesquisa (autor, título, isbn ou categoria)
     * @param busca     Corresponde a busca desejada
     * @return          Resultado da pesquisa
     * */
    public boolean pesquisarLivros(String tipo, String busca) {
        return livroDAO.pesquisarLivro(tipo, busca);
    }

    /**
     * Realiza o Empréstimo de livros por 14 dias se a conta
     * do usuário não estiver bloqueada e adiciona os empréstimos
     * na lista de empréstimos do usuário.
     *
     * @param livro      Livro a ser emprestado
     * @param usuario    Usuário a fazer empréstimo
     * @return           Registro de empréstimo se for bem-sucedido, ou nulo
     *                   se a conta estiver bloqueada ou livro não disponível
     * */
    public EmprestimoDevolucao realizarEmprestimo(Livro livro, Usuario usuario) {
        if (!usuario.isContaBloqueada() && livro.isLivroDisponivel()) {
            Date dataEmprestimo = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataEmprestimo);
            calendar.add(Calendar.DATE, 14);
            Date dataDevolucaoEsperada = calendar.getTime();

            EmprestimoDevolucao emprestimo = new EmprestimoDevolucao(usuario, livro, dataEmprestimo, dataDevolucaoEsperada);

            /* Adiciona os empréstimos na lista de empréstimos do usuário. */
            usuario.getEmprestimos().add(emprestimo);

            /* Marca como livro emprestado */
            livro.marcarComoLivroEmprestado();

            return emprestimo;
        }
        else {
            /* A conta está bloqueada ou o livro não está disponível para empréstimo */
            return null;
        }
    }

    /**
     * Realiza a devolução do livro e marca o livro como disponível
     * Calcula a multa por atraso, se houver, e atualiza estado da conta do usuário
     *
     * @param emprestimo            Empréstimo realizado pelo usuário
     * @param dataDevolucaoReal     Data de devolução do livro
     * @param usuario               Usuário a fazer devolução
     * */
    public void realizarDevolucao(EmprestimoDevolucao emprestimo, Date dataDevolucaoReal, Usuario usuario) {
        emprestimo.setDataDevolucaoReal(dataDevolucaoReal);
        emprestimo.getLivro().marcarComoLivroDevolvido();

        /* Verifica se a devolução está atrasada */
        if (dataDevolucaoReal.after(emprestimo.getDataDevolucaoEsperada())) {
            usuario.setContaBloqueada(true); // Bloqueia a conta

            /* Calcula a diferença de tempo em dias entre a data de devolução real e a data esperada */
            long diasAtraso = TimeUnit.DAYS.convert(dataDevolucaoReal.getTime() - emprestimo.getDataDevolucaoEsperada().getTime(), TimeUnit.MILLISECONDS);

            /* Calcula a multa com o dobro de dias */
            double multa = diasAtraso * 2;

            /* Atualiza o estado da conta do usuário com a multa */
            usuario.setMulta(usuario.getMulta() + multa);
        }

        /* Atualiza conta do usuário para bloqueada, caso haja multa */
        if (usuario.getMulta() > 0) {
            usuario.setContaBloqueada(true);
        }
    }


    /**
     * Realiza a reserva de um livro
     *
     * @param livro       Livro a ser reservado
     * @param usuario     Usuário que está fazendo a reserva
     * @return            true se a reserva for bem-sucedida, false caso contrário
     * */
    public boolean realizarReserva(Livro livro, Usuario usuario) {
        if (livro.isLivroDisponivel() && !livro.getUsuariosReservados().contains(usuario)) {
            livro.adicionarReserva(usuario);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Renova o empréstimo de livros para mais 14 dias se a conta do usuário
     * não estiver bloqueada
     *
     * @param usuario         Usuário que deseja renovar o empréstimo
     * @param emprestimo      Empréstimo que deve ser renovado
     * @return                true se a renovação for bem-sucedida, false caso contrário, ou
     *                        conta bloqueada
     * */
    public boolean renovarEmprestimo(Usuario usuario, EmprestimoDevolucao emprestimo) {
        if (!usuario.isContaBloqueada() && usuario.getEmprestimos().contains(emprestimo) && emprestimo.getRenovacoes() < 1) {
            Livro livro = emprestimo.getLivro();

            if (livro.isLivroDisponivel() && livro.getUsuariosReservados().isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, 14); /* Adiciona mais 14 dias para a renovação */
                Date novaDataRenovacaoEsperada = calendar.getTime();

                /* Atualiza o emprestismo com a nova data de devolução */
                emprestimo.setDataDevolucaoEsperada(novaDataRenovacaoEsperada);

                emprestimo.contagemRenovacoes();
                return true;
            }
        }
        /* Conta bloqueada ou não foi possível fazer a renovação */
        return false;
    }
}
