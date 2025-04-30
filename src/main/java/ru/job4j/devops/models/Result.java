package ru.job4j.devops.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Используется Lombok для генерации конструктора и геттеров/сеттеров.
 * Класс передающий результирующее состояние.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private double value;
}
