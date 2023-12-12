package Model.Testes;

import FileData.Entidade.Serializador;
import Model.DAO.EmprestimoDevolucao.EmprestimoDevolucaoDAOImpl;
import Model.Entidade.EmprestimoDevolucao;
import Model.Entidade.Livro;
import Model.Entidade.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoDevolucaoDAOImplTest {

    private Usuario usuarioTeste, usuarioTeste2;
    private Livro livroTeste, livroTeste2;
    private EmprestimoDevolucaoDAOImpl emprestimoDevolucaoDAO;
    private EmprestimoDevolucao emprestimoDevolucaoTeste, emprestimoDevolucaoTeste2;
    @BeforeEach
    public void setUp() {
        usuarioTeste = new Usuario("Lara Levy", "Rua A", "87877");
        usuarioTeste2 = new Usuario("Edward House", "Rua B", "87569");

        livroTeste = new Livro("Livro 1", "Autor 1", "Editora 1", "Isbn 1", 2023, "Categoria 1");
        livroTeste2 = new Livro("Livro 2", "Autor 2", "Editora 2", "Isbn 2", 2023, "Categoria 2");


        emprestimoDevolucaoDAO = new EmprestimoDevolucaoDAOImpl();
        emprestimoDevolucaoTeste = new EmprestimoDevolucao(usuarioTeste, livroTeste, LocalDate.now(), LocalDate.now().plusDays(14));
        emprestimoDevolucaoTeste2 = new EmprestimoDevolucao(usuarioTeste2, livroTeste2, LocalDate.now(), LocalDate.now().plusDays(14));
    }

    /**
     * Testa o método create da classe LivroDAOImpl.
     *
     * Adiciona corretamente um livro à lista e verifica
     * se o livro está presente na lista.
     *
     * @throws IOException se ocorrer um erro de E/S durante o teste.
     */
    @DisplayName("Deve Adicionar Um Empréstimo à Lista")
    @Test
    public void testCreate() throws IOException {
        List<EmprestimoDevolucao> resultado = emprestimoDevolucaoDAO.create(emprestimoDevolucaoTeste);
        assertTrue(resultado.contains(emprestimoDevolucaoTeste));
    }

    /**
     * Testa o método read da classe EmprestimoDevolucaoDAOImpl.
     * <p>
     * Cria um empréstimo, adiciona à lista, e verifica se as informações
     * do empréstimo recuperado correspondem às informações originais.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Ler Um Empréstimo Pelo Título do Livro")
    @Test
    public void testRead() throws IOException, ClassNotFoundException {
        Serializador.salvarDados("emprestimoDevolucao.dat", new ArrayList<>());

        EmprestimoDevolucao resultado = emprestimoDevolucaoDAO.read(livroTeste.getTitulo());

        // Verifica se o resultado é nulo, pois não deve haver empréstimo no arquivo vazio
        assertNull(resultado);

        // Adiciona um empréstimo ao arquivo
        emprestimoDevolucaoDAO.create(emprestimoDevolucaoTeste);

        // Executa novamente o método read
        resultado = emprestimoDevolucaoDAO.read(livroTeste.getTitulo());

        // Verifica se o resultado não é nulo e se as informações correspondem
        assertNotNull(resultado);
        assertEquals(emprestimoDevolucaoTeste.getLivro().getTitulo(), resultado.getLivro().getTitulo());
        assertEquals(emprestimoDevolucaoTeste.getUsuario().getNome(), resultado.getUsuario().getNome());
    }

    /**
     * Testa o método update da classe EmprestimoDevolucaoDAOImpl.
     *
     * Atualiza as informações do empréstimo e verifica se as informações
     * foram alteradas corretamente.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Atualizar Informações do Empréstimo")
    @Test
    public void testUpdate() throws IOException, ClassNotFoundException {
        emprestimoDevolucaoDAO.create(emprestimoDevolucaoTeste);
        emprestimoDevolucaoDAO.update(emprestimoDevolucaoTeste, emprestimoDevolucaoTeste2);

        EmprestimoDevolucao resultado = emprestimoDevolucaoDAO.read(livroTeste.getTitulo());

        assertNotNull(resultado);
        assertEquals(emprestimoDevolucaoTeste2.getDataDevolucaoEsperada(), resultado.getDataDevolucaoEsperada());
    }



    /**
     * Testa o método delete da classe EmprestimoDevolucaoDAOImpl.
     *
     * Remove um empréstimo da lista e verifica se o empréstimo
     * não está presente na lista.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Remover Um Empréstimo Da Lista")
    @Test
    public void testDelete() throws IOException, ClassNotFoundException {
        emprestimoDevolucaoDAO.delete(emprestimoDevolucaoTeste);

        EmprestimoDevolucao resultado = emprestimoDevolucaoDAO.read(livroTeste.getTitulo());

        assertNull(resultado);
    }
}

