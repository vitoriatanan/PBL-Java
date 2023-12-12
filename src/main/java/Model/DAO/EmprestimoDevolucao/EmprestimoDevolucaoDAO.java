package Model.DAO.EmprestimoDevolucao;

import Model.Entidade.EmprestimoDevolucao;

import java.io.IOException;
import java.util.List;

public interface EmprestimoDevolucaoDAO {
    List<EmprestimoDevolucao> create(EmprestimoDevolucao emprestimo) throws IOException;
    EmprestimoDevolucao read(String titulo) throws IOException, ClassNotFoundException;
    void update(EmprestimoDevolucao emprestimoAtual, EmprestimoDevolucao novoEmprestimo) throws IOException, ClassNotFoundException;
    void delete(EmprestimoDevolucao emprestimo) throws IOException, ClassNotFoundException;
}
