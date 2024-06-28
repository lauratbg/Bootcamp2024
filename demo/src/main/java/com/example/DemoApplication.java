package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;


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
	CommandLineRunner lookup(Jaxb2Marshaller marshaller) {
		return args -> {		
			WebServiceTemplate ws = new WebServiceTemplate(marshaller);
			var request = new AddRequest();
			request.setOp1(2);
			request.setOp2(3);
			var response = (AddResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
					 request, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
			System.err.println("Calculo remoto --> " + response.getAddResult());
		};
	}

}