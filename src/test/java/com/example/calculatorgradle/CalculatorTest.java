package com.example.calculatorgradle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private Calculator calculator = new Calculator();
    //ee
    @Test
    public void testSum(){
        assertEquals(30, calculator.sum(10,20));
    }
    @Test
    public void testSub(){
        assertEquals(10, calculator.sub(20,10));
    }
}
