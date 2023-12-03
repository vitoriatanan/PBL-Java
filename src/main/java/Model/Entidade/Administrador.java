package Model.Entidade;

public class Administrador extends Pessoa {
    // Atributos
    private String cargo;
    private String senhaAcesso;

    // Método Construtor
    public Administrador(String nome, Integer id, String cargo, String senhaAcesso) {
        super(nome, id);
        this.cargo = cargo;
        this.senhaAcesso = senhaAcesso;
    }

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
