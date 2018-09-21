package controlefx;

import dao.MecanicoDAO;
import dao.TelefoneDAO;
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
import modelo.Mecanico;
import modelo.Telefone;
import util.Mascara;

public class MecanicoFXMLController implements Initializable {

    @FXML
    private TableView<Mecanico> tabelaMecanico;
    @FXML
    private TableColumn<Mecanico, Integer> columnCodigo;
    @FXML
    private TableColumn<Mecanico, String> columnNome;
    @FXML
    private TableColumn<Mecanico, String> columnLogin;
    private List<Mecanico> mecanicos;
    private List<Telefone> telefones;
    private ObservableList<Mecanico> mecanicofx;
    private ObservableList<Telefone> telefonefx;
    private MecanicoDAO mecanicoDao = null;
    private TelefoneDAO telefoneDao = null;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtTipo;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnImprimir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mecanicoDao = new MecanicoDAO();
        telefoneDao = new TelefoneDAO();

        txtCodigo.setEditable(false);
        Mascara.mascaraTelefone(txtTelefone);
        txtSenha.setEditable(false);
        preencheTabela();
        tabelaMecanico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionaItemTabela(newValue));

    }

    private void selecionaItemTabela(Mecanico mecanico) {
        if (mecanico != null) {
            Telefone tel = telefoneDao.buscarId(mecanico.getMecId());
            
            txtCodigo.setText(String.valueOf(mecanico.getMecId()));
            txtNome.setText(mecanico.getMecNome());
            txtLogin.setText(mecanico.getMecLogin());
            txtSenha.setText(mecanico.getMecSenha());
            txtTelefone.setText(tel.getTelNumero());
            txtTipo.setText(tel.getTelTipo());
            btnSalvar.setDisable(true);
        } else {
            cancelar();
        }

    }

    private void preencheTabela() {
        atualizaTabela();
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("mecId"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("mecNome"));
        columnLogin.setCellValueFactory(new PropertyValueFactory<>("mecLogin"));
    }

    private void atualizaTabela() {
        mecanicos = mecanicoDao.buscarTodos();
        mecanicofx = FXCollections.observableArrayList(mecanicos);
        tabelaMecanico.setItems(mecanicofx);
    }

    @FXML
    private void salvarMecanico() {
    }

    @FXML
    private void excluirMecanico() {
    }

    @FXML
    private void atualizarMecanico() {
    }

    @FXML
    private void cancelar() {
        txtCodigo.clear();
        txtNome.clear();
        txtLogin.clear();
        txtSenha.clear();
        txtTelefone.clear();
        txtTipo.clear();
        txtNome.requestFocus();
        btnSalvar.setDisable(false);
        
    }
    
    public void alertas(String titulo, String frase) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(frase);
        alert.showAndWait();
    }
    
    

}
