package com.lrtech.desafio_padroes_de_projeto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioPadroesDeProjetoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioPadroesDeProjetoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("BACK END RODANDO");
	}
}
