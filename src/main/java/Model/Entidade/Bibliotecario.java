package Model.Entidade;

public class Bibliotecario extends Pessoa {
    // Atributos
    private String cargo;
    private String senhaAcesso;

    // Método Construtor
    public Bibliotecario(String nome, String id, String cargo, String senhaAcesso) {
        super(nome, id);
        this.cargo = cargo;
        this.senhaAcesso = senhaAcesso;
    }

    // Métodos Acessores
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenhaAcesso() {
        return senhaAcesso;
    }

    public void setSenhaAcesso(String senhaAcesso) {
        this.senhaAcesso = senhaAcesso;
    }

}
