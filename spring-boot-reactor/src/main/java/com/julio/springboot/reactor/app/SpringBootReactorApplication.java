package com.julio.springboot.reactor.app;

import com.julio.springboot.reactor.app.models.Usuario;
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
		Flux<Usuario> nombres = Flux.just("Julio Hernandez" , "Raquel Sanabria" , "Carlos Hernandez" ,"Hugo Hernandez" )
				.filter(a -> "HERNANDEZ".equals(a.toUpperCase().split(" ")[1]))
				.map( a -> new Usuario(a.toUpperCase().split(" ")[0],a.toUpperCase().split(" ")[1]))
				.doOnNext(usuario -> {
					if (usuario.getNombre().isEmpty()){
						throw new RuntimeException();
					}
					System.out.println(usuario);
				});

		nombres.subscribe(l -> log.info(l.toString()) ,
				error -> System.out.println("Los nombres no puede estar vacios - " + error.getClass()),
				() -> System.out.println("el proceso ha terminado"));
	}
}
