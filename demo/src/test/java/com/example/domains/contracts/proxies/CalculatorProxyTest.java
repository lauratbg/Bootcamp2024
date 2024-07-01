package com.example.domains.contracts.proxies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalculatorProxyTest {

	@Autowired
	CalculatorProxy calculator;
	
	@Test
	void testAdd() {
		assertEquals(2, calculator.add(1, 2));
	}
	
	@Test
	void testSub() {
		assertEquals(0.1, calculator.sub(1, 0.9));
	}
	
	@Test
	void testMul() {
		assertEquals(0.6, calculator.mul(0.3, 0.2));
	}
	
	@Test
	void testDiv() {
		assertEquals(0.5, calculator.div(1, 2));
	}

}
