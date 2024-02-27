package model;

import exceptions.SaldoInsuficiente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Date;

public abstract class Conta implements MetodosBancarios {
    protected int numero, agencia;
    protected Pessoa titular;
    protected double saldo;
    protected List<Saque> saques = new ArrayList<>();
    protected List<Deposito> depositos = new ArrayList<>();
    protected List<Transferencia> transferencias = new ArrayList<>();
    protected List<Operacao> operacoes = new ArrayList<>();

    public Conta(int numero, int agencia, Pessoa titular) {
        setNumero(numero);
        setAgencia(agencia);
        setTitular(titular);
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getAgencia() {
        return this.agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public Pessoa getTitular() {
        return titular;
    }

    public void setTitular(Pessoa titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Saque> getSaques() {
        return saques;
    }

    public void setSaques(List<Saque> saques) {
        this.saques = saques;
    }

    public List<Deposito> getDepositos() {
        return depositos;
    }

    public void setDepositos(List<Deposito> depositos) {
        this.depositos = depositos;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }

    public void sacar(double valor) throws SaldoInsuficiente {
        if (this.getSaldo() < valor)
            throw new SaldoInsuficiente("Valor de saque excede o saldo da conta.");
        else {
            this.setSaldo(this.getSaldo() - valor);
            System.out.println("Saque de " + valor + " realizado com sucesso. Novo saldo: " + this.getSaldo());
            this.saques.add(new Saque(valor, new Date()));
            this.operacoes.add(saques.get(saques.size() - 1));
        }
    }

    public void depositar(double valor) {
        this.setSaldo(this.getSaldo() + valor);
        System.out.println("Depósito de " + valor + " realizado com sucesso. Novo saldo: " + this.getSaldo());
        this.depositos.add(new Deposito(valor, new Date()));
        this.operacoes.add(depositos.get(depositos.size() - 1));
    }

    public void transferir(Conta origem, Conta destino, double valor) throws SaldoInsuficiente{
        if (this.getSaldo() < valor)
            throw new SaldoInsuficiente("Valor de transferência excede o saldo da conta.");
        else {
            this.setSaldo(this.getSaldo() - valor);
            destino.setSaldo(destino.getSaldo() + valor);
            System.out.println("Transferência de " + valor + " realizada com sucesso. Novo saldo: " + this.getSaldo());
            this.transferencias.add(new Transferencia(valor, new Date(), origem, destino));
            destino.transferencias.add(new Transferencia(valor, new Date(), origem, destino));
            this.operacoes.add(transferencias.get(transferencias.size() - 1));
        }
    }

    public void imprimirExtrato() {
        this.operacoes.sort(Comparator.comparing(Operacao::getData));
        for (Operacao operacao : this.operacoes)
            switch (operacao.getClass().getSimpleName()) {
                case "Saque":
                    System.out.println("Tipo de operação: Saque | Valor: " + operacao.getValor() + " | Data: " + operacao.getData());
                    break;
                case "Deposito":
                    System.out.println("Tipo de operação: Depósito | Valor: " + operacao.getValor() + " | Data: " + operacao.getData());
                    break;
                case "Transferencia":
                    System.out.println("Tipo de operação: Transferência | Valor: " + operacao.getValor() + " | Data: " + operacao.getData() + " | Conta de origem: " + ((Transferencia) operacao).getContaOrigem().getNumero() + " | Conta de destino: " + ((Transferencia) operacao).getContaDestino().getNumero());
                    break;
            }
    }
}
