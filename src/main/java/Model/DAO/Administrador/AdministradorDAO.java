package Model.DAO.Administrador;

import Model.Entidade.Administrador;

import java.util.List;

public interface AdministradorDAO {
    List<Administrador> create(Administrador administrador);
    Administrador read(String id);
    void update(String id, Administrador novoAdm);
    List<Administrador> delete(Administrador administrador);
}
