package Model.Testes;

import Model.DAO.Usuario.UsuarioDAOImpl;
import Model.Entidade.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOImplTest {

    private UsuarioDAOImpl usuarioDAO;
    private Usuario usuarioTeste, usuarioTeste2;

    @BeforeEach
    public void setUp() {
        usuarioDAO = new UsuarioDAOImpl();
        usuarioTeste = new Usuario("Lara Levy","Rua A", "87877");
        usuarioTeste2 = new Usuario("Edward House", "Rua B", "87569");

    }

    /**
     * Testa o método create da classe UsuarioDAOImpl.
     *
     * Adiciona um usuário de teste e verifica se o usuário está
     * presente na lista resultante.
     */
    @DisplayName("Deve Armazenar Um Usuário à Lista")
    @Test
    public void testCreate() throws IOException {
        List<Usuario> usuarios = usuarioDAO.create(usuarioTeste);

        assertTrue(usuarios.contains(usuarioTeste));
    }

    /**
    * Testa o método read da classe UsuarioDAOImpl.
    *
    * Adiciona um usuário de teste, lê o usuário pelo ID e verifica
    * se o usuário lido é não nulo e tem o ID esperado.
    * */
    @DisplayName("Deve Ler Um Usuário Pelo ID")
    @Test
    public void testRead() {
        try {
            usuarioDAO.create(usuarioTeste);

            Usuario usuarioLido = usuarioDAO.read(usuarioTeste.getId());
            Assertions.assertNotNull(usuarioLido);
            Assertions.assertEquals(usuarioTeste.getId(), usuarioLido.getId());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Assertions.fail("Exceção lançada durante o teste de ler");
        }
    }

    /**
     * Testa se o método update da classe UsuarioDAOImpl atualiza corretamente as informações de um usuário.
     *
     * Adiciona um usuário de teste, modifica os detalhes do usuário, chama o método update para atualizar
     * o usuário e verifica se as informações foram atualizadas corretamente.
     *
     * @throws IOException             caso ocorra um problema de E/S durante o teste.
     * @throws ClassNotFoundException  caso a classe não seja encontrada durante o teste.
     */
    @DisplayName("Deve Atualizar Informações do Usuário")
    @Test
    public void testUpdate() throws IOException, ClassNotFoundException {
        usuarioDAO.create(usuarioTeste2);

        // Modifica dados do usuário
        usuarioTeste2.setEndereco("Rua C");
        usuarioTeste2.setTelefone("14789");

        // Atualiza o usuário no sistema
        usuarioDAO.update(usuarioTeste2.getId(), usuarioTeste2);

        // Ler o usuário do sistema para verificar se foi atualizado
        Usuario updatedUsuario = usuarioDAO.read(usuarioTeste2.getId());

        // Verifica se o usuário foi atualizado com sucesso
        assertNotNull(updatedUsuario);
        assertEquals("Rua C", updatedUsuario.getEndereco());
        assertEquals("14789", updatedUsuario.getTelefone());
    }

    /**
     * Testa se o método delete da classe UsuarioDAOImpl remove corretamente um usuário da lista.
     * Adiciona um usuário de teste, chama o método delete para remover o usuário e verifica se o
     * usuário foi removido corretamente da lista.
     *
     * @throws IOException              caso ocorra um problema de E/S durante o teste.
     * @throws ClassNotFoundException   caso a classe não seja encontrada durante o teste.
     */
    @DisplayName("Deve Remover Um Usuário Da Lista")
    @Test
    public void testDelete() throws IOException, ClassNotFoundException {
        usuarioDAO.create(usuarioTeste);

        usuarioDAO.delete(usuarioTeste);

        // Tentar ler o usuário removido do sistema
        Usuario usuarioRemovido = usuarioDAO.read(usuarioTeste.getId());

        // Verifica se o usuário foi deletado
        assertNull(usuarioRemovido);
    }
}




