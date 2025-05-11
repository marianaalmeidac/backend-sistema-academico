package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class  AlunoService {

    @Autowired
    AlunoRepository alunoRepository;


    public void criarAluno(Aluno aluno ) {

        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodosAlunos() {

        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id) {
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId (Long id, Aluno aluno) {
         // 1: verificar se aluno existe no bd
        Optional<Aluno> alunoDoBancoDeDados = buscarAlunoPorId(id);

        // 2: se não existir o aluno?
        if (alunoDoBancoDeDados.isEmpty()){
           //se ele nao existir é preciso lançar uma nova exceção para response http com status 404 e mensagem
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado no banco de dados");
        }
        // 3: se existir (chegar aqui) armazenar o aluno em uma variavel para depois edita-lo
        Aluno alunoParaEditar = alunoDoBancoDeDados.get();
        // 4: com o aluno para ser editado,faz-se os stes necessarios para atualizar os atributos dele
        alunoParaEditar.setNome(aluno.getNome());    //QUAIS SÃO OS ATRIBUTOS DE ALUNO QUE SE PODE EDITAR?
        alunoParaEditar.setCpf(aluno.getCpf());
        alunoParaEditar.setEmail(aluno.getCpf());
    }

}
