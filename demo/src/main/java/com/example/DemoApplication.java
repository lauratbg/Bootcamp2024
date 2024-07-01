package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.domains.contracts.proxies.CalculatorProxy;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
	}

	@Bean
	CommandLineRunner lookup(CalculatorProxy client) {
		return args -> {
			System.err.println("Calculo remoto (suma) --> " + client.add(3, 2));
			System.err.println("Calculo remoto (resta 1-0.9) --> " + client.sub(1, 0.9));
			System.err.println("Calculo remoto (mul) --> " + client.mul(3, 2));
			System.err.println("Calculo remoto (div) --> " + client.div(3, 2));
		};
	}

//	@Bean
//	CommandLineRunner lookup2(Jaxb2Marshaller marshaller) {
//		return args -> {		
//			WebServiceTemplate ws = new WebServiceTemplate(marshaller);
//			var request = new AddRequest();
//			request.setOp1(2);
//			request.setOp2(3);
//			var response = (AddResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
//					 request, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
//			System.err.println("Calculo remoto --> " + response.getAddResult());
//		};
//	}

}