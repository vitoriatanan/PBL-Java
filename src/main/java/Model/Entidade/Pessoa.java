package Model.Entidade;

public abstract class Pessoa {
    // Atributos
    private String nome;
    private String id;

    // Construtor
    public Pessoa(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    // Métodos Acessores
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
