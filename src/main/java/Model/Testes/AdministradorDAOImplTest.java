package Model.Testes;

import Model.DAO.Administrador.AdministradorDAOImpl;
import Model.Entidade.Administrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdministradorDAOImplTest {
    private AdministradorDAOImpl administradorDAO;
    private Administrador admTeste, admTeste2;
    @BeforeEach
    public void setUp() {
        administradorDAO = new AdministradorDAOImpl();
        admTeste = new Administrador("Ursula Carroll", "Administrador", "senha123");
        admTeste2 = new Administrador("Hope Jimenez", "Administrador", "novasenha");
    }

    /**
     * Testa o método create da classe AdministradorDAOImpl.
     *
     * Adiciona corretamente um administrador à lista e verifica
     * se o administrador está presente na lista.
     *
     * @throws IOException    se ocorrer um erro de E/S durante o teste.
     */
    @DisplayName("Deve Armazenar Um Administrador à Lista")
    @Test
    public void testCreate() throws IOException {
        List<Administrador> resultado = administradorDAO.create(admTeste);
        assertTrue(resultado.contains(admTeste));
    }

    /**
     * Testa o método read da classe AdministradorDAOImpl.
     *
     * Cria um administradoro, adiciona à lista, e verifica se as informações
     * do administrador recuperado correspondem às informações originais.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Ler Um Administrador Pelo ID")
    @Test
    public void testRead() throws IOException, ClassNotFoundException {

        administradorDAO.create(admTeste);

        Administrador resultado = administradorDAO.read(admTeste.getId());

        assertNotNull(resultado);
        assertEquals(admTeste.getId(), resultado.getId());
    }

    /**
     * Testa o método update da classe AdministradorDAOImpl.
     *
     * Atualiza as informações do Administrador.
     *
     * @throws IOException            se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Atualizar Informações do Admministrador")
    @Test
    public void testUpdate() throws IOException, ClassNotFoundException {
        administradorDAO.create(admTeste);

        administradorDAO.update(admTeste.getId(), admTeste2);

        Administrador resultado = administradorDAO.read(admTeste.getId());

        assertNotNull(resultado);
        assertEquals(admTeste2.getNome(), resultado.getNome());
        assertEquals(admTeste2.getCargo(), resultado.getCargo());
        assertEquals(admTeste2.getSenhaAcesso(), resultado.getSenhaAcesso());
    }


    /**
     * Testa o método delete da classe AdministradorDAOImpl.
     *
     * Remove um Administrador da lista e verifica se o Administrador
     * não está presente na lista.
     *
     * @throws IOException              se ocorrer um erro de E/S durante o teste.
     * @throws ClassNotFoundException   se ocorrer um erro de classe não encontrada durante o teste.
     */
    @DisplayName("Deve Remover Um Administrador Da Lista")
    @Test
    public void testDelete() throws IOException, ClassNotFoundException {
        administradorDAO.create(admTeste);

        List<Administrador> resultado = administradorDAO.delete(admTeste);

        assertFalse(resultado.contains(admTeste));
    }
}
