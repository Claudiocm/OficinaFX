package controlefx;

import dao.OficinaDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author estagio
 */
public class OficinaFXMLController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MenuItem menuCliente;
    @FXML
    private MenuItem menuMecanico;
    @FXML
    private MenuItem menuCarro;
    @FXML
    private MenuItem menuFornecedor;
    @FXML
    private MenuItem menuInsumos;
    @FXML
    private MenuItem menuProdutos;
    @FXML
    private MenuItem menuServico;
    @FXML
    private MenuItem menuVenda;
    @FXML
    private MenuItem menuOrdemservico;
    @FXML
    private MenuItem menuCaixa;
    @FXML
    private MenuItem menuFechamentos;
    @FXML
    private MenuItem menuSair;
    private OficinaDAO oficina = null;
    @FXML
    private MenuItem menuRelatorio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oficina = new OficinaDAO();
        oficina.open();
    }

    @FXML
    private void fecharTela(ActionEvent event) {
        oficina.close();
        System.exit(0);
    }

    @FXML
    private void acessaCliente(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/ClienteFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Cliente");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaMecanico(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/MecanicoFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Mecanicos");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaCarro(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/CarroFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Carros");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaServico(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/ServicoFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Serviços");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaFornecedor(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/FornecedorFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Fornecedor");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaInsumos(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/InsumoFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Insumos");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaProduto(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/ProdutoFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Cadastro de Produtos");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaVenda(ActionEvent event) {
    }

    @FXML
    private void acessaOrdem(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/OrdemFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Ordem de Serviços");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaCaixa(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/tela/CaixaD3FXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Caixa de venda");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void acessaFechamentoCaixa(ActionEvent event) {
    }

    @FXML
    private void acessaRelatorio(ActionEvent event)throws IOException {
         Parent parent = FXMLLoader.load(getClass().getResource("/tela/RelatorioProdutofornecedorFXML.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Caixa de venda");
        stage.setScene(scene);
        stage.show();
    }

}
