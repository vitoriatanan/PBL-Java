package Model.DAO.EmprestimoDevolucao;

import Model.DAO.EmprestimoDevolucao.EmprestimoDevolucaoDAO;
import Model.Entidade.EmprestimoDevolucao;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoDevolucaoDAOImpl implements EmprestimoDevolucaoDAO {

    private List<EmprestimoDevolucao> emprestimos;

    public EmprestimoDevolucaoDAOImpl() {
        this.emprestimos = new ArrayList<>();
    }

    @Override
    public List<EmprestimoDevolucao> create(EmprestimoDevolucao emprestimo) {
        emprestimos.add(emprestimo);
        return emprestimos;
    }

    @Override
    public EmprestimoDevolucao read(EmprestimoDevolucao emprestimo) {
        if (emprestimos.contains(emprestimo)) {
            int index = emprestimos.indexOf(emprestimo);
            return emprestimos.get(index);
        }
        return null;
    }

    @Override
    public void update(EmprestimoDevolucao emprestimoAtual, EmprestimoDevolucao novoEmprestimo) {
        if (emprestimos.contains(emprestimoAtual)) {
            int index = emprestimos.indexOf(emprestimoAtual);
            emprestimos.set(index, novoEmprestimo);
        }

    }

    @Override
    public void delete(EmprestimoDevolucao emprestimo) {
        emprestimos.remove(emprestimo);
    }


}
