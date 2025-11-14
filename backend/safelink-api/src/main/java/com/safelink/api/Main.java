package com.safelink.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
        String nomeVariavel = System.getenv("PUBLIC_KEY");
        System.out.println("O valor da variável é: " + nomeVariavel);

		SpringApplication.run(Main.class, args);
	}
}
