package model;

import exceptions.SaldoInsuficiente;

import java.util.Date;

public class ContaPoupanca extends Conta {
    protected final double taxaJuros = 0.02;
    protected final double bonusAniversario = 0.05;

    public ContaPoupanca(int numero, int agencia, Pessoa titular) {
        super(numero, agencia, titular);
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficiente {
        if (this.getSaldo() < valor)
            throw new SaldoInsuficiente("Saldo insuficiente para realizar saque.");
        else {
            this.setSaldo(this.getSaldo() - valor);
            valor -= valor * (1 - taxaJuros);
            System.out.println("Saque de " + valor + " realizado com sucesso (juros aplicados). Novo saldo: " + this.getSaldo());
            this.saques.add(new Saque(valor, new Date()));
            this.operacoes.add(saques.get(saques.size() - 1));
        }
    }

    @Override
    public void depositar(double valor) {
        this.setSaldo(this.getSaldo() + valor);
        System.out.println("Depósito de " + valor + " realizado com sucesso. Novo saldo: " + this.getSaldo());
        this.depositos.add(new Deposito(valor, new Date()));
        this.operacoes.add(depositos.get(depositos.size() - 1));
    }

    public void aplicarRendimento() {
        double rendimento = this.getSaldo() * bonusAniversario;
        this.setSaldo(this.getSaldo() + rendimento);
        System.out.println("Rendimento de " + rendimento + " aplicado com sucesso. Novo saldo: " + this.getSaldo());
    }
}