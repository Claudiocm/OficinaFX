package controlefx;

import dao.FornecedorDAO;
import dao.FornecedorprodutoDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Fornecedor;
import modelo.Produtofornecedor;

public class FornecedorFXMLController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtFantasia;
    @FXML
    private TextField txtCnpj;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private TextField txtUF;
    @FXML
    private TextField txtCEP;
    @FXML
    private TextField txtContato;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Fornecedor> tabelaFornecedor;
    @FXML
    private TableColumn<Fornecedor, Integer> columnCodigo;
    @FXML
    private TableColumn<Fornecedor, String> columnFantasia;
    @FXML
    private TableColumn<Fornecedor, String> columnCnpj;
    @FXML
    private TableColumn<Fornecedor, String> columnContato;
    @FXML
    private TableView<Produtofornecedor> tabelaProduto;
    @FXML
    private TableColumn<Produtofornecedor, String> columnNome;
    @FXML
    private TableColumn<Produtofornecedor, Double> columnPreco;
    @FXML
    private TableColumn<Produtofornecedor, Integer> columnId;
    private List<Fornecedor> fornecedores;
    private List<Produtofornecedor> produtos;
    private ObservableList<Produtofornecedor> produtofx;
    private ObservableList<Fornecedor> fornecedorfx;
    private FornecedorDAO fornecedorDao = null;
    private FornecedorprodutoDAO produtoDao = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fornecedorDao = new FornecedorDAO();
        produtoDao = new FornecedorprodutoDAO();
        preencheTabela();
        tabelaFornecedor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));

    }

    private void preencheProduto() {
        atualizaProduto();
        columnId.setCellValueFactory(new PropertyValueFactory<>("profId"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("profProduto"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("profPreco"));
    }

    private void atualizaProduto() {
        produtos = produtoDao.buscarTodos();
        produtofx = FXCollections.observableArrayList(produtos);
        tabelaProduto.setItems(produtofx);
    }

    @FXML
    private void salvarFornecedor() {
        Fornecedor fornecedor = new Fornecedor();
        String fantasia = txtFantasia.getText();
        String cnpj = txtCnpj.getText();

        if (!fantasia.trim().isEmpty() || !cnpj.trim().isEmpty()) {
            fornecedor.setForFantasia(fantasia);
            fornecedor.setForCnpj(cnpj);
            fornecedor.setForContato(txtContato.getText());
            fornecedor.setForLogradouro(txtEndereco.getText());
            fornecedor.setForBairro(txtBairro.getText());
            fornecedor.setForCidade(txtCidade.getText());
            fornecedor.setForEstado(txtUF.getText());
            fornecedor.setForCep(txtCEP.getText());

            fornecedores.add(fornecedor);
            fornecedorDao.salvar(fornecedor);
            tabelaFornecedor.getItems().add(fornecedor);
           
            alertas("Parabéns", "Fornecedor atualizado com sucesso!");
             atualizaTabela();
        } else {
            if (fantasia.trim().isEmpty()) {
                txtFantasia.setText("Preencha com o nome Fantasia");
                txtFantasia.requestFocus();
            }
            if (cnpj.trim().isEmpty()) {
                txtCnpj.setText("Preencha o CNPJ");
                txtCnpj.requestFocus();
            }
            alertas("Atenção", "Preencha os campos vazios!");
        }
    }

    @FXML
    private void atualizarFornecedor() {
        Fornecedor fornecedor = tabelaFornecedor.getSelectionModel().getSelectedItem();
        if (fornecedor != null) {
            fornecedor.setForFantasia(txtFantasia.getText());
            fornecedor.setForCnpj(txtCnpj.getText());
            fornecedor.setForContato(txtContato.getText());
            fornecedor.setForLogradouro(txtEndereco.getText());
            fornecedor.setForBairro(txtBairro.getText());
            fornecedor.setForCidade(txtCidade.getText());
            fornecedor.setForEstado(txtUF.getText());
            fornecedor.setForCep(txtCEP.getText());

            fornecedorDao.atualizar(fornecedor);
            tabelaFornecedor.getItems().setAll(fornecedores);
           
            alertas("Parabéns", "Fornecedor atualizado com sucesso!");
             atualizaTabela();
        } else {
            alertas("Atenção", "Selecione um fornecedor!");
        }
    }

    @FXML
    private void excluirFornecedor() {
        Fornecedor fornecedor = tabelaFornecedor.getSelectionModel().getSelectedItem();
        if (fornecedor != null) {
            fornecedorDao.apagar(fornecedor.getForId());
           
            alertas("Parabéns", "Fornecedor excluido com sucesso!");
             atualizaTabela();
        } else {
            alertas("Atenção", "Selecione um fornecedor!");
        }
    }

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtFantasia.clear();
        txtEndereco.clear();
        txtBairro.clear();
        txtCidade.clear();
        txtCnpj.clear();
        txtUF.clear();
        txtCEP.clear();
        txtContato.clear();
        btnAdicionar.setDisable(false);
        tabelaFornecedor.getSelectionModel().clearSelection();
         tabelaProduto.getSelectionModel().clearSelection();
        tabelaProduto.getItems().clear();
        txtFantasia.requestFocus();
    }

    private void preencheTabela() {
        atualizaTabela();
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("forId"));
        columnFantasia.setCellValueFactory(new PropertyValueFactory<>("forFantasia"));
        columnContato.setCellValueFactory(new PropertyValueFactory<>("forContato"));
        columnCnpj.setCellValueFactory(new PropertyValueFactory<>("forCnpj"));
    }

    private void atualizaTabela() {
        fornecedores = fornecedorDao.buscarTodos();
        fornecedorfx = FXCollections.observableArrayList(fornecedores);
        tabelaFornecedor.setItems(fornecedorfx);
    }

    private void selecionaItemTabela(Fornecedor fornecedor) {
        if (fornecedor != null) {
            Produtofornecedor forpro = produtoDao.buscarId(fornecedor.getForId());
            produtos = produtoDao.buscarTodos();
            produtofx = FXCollections.observableArrayList(produtos);
            
            txtCodigo.setText(String.valueOf(fornecedor.getForId()));
            txtCnpj.setText(fornecedor.getForCnpj());
            txtFantasia.setText(fornecedor.getForFantasia());
            txtEndereco.setText(fornecedor.getForLogradouro());
            txtBairro.setText(fornecedor.getForBairro());
            txtCidade.setText(fornecedor.getForCidade());
            txtUF.setText(fornecedor.getForEstado());
            txtCEP.setText(fornecedor.getForCep());
            txtContato.setText(fornecedor.getForContato());
            btnAdicionar.setDisable(true);
            
            if (forpro != null) {
                tabelaProduto.getItems().setAll(forpro);
                columnId.setCellValueFactory(new PropertyValueFactory<>("profId"));
                columnNome.setCellValueFactory(new PropertyValueFactory<>("profProduto"));
                columnPreco.setCellValueFactory(new PropertyValueFactory<>("profPreco"));
            } else {

            }
        } else {
            cancelar();
        }
    }

    public void alertas(String titulo, String frase) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }
}
