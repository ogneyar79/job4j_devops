package ru.job4j.devops.models;

import lombok.Data;


/**
 * Используется Lombok для генерации конструктора и геттеров/сеттеров.
 * Класс передающий результирующее состояние.
 */
@Data
public class Result {

    /**
     * Конструктор по умолчанию, используется для сериализации.
     * @param value значение результата
     */
    public Result(double value) {
        this.value = value;
    }

    /**
     * Конструктор по умолчанию, не содержит аргументы, используется для сериализации.
     */
    public Result() {
    }

    private double value;
}
