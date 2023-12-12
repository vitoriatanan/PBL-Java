package Model.Entidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimoDevolucao implements Serializable {
    private static final long serialVersionUID = 2L;
    // Atributos
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoEsperada;
    private Date dataDevolucaoReal;
    private int renovacoes;
    private List<EmprestimoDevolucao> listaDevolucoes;

    // Método Construtor
    public EmprestimoDevolucao(Usuario usuario, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucaoEsperada) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoEsperada = dataDevolucaoEsperada;
        this.renovacoes = 0;
        this.listaDevolucoes = new ArrayList<>();
    }


    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoEsperada() {
        return dataDevolucaoEsperada;
    }

    public void setDataDevolucaoEsperada(LocalDate dataDevolucaoEsperada) {
        this.dataDevolucaoEsperada = dataDevolucaoEsperada;
    }

    public Date getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(Date dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public Livro getLivro() {
        return livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getRenovacoes() {
        return renovacoes;
    }

    public void contagemRenovacoes() {
        renovacoes++;
    }

    public List<EmprestimoDevolucao> getListaDevolucoes() {
        return listaDevolucoes;
    }

    public void setListaDevolucoes(List<EmprestimoDevolucao> listaDevolucoes) {
        this.listaDevolucoes = listaDevolucoes;
    }
}
