package com.espe.test.services;

import com.espe.test.models.entities.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    List<Libro> buscarTodos();
    Optional<Libro> buscarPorId(Long id);
    Libro guardar(Libro libro);
    void eliminarPorId(Long id);

    Optional<Libro> editar(Long id, Libro libro);
    Optional<Libro> eliminar(Long id);
}
