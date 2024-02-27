package model;

import java.util.Date;

public abstract class Operacao {
    protected double valor;
    protected Date data;

    public Operacao(double valor, Date data) {
        setValor(valor);
        setData(data);
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}