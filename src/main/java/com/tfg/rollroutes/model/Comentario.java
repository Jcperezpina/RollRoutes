package com.tfg.rollroutes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    private LocalDate fecha;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private AgendaRuta evento;

    public Comentario() {}

    public Long getId() { return id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public AgendaRuta getEvento() { return evento; }
    public void setEvento(AgendaRuta evento) { this.evento = evento; }
}
