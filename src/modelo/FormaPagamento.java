package modelo;

public enum FormaPagamento {
    DINHEIRO("Dinheiro"), CARTAO_CREDITO("Cartão de Crédito"), CARTAO_DEBITO("Cartão de Débito"), CHEQUE("Cheque");
    private final String valor;

    private FormaPagamento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
