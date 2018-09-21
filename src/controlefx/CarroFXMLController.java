package controlefx;

import dao.CarroDAO;
import dao.ClienteDAO;
import dao.ItensDAO;
import dao.OrdemDAO;
import dao.ServicoDAO;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import modelo.Carro;
import modelo.Cliente;
import modelo.Itensservicos;
import modelo.Ordemservico;
import modelo.Servicos;
import util.Mascara;

public class CarroFXMLController implements Initializable {

    @FXML
    private TableView<Carro> tabelaCarro;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtChassi;
    @FXML
    private TextField txtAno;
    @FXML
    private ComboBox<Ordemservico> cmbOS;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtValor;
    @FXML
    private Button btnImprirmir;
    private List<Carro> carros;
    private List<Cliente> clientes;
    private List<Servicos> servicos;
    private List<Ordemservico> ordens;
    private ObservableList<Carro> carrofx;
    private ObservableList<Cliente> clientefx;
    private ObservableList<Ordemservico> ordemfx;
    private ObservableList<Servicos> servicofx;
    private OrdemDAO ordemDao = null;
    private CarroDAO carroDao = null;
    private ClienteDAO clienteDao = null;
    private ServicoDAO servicoDao = null;
    @FXML
    private Button btnPesquisar;
    SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
    @FXML
    private TableColumn<Carro, String> columnPlaca;
    @FXML
    private TableColumn<Carro, String> columnModelo;
    @FXML
    private TableColumn<Carro, Integer> columnAno;
    @FXML
    private ComboBox<Cliente> cmbProprietario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ordemDao = new OrdemDAO();
        clienteDao = new ClienteDAO();
        carroDao = new CarroDAO();
        servicoDao = new ServicoDAO();

        txtCodigo.setEditable(false);
        txtPlaca.requestFocus();
        mascaras();
        tabelaCarro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));
        cmbOS.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaOS(newValue));
    }

    @FXML
    private void preencheOS() {
        ordens = ordemDao.buscarTodos();
        ordemfx = FXCollections.observableArrayList(ordens);
        cmbOS.setItems(ordemfx);
    }

    @FXML
    private void impressao() {
    }

    private void preencheTabela() {
        atualizaTabela();
        columnPlaca.setCellValueFactory(new PropertyValueFactory<>("carPlaca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("carModelo"));
        columnAno.setCellValueFactory(new PropertyValueFactory<>("carAno"));
    }

    private void selecionaItemTabela(Carro carro) {
        if (carro != null) {
            Ordemservico ordem = ordemDao.buscarId(carro.getCarId());
            
            txtCodigo.setText(String.valueOf(carro.getCarId()));
            txtModelo.setText(carro.getCarModelo());
            txtMarca.setText(carro.getCarMarca());
            txtChassi.setText(carro.getCarChassi());
            txtAno.setText(String.valueOf(carro.getCarAno()));
            txtPlaca.setText(carro.getCarPlaca());
            cmbProprietario.setValue(carro.getCarCliente());
            cmbOS.setValue(ordem);
            
            btnSalvar.setDisable(true);
        } else {
            cancelar();
        }
    }

    private void limpaTabela() {
        tabelaCarro.getItems().clear();
    }

    @FXML
    private void pesquisar() {
        String placa = txtPlaca.getText();
        //preencheOS();
        
        if (!placa.trim().isEmpty()) {
            carros = carroDao.listarPorPlaca(placa);
            carrofx = FXCollections.observableArrayList(carros);

            for (Carro c : carros) {
                if (c.getCarPlaca().toUpperCase().contains(placa)) {
                    //preencheOS();
                    Ordemservico ordem = ordemDao.buscarId(c.getCarId());
                    
                    txtCodigo.setText(String.valueOf(c.getCarId()));
                    txtModelo.setText(c.getCarModelo());
                    txtMarca.setText(c.getCarMarca());
                    txtChassi.setText(c.getCarChassi());
                    txtAno.setText(String.valueOf(c.getCarAno()));
                    txtPlaca.setText(c.getCarPlaca());
                    cmbProprietario.setValue(c.getCarCliente());
                    cmbOS.setValue(ordem);
                    
                    tabelaCarro.getItems().clear();
                    tabelaCarro.getItems().setAll(carros);
                    
                    columnPlaca.setCellValueFactory(new PropertyValueFactory<>("carPlaca"));
                    columnModelo.setCellValueFactory(new PropertyValueFactory<>("carModelo"));
                    columnAno.setCellValueFactory(new PropertyValueFactory<>("carAno"));

                } else {
                    //JOptionPane.showMessageDialog(null, "Tag não encontrada!");
                    limpaTabela();
                    cancelar();
                    alertas("Informação!", "Veículo não encontrado");
                    txtPlaca.requestFocus();
                }
            }

        } else {
            limpaTabela();
            cancelar();
            alertas("Informação!", "Insira a placa do carro");
        }
    }

    @FXML
    private void salvarCarro() {
        Carro carro = new Carro();
        String modelo = txtModelo.getText();
        String placa = txtPlaca.getText();

        if (!placa.trim().isEmpty() || !modelo.trim().isEmpty()) {

            carro.setCarMarca(txtMarca.getText());
            carro.setCarModelo(modelo);
            carro.setCarPlaca(placa);
            carro.setCarChassi(txtChassi.getText());
            carro.setCarAno(Integer.parseInt(txtAno.getText()));
            carro.setCarCliente(cmbProprietario.getValue());
            carroDao.salvar(carro);
            carros.add(carro);
            tabelaCarro.getItems().add(carro);
            atualizaTabela();
            cancelar();
            alertas("Parabéns", "Carro adicionado com sucesso!");
        } else {
            if(placa.trim().isEmpty()){
                txtPlaca.setText("Preencha com a placa do veículo");
                txtPlaca.requestFocus();
            }
            if(modelo.trim().isEmpty()){
               txtModelo.setText("Preencha com o modelo do veículo");
               txtModelo.requestFocus();
            }
            alertas("Atenção", "Preencha os campos vazios!");
        }
    }

    @FXML
    private void excluirCarro() {
        Carro carro = tabelaCarro.getSelectionModel().getSelectedItem();
       
        if (carro != null) {
            carroDao.apagar(carro.getCarId());
            atualizaTabela();
            cancelar();
            alertas("Parabens", "Carro excluido com sucesso");
        } else {
            alertas("Atenção", "Selecione um carro");
        }
    }

    @FXML
    private void AtualizarCarro() {
        Carro carro = tabelaCarro.getSelectionModel().getSelectedItem();

        if (carro != null) {

            carro.setCarMarca(txtMarca.getText());
            carro.setCarModelo(txtModelo.getText());
            carro.setCarPlaca(txtPlaca.getText());
            carro.setCarChassi(txtChassi.getText());
            carro.setCarAno(Integer.parseInt(txtAno.getText()));
            carro.setCarCliente(cmbProprietario.getValue());
            
            carroDao.atualizar(carro);
            tabelaCarro.getItems().setAll(carros);
            atualizaTabela();
            cancelar();
            alertas("Parabens", "Carro atualizado com sucesso");
        } else {
            alertas("Atenção", "Selecione um carro");
        }
    }

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtModelo.clear();
        txtMarca.clear();
        txtChassi.clear();
        txtAno.clear();
        txtPlaca.clear();
        cmbProprietario.getItems().clear();
        txtData.clear();
        txtValor.clear();
        selecionaOS(null);
        cmbOS.getItems().clear();
        tabelaCarro.getSelectionModel().clearSelection();
        btnSalvar.setDisable(false);
        txtPlaca.requestFocus();
    }

    public void alertas(String titulo, String frase) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }

    private void selecionaOS(Ordemservico ordem) {
        //ordem = cmbOS.getSelectionModel().getSelectedItem();
        if (ordem != null) {
            txtValor.setText(String.valueOf(ordem.getOrdTotal()));
            txtData.setText(formataData.format(ordem.getOrdData()));

        } else {

        }
    }
   
    private void mascaras() {
        Mascara.mascaraData(txtData);
        Mascara.numerico(txtAno);
        Mascara.mascaraPlaca(txtPlaca);
    }

    private void atualizaTabela() {
        carros = carroDao.buscarTodos();
        carrofx = FXCollections.observableArrayList(carros);
        tabelaCarro.getItems().setAll(carrofx);
    }

    @FXML
    private void preencheProprietario() {
        clientes = clienteDao.buscarTodos();
        clientefx = FXCollections.observableArrayList(clientes);
        cmbProprietario.setItems(clientefx);
    }
}
