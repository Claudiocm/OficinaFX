package util;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Campo {
    private Campo() {
    }

    /**
     * Não permitir que campos de textos com valores nulos
     */
    public static boolean noEmpty(TextField... field) {

        boolean vazio = false;

        for (TextField campo : field) {
            if (campo.getText().trim().isEmpty()) {
                erro(campo, "Verificar valor vazio!");
                vazio = true;
            }
        }

        return vazio;
    }

    /**
     * Limpar textos dos campos informados
     */
    public static void limpar(TextField... no) {
        for (TextField campo : no) {
            campo.setText("");
        }
    }

    /**
     * Limpar textos dos labels informados
     */
    public static void limpar(Label... no) {
        for (Label campo : no) {
            campo.setText("");
        }
    }
    
     /**
     * Indicador erro no campo informado com borda vermelha
     */
    public static void erro(Node no, String mensagem) {
        try {
            if (no != null) {
                no.setStyle("-fx-border-color: #ff7575;");
                origem(no);
            }
        } catch (Exception ex) {
          
        }
    }

    /**
     * Ao clicar no campo voltar ao estilo padrão do campo
     */
    private static void origem(Node no) {
        no.setOnMouseClicked((MouseEvent me) -> {
            no.setStyle("-fx-border-color: #eaeaea;");
        });
    }
}
