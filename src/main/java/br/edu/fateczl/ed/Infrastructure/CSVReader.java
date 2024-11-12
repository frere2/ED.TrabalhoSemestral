package br.edu.fateczl.ed.Infrastructure;

import model.Lista;

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

    public Lista<T> mapFromCSV(String filePath, String delimiter) throws Exception {
        Lista<T> resultList = new Lista<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = br.readLine().split(delimiter);

            if (headers.length != type.getDeclaredFields().length) {
                throw new Exception("ERRO ao ler arquivo!");
            }

            int pos = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                T obj = createObject(headers, values);
                resultList.add(pos, obj);
                pos++;
            }
        } catch (IOException e) {
            System.err.println("ERRO ao ler arquivo: " + filePath + ": " + e.getMessage());
        }

        return resultList;
    }

    private T createObject(String[] headers, String[] values) throws Exception {
        Constructor<T> constructor = type.getConstructor();
        T obj = constructor.newInstance();

        for (int i = 0; i < headers.length; i++) {
            String fieldName = headers[i].toLowerCase();
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
        } else if (fieldType == double.class) {
            field.set(obj, Double.parseDouble(value));
        } else if (fieldType == boolean.class) {
            field.set(obj, Boolean.parseBoolean(value));
        } else {
            field.set(obj, value);
        }
    }
}
