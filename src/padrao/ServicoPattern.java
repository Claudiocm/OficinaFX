package padrao;

import java.util.List;
import modelo.Servicos;

/**
 *
 * @author estagio
 */
public interface ServicoPattern{
    public void criarServico();
    public void removeServico();
    public void atualizaServico();
    public Servicos buscaPorId(int id);
    public List<Servicos> listaServicos();
}
