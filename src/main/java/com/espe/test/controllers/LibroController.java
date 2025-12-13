package com.espe.test.controllers;

import com.espe.test.clientes.AutorClienteRest;
import com.espe.test.models.dto.AutorDTO;
import com.espe.test.models.entities.Libro;
import com.espe.test.repositories.LibroRepository;
import com.espe.test.services.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @Autowired
    private AutorClienteRest autorClienteRest;

    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    //buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.buscarPorId(id);
        if (libroOptional.isPresent()) {
            return ResponseEntity.ok(libroOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Libro libro) {
        Optional<AutorDTO> autorDTO = autorClienteRest.buscarPorId(libro.getAutor_id());
        if (autorDTO.isPresent()) {

            Libro libroDB = service.guardar(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroDB);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Libro libro) {
        Optional<Libro> o = service.editar(id, libro);
        if (o.isPresent()) {
            Libro libroDB= o.get();
            libroDB.setTitulo(libro.getTitulo());
            libroDB.setGenero(libro.getGenero());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(libroDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Libro> o = service.buscarPorId(id);
        if (o.isPresent()) {
            service.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
