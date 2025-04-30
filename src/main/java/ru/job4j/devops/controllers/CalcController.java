package ru.job4j.devops.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.TwoArgs;


/**
 * Сервис контроллер.
 */
@RestController
@RequestMapping("calc")
public class CalcController {
    /**
     * Конструктор по умолчанию. Не содержит логики.
     */
    public CalcController() {
    }

    /**
     * Метод в котором происходит суммирование частей аргумента.
     * @param twoArgs сущность 2 аргумента
     * @return результат сложения
     */
    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() + twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }

    /**
     * Метод в котором происходит умножение частей аргумента.
     * @param twoArgs сущность 2 аргумента
     * @return результат сложения
     */
    @PostMapping("times")
    public ResponseEntity<Result> times(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() * twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }
}
