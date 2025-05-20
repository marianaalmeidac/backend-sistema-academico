package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import br.com.alunoonline.api.service.AlunoService;
import br.com.alunoonline.api.service.ProfessorService;
import org.hibernate.query.internal.SimpleQueryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")

public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarProfessor(@RequestBody Professor professor) {
        professorService.criarProfessor(professor);
    }

    @GetMapping
    public ResponseEntity<List<Professor>> listarProfessores() {
        List<Professor> professores = professorService.listarTodosProfessores();
        return new ResponseEntity<>(professores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarProfessorPorId(@PathVariable Long id) {
        Optional<Professor> professorOptional = professorService.buscarProfessorPorId(id); // Declare a variável aqui
        if (professorOptional.isPresent()) {
            return new ResponseEntity<>(professorOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessorPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarProfessorPorId(@PathVariable Long id, @RequestBody Professor professor) {
        professorService.atualizarProfessorPorId(id, professor);
    }
}
