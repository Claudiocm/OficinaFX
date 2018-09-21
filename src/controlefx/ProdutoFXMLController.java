package controlefx;

import dao.ProdutoDAO;
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
import modelo.Produto;
import util.Mascara;

public class ProdutoFXMLController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private TextField txtUnidade;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Produto> tabelaProduto;
    @FXML
    private TableColumn<Produto, Integer> columnCodigo;
    @FXML
    private TableColumn<Produto, String> columnNome;
    @FXML
    private TableColumn<Produto, String> columnModelo;
    @FXML
    private TableColumn<Produto, Integer> columnQuantidade;
    private List<Produto> produtos;
    private ObservableList<Produto> produtofx;
    private ProdutoDAO produtoDao = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDao = new ProdutoDAO();

        Mascara.numerico(txtQuantidade);
        txtCodigo.setEditable(false);
        preencherTabela();
        tabelaProduto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));

    }

    @FXML
    private void adicionarProduto() {
        Produto pro = new Produto();
        String nome = txtNome.getText();
        String modelo = txtModelo.getText();

        if (!nome.trim().isEmpty() || !modelo.trim().isEmpty()) {
            pro.setProNome(nome);
            pro.setProMarca(txtMarca.getText());
            pro.setProModelo(modelo);
            pro.setProQuantidade(Integer.parseInt(txtQuantidade.getText()));
            pro.setProUnidade(txtUnidade.getText());

            produtos.add(pro);
            produtoDao.salvar(pro);
            tabelaProduto.getItems().add(pro);
            atualizarTabela();

            cancelar();
            alertas("Parabéns", "Produto atualizado com sucesso!");
        } else {
            alertas("Atenção", "Selecione um Produto");
        }
    }

    @FXML
    private void atualizarProduto() {
        Produto pro = tabelaProduto.getSelectionModel().getSelectedItem();

        if (pro != null) {
            pro.setProNome(txtNome.getText());
            pro.setProMarca(txtMarca.getText());
            pro.setProModelo(txtModelo.getText());
            pro.setProQuantidade(Integer.parseInt(txtQuantidade.getText()));
            pro.setProUnidade(txtUnidade.getText());

            produtoDao.atualizar(pro);
            tabelaProduto.getItems().setAll(produtos);
            atualizarTabela();

            cancelar();
            alertas("Parabéns", "Produto atualizado com sucesso!");
        } else {
            alertas("Atenção", "Selecione um Produto");
        }
    }

    @FXML
    private void exlcluirProduto() {
        Produto pro = tabelaProduto.getSelectionModel().getSelectedItem();

        if (pro != null) {
            produtoDao.apagar(pro.getProId());
            atualizarTabela();
            cancelar();
            alertas("Parabéns", "Produto excluido com sucesso!");

        } else {
            alertas("Atenção", "Selecione um Produto");
        }
    }

    public void alertas(String titulo, String frase) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtNome.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtQuantidade.clear();
        txtUnidade.clear();
        tabelaProduto.getItems().clear();
        btnAdicionar.setDisable(false);
        txtNome.requestFocus();
    }

    private void preencherTabela() {
        atualizarTabela();
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("proId"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("proNome"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("proModelo"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("proQuantidade"));
    }

    private void atualizarTabela() {
        produtos = produtoDao.buscarTodos();
        produtofx = FXCollections.observableArrayList(produtos);
        tabelaProduto.getItems().setAll(produtos);
    }

    private void selecionaItemTabela(Produto produto) {
        if (produto != null) {
            txtCodigo.setText(String.valueOf(produto.getProId()));
            txtNome.setText(produto.getProNome());
            txtMarca.setText(produto.getProMarca());
            txtModelo.setText(produto.getProModelo());
            txtQuantidade.setText(String.valueOf(produto.getProQuantidade()));
            txtUnidade.setText(produto.getProUnidade());
            btnAdicionar.setDisable(true);
        } else {
            cancelar();
        }
    }

}
