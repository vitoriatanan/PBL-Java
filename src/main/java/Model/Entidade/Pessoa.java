package Model.Entidade;


import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    // Atributos
    private String nome;

    // Construtor
    public Pessoa(String nome) {
        this.nome = nome;
    }

    // Métodos Acessores
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
