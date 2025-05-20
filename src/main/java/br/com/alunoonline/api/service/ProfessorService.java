package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public void criarProfessor(Professor professor) {

        professorRepository.save(professor);
    }

    public List<Professor> listarTodosProfessores() {

        return professorRepository.findAll();
    }

    public Optional<Professor> buscarProfessorPorId(Long id) {
        return professorRepository.findById(id);
    }

    public void deletarProfessorPorId(Long id) {
        professorRepository.deleteById(id);
    }

    public void atualizarProfessorPorId(Long id, Professor professor) {
        // 1: verificar se professor existe no bd
        Optional<Professor> professorDoBancoDeDados = buscarProfessorPorId(id);

        // 2: se não existir o professor?
        if (professorDoBancoDeDados.isEmpty()) {
            //se ele nao existir é preciso lançar uma nova exceção para response http com status 404 e mensagem
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado no banco de dados");
        }
        // 3: se existir (chegar aqui) armazenar o professor em uma variavel para depois edita-lo
        Professor professorParaEditar = professorDoBancoDeDados.get();

        // 4: com o professor para ser editado,faz os sets necessarios para atualizar os atributos dele
        professorParaEditar.setNome(professor.getNome());    //QUAIS SÃO OS ATRIBUTOS DE ALUNO QUE SE PODE EDITAR?
        professorParaEditar.setCpf(professor.getCpf());
        professorParaEditar.setEmail(professor.getEmail());

        // 5: apos professor ser editado, devolve ele atualizado no banco de dados
        professorRepository.save(professorParaEditar);
    }
}
