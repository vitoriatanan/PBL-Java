package Model.Testes;

import Model.DAO.Bibliotecario.BibliotecarioDAOImpl;
import Model.Entidade.Bibliotecario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BibliotecarioDAOImplTest {

    private BibliotecarioDAOImpl bibliotecarioDAO;
    private Bibliotecario bibliotecarioTeste, bibliotecarioTeste2;

    @BeforeEach
    public void setUp() {
        bibliotecarioDAO = new BibliotecarioDAOImpl();
        bibliotecarioTeste = new Bibliotecario("Ursula Carroll", "Bibliotecário", "senha123");
        bibliotecarioTeste2 = new Bibliotecario("Hope Jimenez", "Bibliotecário", "novasenha");

    }

    /**
     * Testa o método create da classe BibliotecarioDAOImpl.
     *
     * Aadiciona corretamente um bibliotecário à lista e verifica
     * se o bibliotecário está presente na lista.
     *
     * @throws IOException    se ocorrer um erro de E/S durante o teste.
     */
    @DisplayName("Deve Armazenar Um Bibliotecário à Lista")
    @Test
    public void testCreate() throws IOException {
        List<Bibliotecario> resultado = bibliotecarioDAO.create(bibliotecarioTeste);
        assertTrue(resultado.contains(bibliotecarioTeste));
    }

    /**
     * Testa o método read da classe BibliotecarioDAOImpl.
     *
     * Cria um bibliotecário, adiciona à lista, e verifica se as informações
     * do bibliotecário recuperado correspondem às informações originais.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Ler Um Bibliotecário Pelo ID")
    @Test
    public void testRead() throws IOException, ClassNotFoundException {

        bibliotecarioDAO.create(bibliotecarioTeste);

        Bibliotecario resultado = bibliotecarioDAO.read(bibliotecarioTeste.getId());

        assertNotNull(resultado);
        assertEquals(bibliotecarioTeste.getId(), resultado.getId());
    }

    /**
     * Testa o método update da classe BibliotecarioDAOImpl.
     *
     * Atualiza as informações do Bibliotecário.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Atualizar Informações do Bibliotecário")
    @Test
    public void testUpdate() throws IOException, ClassNotFoundException {
        bibliotecarioDAO.create(bibliotecarioTeste);

        bibliotecarioDAO.update(bibliotecarioTeste.getId(), bibliotecarioTeste2);

        Bibliotecario resultado = bibliotecarioDAO.read(bibliotecarioTeste.getId());

        assertNotNull(resultado);
        assertEquals(bibliotecarioTeste2.getNome(), resultado.getNome());
        assertEquals(bibliotecarioTeste2.getCargo(), resultado.getCargo());
        assertEquals(bibliotecarioTeste2.getSenhaAcesso(), resultado.getSenhaAcesso());
    }


    /**
     * Testa o método delete da classe BibliotecarioDAOImpl.
     *
     * Remove um bibliotecário da lista e verifica se o bibliotecário
     * não está presente na lista.
     *
     * @throws IOException              se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException   se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Remover Um Bibliotecário Da Lista")
    @Test
    public void testDelete() throws IOException, ClassNotFoundException {
        bibliotecarioDAO.create(bibliotecarioTeste);

        List<Bibliotecario> resultado = bibliotecarioDAO.delete(bibliotecarioTeste);

        assertFalse(resultado.contains(bibliotecarioTeste));
    }
}



