package controlefx;

import dao.FornecedorDAO;
import dao.ProdutoDAO;
import dao.ProdutoFornecedorDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Fornecedor;
import modelo.Produto;
import modelo.Produtofornecedor;

public class InsumoFXMLController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtContato;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnPesquisar;
    @FXML
    private ComboBox<Fornecedor> cmbFornecedor;
    @FXML
    private ComboBox<Produto> cmbProduto;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Produtofornecedor> tabelaProduto;
    @FXML
    private TableColumn<Produtofornecedor, Integer> columnCodigo;
    @FXML
    private TableColumn<Produtofornecedor, String> columnNome;
    @FXML
    private TableColumn<Produtofornecedor, Double> columnPreco;
    private List<Fornecedor> fornecedores;
    private List<Produtofornecedor> produtos;
    private List<Produto> produto;
    private ObservableList<Produtofornecedor> produtosfx;
    private ObservableList<Fornecedor> fornecedorfx;
    private ObservableList<Produto> produtofx;
    private ProdutoFornecedorDAO profornecedorDao = null;
    private FornecedorDAO fornecedorDao = null;
    private ProdutoDAO produtoDao = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profornecedorDao = new ProdutoFornecedorDAO();
        fornecedorDao = new FornecedorDAO();
        produtoDao = new ProdutoDAO();
        
        preencheFornecedor();
        preencheProduto();
        tabelaProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));
        cmbFornecedor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaFornecedor(newValue));

    }

    @FXML
    private void preencheProduto() {
        produto = produtoDao.buscarTodos();
        produtofx = FXCollections.observableArrayList(produto);
        cmbProduto.setItems(produtofx);
    }

    private void selecionaItemTabela(Produtofornecedor produto) {

    }

    private void atualizaTabela() {
        produtos = profornecedorDao.buscarTodos();
        produtosfx = FXCollections.observableArrayList(produtos);
        tabelaProduto.getItems().setAll(produtosfx);
    }

    private void selecionaFornecedor(Fornecedor fornecedor) {
        if (fornecedor != null) {
            Produtofornecedor insumo = profornecedorDao.buscarId(fornecedor.getForId());

            preencheFornecedor();
            txtId.setText(String.valueOf(fornecedor.getForId()));
            txtContato.setText(fornecedor.getForContato());
            preencheProduto();
            tabelaProduto.getItems().clear();
            tabelaProduto.getItems().setAll(insumo);
            columnCodigo.setCellValueFactory(new PropertyValueFactory<>("profFornecedor"));
            columnNome.setCellValueFactory(new PropertyValueFactory<>("profProduto"));
            columnPreco.setCellValueFactory(new PropertyValueFactory<>("profPreco"));

        } else {
            tabelaProduto.getItems().clear();

            columnCodigo.setCellValueFactory(new PropertyValueFactory<>(""));
            columnNome.setCellValueFactory(new PropertyValueFactory<>(""));
            columnPreco.setCellValueFactory(new PropertyValueFactory<>(""));
        }
    }

    private void preencheTabela() {
        atualizaTabela();
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("profFornecedor"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("profProduto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("profPreco"));
    }

    @FXML
    private void preencheFornecedor() {
        fornecedores = fornecedorDao.buscarTodos();
        fornecedorfx = FXCollections.observableArrayList(fornecedores);
        cmbFornecedor.setItems(fornecedorfx);
    }

}
