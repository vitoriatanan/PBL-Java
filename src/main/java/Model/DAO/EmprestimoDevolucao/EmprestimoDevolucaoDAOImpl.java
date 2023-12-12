package Model.DAO.EmprestimoDevolucao;

import FileData.Entidade.Serializador;
import Model.Entidade.EmprestimoDevolucao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDevolucaoDAOImpl implements EmprestimoDevolucaoDAO {

    private List<EmprestimoDevolucao> emprestimos;

    public EmprestimoDevolucaoDAOImpl() {
        this.emprestimos = new ArrayList<>();
    }


    /**
     * Responsável por adicionar um novo empréstimo ao sistema.
     *
     * Adiciona o empréstimo à lista de empréstimos e salva a
     * lista atualizada de empréstimos no arquivo "empréstimoDevolucao.dat".
     *
     * @param emprestimo    empréstimo a ser adicionado
     * @return              lista de empréstimos
     * */
    @Override
    public List<EmprestimoDevolucao> create(EmprestimoDevolucao emprestimo) throws IOException {
        emprestimos.add(emprestimo);
        Serializador.salvarDados("emprestimoDevolucao.dat", emprestimos);
        return emprestimos;
    }

    /**
    *
    * Lê a lista de empréstimos do arquivo "emprestimoDevolucao.dat", itera
    * sobre ela procurando por um empréstimo específico e salva a lista
    * atualizada no arquivo.
    *
    * @param titulo     Título do livro
    * @return           Empréstimo se encontrado, caso contrário, retorna nulo
    * */
    @Override
    public EmprestimoDevolucao read(String titulo) throws IOException, ClassNotFoundException {

        /* Faz a leitura do arquivo "emprestimoDevolucao.dat" */
        ArrayList<EmprestimoDevolucao> listaEmprDevo = new ArrayList<EmprestimoDevolucao>();
        listaEmprDevo = (ArrayList<EmprestimoDevolucao>) Serializador.leituraDados("emprestimoDevolucao.dat");

        if (listaEmprDevo != null) {
            for (EmprestimoDevolucao emprestimoDevolucao : listaEmprDevo) {
                if (emprestimoDevolucao.getLivro().getTitulo().equals(titulo)) {
                    return emprestimoDevolucao;
                }
            }
        }
        return null;
    }

    /**
    * Responsável por atualizar as informações de um empréstimo específico.
    *
    * Lê a lista de empréstimos do arquivo, verifica se a lista contém o
    * empréstimo a ser atualizado, substitui pelo novo empréstimo e salva a
    * lista atualizada no arquivo "emprestimoDevolucao.dat".
    *
    * @param emprestimoAtual       empréstimo realizado recente
    * @param novoEmprestimo        emprestimo a ser atualizadp
    * */
    @Override
    public void update(EmprestimoDevolucao emprestimoAtual, EmprestimoDevolucao novoEmprestimo) throws IOException, ClassNotFoundException {

        /* Faz a leitura do arquivo "emprestimoDevolucao.dat" */
        ArrayList<EmprestimoDevolucao> listaEmprDevo = new ArrayList<EmprestimoDevolucao>();
        listaEmprDevo = (ArrayList<EmprestimoDevolucao>) Serializador.leituraDados("emprestimoDevolucao.dat");

        for (int i = 0; i < listaEmprDevo.size(); i++) {
            if (listaEmprDevo.get(i).equals(emprestimoAtual)) {
                listaEmprDevo.set(i, novoEmprestimo);
                break;
            }
        }

        emprestimos = listaEmprDevo;
        Serializador.salvarDados("emprestimoDevolucao.dat", listaEmprDevo);
    }

    /**
    * Responsável por remover um empréstimo específico.
    *
    * Lê a lista de empréstimos do arquivo "emprestimoDevolucao.dat", itera sobre
    * uma cópia da lista de empréstimos, verifica se cada empréstimo na cópia
    * possui as mesmas características do empréstimo a ser removido, remove esse
    * empréstimo da lista e salva a lista atualizada no arquivo.
    *
    * @param emprestimo     empréstimo a ser removido
    * */
    @Override
    public void delete(EmprestimoDevolucao emprestimo) throws IOException, ClassNotFoundException {
        emprestimos.remove(emprestimo);
        Serializador.salvarDados("emprestimoDevolucao.dat", emprestimos);
    }
}
