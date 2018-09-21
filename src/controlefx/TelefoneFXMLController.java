package controlefx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.Telefone;


public class TelefoneFXMLController implements Initializable {

    @FXML
    private TextField txtCodigo;
    @FXML
    private ComboBox<?> cmbTipo;
    @FXML
    private ComboBox<?> cmbUsuario;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtNumero;
    @FXML
    private TableView<Telefone> tabelaTelefone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void preencheTipo() {
    }

    @FXML
    private void preencheUsuario() {
    }

    @FXML
    private void adicionar() {
    }

    @FXML
    private void alterar() {
    }

    @FXML
    private void excluir() {
    }

    @FXML
    private void cancelar() {
    }
    
}
