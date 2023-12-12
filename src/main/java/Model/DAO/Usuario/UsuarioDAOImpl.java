package Model.DAO.Usuario;

import FileData.Entidade.Serializador;
import Model.DAO.Livro.LivroDAOImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

import Model.Entidade.Livro;
import Model.Entidade.EmprestimoDevolucao;
import Model.Entidade.Usuario;


public class UsuarioDAOImpl implements UsuarioDAO {
    private List<Usuario> usuarios;
    private LivroDAOImpl livroDAO;

    public UsuarioDAOImpl() {
        this.usuarios = new ArrayList<>();
        this.livroDAO = new LivroDAOImpl();
    }

    /**
     * Responsável por adicionar um novo usuário ao sistema.
     * <p>
     * Gera um novo id para o usuário, adiciona o usuário à
     * lista de usuarios e salva a lista atualizada de usuários
     * no arquivo "usuario.dat".
     *
     * @param usuario usuario a ser adicionado
     * @return lista de usuarios
     */
    @Override
    public List<Usuario> create(Usuario usuario) throws IOException {
        usuario.setId(gerarIdUsuario());
        usuarios.add(usuario);
        Serializador.salvarDados("usuario.dat", usuarios);
        return usuarios;
    }

    /**
     * Responsável por procurar um usuário com um id específico
     * na lista de usuários.
     *
     * Faz a leitura do arquivo "usuario.dat", itera sobre a lista
     * e verifica se cada usuário possui o id.
     *
     * @param id id do usuario a ser procurado
     * @return usuário que possui o id ou nulo se o usuário não foi encontrado
     */
    @Override
    public Usuario read(String id) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
        listaUsuario = (ArrayList<Usuario>) Serializador.leituraDados("usuario.dat");

        for (Usuario usuario : listaUsuario) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Responsável por atualizar informações de um usuário no sistema.
     * <p>
     * Faz a leitura do arquivo para obter a lista atual de usuários,
     * remove o usuário antigo, adiciona o novo usuário à lista e salva
     * no arquivo "usuario.dat".
     *
     * @param id            id do usuário
     * @param novoUsuario   dados do usuário a serem atualizadas
     */
    @Override
    public void update(String id, Usuario novoUsuario) throws IOException, ClassNotFoundException {
        Usuario usuarioAntigo = read(id);
        if (usuarioAntigo != null) {
            usuarios.remove(usuarioAntigo);
            usuarios.add(novoUsuario);
            Serializador.salvarDados("usuario.dat", usuarios);
        }
    }


    /**
     * Responsável por remover um usuário do sistema.
     *
     * Faz a leitura do arquivo para obter a lista atual de usuários,
     * cria uma cópia dessa lista para iterar sobre a copia, verifica
     * o usuario a ser removido, remove o usuário e salva a lista
     * modificada no arquivo "usuario.dat".
     *
     * @param usuario usuário a ser removido.
     * @return lista atualizada.
     */
    @Override
    public List<Usuario> delete(Usuario usuario) throws IOException, ClassNotFoundException {
        usuarios.remove(usuario);
        Serializador.salvarDados("usuario.dat", usuarios);
        return usuarios;
    }


    /**
     * Método que cria id aleatórios para o usuário
     *
     * @return id do usuário
     */
    public String gerarIdUsuario() {
        Random rand = new Random();
        String novoID;

        // Loop para gerar um novo ID e verificar se já existe na lista de usuários
        do {
            int numeroAleatorio = rand.nextInt(1000);
            novoID = "u" + numeroAleatorio;
        } while (existeId(novoID));

        return novoID;
    }

    /**
     * Método auxiliar para verificar a existência de um ID na lista de usuários
     *
     * @param id id a ser verificado
     * @return true se já existir um id e false caso contrário
     */
    private boolean existeId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return true; // ID encontrado, já existe
            }
        }
        return false; // ID não encontrado, é único
    }

    /**
     * Permite que o usuário realize pesquisa de livros
     *
     * @param tipo  Corresponde a categoria para pesquisa (autor, título, isbn ou categoria)
     * @param busca Corresponde a busca desejada
     * @return Resultado da pesquisa
     */
    public boolean pesquisarLivros(String tipo, String busca) {
        return livroDAO.pesquisarLivro(tipo, busca);
    }

    /**
     * Realiza o Empréstimo de livros por 14 dias se a conta
     * do usuário não estiver bloqueada, adiciona os empréstimos
     * na lista de empréstimos do usuário e salva o empréstimo
     * no arquivo "emprestimoDevolucao.dat".
     *
     * @param livro   Livro a ser emprestado
     * @param usuario Usuário a fazer empréstimo
     * @return Registro de empréstimo se for bem-sucedido, ou nulo
     * se a conta estiver bloqueada ou livro não disponível
     */
    public EmprestimoDevolucao realizarEmprestimo(Livro livro, Usuario usuario) throws IOException {
        if (!usuario.isContaBloqueada() && livro.isLivroDisponivel()) {
            LocalDate dataEmprestimo = LocalDate.now();
            LocalDate dataDevolucaoEsperada = dataEmprestimo.plus(14, ChronoUnit.DAYS);

            EmprestimoDevolucao emprestimo = new EmprestimoDevolucao(usuario, livro, dataEmprestimo, dataDevolucaoEsperada);

            /* Adiciona os empréstimos na lista de empréstimos do usuário */
            usuario.getEmprestimos().add(emprestimo);

            /* Salva os empréstimos no arquivo de empréstimos */
            Serializador.salvarDados("emprestimoDevolucao.dat", usuario.getEmprestimos());

            /* Marca o livro como emprestado */
            livro.marcarComoLivroEmprestado();

            return emprestimo;
        } else {
            /* A conta está bloqueada ou o livro não está disponível para empréstimo */
            return null;
        }
    }


    /**
    * Responsável por fazer a devolução de um empréstimo.
    *
    * Verifica se o empréstimo está na lista de empréstimo do usuário, adiciona
    * o empréstimo à lista de devoluções, salva a lista de devoluções no arquivo
    * "emprestimoDevolucao.dat", verifica as datas e calcula a multa caso haja atraso
    *
    * @param emprestimo            Empréstimo do livro
    * @param dataDevolucaoReal     Data atual de devolução do livro
    * @param usuario               Usuário a fazer a devolução
    * */
    public void realizarDevolucao(EmprestimoDevolucao emprestimo, Date dataDevolucaoReal, Usuario usuario) throws IOException {
        emprestimo.setDataDevolucaoReal(dataDevolucaoReal);
        emprestimo.getLivro().marcarComoLivroDevolvido();

        if (usuario.getEmprestimos().contains(emprestimo)) {
            emprestimo.getListaDevolucoes().add(emprestimo);
            Serializador.salvarDados("emprestimoDevolucao.dat", emprestimo.getListaDevolucoes());

            LocalDate dataDevolucaoEsperada = emprestimo.getDataDevolucaoEsperada();
            if (dataDevolucaoReal.after(Date.from(dataDevolucaoEsperada.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                /* Calcula a multa */
                long diasAtraso = ChronoUnit.DAYS.between((Temporal) dataDevolucaoEsperada, (Temporal) dataDevolucaoReal);
                double multa = diasAtraso * 2;
                /* Atualiza o estado da conta do usuário com a multa */
                usuario.setMulta(usuario.getMulta() + multa);
            }
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
    public boolean realizarReserva(Livro livro, Usuario usuario) throws IOException {
        if (livro.isLivroDisponivel() && !livro.getUsuariosReservados().contains(usuario)) {
            livro.adicionarReserva(usuario);

            /* Salva os usuários reservados no arquivo livro.dat */
            Serializador.salvarDados("livro.dat", livro.getUsuariosReservados());
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Renova o empréstimo de livros para mais 14 dias se a conta do usuário
     * não estiver bloqueada.
     *
     * Lê a lista de empréstimos do arquivo "emprestimoDevolucao.dat", atualiza
     * o empréstimo desejado e, em seguida, salvando a lista atualizada de volta
     * no arquivo.
     *
     * @param usuario         Usuário que deseja renovar o empréstimo
     * @param emprestimo      Empréstimo a ser renovado
     * @return                true se a renovação for bem-sucedida, false caso contrário, ou
     *                        conta bloqueada
     * */
    public boolean renovarEmprestimo(Usuario usuario, EmprestimoDevolucao emprestimo) throws IOException, ClassNotFoundException {

        /* Faz a leitura do arquivo "emprestimoDevolucao.dat" */
        ArrayList<EmprestimoDevolucao> listaEmprestimo = new ArrayList<EmprestimoDevolucao>();
        listaEmprestimo = (ArrayList<EmprestimoDevolucao>) Serializador.leituraDados("emprestimoDevolucao.dat");

        for (EmprestimoDevolucao emp : listaEmprestimo) {
            if (!usuario.isContaBloqueada() && usuario.getEmprestimos().contains(emprestimo) && emprestimo.getRenovacoes() < 1) {
                Livro livro = emprestimo.getLivro();
                if (emp.getLivro().getTitulo().equals(emprestimo.getLivro().getTitulo())) {
                    if (livro.isLivroDisponivel() || livro.getUsuariosReservados().isEmpty()) {
                        LocalDate dataAtual = LocalDate.now();
                        LocalDate novaDataRenovacaoEsperada = dataAtual.plus(14, ChronoUnit.DAYS);

                        /* Atualiza o emprestismo com a nova data de devolução */
                        emp.setDataDevolucaoEsperada(novaDataRenovacaoEsperada);
                        emp.contagemRenovacoes();

                        Serializador.salvarDados("emprestimoDevolucao.dat", listaEmprestimo);
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
