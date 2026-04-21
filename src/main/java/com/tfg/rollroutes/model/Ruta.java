package com.tfg.rollroutes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String ubicacion;
    private LocalDate fecha;

    //Relación con el creador de la ruta
    @ManyToOne
    private Usuario creador;

    public Ruta() {}

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }
}