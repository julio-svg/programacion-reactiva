package com.julio.springboot.reactor.app;

import com.julio.springboot.reactor.app.models.Comentarios;
import com.julio.springboot.reactor.app.models.Usuario;
import com.julio.springboot.reactor.app.models.UsuarioComentarios;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {
// CommandLineRunner para ejecutar desde consola

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        usuarioComentariosFlatMap();
    }

    private void fluxJust() {
        Flux<Usuario> nombres = Flux.just("Julio Hernandez", "Raquel Sanabria", "Carlos Hernandez", "Hugo Hernandez")
                .filter(a -> "HERNANDEZ".equals(a.toUpperCase().split(" ")[1]))
                .map(a -> new Usuario(a.toUpperCase().split(" ")[0], a.toUpperCase().split(" ")[1]))
                .doOnNext(usuario -> {
                    if (usuario.getNombre().isEmpty()) {
                        throw new RuntimeException();
                    }
                    System.out.println(usuario);
                });

        nombres.subscribe(l -> log.info(l.toString()),
                error -> System.out.println("Los nombres no puede estar vacios - " + error.getClass()),
                () -> System.out.println("el proceso ha terminado"));
    }

    private void fluxFromListWithFlapMap() {
        List<String> lista = Arrays.asList("Julio Hernandez", "Raquel Sanabria", "Carlos Hernandez", "Hugo Hernandez");
        Flux.fromIterable(lista)
                .flatMap(a -> {
                    if (a.toUpperCase().split(" ")[1].equals("HERNANDEZ")) {
                        return Mono.just(new Usuario(a.split(" ")[0], a.split(" ")[1]));
                    }
                    return Mono.empty();
                })
                .subscribe(l -> log.info(l.toString()));
    }

    private void fluxFromListUsuariosWithFlapMap() {
        List<Usuario> lista = Arrays.asList(new Usuario("Julio", "Hernandez"), new Usuario("Raquel", "Sanabria"), new Usuario("Carlos", "Hernandez"), new Usuario("Hugo", "Hernandez"));
        Flux.fromIterable(lista)
                .flatMap(usuario -> {
                    if (usuario.getApellidos().toUpperCase().equals("HERNANDEZ")) {
                        return Mono.just(usuario.getNombre());
                    }
                    return Mono.empty();
                })
                .subscribe(log::info);
    }

    private void fluxVsMono() {

        List<Usuario> lista = Arrays.asList(new Usuario("Julio", "Hernandez"), new Usuario("Raquel", "Sanabria"), new Usuario("Carlos", "Hernandez"), new Usuario("Hugo", "Hernandez"));
        System.out.println("Flux emite lo elementos de la lista 1 a 1");


        Flux.fromIterable(lista)
                .flatMap(usuario -> {
                    if (usuario.getApellidos().toUpperCase().equals("HERNANDEZ")) {
                        return Mono.just(usuario.getNombre());
                    }
                    return Mono.empty();
                })  // aunque retornes mono, flatMap nos devuelve un Flux
                .subscribe(log::info);


        System.out.println("Mono Emite la lista entera");
        Flux.fromIterable(lista)
                .flatMap(usuario -> {
                    if (usuario.getApellidos().toUpperCase().equals("HERNANDEZ")) {
                        return Mono.just(usuario.getNombre());
                    }
                    return Mono.empty();
                }).collectList() // nos lo convierte en mono
                .subscribe(ListaMono -> log.info(ListaMono.toString()));
    }

    private void usuarioComentariosFlatMap() {
        Mono<Usuario> usuarioMono = Mono.fromCallable(() -> new Usuario("Julio", "Hernandez"));
        Mono<Comentarios> comentariosMono = Mono.fromCallable(() -> {
            Comentarios comentarios = new Comentarios();
            comentarios.addComentarios("Hola").addComentarios("Soy un comentario");
            return comentarios;
        });

        usuarioMono.flatMap(u -> comentariosMono.map(c -> new UsuarioComentarios(u, c)))
                .subscribe(l -> log.info(l.toString()));
    }


}
