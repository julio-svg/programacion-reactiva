package com.julio.springboot.reactor.app.models;

import java.util.ArrayList;
import java.util.List;

public class Comentarios {
    private List<String> comentariosList;

    public Comentarios() {
        comentariosList = new ArrayList<>();
    }

    public Comentarios addComentarios(String comentario){
        comentariosList.add(comentario);
        return this;

    }

    @Override
    public String toString() {
        return "\nComentarios{" +
                "comentariosList=" + comentariosList +
                '}';
    }
}
