package EmprestimoDevolucao;

import java.util.Date;
import Livro.Livro;
import Usuario.Usuario;

public class EmprestimoDevolucao {
    // Atributos
    private Livro livro;
    private Usuario usuario;
    private Date dataEmprestimo;
    private Date dataDevolucaoEsperada;
    private Date dataDevolucaoReal;
    private int renovacoes;

    // Método Construtor
    public EmprestimoDevolucao(Usuario usuario, Livro livro, Date dataEmprestimo, Date dataDevolucaoEsperada) {
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoEsperada = dataDevolucaoEsperada;
        this.renovacoes = 0;
    }


    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoEsperada() {
        return dataDevolucaoEsperada;
    }

    public void setDataDevolucaoEsperada(Date dataDevolucaoEsperada) {
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

}
