package controlefx;

import dao.ItensDAO;
import dao.OrdemDAO;
import dao.ProdutoDAO;
import dao.ServicoDAO;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.FormaPagamento;
import modelo.Itensservicos;
import modelo.Ordemservico;
import modelo.Produto;
import modelo.Servicos;
import util.Mascara;

public class CaixaD3FXMLController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtTotal;
    @FXML
    private TableView<Itensservicos> tabelaItem;
    @FXML
    private TableColumn<Itensservicos, Integer> columnCodigo;
    @FXML
    private TableColumn<Itensservicos, String> columnDescricao;
    @FXML
    private TableColumn<Itensservicos, Double> columnValor;
    @FXML
    private TableColumn<Itensservicos, Integer> columnQuantidade;
    private ItensDAO itemDao = null;
    private OrdemDAO ordemDao = null;
    private ProdutoDAO produtoDao = null;
    private List<Produto> produtos;
    private ServicoDAO servicoDao = null;
    private List<Itensservicos> itens;
    private List<Servicos> servicos;
    private List<Ordemservico> ordens;
    @FXML
    private Label lblCupom;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnFinalizar;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblNumerovenda;
    @FXML
    private Label lblDataHora;
    private final SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtAutomovel;
    @FXML
    private TextField txtRecebido;
    @FXML
    private TextField txtTroco;
    @FXML
    private ComboBox<FormaPagamento> cmbFormapagamento;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        itemDao = new ItensDAO();
        ordemDao = new OrdemDAO();
        servicoDao = new ServicoDAO();
        produtoDao = new ProdutoDAO();

        criaCupom();
        preencheForma();
        mascaras();
        txtCliente.setEditable(false);
        txtAutomovel.setEditable(false);
        txtTotal.setEditable(false);
        txtTroco.setEditable(false);
        tabelaItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));
        txtCodigo.textProperty().addListener((observable, oldValue, newValue) -> buscaCodigo());
        txtRecebido.textProperty().addListener((obs, oldValue, newValue) -> calculaTroco());
    }

    private void selecionaItemTabela(Itensservicos itens) {

    }

    private void criaCupom() {
        String cabecalho = "Codigo     " + "Descrição                " + "Quantidade  " + "Preço    " + "\n";
        String titulo = "CUPO FISCAL \n" + "====================================================\n"
                + "Empresa:       \n" + "                                          \n\n";
        String total = "Total:  ";
        lblCupom.setText(titulo + cabecalho + "                                                       \n\n\n\n" + total);
    }

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtCliente.clear();
        txtAutomovel.clear();
        txtTroco.setText("");
        txtRecebido.clear();
        txtTotal.clear();
        lblDataHora.setText("");
        tabelaItem.getItems().clear();
        txtCodigo.requestFocus();
    }

    @FXML
    private void finaliza() {

    }

    @FXML
    private void buscaCodigo() {
        if (!txtCodigo.getText().trim().isEmpty()) {
            int id = Integer.parseInt(txtCodigo.getText());
            Ordemservico ordem = ordemDao.buscarId(id);

            if (ordem != null) {
                txtCodigo.setText(String.valueOf(ordem.getOrdId()));
                txtCliente.setText(String.valueOf(ordem.getOrdCliente().getCliNome()));
                txtAutomovel.setText(String.valueOf(ordem.getOrdCarro()));

                tabelaItem.getItems().clear();
                tabelaItem.getItems().setAll(ordem.getItensservicosList());
                columnCodigo.setCellValueFactory(new PropertyValueFactory<>("itesId"));
                columnDescricao.setCellValueFactory(new PropertyValueFactory<>("itesServico"));
                columnValor.setCellValueFactory(new PropertyValueFactory<>("itesValor"));
                columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("itesQuantidade"));

                txtTotal.setText(String.valueOf(ordem.getOrdTotal()));
                lblDataHora.setText(formataData.format(ordem.getOrdData()));
            }
        } else {
            cancelar();
        }
    }

    @FXML
    private void preencheForma() {
        cmbFormapagamento.getItems().setAll(FormaPagamento.values());
    }

    private void calculaTroco() {
        if (!txtRecebido.getText().trim().isEmpty()) {
            double recebido = Double.parseDouble(txtRecebido.getText());
            double total = Double.parseDouble(txtTotal.getText());

            txtTroco.setText(String.format("%2.00f",(recebido - total)));
        } else {
            txtRecebido.setText("");
        }

    }

    private void mascaras() {
        Mascara.numerico(txtCodigo);
        Mascara.numerico(txtRecebido);

    }

}
