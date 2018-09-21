package controlefx;

import dao.ProdutoDAO;
import dao.ProdutoFornecedorDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import modelo.Produto;
import modelo.Produtofornecedor;

public class RelatorioProdutofornecedorController implements Initializable {

    @FXML
    private PieChart piechartfx;
    @FXML
    private Button btnGerar;

    private List<Produto> produtos;
    private ObservableList<Produto> produtofx;
    private ProdutoDAO produtoDao = null;
    private List<Produtofornecedor> profornecedores;
    private ObservableList<Produto> profornecedoresfx;
    private ProdutoFornecedorDAO proFornecedorDao = null;
    private Produtofornecedor prof;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDao = new ProdutoDAO();
        proFornecedorDao = new ProdutoFornecedorDAO();
        produtos = produtoDao.buscarTodos();
        geraGrafico();
    }    

    @FXML
    private void geraRelatorio() {
        geraGrafico();
    }
    
    public PieChart geraGrafico(){
        if(prof != null){
            PieChart grafico = new PieChart();
            profornecedores = proFornecedorDao.buscarTodos(prof.getProfFornecedor());
            
            for(Produtofornecedor pf: profornecedores){
                
            }
           
            grafico.setTitle("Grafico de Produtos por Fornecedor");
            grafico.setLegendSide(Side.TOP);
            grafico.setLabelsVisible(true);
           
        }else{
            
            return null;
        }
        return null;
    }
    
}
