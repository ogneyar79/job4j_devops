package ru.job4j.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения калькулятора.
 */
@SpringBootApplication
public class CalcApplication {
	/**
	 * Точка входа в приложение.
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(CalcApplication.class, args);
	}
}
