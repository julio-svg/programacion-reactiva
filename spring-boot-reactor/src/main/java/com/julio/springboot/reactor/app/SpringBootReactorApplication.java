package com.julio.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {
// CommandLineRunner para ejecutar desde consola

	private  static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<String> nombres = Flux.just("Julio" , "Raquel" , "" ,"Hugo" )
				.doOnNext(a -> {
					if (a.isEmpty()){
						throw new RuntimeException();
					}
					System.out.println(a);
				});
		nombres.subscribe(log::info , error -> System.out.println("Los nombres no puede estar vacios - " + error.getClass()));
	}
}
