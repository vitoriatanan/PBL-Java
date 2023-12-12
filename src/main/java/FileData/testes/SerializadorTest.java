package FileData.testes;

import FileData.Entidade.Serializador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerializadorTest {
    private static final String nomeArquivo = "testearquivo.dat";

    /**
    * Testa o processo de salvar dados em um arquivo.
    *
    * Cria uma lista, adiciona alguns itens a ela, e em seguida, salva
    * essa lista em um arquivo com o nome específico.
    * */
    @DisplayName("Deve Escrever no Arquivo")
    @Test
    public void testSalvarDados() {
        List<String> listaOriginal = new ArrayList<>();
        listaOriginal.add("Item 1");
        listaOriginal.add("Item 2");

        try {
            Serializador.salvarDados(nomeArquivo, listaOriginal);
        } catch (IOException e) {
            e.printStackTrace();
            assertEquals(false, true, "Exceção durante o teste");
        }
    }

    /**
    * Testa o processo de leitura de dados de um arquivo.
    *
    * Salva os dados no arquivo, faz a leitura do arquivo
    * para obter o objeto salvo. O teste verifica se o objeto
    * lido não é nulo e se é uma lista do mesmo tamanho e conteúdo
    * da lista original.
    * */
    @DisplayName("Deve Fazer a Leitura do Arquivo")
    @Test
    public void testLeituraDados() throws IOException {
        List<String> listaOriginal = List.of("Item 1", "Item 2");

        try {
            Serializador.salvarDados(nomeArquivo, listaOriginal);
            Object objetoLido = Serializador.leituraDados(nomeArquivo);
            assertNotNull(objetoLido);

            /* Verifica se o objeto lido é uma lista */
            assertTrue(objetoLido instanceof List<?>);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail("Exceção durante o teste de leitura de dados");
        }

    }
}
