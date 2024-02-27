import exceptions.NomeInvalido;
import exceptions.NumeroInvalido;
import model.*;
import service.*;

import static service.ValidadorEntrada.validarNumero;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class SistemaBancario {
    static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("1. Criar Conta Poupança");
        System.out.println("2. Criar Conta Corrente");
        System.out.println("3. Sacar");
        System.out.println("4. Depositar");
        System.out.println("5. Transferir");
        System.out.println("6. Imprimir Extrato");
        System.out.println("7. Buscar Conta");
        System.out.println("8. Exibir Todas as Contas");
        System.out.println("9. Editar Informações da Conta");
        System.out.println("10. Excluir Conta");
        System.out.println("11. Sair");
    }

    public static Conta buscarConta(List<Conta> listaDeContas) {
        String entrada = "";
        int numero = 0, agencia = 0;

        while (true) {
            try {
                System.out.print("Digite o número da agência: ");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 1))
                    throw new NumeroInvalido("Número da agência deve ser número inteiro positivo.");
                agencia = Integer.parseInt(entrada);
                break;
            } catch (Exception e) {
                System.out.println("Erro de identificação de conta: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Digite o número da conta: ");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 1))
                    throw new NumeroInvalido("Número da conta deve ser número inteiro positivo.");
                numero = Integer.parseInt(entrada);
                break;
            } catch (Exception e) {
                System.out.println("Erro de identificação de conta: " + e.getMessage());
            }
        }

        for (Conta conta : listaDeContas)
            if (conta.getAgencia() == agencia && conta.getNumero() == numero) return conta;

        return null;
    }

    public static Conta criarConta(int numero, int tipo) {
        String entrada = "", nomeTitular = "", cpf = "";
        int agencia = 0;

        System.out.println("Número da conta: " + numero);

        while (true) {
            try {
                System.out.print("Digite o número da agência: ");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 1))
                    throw new NumeroInvalido("Valor digitado não é um número inteiro positivo.");
                else agencia = Integer.parseInt(entrada);
                break;
            } catch (Exception e) {
                System.out.println("Erro ao criar conta: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Digite o nome do titular: ");
                nomeTitular = scanner.nextLine();
                if (nomeTitular.length() < 3)
                    throw new NomeInvalido("Nome do titular deve ter no mínimo 3 caracteres.");
                break;
            } catch (Exception e) {
                System.out.println("Erro ao criar conta: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Digite o CPF do titular: ");
                cpf = scanner.nextLine();
                if (!ValidadorEntrada.validarCPF(cpf))
                    throw new NumeroInvalido("CPF inválido.");
                break;
            } catch (Exception e) {
                System.out.println("Erro ao criar conta: " + e.getMessage());
            }
        }

        Pessoa titular = new Pessoa(nomeTitular, cpf);

        if (tipo == 1) {
            System.out.println("Conta Poupança criada com sucesso.");
            return new ContaPoupanca(numero, agencia, titular);
        } else {
            while (true) {
                try {
                    System.out.print("Digite o limite de crédito da conta corrente: ");
                    entrada = scanner.nextLine();
                    if (!validarNumero(entrada, 2) || Double.parseDouble(entrada) <= 0)
                        throw new NumeroInvalido("Valor digitado não é um número real positivo.");
                    double limiteCredito = Double.parseDouble(entrada);
                    System.out.println("Conta Corrente criada com sucesso.");
                    return new ContaCorrente(numero, agencia, titular, limiteCredito);
                } catch (Exception e) {
                    System.out.println("Erro ao criar conta: " + e.getMessage());
                }
            }
        }
    }

    public static void sacar(Conta conta) {
        String entrada = "";
        double valor = 0;

        while (true) {
            try {
                System.out.print("Digite o valor do saque: ");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 2))
                    throw new NumeroInvalido("Valor de saque deve ser um número real positivo.");
                valor = Double.parseDouble(entrada);
                break;
            } catch (Exception e) {
                System.out.println("Erro de valor de saque: " + e.getMessage());
            }
        }

        try {
            conta.sacar(valor);
        } catch (Exception e) {
            System.out.println("Erro de saque: " + e.getMessage());
        }
    }

    public static void depositar(Conta conta) {
        Scanner scanner = new Scanner(System.in);
        String entrada = "";
        double valor = 0;

        while (true) {
            try {
                System.out.print("Digite o valor do depósito: ");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 2))
                    throw new NumeroInvalido("Valor de depósito não é um número decimal positivo.");
                valor = Double.parseDouble(entrada);
                break;
            } catch (Exception e) {
                System.out.println("Erro de valor de depósito: " + e.getMessage());
            }

            try {
                conta.depositar(valor);
            } catch (Exception e) {
                System.out.println("Erro de depósito: " + e.getMessage());
            }
        }
    }

    public static void transferir(Conta contaOrigem, Conta contaDestino) {
        Scanner scanner = new Scanner(System.in);
        String entrada = "";
        double valor = 0;

        while (true) {
            try {
                System.out.print("Digite o valor da transferência: ");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 2))
                    throw new NumeroInvalido("Valor de transferência não é um número real positivo.");
                valor = Double.parseDouble(entrada);
                break;
            } catch (Exception e) {
                System.out.println("Erro de valor de transferência: " + e.getMessage());
            }
        }

        try {
            contaOrigem.transferir(contaOrigem, contaDestino, valor);
        } catch (Exception e) {
            System.out.println("Erro de transferência: " + e.getMessage());
        }
    }

    public static void exibirTodasAsContas(List<Conta> listaDeContas) {
        if (listaDeContas.isEmpty()) System.out.println("Nenhuma conta cadastrada.");
        else {
            System.out.println("Contas cadastradas: " + listaDeContas.size() + " conta(s).");
            System.out.println("Informações das contas: ");
            for (Conta conta : listaDeContas) {
                if (conta instanceof ContaCorrente)
                    System.out.println("Número: " + conta.getNumero() + "Tipo: " + " | Agência: " + conta.getAgencia() + " | Saldo: " + conta.getSaldo() + " | Limite de Crédito: " + ((ContaCorrente) conta).getLimiteCredito());
                else
                    System.out.println("Número: " + conta.getNumero() + " | Agência: " + conta.getAgencia() + " | Saldo: " + conta.getSaldo());
            }
        }
    }

    public static void editarInformacoesDaConta(Conta conta) throws NumeroInvalido {
        String entrada = "";
        int opcao = 0;

        do {
            System.out.println("1. Editar número da agência");
            System.out.println("2. Editar titular da conta");
            if (conta instanceof ContaCorrente) {
                System.out.println("3. Editar limite de crédito");
                System.out.println("4. Voltar");
            } else System.out.println("3. Voltar");

            while (true) {
                try {
                    System.out.print("Digite a opção desejada: ");
                    entrada = scanner.nextLine();
                    if (!validarNumero(entrada, 1) || Integer.parseInt(entrada) < 1 || Integer.parseInt(entrada) > 4)
                        throw new NumeroInvalido("Valor digitado não é um número inteiro positivo.");
                    opcao = Integer.parseInt(entrada);
                    break;
                } catch (Exception e) {
                    System.out.println("Erro de edição de informações da conta: " + e.getMessage());
                }
            }

            switch (opcao) {
                case 1:
                    while (true) {
                        System.out.println("1. Editar número da agência");
                        try {
                            System.out.print("Digite o novo número da agência: ");
                            entrada = scanner.nextLine();
                            if (!validarNumero(entrada, 1))
                                throw new NumeroInvalido("Número da agência deve ser um número inteiro positivo.");
                            conta.setAgencia(Integer.parseInt(entrada));
                            break;
                        } catch (Exception e) {
                            System.out.println("Erro de edição de número da agência: " + e.getMessage());
                        }
                    }
                    break;

                case 2:
                    System.out.println("2. Editar titular da conta");
                    String nomeTitular = "", cpf = "";
                    while (true) {
                        try {
                            System.out.print("Digite o novo nome do titular: ");
                            nomeTitular = scanner.nextLine();
                            if (nomeTitular.length() < 3)
                                throw new NumeroInvalido("Nome do titular deve ter no mínimo 3 caracteres.");
                            break;
                        } catch (Exception e) {
                            System.out.println("Erro de edição de nome do titular: " + e.getMessage());
                        }
                    }

                    while (true) {
                        try {
                            System.out.print("Digite o novo CPF do titular: ");
                            cpf = scanner.nextLine();
                            if (!ValidadorEntrada.validarCPF(cpf)) throw new NumeroInvalido("CPF inválido.");
                            break;
                        } catch (Exception e) {
                            System.out.println("Erro de edição de CPF do titular: " + e.getMessage());
                        }
                    }

                    conta.getTitular().setNome(nomeTitular);
                    conta.getTitular().setCPF(cpf);

                case 3:
                    if (conta instanceof ContaCorrente) {
                        System.out.println("3. Editar limite de crédito");
                        while (true) {
                            try {
                                System.out.println("Digite o novo limite de crédito: ");
                                entrada = scanner.nextLine();
                                if (!validarNumero(entrada, 2) || Double.parseDouble(entrada) <= 0)
                                    throw new NumeroInvalido("Valor digitado não é um número real positivo.");
                                ((ContaCorrente) conta).setLimiteCredito(Double.parseDouble(entrada));
                                break;
                            } catch (Exception e) {
                                System.out.println("Erro de edição de limite de crédito: " + e.getMessage());
                            }
                        }
                    } else {
                        opcao = 4;
                        System.out.println("3. Voltar");
                    }
                    break;

                case 4:
                    System.out.println("4. Voltar");
                    break;
            }
        } while (opcao != 4);
    }

    public static void excluirConta(List<Conta> listaDeContas, Conta conta) {
        String entrada = "";
        int opcao = 0;

        while (true) {
            try {
                System.out.println("Deseja realmente excluir a conta? (1. Sim / 2. Não)");
                entrada = scanner.nextLine();
                if (!validarNumero(entrada, 1) || Integer.parseInt(entrada) < 1 || Integer.parseInt(entrada) > 2)
                    throw new NumeroInvalido("Valor digitado não é um número inteiro positivo.");
                opcao = Integer.parseInt(entrada);
                if (opcao == 1) {
                    listaDeContas.remove(conta);
                    System.out.println("Conta excluída com sucesso.");
                    break;
                } else if (opcao == 2) {
                    System.out.println("Exclusão de conta cancelada.");
                    break;
                } else throw new NumeroInvalido("Opção inválida.");
            } catch (Exception e) {
                System.out.println("Erro de exclusão de conta: " + e.getMessage());
            }
        }
    }

    public static void verificaRendimentos(List<Conta> listaDeContas) {
        for (Conta conta : listaDeContas) {
            if (conta instanceof ContaPoupanca) {
                List<Operacao> operacoes = conta.getOperacoes();
                if (!operacoes.isEmpty()) {
                    for (int i = 0; i < operacoes.size(); i++) {
                        if (operacoes.get(i) instanceof Deposito) {
                            Date dataDeposito = operacoes.get(i).getData(), dataAtual = new Date(), dataSaque = null;
                            long diferencaDataAtual = dataAtual.getTime() - dataDeposito.getTime(), diferencaDataSaque = 0;
                            if (diferencaDataAtual >= 15000) {
                                for (int j = i + 1; j < operacoes.size(); j++) {
                                    if (operacoes.get(j) instanceof Saque) {
                                        dataSaque = operacoes.get(j).getData();
                                        diferencaDataSaque = dataSaque.getTime() - dataDeposito.getTime();
                                        break;
                                    }
                                }
                                if (dataSaque == null || diferencaDataSaque >= 15000)
                                    ((ContaPoupanca) conta).aplicarRendimento();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws NumeroInvalido {
        List<Conta> listaDeContas = new ArrayList<>();
        Conta conta = null;

        int opcao = 0;

        do {
            verificaRendimentos(listaDeContas);
            exibirMenu();

            while (true) {
                try {
                    System.out.print("Digite a opção desejada: ");
                    String entrada = scanner.nextLine();
                    if (!validarNumero(entrada, 1) || Integer.parseInt(entrada) < 1 || Integer.parseInt(entrada) > 11)
                        throw new NumeroInvalido("Valor digitado não é um número inteiro positivo.");
                    opcao = Integer.parseInt(entrada);
                    break;
                } catch (Exception e) {
                    System.out.println("Erro de opção: " + e.getMessage());
                }
            }

            switch (opcao) {
                case 1:
                    System.out.println("1. Criar Conta Poupança");
                    conta = criarConta(listaDeContas.size() + 1, 1);
                    listaDeContas.add(conta);
                    break;

                case 2:
                    System.out.println("2. Criar Conta Corrente");
                    conta = criarConta(listaDeContas.size() + 1, 2);
                    listaDeContas.add(conta);
                    break;

                case 3:
                    System.out.println("3. Sacar");
                    if ((conta = buscarConta(listaDeContas)) != null) sacar(conta);
                    else System.out.println("Conta não encontrada.");
                    break;

                case 4:
                    System.out.println("4. Depositar");
                    if ((conta = buscarConta(listaDeContas)) != null) depositar(conta);
                    else System.out.println("Conta não encontrada.");
                    break;

                case 5:
                    System.out.println("5. Transferir");
                    Conta contaOrigem = null, contaDestino = null;

                    System.out.println("Conta de origem: ");
                    if ((contaOrigem = buscarConta(listaDeContas)) != null) {
                        System.out.println("Conta de destino: ");
                        if ((contaDestino = buscarConta(listaDeContas)) != null)
                            transferir(contaOrigem, contaDestino);
                        else System.out.println("Conta de destino não encontrada.");
                    } else System.out.println("Conta de origem não encontrada.");
                    break;

                case 6:
                    System.out.println("6. Imprimir Extrato");
                    if ((conta = buscarConta(listaDeContas)) != null) conta.imprimirExtrato();
                    else System.out.println("Conta não encontrada.");
                    break;

                case 7:
                    System.out.println("7. Buscar Conta");
                    if ((conta = buscarConta(listaDeContas)) != null) {
                        System.out.println("Conta encontrada.");
                        if (conta instanceof ContaCorrente)
                            System.out.println("Número: " + conta.getNumero() + " | Tipo: Corrente | Agência: " + conta.getAgencia() + " | Saldo: " + conta.getSaldo() + " | Limite de Crédito: " + ((ContaCorrente) conta).getLimiteCredito());
                        else
                            System.out.println("Número: " + conta.getNumero() + " | Tipo: Poupança | Agência: " + conta.getAgencia() + " | Saldo: " + conta.getSaldo());
                    } else System.out.println("Conta não encontrada.");
                    break;

                case 8:
                    System.out.println("8. Exibir Todas as Contas");
                    exibirTodasAsContas(listaDeContas);
                    break;

                case 9:
                    System.out.println("9. Editar Informações da Conta");
                    if ((conta = buscarConta(listaDeContas)) != null) editarInformacoesDaConta(conta);
                    else System.out.println("Conta não encontrada.");
                    break;

                case 10:
                    System.out.println("10. Excluir Conta");
                    if ((conta = buscarConta(listaDeContas)) != null) excluirConta(listaDeContas, conta);
                    else System.out.println("Conta não encontrada.");
                    break;

                case 11:
                    System.out.println("11. Sair");
                    break;

                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 11);
    }
}