package model;

import exceptions.SaldoInsuficiente;
import java.util.Date;

public class ContaCorrente extends Conta {
    protected double limiteCredito;

    public ContaCorrente(int numero, int agencia, Pessoa titular, double limiteCredito) {
        super(numero, agencia, titular);
        setLimiteCredito(limiteCredito);
    }

    public double getLimiteCredito() {
        return this.limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficiente {
        double saldoTotal = this.getSaldo() + this.getLimiteCredito();
        if (saldoTotal < valor)
            throw new SaldoInsuficiente("Valor de saque excede o saldo com limite de crédito da conta.");
        else {
            this.setSaldo(this.getSaldo() - valor);
            System.out.println("Saque de " + valor + " realizado com sucesso. Novo saldo: " + this.getSaldo());
            this.saques.add(new Saque(valor, new Date()));
            this.operacoes.add(saques.get(saques.size() - 1));
        }
    }
}
