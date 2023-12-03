package Model.Entidade;

public abstract class Pessoa {
    // Atributos
    private String nome;
    private Integer id;

    // Construtor
    public Pessoa(String nome, Integer id) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
