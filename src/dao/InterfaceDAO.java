package dao;

import java.util.List;

public interface InterfaceDAO<T> {

    public void salvar(T t);

    public void atualizar(T t);

    public void apagar(int id);

    public T buscarId(int id);

    public List<T> buscarTodos();
}
