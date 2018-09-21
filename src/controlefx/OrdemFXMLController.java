package controlefx;

import dao.CarroDAO;
import dao.ClienteDAO;
import dao.ItensDAO;
import dao.MecanicoDAO;
import dao.OrdemDAO;
import dao.ServicoDAO;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Carro;
import modelo.Cliente;
import modelo.Itensservicos;
import modelo.Mecanico;
import modelo.Ordemservico;
import modelo.Servicos;

public class OrdemFXMLController implements Initializable {

    private ServicoDAO servicoDao = null;
    private ClienteDAO clienteDao = null;
    private ItensDAO itensDao = null;
    private OrdemDAO ordemDao = null;
    private MecanicoDAO mecanicoDao = null;
    private CarroDAO carroDao = null;
    private List<Ordemservico> ordens;
    private List<Cliente> clientes;
    private List<Itensservicos> itens;
    private List<Mecanico> mecanicos;
    private List<Carro> carros;
    private List<Servicos> servicos;
    private ObservableList<Carro> carrofx;
    private ObservableList<Mecanico> mecanicosfx;
    private ObservableList<Cliente> clientesfx;
    private ObservableList<Ordemservico> ordensfx;
    private ObservableList<Itensservicos> itensfx;
    private ObservableList<Servicos> servicofx;
    @FXML
    private ComboBox<Ordemservico> cmbOrdem;
    @FXML
    private ComboBox<Cliente> cmbCliente;
    @FXML
    private ComboBox<Carro> cmbCarro;
    @FXML
    private ComboBox<Servicos> cmbServico;
    @FXML
    private CheckBox chkStatus;
    @FXML
    private TextField txtValor;
    @FXML
    private TableView<Itensservicos> tabelaItens;
    @FXML
    private TableColumn<Itensservicos, Integer> columnCodigo;
    @FXML
    private TableColumn<Itensservicos, Integer> columnQuantidade;
    @FXML
    private TableColumn<Itensservicos, Double> columnPreco;
    @FXML
    private TableColumn<Itensservicos, Integer> columnServico;
    @FXML
    private Button btnCancelar;
    @FXML
    private DatePicker dtpEntrada;
    @FXML
    private DatePicker dtpSaida;
    @FXML
    private Button btnNovo;
    @FXML
    private TextField txtQuantidade;
    @FXML
    private Button btnInserirItem;
    @FXML
    private Button btnLimpar;
    SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
    @FXML
    private Button btnConfirmapedido;
    @FXML
    private Button btnRemover;
    @FXML
    private ComboBox<Mecanico> cmbMecanico;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnEditar;
    @FXML
    private Label lblNumero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ordemDao = new OrdemDAO();
        clienteDao = new ClienteDAO();
        mecanicoDao = new MecanicoDAO();
        itensDao = new ItensDAO();
        carroDao = new CarroDAO();
        servicoDao = new ServicoDAO();
        preencheOS();
        preencheServico();
        preencheCliente();
        preencheCarro();
        preencheMecanico();

        dtpEntrada.setValue(LocalDate.now());
        cmbServico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaServico(newValue));
        cmbOrdem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaOrdem(newValue));
        cmbCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaCliente(newValue));
        tabelaItens.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));
    }

    @FXML
    private void preencheOS() {
        ordens = ordemDao.buscarTodos();
        ordensfx = FXCollections.observableArrayList(ordens);
        cmbOrdem.setItems(ordensfx);
    }

    @FXML
    private void preencheCliente() {
        clientes = clienteDao.buscarTodos();
        clientesfx = FXCollections.observableArrayList(clientes);
        cmbCliente.setItems(clientesfx);
    }

    @FXML
    private void preencheCarro() {
        carros = carroDao.buscarTodos();
        carrofx = FXCollections.observableArrayList(carros);
        cmbCarro.setItems(carrofx);

    }

    @FXML
    private void preencheServico() {
        servicos = servicoDao.buscarTodos();
        servicofx = FXCollections.observableArrayList(servicos);
        cmbServico.setItems(servicofx);
    }

    @FXML
    private void novoPedido() {
        cancelarNovaOrdem();
    }

    @FXML
    private void limparInserir() {
        cmbServico.setItems(null);
        txtQuantidade.clear();
        cmbServico.requestFocus();
    }

    @FXML
    private void cancelarNovaOrdem() {
        cmbOrdem.setItems(null);
        cmbCarro.setValue(null);
        cmbCliente.setValue(null);
        cmbMecanico.setValue(null);
        dtpEntrada.setPromptText("");
        dtpSaida.setPromptText("");
        txtValor.clear();
        chkStatus.setSelected(false);
        tabelaItens.getItems().clear();
        preencheOS();
        preencheCliente();
        preencheMecanico();
        preencheCarro();
        cmbOrdem.requestFocus();
    }

    @FXML
    private void inserirItens() {
        Itensservicos items = new Itensservicos();
        if (!cmbServico.getValue().getSerNome().trim().isEmpty() && !txtQuantidade.getText().trim().isEmpty()) {
            items.setItesServico(cmbServico.getValue());
            items.setItesQuantidade(Integer.parseInt(txtQuantidade.getText()));
            items.setItesOrdemservico(cmbOrdem.getValue());

            itensDao.salvar(items);
            itens.add(items);
            tabelaItens.getItems().add(items);
            limparInserir();
            btnRemover.setDisable(false);

            columnCodigo.setCellValueFactory(new PropertyValueFactory<>("itesId"));
            columnServico.setCellValueFactory(new PropertyValueFactory<>("itesServico"));
            columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("itesQuantidade"));
            columnPreco.setCellValueFactory(new PropertyValueFactory<>("itesValor"));
        } else {
            limparInserir();

            columnCodigo.setCellValueFactory(new PropertyValueFactory<>(""));
            columnServico.setCellValueFactory(new PropertyValueFactory<>(""));
            columnQuantidade.setCellValueFactory(new PropertyValueFactory<>(""));
            columnPreco.setCellValueFactory(new PropertyValueFactory<>(""));
        }
    }

    private void atualizaTabelaItens() {
        itens = itensDao.buscarTodos();
        itensfx = FXCollections.observableArrayList(itens);
        tabelaItens.setItems(itensfx);
    }

    private void selecionaItemTabela(Itensservicos itens) {

    }

    private void selecionaOrdem(Ordemservico ordem) {

        double total = 0;

        if (ordem != null) {
            Date entrada = ordem.getOrdData();
            Date saida = ordem.getOrdEntrega();
            
            preencheCliente();
            preencheCarro();
            preencheServico();
            preencheMecanico();

            cmbOrdem.setValue(ordem);
            cmbCliente.setValue(ordem.getOrdCliente());
            cmbCarro.setValue(ordem.getOrdCarro());
            cmbMecanico.setValue(ordem.getOrdMecanico());
            dtpEntrada.setPromptText(formataData.format(entrada));
            dtpSaida.setPromptText(formataData.format(saida));
           
            txtValor.setText(String.valueOf(ordem.getOrdTotal()));

            tabelaItens.getItems().setAll(ordem.getItensservicosList());

            columnCodigo.setCellValueFactory(new PropertyValueFactory<>("itesId"));
            columnServico.setCellValueFactory(new PropertyValueFactory<>("itesServico"));
            columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("itesQuantidade"));
            columnPreco.setCellValueFactory(new PropertyValueFactory<>("itesValor"));
        } else {
            cmbOrdem.setValue(null);
            cmbCliente.setValue(null);
            cmbCarro.setValue(null);
            cmbMecanico.setValue(null);
            dtpEntrada.setPromptText("");
            dtpSaida.setPromptText("");
            chkStatus.setSelected(false);
            txtValor.setText("");

            tabelaItens.setItems(null);

            columnCodigo.setCellValueFactory(new PropertyValueFactory<>(""));
            columnServico.setCellValueFactory(new PropertyValueFactory<>(""));
            columnQuantidade.setCellValueFactory(new PropertyValueFactory<>(""));
            columnPreco.setCellValueFactory(new PropertyValueFactory<>(""));
        }

    }

    @FXML
    private void removerItens() {
        Itensservicos item = tabelaItens.getSelectionModel().getSelectedItem();
        if (item != null) {
            itensDao.apagar(item.getItesId());
            atualizaTabelaItens();
            alertas("Parabens", "Ordem de serviço excluida!");
        } else {
            alertas("Atenção", "Selecione uma Ordem");
        }
    }

    @FXML
    private void preencheMecanico() {
        mecanicos = mecanicoDao.buscarTodos();
        mecanicosfx = FXCollections.observableArrayList(mecanicos);
        cmbMecanico.setItems(mecanicosfx);
    }

    private void selecionaServico(Servicos servicos) {
//        int qtd = Integer.parseInt(txtQuantidade.getText());
        if (servicos != null) {
            btnRemover.setDisable(true);

        } else {
            btnRemover.setDisable(false);
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
    private void confirmar() {

    }

    @FXML
    private void salvar() throws ParseException {
        Ordemservico ordem = new Ordemservico();
        if (!cmbCliente.getValue().getCliNome().trim().isEmpty() && !cmbCarro.getValue().getCarModelo().trim().isEmpty()) {

            ordem.setOrdCarro(cmbCarro.getValue());
            ordem.setOrdCliente(cmbCliente.getValue());
            ordem.setOrdData(formataData.parse(dtpEntrada.getEditor().getText()));
            ordem.setOrdMecanico(cmbMecanico.getValue());
            ordem.setOrdEntrega(formataData.parse(dtpEntrada.getEditor().getText()));
            ordem.setItensservicosList(ordem.getItensservicosList());

            ordemDao.salvar(ordem);
            ordens.add(ordem);
            cmbOrdem.setValue(ordem);
            limpar();
        } else {
            limpar();
        }

    }

    @FXML
    private void editar() {

    }

    @FXML
    private void excluir() {
    }

    @FXML
    private void limpar() {
        cmbOrdem.setItems(null);
        cmbCarro.setValue(null);
        cmbCliente.setValue(null);
        cmbMecanico.setValue(null);
        dtpEntrada.setPromptText("");
        dtpSaida.setPromptText("");
        txtValor.clear();
        chkStatus.setSelected(false);
        cmbCarro.requestFocus();
    }

    private void selecionaCliente(Cliente cliente) {
        if (cliente != null) {
            Mecanico mec = mecanicoDao.buscarId(cliente.getCliId());
            Carro carro = carroDao.buscarId(cliente.getCliId());

            cmbCliente.setValue(cliente);
            cmbCarro.setValue(carro);

        } else {
            cmbCliente.setValue(null);
            cmbCarro.setValue(null);
        }
    }

}
