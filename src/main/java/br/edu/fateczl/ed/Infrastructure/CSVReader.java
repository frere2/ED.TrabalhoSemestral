package br.edu.fateczl.ed.Infrastructure;

import br.edu.fateczl.ed.Enums.EDiaSemana;
import br.edu.fateczl.fila.Fila;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CSVReader<T> {
    private final Class<T> type;

    public CSVReader(Class<T> type) {
        this.type = type;
    }

    public Fila<T> mapFromCSV(String filePath, String delimiter) throws Exception {
        Fila<T> resultFila = new Fila<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String firstLine = br.readLine();
            if (firstLine == null || firstLine.isEmpty()) {
                return resultFila;
            }

            String[] headers =  firstLine.split(delimiter);

            if (headers.length != type.getDeclaredFields().length) {
                throw new Exception("ERRO ao ler arquivo!");
            }
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                T obj = createObject(headers, values);

                resultFila.insert(obj);
            }
        } catch (IOException e) {
            System.err.println("ERRO ao ler arquivo: " + filePath + ": " + e.getMessage());
        }

        return resultFila;
    }

    private T createObject(String[] headers, String[] values) throws Exception {
        Constructor<T> constructor = type.getConstructor();
        T obj = constructor.newInstance();

        for (int i = 0; i < headers.length; i++) {
            String fieldName = headers[i];
            Field field = type.getDeclaredField(fieldName);
            field.setAccessible(true); // não podemos acessar os getters e setters aqui
            setFieldValue(field, obj, values[i]);
        }

        return obj;
    }

    private void setFieldValue(Field field, T obj, String value) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        if (fieldType == int.class) {
            field.set(obj, Integer.parseInt(value));
        } else if (fieldType == Double.class) {
            field.set(obj, Double.parseDouble(value));
        } else if (fieldType == Boolean.class) {
            field.set(obj, Boolean.parseBoolean(value));
        } else if (fieldType == EDiaSemana.class) {
            field.set(obj, EDiaSemana.valueOf(value));
        } else {
            field.set(obj, value);
        }
    }
}
