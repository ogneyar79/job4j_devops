package ru.job4j.devops.models;

import lombok.Data;

/**
 * DTO, содержащий два аргумента для арифметических операций.
 * Используется в качестве входных данных для REST-контроллеров.
 */
@Data
public class TwoArgs {

    /**
     * Конструктор, создающий пару аргументов.
     *
     * @param first  первый аргумент
     * @param second второй аргумент
     */
    public TwoArgs(double first, double second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Конструктор по умолчанию, не содержит аргументы, используется для сериализации.
     */
    public TwoArgs() {
    }

    private double first;
    private double second;
}
