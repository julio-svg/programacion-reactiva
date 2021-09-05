package com.julio.springboot.reactor.app.models;

import reactor.core.publisher.Mono;

public class UsuarioComentarios {

    public UsuarioComentarios(Usuario usuario, Comentarios comentarios) {
        this.usuario = usuario;
        this.comentarios = comentarios;
    }

    Usuario usuario;
    Comentarios comentarios;

    @Override
    public String toString() {
        return "UsuarioComentarios{" +
                "usuario=" + usuario +
                ", comentarios=" + comentarios +
                '}';
    }
}
