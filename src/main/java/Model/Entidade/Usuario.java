package Model.Entidade;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Pessoa {
    // Atributos
    private String endereco;
    private String telefone;
    private List<EmprestimoDevolucao> emprestimos;
    private boolean contaBloqueada;
    private double multa;

    // Método Construtor
    public Usuario(String nome, String id, String endereco, String telefone) {
        super(nome, id);
        this.endereco = endereco;
        this.telefone = telefone;
        this.emprestimos = new ArrayList<>();
        this.contaBloqueada = false;
        this.multa = 0;
    }


    // Métodos Acessores
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<EmprestimoDevolucao> getEmprestimos() {
        return emprestimos;
    }

    public boolean isContaBloqueada() {
        return contaBloqueada;
    }

    public void setContaBloqueada(boolean contaBloqueada) {
        this.contaBloqueada = contaBloqueada;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

}

