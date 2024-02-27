package model;

import exceptions.SaldoInsuficiente;

public interface MetodosBancarios {
    void sacar(double valor) throws SaldoInsuficiente;

    void depositar(double valor);

    void transferir(Conta origem, Conta destino, double valor) throws SaldoInsuficiente;

    void imprimirExtrato();
}