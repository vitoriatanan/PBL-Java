package Model.DAO.Administrador;

import Model.Entidade.Administrador;

import java.io.IOException;
import java.util.List;

public interface AdministradorDAO {
    List<Administrador> create(Administrador administrador) throws IOException;
    Administrador read(String id) throws IOException, ClassNotFoundException;
    void update(String id, Administrador novoAdm) throws IOException, ClassNotFoundException;
    List<Administrador> delete(Administrador administrador) throws IOException, ClassNotFoundException;
}
