package com.app.exceptions;

import java.lang.reflect.Field;
import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ValidationUtil {

    public static void validarObjeto(Object obj) {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                validarCampo(field, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Erro ao acessar o campo: " + field.getName(), e);
            }
        }
    }

    private static void validarCampo(Field field, Object value) {
        // Verificar anotação NotNull
        if (field.isAnnotationPresent(NotNull.class) && value == null) {
            throw new InvalidDataException("O campo '" + field.getName() + "' é obrigatório.");
        }

        // Verificar anotações de tamanho
        if (field.isAnnotationPresent(Size.class)) {
            Size size = field.getAnnotation(Size.class);
            if (value instanceof String) {
                String stringValue = (String) value;
                if (stringValue.length() < size.min() || stringValue.length() > size.max()) {
                    throw new InvalidDataException("O campo '" + field.getName() + "' deve ter entre "
                            + size.min() + " e " + size.max() + " caracteres.");
                }
            }
        }

        // Verificar tipo de Date
        if (field.getType().equals(Date.class) && !(value instanceof Date)) {
            throw new InvalidDataException("O campo '" + field.getName() + "' deve ser uma data válida.");
        }

        // Adicione outras verificações de tipo conforme necessário
    }
}
