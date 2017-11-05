package com.ufcg.sad.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.sad.exceptions.QuestaoInvalidaException;
import com.ufcg.sad.exceptions.QuestionarioNaoExisteException;
import com.ufcg.sad.exceptions.QuestionarioSemNomeException;
import com.ufcg.sad.exceptions.QuestionarioVazioException;
import com.ufcg.sad.models.Questao;
import com.ufcg.sad.models.Questionario;
import com.ufcg.sad.repositories.QuestionarioRepository;

/**
 * Serviço para um Questionário.
 * 
 * @author Marianne Linhares
 */
@Service
public class QuestionarioService {
	
	private QuestionarioRepository questionarioRepository;
	private QuestaoService questaoService;

	/**
	 * Construtor para o tipo QuestionarioService.
	 * @param questionarioRepository
	 * @param questaoService
	 */
	@Autowired
	public QuestionarioService(QuestionarioRepository questionarioRepository,
						       QuestaoService questaoService) {
		super();
		this.questionarioRepository = questionarioRepository;
		this.questaoService = questaoService;
	}
	
	/**
	 * Método que busca um questionário a partir de um certo id.
	 * @param id
	 * @return questionario
	 */
	public Questionario getQuestionario(Long id) throws QuestionarioNaoExisteException {
		
		Questionario questionario = questionarioRepository.findOne(id);
		
		if(questionario != null) {
			return questionario;
		}
		else {
			throw new QuestionarioNaoExisteException();
		}
	}
	
	/**
	 * Método que busca todos os questionários.
	 * @param id
	 * @return lista contendo questionarios
	 */
	public List<Questionario> getTodosQuestionarios() {
		List<Questionario> questionarios = new ArrayList<>();

		for (Questionario questionario : questionarioRepository.findAll()) {
			questionarios.add(questionario);
		}

		return questionarios;
	}
	
	/**
	 * Método que salva um questionário.
	 * @param questionario
	 */
	public void criaQuestionario(Questionario questionario) throws QuestionarioVazioException, QuestionarioSemNomeException, QuestaoInvalidaException {
		
		if(questionario.getName() == null || questionario.getName().isEmpty()) {
			throw new QuestionarioSemNomeException();
		}
		else if(questionario.getQuestoes() == null || questionario.getQuestoes().isEmpty()) {
			throw new QuestionarioVazioException();
		}
		
		for(Questao questao : questionario.getQuestoes()) {
			if(!questaoService.validaQuestao(questao)) {
				throw new QuestaoInvalidaException();
			}
		}
		
		questionarioRepository.save(questionario);
	}

	/**
	 * Método que atualiza um questionário.
	 * @param id
	 * @param questionario
	 */
	public void atualizaQuestionario(Long id, Questionario questionario) throws QuestionarioNaoExisteException, QuestaoInvalidaException {
		
		Questionario questionarioAtualizado = getQuestionario(id);
		
		if(questionario.getQuestoes() != null && !questionario.getQuestoes().isEmpty()) {
			
			for(Questao questao : questionario.getQuestoes()) {
				if(!questaoService.validaQuestao(questao)) {
					throw new QuestaoInvalidaException();
				}
			}
			
			questionarioAtualizado.setQuestoes(questionario.getQuestoes());
		}
		
		if(questionario.getName() != null && !questionario.getName().equals(questionarioAtualizado.getName())) {
			questionarioAtualizado.setName(questionario.getName());
		}
		
		questionarioRepository.save(questionarioAtualizado);
	}
}