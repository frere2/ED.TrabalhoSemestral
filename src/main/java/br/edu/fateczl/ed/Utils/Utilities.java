package br.edu.fateczl.ed.Utils;
import java.lang.reflect.Field;

public class Utilities {
    public static String FormatCPF(String cpf) {
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public static String GetHeadersByClass(Class<?> type) {
        Field[] fields = type.getDeclaredFields();

        StringBuilder headers = new StringBuilder();

        for (Field field : fields) {
            headers.append(field.getName()).append(";");
        }

        // tirar o ultimo ;
        if (!headers.isEmpty()) {
            headers.setLength(headers.length() - 1);
        }

        return headers.toString();
    }
}
