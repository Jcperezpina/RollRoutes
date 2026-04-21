package com.tfg.rollroutes.repository;

import com.tfg.rollroutes.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}