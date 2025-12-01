package com.espe.test.services;

import com.espe.test.models.entities.Libro;
import com.espe.test.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository repository;

    @Override
    @Transactional(readOnly = true) // Ahora es de Spring, sí tiene readOnly
    public List<Libro> buscarTodos() {
        return (List<Libro>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true) // También podemos agregarlo aquí
    public Optional<Libro> buscarPorId(Long id) {
        return repository.findById(id); // Corregimos para que use el repository
    }

    @Override
    @Transactional // Para operaciones de escritura, no es readOnly
    public Libro guardar(Libro libro) {
        return repository.save(libro);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        repository.deleteById(id); // Agregamos la lógica para eliminar
    }

    //utiles
    @Override
    @Transactional
    public Optional<Libro> editar(Long id, Libro libro) {
        Optional<Libro> o = repository.findById(id);
        if (o.isPresent()) {
            Libro libroDB = o.get();
            libroDB.setTitulo(libro.getTitulo());
            libroDB.setAutor(libro.getAutor());
            libroDB.setGenero(libro.getGenero());
            return Optional.of(repository.save(libroDB));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Libro> eliminar(Long id) {
        Optional<Libro> o = repository.findById(id);
        if (o.isPresent()) {
            repository.delete(o.get());
            return o;
        }
        return Optional.empty();
    }


}