package model;

import java.util.Date;

public class Transferencia extends Operacao {
    Conta contaOrigem, contaDestino;
    public Transferencia(double valor, Date data, Conta contaOrigem, Conta contaDestino) {
        super(valor, data);
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }
}
