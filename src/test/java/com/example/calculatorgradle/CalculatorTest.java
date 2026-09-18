package com.example.calculatorgradle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testSum(){
        assertEquals(30, calculator.sum(10,20));
    }
    public void testSub(){
        assertEquals(0, calculator.sub(20,10));
    }
}
