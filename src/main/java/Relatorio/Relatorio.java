package Relatorio;

import EmprestimoDevolucao.EmprestimoDevolucao;
import Usuario.Usuario;
import Livro.Livro;

import java.util.*;

public class Relatorio {

    /**
     * Método que gera a quantidade de livros emprestados
     *
     * @param usuarios        Lista de usuários
     * @return                Total de livros emprestados
     * */
    public static int quantidadeLivrosEmprestados(List<Usuario> usuarios) {
        int quantidade = 0;

        for (Usuario usuario : usuarios) {
            quantidade += usuario.getEmprestimos().size();
        }
        return quantidade;
    }

    /**
     *  Método que gera a quantidade de livros atrasados
     *
     * @param usuarios        Lista de usuários
     * @return                Total de livros atrasados
     * */
    public static int quantidadeLivrosAtrasados(List<Usuario> usuarios) {
        int quantidade = 0;

        for (Usuario usuario : usuarios) {
            for (EmprestimoDevolucao emprestimo : usuario.getEmprestimos()) {
                if (emprestimo.getDataDevolucaoReal() == null && new Date().after(emprestimo.getDataDevolucaoEsperada())) {
                    quantidade++;
                }
            }
        }
        return quantidade;
    }

    /**
     * Método que gera a quantidade de livros reservados
     *
     * @param livros      Lista de livros
     * @return            Total de livros reservados
     * */
    public static int quantidadeLivrosReservados(List<Livro> livros) {
        int quantidade = 0;

        for (Livro livro : livros) {
            if (!livro.getUsuariosReservados().isEmpty()) {
                quantidade ++;
            }
        }
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
    public static List<Livro> livrosMaisPopulares(List<Usuario> usuarios) {
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

        return livrosMaisPopulares;
    }
}

