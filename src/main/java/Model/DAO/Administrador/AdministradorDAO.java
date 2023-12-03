package Model.DAO.Administrador;

import Model.Entidade.Administrador;

import java.util.List;

public interface AdministradorDAO {
    List<Administrador> create(Administrador administrador);
    Administrador read(Integer id);
    void update(Integer id, Administrador novoAdm);
    List<Administrador> delete(Administrador administrador);
}
