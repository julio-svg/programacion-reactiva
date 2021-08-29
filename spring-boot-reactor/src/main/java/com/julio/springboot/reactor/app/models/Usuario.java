package com.julio.springboot.reactor.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class Usuario {
    private String nombre;
    private String apellidos;
}
