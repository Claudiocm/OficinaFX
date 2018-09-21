package controlefx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.Fornecedor;
import modelo.Produto;
import modelo.Produtofornecedor;

public class ProdutoFornecedorController implements Initializable {

    @FXML
    private TableView<Produto> tabelaProduto;
    @FXML
    private TableColumn<Produto, Integer> columnCodigo;
    @FXML
    private TableColumn<Produto, String> columnNome;
    @FXML
    private TableColumn<Produto, String> columnMarca;
    @FXML
    private TableColumn<Produto, Integer> columnQuantidade;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtPreco;
    @FXML
    private ComboBox<Fornecedor> cmbFornecedor;
    @FXML
    private ComboBox<Produtofornecedor> cmbProduto;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtPreco.clear();
        cmbFornecedor.getItems().clear();
        cmbProduto.getItems().clear();
        tabelaProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItem(newValue));

    }

    @FXML
    private void exlcluirProduto() {
    }

    @FXML
    private void atualizarProduto() {
    }

    @FXML
    private void adicionarProduto() {
    }

    @FXML
    private void preencherFornecedor() {
    }

    @FXML
    private void preencherProduto() {
    }

    private void selecionaItem(Produto produto) {
        
    }
    
}
