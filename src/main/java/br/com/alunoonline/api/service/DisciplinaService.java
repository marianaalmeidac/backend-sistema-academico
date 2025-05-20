package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public void criarDisciplina(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarDisciplinaPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public void deletarDisciplinaPorId(Long id) {
        disciplinaRepository.deleteById(id);
    }

    public void atualizarDisciplinaPorId(Long id, Disciplina disciplina) {
        // 1: verificar se disciplina existe no bd
        Optional<Disciplina> disciplinaDoBancoDeDados = buscarDisciplinaPorId(id);

        // 2: se não existir a disciplina?
        if (disciplinaDoBancoDeDados.isEmpty()) {
            //se ele nao existir é preciso lançar uma nova exceção para response http com status 404 e mensagem
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada no banco de dados");
        }
        // 3: se existir (chegar aqui) armazenar a disciplina em uma variavel para depois edita-la
        Disciplina disciplinaParaEditar = disciplinaDoBancoDeDados.get();

        // 4: com a disciplina para ser editada,faz os sets necessarios para atualizar os atributos dela
        disciplinaParaEditar.setNome(disciplina.getNome());
        disciplinaParaEditar.setDescricao(disciplina.getDescricao());
        disciplinaParaEditar.setCargaHoraria(disciplina.getCargaHoraria());

        // 5: apos disciplina ser editada, devolve ela atualizada no banco de dados
        disciplinaRepository.save(disciplinaParaEditar);
    }

}
