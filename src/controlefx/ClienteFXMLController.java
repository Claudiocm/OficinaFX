package controlefx;

import dao.CarroDAO;
import dao.ClienteDAO;
import dao.TelefoneDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Carro;
import modelo.Cliente;
import modelo.Telefone;
import util.Mascara;

public class ClienteFXMLController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCPF;
    @FXML
    private TextField txtCNPJ;
    @FXML
    private ComboBox<Telefone> cmbTelefone;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtEstado;
    @FXML
    private DatePicker dtpDataCadastro;
    @FXML
    private TextField txtBairro;
    @FXML
    private TextField txtCidade;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TableView<Cliente> tabelaCliente;
    @FXML
    private TableColumn<Cliente, Integer> columnCodigo;
    @FXML
    private TableColumn<Cliente, String> columnNome;
    @FXML
    private TableColumn<Cliente, String> columnCPF;
    @FXML
    private TableColumn<Cliente, String> columnCidade;
    @FXML
    private Button btnImprimir;
    private List<Cliente> clientes;
    private List<Carro> carros;
    private ObservableList<Cliente> clientefx;
    private ObservableList<Carro> carrofx;
    private ObservableList<Telefone> telefonefx;
    private List<Telefone> telefones;
    private ClienteDAO clienteDao = null;
    private TelefoneDAO telefoneDao = null;
    private CarroDAO carroDao = null;
    private final SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat datas = new SimpleDateFormat("yyyy/MM/dd");
    @FXML
    private TextField txtCEP;
    @FXML
    private TableView<Carro> tabelaCarro;
    @FXML
    private TableColumn<Carro, String> columnPlaca;
    @FXML
    private TableColumn<Carro, String> columnModelo;
    @FXML
    private TableColumn<Carro, String> columnMarca;
    @FXML
    private TableColumn<Carro, Integer> columnAno;
    @FXML
    private TextField txtTipo;
    @FXML
    private TextField txtNumero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDao = new ClienteDAO();
        telefoneDao = new TelefoneDAO();
        carroDao = new CarroDAO();

        preencheTabela();
        //preencheTelefone();
        mascaras();
        txtCodigo.setEditable(false);
        dtpDataCadastro.setValue(LocalDate.now());
        tabelaCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));
    }

    @FXML
    private void preencheTelefone() {
        telefones = telefoneDao.buscarTodos();
        telefonefx = FXCollections.observableArrayList(telefones);
        cmbTelefone.setItems(telefonefx);
    }

    private void preencheCarro() {
        atualizaTabelaCarro();

        columnPlaca.setCellValueFactory(new PropertyValueFactory<>("carPlaca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("carModelo"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("carMarca"));
        columnAno.setCellValueFactory(new PropertyValueFactory<>("carAno"));
    }

    private void atualizaTabelaCarro() {
        carros = carroDao.buscarTodos();
        carrofx = FXCollections.observableArrayList(carros);
        tabelaCarro.getItems().setAll(carrofx);
    }

    @FXML
    private void salvarCliente() throws ParseException {
        Cliente cliente = new Cliente();
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();

        if (!nome.trim().isEmpty() || !cpf.trim().isEmpty()) {
            Telefone telefone = new Telefone();
            Date datacadastro = data.parse(dtpDataCadastro.getEditor().getText());

            cliente.setCliNome(nome);
            cliente.setCliCpf(cpf);
            cliente.setCliCnpj(txtCNPJ.getText());
            cliente.setCliDatacadastro(datacadastro);
            cliente.setCliBairro(txtBairro.getText());
            cliente.setCliCidade(txtCidade.getText());
            cliente.setCliEstado(txtEstado.getText());
            cliente.setCliCep(txtCEP.getText());
            cliente.setCliLogradouro(txtEndereco.getText());

            clientes.add(cliente);
            clienteDao.salvar(cliente);
            telefone.setTelClienteId(cliente);
            telefone.setTelNumero(txtNumero.getText());
            telefone.setTelTipo(txtTipo.getText());
            telefoneDao.salvar(telefone);
            telefones.add(telefone);
            
            tabelaCliente.getItems().add(cliente);
            atualizarTabela();
            cmbTelefone.getItems().setAll(telefones);
            cancelar();
            alertas("Parabens!", "Cliente Atualizado com sucesso!");
        } else {
            if (nome.trim().isEmpty()) {
                txtNome.setText("Nome está vazio");
                txtNome.requestFocus();
            }
            if (cpf.trim().isEmpty()) {
                txtCPF.setText("CPF está vazio!");
                txtCPF.requestFocus();
            }
            alertas("Atenção", "Preencha os campos vazios!");
        }
    }

    @FXML
    private void atualizarCliente() throws ParseException {
        Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();

        if (cliente != null) {
            Telefone telefone = telefoneDao.buscarId(cliente.getCliId());
            //Date datacadastro = data.parse(dtpDataCadastro.getEditor().getText());

            cliente.setCliNome(txtNome.getText());
            cliente.setCliCpf(txtCPF.getText());
            cliente.setCliCnpj(txtCNPJ.getText());
            cliente.setCliDatacadastro(data.parse(dtpDataCadastro.getPromptText()));
            cliente.setCliBairro(txtBairro.getText());
            cliente.setCliCidade(txtCidade.getText());
            cliente.setCliEstado(txtEstado.getText());
            cliente.setCliCep(txtCEP.getText());
            cliente.setCliLogradouro(txtEndereco.getText());

            clienteDao.atualizar(cliente);
            telefone.setTelClienteId(cliente);
            telefone.setTelNumero(txtNumero.getText());
            telefone.setTelTipo(txtTipo.getText());
            telefoneDao.atualizar(telefone);
            tabelaCliente.getItems().setAll(clientes);

            //preencheTelefone();
            atualizarTabela();
            cancelar();
            alertas("Parabens!", "Cliente Atualizado com sucesso!");
        } else {
            alertas("Atenção", "Selecione um cliente!");
        }
    }

    @FXML
    private void excluirCliente() throws ParseException {
        Cliente cliente = tabelaCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            clienteDao.apagar(cliente.getCliId());
            tabelaCliente.getItems().setAll(clientes);
            tabelaCarro.getSelectionModel().clearSelection();
            atualizarCliente();
            cancelar();
            alertas("Parabens!", "Cliente excluído com sucesso!");
        } else {
            alertas("Atenção", "Selecione um cliente!");
        }
    }

    private void mascaras() {
        Mascara.numerico(txtCodigo);
        Mascara.mascaraCNPJ(txtCNPJ);
        Mascara.mascaraCPF(txtCPF);
        Mascara.mascaraCEP(txtCEP);
        Mascara.max(txtEstado, 2);

        Mascara.mascaraTelefone(txtNumero);
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
        dtpDataCadastro.setPromptText("");
        txtCNPJ.clear();
        txtCPF.clear();
        txtEndereco.clear();
        txtCidade.clear();
        txtBairro.clear();
        txtEstado.clear();
        txtCEP.clear();
        txtNumero.clear();
        txtTipo.clear();
        cmbTelefone.getItems().clear();
        btnSalvar.setDisable(false);
        tabelaCliente.getSelectionModel().clearSelection();
        tabelaCarro.getItems().clear();
        txtNome.requestFocus();
    }

    private void selecionaItemTabela(Cliente cliente) {
        dtpDataCadastro.setValue(null);

        if (cliente != null) {

            btnSalvar.setDisable(true);
            Telefone telefone = telefoneDao.buscarId(cliente.getCliId());
            Carro carro = carroDao.buscarId(cliente.getCliId());
            String cadastro = data.format(cliente.getCliDatacadastro());

            txtCodigo.setText(String.valueOf(cliente.getCliId()));
            txtNome.setText(cliente.getCliNome());
            dtpDataCadastro.setPromptText(cadastro);
            if (!txtCNPJ.getText().trim().isEmpty()) {
                txtCNPJ.setText(cliente.getCliCnpj());
            } else {
                txtCPF.setText(cliente.getCliCpf());
            }
            txtEndereco.setText(cliente.getCliLogradouro());
            txtCidade.setText(cliente.getCliCidade());
            txtBairro.setText(cliente.getCliBairro());
            txtEstado.setText(cliente.getCliEstado());
            txtCEP.setText(cliente.getCliCep());

            if (telefone != null) {
//                txtNumero.setText(telefone.getTelNumero());
//                txtTipo.setText(telefone.getTelTipo());
                cmbTelefone.getItems().setAll(cliente.getTelefoneList());
            } else {
//                txtNumero.clear();
//                txtTipo.clear();
                cmbTelefone.getItems().clear();
            }

            if (carro != null) {
                tabelaCarro.getItems().clear();
                tabelaCarro.getItems().setAll(carro.getCarCliente().getCarroList());
                columnPlaca.setCellValueFactory(new PropertyValueFactory<>("carPlaca"));
                columnModelo.setCellValueFactory(new PropertyValueFactory<>("carModelo"));
                columnMarca.setCellValueFactory(new PropertyValueFactory<>("carMarca"));
                columnAno.setCellValueFactory(new PropertyValueFactory<>("carAno"));
                //preencheCarro();

            } else {
                tabelaCarro.getItems().clear();
                columnPlaca.setCellValueFactory(new PropertyValueFactory<>(""));
                columnModelo.setCellValueFactory(new PropertyValueFactory<>(""));
                columnMarca.setCellValueFactory(new PropertyValueFactory<>(""));
                columnAno.setCellValueFactory(new PropertyValueFactory<>(""));
            }
        } else {
            cancelar();
        }
    }

    private void atualizarTabela() {
        clientes = clienteDao.buscarTodos();
        clientefx = FXCollections.observableArrayList(clientes);
        tabelaCliente.getItems().setAll(clientefx);
    }

    private void preencheTabela() {
        atualizarTabela();

        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("cliId"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("cliNome"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("cliCpf"));
        columnCidade.setCellValueFactory(new PropertyValueFactory<>("cliCidade"));
    }

}
