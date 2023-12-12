package Model.DAO.Bibliotecario;

import FileData.Entidade.Serializador;
import Model.Entidade.Bibliotecario;
import Model.Entidade.EmprestimoDevolucao;
import Model.Entidade.Livro;
import Model.Entidade.Usuario;

import java.io.IOException;
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

    /**
     * Responsável por adicionar um novo bibliotecário ao sistema.
     *
     * Gera um novo id para o bibliotecário, adiciona o bibliotecário à
     * lista de bibliotecários e salva a lista atualizada de usuários
     * no arquivo "bibliotecario.dat".
     *
     * @param bibliotecario     bibliotecário a ser adicionado
     * @return                  lista de bibliotecarios
     * */
    @Override
    public List<Bibliotecario> create(Bibliotecario bibliotecario) throws IOException {
        bibliotecario.setId(gerarIdBibliotecario());
        bibliotecarios.add(bibliotecario);
        Serializador.salvarDados("bibliotecario.dat", bibliotecarios);
        return bibliotecarios;
    }


    /**
     * Responsável por procurar um bibliotecário com um id específico
     * na lista de bibliotecários.
     *
     * Faz a leitura do arquivo "bibliotecario.dat", itera sobre a lista
     * e verifica se cada bibliotecário possui o id.
     *
     * @param id    id do bibliotecário a ser procurado
     * @return      bibliotecário que possui o id ou nulo se o bibliotecário não foi encontrado
     * */
     @Override
    public Bibliotecario read(String id) throws IOException, ClassNotFoundException {
        ArrayList<Bibliotecario> listaBibliotecario = new ArrayList<Bibliotecario>();
        listaBibliotecario = (ArrayList<Bibliotecario>) Serializador.leituraDados("bibliotecario.dat");

        for (Bibliotecario bibliotecario : listaBibliotecario) {
            if (bibliotecario.getId().equals(id)) {
                return bibliotecario;
            }
        }
        return null;
    }

    /**
     * Responsável por atualizar informações de um bibliotecario no sistema.
     *
     * Faz a leitura do arquivo para obter a lista atual de bibliotecarios,
     * remove o bibliotecário antigo, adiciona o novo bibliotecário à lista
     * e salva  no arquivo "bibliotecario.dat".
     *
     * @param id                    id do bibliotecário
     * @param novoBibliotecario     dados do bibliotecário a serem atualizadas
     * */
    @Override
    public void update(String id, Bibliotecario novoBibliotecario) throws IOException, ClassNotFoundException {
        ArrayList<Bibliotecario> listaBibliotecario = (ArrayList<Bibliotecario>) Serializador.leituraDados("bibliotecario.dat");

        for (int i = 0; i < listaBibliotecario.size(); i++) {
            if (listaBibliotecario.get(i).getId().equals(id)) {
                // Atualiza apenas os campos necessários
                listaBibliotecario.get(i).setNome(novoBibliotecario.getNome());
                listaBibliotecario.get(i).setCargo(novoBibliotecario.getCargo());
                listaBibliotecario.get(i).setSenhaAcesso(novoBibliotecario.getSenhaAcesso());

                Serializador.salvarDados("bibliotecario.dat", listaBibliotecario);
                break;
            }
        }
    }

    /**
     * Responsável por remover um bibliotecário do sistema.
     *
     * Verifica o bibliotecário a ser removido, remove o bibliotecário e salva a lista
     * modificada no arquivo "bibliotecario.dat".
     *
     * @param bibliotecario     bibliotecario a ser removido.
     * @return                  lista atualizada.
     * */
    @Override
    public List<Bibliotecario> delete(Bibliotecario bibliotecario) throws IOException, ClassNotFoundException {
        bibliotecarios.remove(bibliotecario);
        Serializador.salvarDados("bibliotecario.dat", bibliotecarios);
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
    public List<Livro> registrarLivros(Livro livro) throws IOException {
        livroDAO.create(livro);
        return livroDAO.getLivros();
    }

    /**
     * Permite que o bibliotecário tenha acesso aos empréstimos
     * @param livro      Livro a ser emprestado
     * @param usuario    Usuario que fará o empréstimo
     * */
    public EmprestimoDevolucao realizarEmprestimo(Livro livro, Usuario usuario) throws IOException, ClassNotFoundException {
        EmprestimoDevolucao emprestimo = usuarioDAO.realizarEmprestimo(livro, usuario);
        return emprestimo;
    }


    /**
     * Realiza a devolução do livro e marca o livro como disponível
     *
     * @param emprestimo           Empréstimo realizado pelo usuário
     * @param dataDevolucaoReal    Data de devolução do livro
     * */

    public void realizarDevolucao(EmprestimoDevolucao emprestimo, Date dataDevolucaoReal, Usuario usuario) throws IOException {
        usuarioDAO.realizarDevolucao(emprestimo, dataDevolucaoReal, usuario);
    }
}

