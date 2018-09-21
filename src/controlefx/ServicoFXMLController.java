package controlefx;

import dao.ProdutoDAO;
import dao.ProdutoFornecedorDAO;
import dao.ServicoDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Produto;
import modelo.Produtofornecedor;
import modelo.Servicos;

public class ServicoFXMLController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtPreco;
    @FXML
    private ComboBox<Produtofornecedor> cmbProduto;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Servicos> tabelaServico;
    @FXML
    private TableColumn<Servicos, Integer> columnCodigo;
    @FXML
    private TableColumn<Servicos, String> columnNome;
    @FXML
    private TableColumn<Servicos, Double> columnPreco;
    private List<Servicos> servicos;
    private List<Produto> produtos;
    private List<Produtofornecedor> profornecedores;
    private ObservableList<Servicos> servicofx;
    private ObservableList<Produto> produtofx;
    private ObservableList<Produtofornecedor> profornecedorfx;
    private ServicoDAO servicoDao = null;
    private ProdutoFornecedorDAO produtosDao = null;
    private ProdutoDAO produtoDao = null;
    @FXML
    private TableView<Produto> tabelaProduto;
    @FXML
    private TableColumn<Produto, Integer> columnCodigos;
    @FXML
    private TableColumn<Produto, Integer> columnUnidade;
     @FXML
    private TableColumn<Produto, String> columnNomes;
    @FXML
    private Button btnAdicionaProduto;
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicoDao = new ServicoDAO();
        produtosDao = new ProdutoFornecedorDAO();
        produtoDao = new ProdutoDAO();

       // mostraTabela();
        preencheProduto();
        tabelaServico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));
    }

    @FXML
    private void salvarServico() {
        Servicos servico = new Servicos();
        String nome = txtNome.getText();

        if (!nome.trim().isEmpty()) {
            servico.setSerNome(nome);
            servico.setSerPreco(Double.parseDouble(txtPreco.getText()));
            servico.setSerProduto(cmbProduto.getValue());

            servicoDao.salvar(servico);
            servicos.add(servico);
            tabelaServico.getItems().add(servico);
            atualizaTabela();
            cancelar();
            alertas("Parabens", "Serviço adicionado com sucesso");
        } else {
            if (nome.trim().isEmpty()) {
                txtNome.setText("Preencha o campo nome");
                txtNome.requestFocus();
            }
            alertas("Atenção", "Preencha os campos vazios!");
        }
    }

    @FXML
    private void atualizarServico() {
        Servicos servico = tabelaServico.getSelectionModel().getSelectedItem();
        if (servico != null) {
            servico.setSerNome(txtNome.getText());
            servico.setSerPreco(Double.parseDouble(txtPreco.getText()));
            servico.setSerProduto(cmbProduto.getValue());

            servicoDao.atualizar(servico);
            tabelaServico.getItems().setAll(servicos);
            atualizaTabela();
            cancelar();
            alertas("Parabens", "Serviço alterado com sucesso");
        } else {
            alertas("Atenção", "Selecione um serviço!");
        }
    }

    @FXML
    private void excluirServico() {
        Servicos servico = tabelaServico.getSelectionModel().getSelectedItem();

        if (servico != null) {
            servicoDao.apagar(servico.getSerCodigo());
            atualizaTabela();
            cancelar();
            alertas("Parabens", "Serviço excluido com sucesso");
        } else {
            alertas("Atenção", "Selecione um serviço!");
        }

    }

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtNome.clear();
        txtPreco.clear();
        cmbProduto.getItems().clear();
        btnSalvar.setDisable(false);
        tabelaServico.getItems().clear();
        txtNome.requestFocus();
    }

    private void mostraTabela() {
        atualizaTabela();
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("serCodigo"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("serNome"));
        columnPreco.setCellValueFactory(new PropertyValueFactory<>("serPreco"));
    }

    private void atualizaTabela() {
        servicos = servicoDao.buscarTodos();
        servicofx = FXCollections.observableArrayList(servicos);
        tabelaServico.setItems(servicofx);
    }

    private void selecionaItemTabela(Servicos servico) {
        preencheProduto();
        if (servico != null) {
            //Produtofornecedor produto = produtoDao.buscarId(servico.getSerCodigo());

            txtCodigo.setText(String.valueOf(servico.getSerCodigo()));
            txtNome.setText(servico.getSerNome());
            txtPreco.setText(String.format("R$ %2.00f", servico.getSerPreco()));
            cmbProduto.setValue(servico.getSerProduto());
            btnSalvar.setDisable(true);
        } else {
            cancelar();
        }
    }

    @FXML
    private void preencheProduto() {
        profornecedores = produtosDao.buscarTodos();
        profornecedorfx = FXCollections.observableArrayList(profornecedores);
        cmbProduto.setItems(profornecedorfx);
    }

    public void alertas(String titulo, String frase) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }

    @FXML
    private void adicionaProduto() {
        Produto p = cmbProduto.getValue().getProfProduto();
        if (p != null) {
            tabelaProduto.getItems().add(p);
            columnCodigos.setCellValueFactory(new PropertyValueFactory<>("proId"));
            columnNomes.setCellValueFactory(new PropertyValueFactory<>("proNome"));
            columnUnidade.setCellValueFactory(new PropertyValueFactory<>("proUnidade"));
        }
    }
}
