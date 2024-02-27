package service;

public class ValidadorEntrada {
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11)
            return false;

        int[] digitos = new int[11];
        int n1 = 0, n2 = 0, resto1 = 0, resto2 = 0, dv1 = 0, dv2 = 0;

        for (int i = 0; i < cpf.length(); i++)
            digitos[i] = Character.getNumericValue(cpf.charAt(i));

        for (int i = 0; i < 9; i++)
            n1 += digitos[i] * (10 - i);

        resto1 = 11 - (n1 % 11);
        dv1 = resto1 >= 10 ? 0 : resto1;

        for (int i = 0; i < 10; i++)
            n2 += digitos[i] * (11 - i);

        resto2 = 11 - (n2 % 11);
        dv2 = resto2 >= 10 ? 0 : resto1;

        return digitos[9] == dv1 && digitos[10] == dv2;
    }

    public static boolean validarNumero(String numero, int tipo) {
        if (tipo == 1)
            return numero.matches("\\d+");
        else
            return numero.matches("\\d+(\\.\\d+)?");
    }
}
