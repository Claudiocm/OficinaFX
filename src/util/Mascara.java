package util;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author estagio
 */
public class Mascara {

    public Mascara() {
    }

    /**
     * Monta a mascara para Data (dd/MM/yyyy).
     *
     * @param textField TextField
     */
    public static void mascaraData(final TextField textField) {
        max(textField, 10);

        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() < 11) {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
                value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
                textField.setText(value);
                positionCaret(textField);
            }
        });
    }
    
    public static void mascaraPlaca(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "###-####";
            String alphaAndDigits = campo.getText().replaceAll("[\\-]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() > number.intValue()) {
                if (campo.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    campo.setText(resultado.toString());
                }
                if (campo.getText().length() > mascara.length()) {
                    campo.setText(campo.getText(0, mascara.length()));
                }
            }
        });
    }

     public static void mascaraCPF(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "###.###.###-##";
            String alphaAndDigits = campo.getText().replaceAll("[\\-\\.]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() > number.intValue()) {
                if (campo.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    campo.setText(resultado.toString());
                }
                if (campo.getText().length() > mascara.length()) {
                    campo.setText(campo.getText(0, mascara.length()));
                }
            }
        });
    }

    public static void mascaraTelefone(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "(##)########";
            String alphaAndDigits = campo.getText().replaceAll("[\\(\\)]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() > number.intValue()) {
                if (campo.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    campo.setText(resultado.toString());
                }
                if (campo.getText().length() > mascara.length()) {
                    campo.setText(campo.getText(0, mascara.length()));
                }
            }
        });
    }

    public static void mascaraCNPJ(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "##.###.###/####-##";
            String alphaAndDigits = campo.getText().replaceAll("[\\/\\-\\.]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() > number.intValue()) {
                if (campo.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    campo.setText(resultado.toString());
                }
                if (campo.getText().length() > mascara.length()) {
                    campo.setText(campo.getText(0, mascara.length()));
                }
            }
        });
    }

    public static void mascaraCEP(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "#####-###";
            String alphaAndDigits = campo.getText().replaceAll("[\\-]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() > number.intValue()) {
                if (campo.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    campo.setText(resultado.toString());
                }
                if (campo.getText().length() > mascara.length()) {
                    campo.setText(campo.getText(0, mascara.length()));
                }
            }
        });
    }

    /**
     * Digitar apenas numeros no campo de texto passado
     *
     * @param campo
     */
    public static void numerico(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> ov, Number t, Number t1) -> {
            if (t1.intValue() > t.intValue()) {
                char ch = campo.getText().charAt(t.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
                    Campo.erro(campo, null);
                }
            }
        });
    }

    /**
     * Digitar apenas numeros e decimais com ponto(.) no campo de texto passado
     *
     * @param campo
     */
    public static void decimal(TextField campo) {
        campo.lengthProperty().addListener((ObservableValue<? extends Number> obs, Number old, Number novo) -> {
            if (novo.intValue() > old.intValue()) {
                char ch = campo.getText().charAt(old.intValue());
                if (!(ch >= '0' && ch <= '9' || ch == '.')) {
                    campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
                    Campo.erro(campo, null);
                }
            }
        });
    }

    /**
     * Digitar apenas letras no campo de texto passado
     *
     * @param campo
     */
    public static void alfabeto(TextField campo) {

        campo.lengthProperty().addListener((ObservableValue<? extends Number> obs, Number old, Number novo) -> {
            if (novo.intValue() > old.intValue()) {
                char ch = campo.getText().charAt(old.intValue());
                if (!(ch >= 'a' && ch <= 'z')) {
                    campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
                    Campo.erro(campo, null);
                }
            }
        });
    }

    /**
     * Limitar o quantidade de caractres do campo de texto
     *
     * @param campo
     * @param maxLength
     */
    public static void max(TextField campo, int maxLength) {
        campo.textProperty().addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
            if (campo.getText().length() > maxLength) {
                campo.setText(campo.getText().substring(0, maxLength));
            }
        });
    }

    /**
     * Devido ao incremento dos caracteres das mascaras eh necessario que o
     * cursor sempre se posicione no final da string.
     *
     * @param textField TextField
     */
    private static void positionCaret(final TextField textField) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Posiciona o cursor sempre a direita.
                textField.positionCaret(textField.getText().length());
            }
        });
    }
}
