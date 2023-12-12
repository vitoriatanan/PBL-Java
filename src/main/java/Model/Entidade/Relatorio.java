package Model.Entidade;

import FileData.Entidade.Serializador;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Relatorio implements Serializable {

    private static List<Integer> livrosEmprestados = new ArrayList<>();
    private static List<Integer> livrosAtrasados = new ArrayList<>();
    private static List<Integer> livrosReservados = new ArrayList<>();

    /**
     * Método que gera a quantidade de livros emprestados
     *
     * @param usuarios        Lista de usuários
     * @return                Total de livros emprestados
     * */
    public static int quantidadeLivrosEmprestados(List<Usuario> usuarios) throws IOException {
        int quantidade = 0;

        //ler o arquivo de emprestimos e contar a quantidade
        for (Usuario usuario : usuarios) {
            quantidade += usuario.getEmprestimos().size();
        }
        livrosEmprestados.add(quantidade);
        Serializador.salvarDados("relatorio.dat", livrosEmprestados);

        return quantidade;
    }

    /**
     *  Método que gera a quantidade de livros atrasados
     *
     * @param usuarios        Lista de usuários
     * @return                Total de livros atrasados
     * */

    public static int quantidadeLivrosAtrasados(List<Usuario> usuarios) throws IOException {
        int quantidade = 0;

        for (Usuario usuario : usuarios) {
            for (EmprestimoDevolucao emprestimo : usuario.getEmprestimos()) {
                if (emprestimo.getDataDevolucaoReal() == null && Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()).after(
                        Date.from(emprestimo.getDataDevolucaoEsperada().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                    quantidade++;
                }
            }
        }

        livrosAtrasados.add(quantidade);
        Serializador.salvarDados("relatorio.dat", livrosAtrasados);

        return quantidade;
    }

    /**
     * Método que gera a quantidade de livros reservados
     *
     * @param livros      Lista de livros
     * @return            Total de livros reservados
     * */
    public static int quantidadeLivrosReservados(List<Livro> livros) throws IOException {
        int quantidade = 0;

        for (Livro livro : livros) {
            if (!livro.getUsuariosReservados().isEmpty()) {
                quantidade++;
            }
        }

        livrosReservados.add(quantidade);
        Serializador.salvarDados("relatorio.dat", livrosReservados);

        return quantidade;
    }

    /**
     * Método que gera o histórico de empréstimos de um usuário específico
     *
     * @param usuario     Dados do usuário
     * @return            Empréstimos do usuário
     * */
    public static List<EmprestimoDevolucao> historicoEmprestimo(Usuario usuario) {
        return usuario.getEmprestimos();
    }

    /**
     * Método que gera os livros mais populares
     *
     * @param usuarios        Lista de usuários
     * @return                Lista de livros mais populares
     * */
    public static List<Livro> livrosMaisPopulares(List<Usuario> usuarios) throws IOException {
        Map<Livro, Integer> contagemLivros = new HashMap<>();

        /* Contagem de empréstimos para cada livro */
        for (Usuario usuario : usuarios) {
            for (EmprestimoDevolucao emprestimo : usuario.getEmprestimos()) {
                Livro livro = emprestimo.getLivro();
                contagemLivros.put(livro, contagemLivros.getOrDefault(livro, 0) + 1);
            }
        }

        /* Ordenação dos livros com base na quantidade de empréstimos */
        List<Map.Entry<Livro, Integer>> livrosOrdenados = new ArrayList<>(contagemLivros.entrySet());
        livrosOrdenados.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        List<Livro> livrosMaisPopulares = new ArrayList<>();
        for (Map.Entry<Livro, Integer> entry : livrosOrdenados) {
            livrosMaisPopulares.add(entry.getKey());
        }

        Serializador.salvarDados("relatorio.dat", livrosMaisPopulares);

        return livrosMaisPopulares;
    }
}

