package Model.Testes;

import Model.DAO.Livro.LivroDAOImpl;
import Model.Entidade.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LivroDAOImplTest {
    private LivroDAOImpl livroDAO;
    private Livro livroTeste, livroTeste2;


    @BeforeEach
    public void setUp() {
        livroDAO = new LivroDAOImpl();
        livroTeste = new Livro("Livro 1", "Autor 1", "Editora 1","Isbn 1", 2023, "Categoria 1");
        livroTeste2 = new Livro("Livro 2", "Autor 2", "Editora 2","Isbn 2", 2023, "Categoria 2");
    }

    /**
     * Testa o método create da classe LivroDAOImpl.
     *
     * Adiciona corretamente um livro à lista e verifica
     * se o livro está presente na lista.
     *
     * @throws IOException    se ocorrer um erro de E/S durante o teste.
     */
    @DisplayName("Deve Armazenar Um Livro à Lista")
    @Test
    public void testCreate() throws IOException {
        List<Livro> resultado = livroDAO.create(livroTeste);
        assertTrue(resultado.contains(livroTeste));
    }

    /**
     * Testa o método read da classe LivroDAOImpl.
     *
     * Cria um livro, adiciona à lista, e verifica se as informações
     * do livro recuperado correspondem às informações originais.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Ler Um Livro Pelo Título")
    @Test
    public void testRead() throws IOException, ClassNotFoundException {

        livroDAO.create(livroTeste);

        Livro resultado = livroDAO.read(livroTeste.getTitulo());

        assertNotNull(resultado);
        assertEquals(livroTeste.getTitulo(), resultado.getTitulo());
    }

    /**
     * Testa o método update da classe LivroDAOImpl.
     *
     * Atualiza as informações do Livro.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Atualizar Informações do Livro")
    @Test
    public void testUpdate() throws IOException, ClassNotFoundException {
        livroDAO.create(livroTeste);

        livroDAO.update(livroTeste.getTitulo(), livroTeste2);

        Livro resultado = livroDAO.read(livroTeste.getTitulo());

        assertNotNull(resultado);
        assertEquals(livroTeste2.getAutor(), resultado.getAutor());
        assertEquals(livroTeste2.getEditora(), resultado.getEditora());
        assertEquals(livroTeste2.getIsbn(), resultado.getIsbn());
        assertEquals(livroTeste2.getAnoPublicacao(), resultado.getAnoPublicacao());
        assertEquals(livroTeste2.getCategoria(), resultado.getCategoria());
    }


    /**
     * Testa o método delete da classe LivroDAOImpl.
     *
     * Remove um livro da lista e verifica se o livro
     * não está presente na lista.
     *
     * @throws IOException              se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException   se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Remover Um Livro Da Lista")
    @Test
    public void testDelete() throws IOException, ClassNotFoundException {
        livroDAO.create(livroTeste);

        List<Livro> resultado = livroDAO.delete(livroTeste);

        assertFalse(resultado.contains(livroTeste));
    }
}
