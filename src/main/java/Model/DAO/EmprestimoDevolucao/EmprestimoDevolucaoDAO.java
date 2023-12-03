package Model.DAO.EmprestimoDevolucao;

import Model.Entidade.EmprestimoDevolucao;

import java.util.List;

public interface EmprestimoDevolucaoDAO {
    List<EmprestimoDevolucao> create(EmprestimoDevolucao emprestimo);
    EmprestimoDevolucao read(EmprestimoDevolucao emprestimo);
    void update(EmprestimoDevolucao emprestimoAtual, EmprestimoDevolucao novoEmprestimo);
    void delete(EmprestimoDevolucao emprestimo);
}
