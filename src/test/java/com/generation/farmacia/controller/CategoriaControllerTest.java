package com.generation.farmacia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.farmacia.model.Categoria;
import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CategoriaRepository temaRepository;

	@BeforeAll
	void start(){
		temaRepository.deleteAll();
	}

	@Test
	@DisplayName("Criar categoria")
	public void deveCriarUmTema() {
		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(new Categoria(0L, "Cosmético", new ArrayList<Produto>()));

		ResponseEntity<Categoria> corpoResposta = testRestTemplate
			.exchange("/categoria", HttpMethod.POST, corpoRequisicao, Categoria.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}


	@Test
	@DisplayName("Atualizar uma Categoria")
	public void deveAtualizarUmaCategoria() {
		HttpEntity<Categoria> corpoRequisicao = new HttpEntity<Categoria>(new Categoria(0L, "Cosmético", new ArrayList<Produto>()));

		testRestTemplate.exchange("/categoria", HttpMethod.POST, corpoRequisicao, Categoria.class);

		HttpEntity<Categoria> corpoRequisicaoAtualizacao = new HttpEntity<Categoria>(new Categoria(1L, "Dermocosmético", new ArrayList<Produto>()));

		ResponseEntity<Categoria> corpoRespostaAtualizacao = testRestTemplate
			.exchange("/categoria", HttpMethod.PUT, corpoRequisicaoAtualizacao, Categoria.class);

		assertEquals(HttpStatus.OK, corpoRespostaAtualizacao.getStatusCode());
	}

	@Test
	@DisplayName("Listar todas as Categorias")
	public void deveMostrarTodasCategorias() {
		HttpEntity<Categoria> corpoRequisicao1 = new HttpEntity<Categoria>(new Categoria(0L, "Cosmético", new ArrayList<Produto>()));

		testRestTemplate.exchange("/categoria", HttpMethod.POST, corpoRequisicao1, Categoria.class);
		
		HttpEntity<Categoria> corpoRequisicao2 = new HttpEntity<Categoria>(new Categoria(0L, "Analgésico", new ArrayList<Produto>()));

		testRestTemplate.exchange("/categoria", HttpMethod.POST, corpoRequisicao2, Categoria.class);
		
		HttpEntity<Categoria> corpoRequisicao3 = new HttpEntity<Categoria>(new Categoria(0L, "Antibiótico", new ArrayList<Produto>()));

		testRestTemplate.exchange("/categoria", HttpMethod.POST, corpoRequisicao3, Categoria.class);
		
		ResponseEntity<String> resposta = testRestTemplate
				.exchange("/categoria", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar categoria por id")
	public void deveMostrarCategoriaPorId() {
		HttpEntity<Categoria> corpoRequisicao1 = new HttpEntity<Categoria>(new Categoria(0L, "Cosmético", new ArrayList<Produto>()));

		testRestTemplate.exchange("/categoria", HttpMethod.POST, corpoRequisicao1, Categoria.class);
		
		ResponseEntity<String> resposta = testRestTemplate
			.exchange("/categoria/1", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Erro 404 se id não existe")
	public void deveDar404SeIdNaoExiste() {
		ResponseEntity<String> resposta = testRestTemplate
			.exchange("/categoria/16514", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}
}