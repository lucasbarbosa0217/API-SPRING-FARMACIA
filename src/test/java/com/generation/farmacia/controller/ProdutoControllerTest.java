package com.generation.farmacia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import com.generation.farmacia.repository.ProdutoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private CategoriaRepository temaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	private Categoria categoria;

	@BeforeAll
	void criarCategorias() {
		temaRepository.deleteAll();
		produtoRepository.deleteAll();
		categoria = temaRepository.save(new Categoria(1l, "Cosmético", new ArrayList<Produto>()));
		temaRepository.save(new Categoria(2l, "Antibiótico", new ArrayList<Produto>()));
		temaRepository.save(new Categoria(3l, "Antinflamatório", new ArrayList<Produto>()));
		temaRepository.save(new Categoria(4l, "Higiene Masculina", new ArrayList<Produto>()));
		temaRepository.save(new Categoria(5l, "Analgésico", new ArrayList<Produto>()));

	}

	@BeforeEach
	void apagarProdutos() {
		produtoRepository.deleteAll();
	}

	@Test
	@DisplayName("Criar Produto")
	public void deveCriarUmProduto() {
		Produto produto = new Produto(null, "Dipirona 20mg", 10, new BigDecimal("10.99"), new Categoria(1l));
		HttpEntity<Produto> corpoRequisicao = new HttpEntity<>(produto);

		ResponseEntity<Produto> corpoResposta = testRestTemplate.exchange("/produto", HttpMethod.POST, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

	}

	@Test
	@DisplayName("Atualizar um Produto")
	public void deveAtualizarUmaCategoria() {

		Produto produto = new Produto(null, "Dipirona 20mg", 10, new BigDecimal("10.99"), new Categoria(1l));
		HttpEntity<Produto> corpoRequisicao = new HttpEntity<>(produto);
		ResponseEntity<Produto> corpoResposta = testRestTemplate.exchange("/produto", HttpMethod.POST, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

		Produto produtoAtualizado = new Produto(corpoResposta.getBody().getId(), "Dipirona Atualizada 20mg", 9, new BigDecimal("12.99"),
				new Categoria(2l));
		HttpEntity<Produto> corpoRequisicaoAtualizado = new HttpEntity<>(produtoAtualizado);

		ResponseEntity<Produto> corpoRespostaAtualizado = testRestTemplate.exchange("/produto", HttpMethod.PUT,
				corpoRequisicaoAtualizado, Produto.class);

		assertEquals(HttpStatus.OK, corpoRespostaAtualizado.getStatusCode());

	}

	@Test
	@DisplayName("Erro 404 se produto não existe ao atualizar")
	public void deveDar404AtualizarProdutoInexistente() {

		Produto produtoAtualizado = new Produto(15461l, "Dipirona Atualizada 20mg", 9, new BigDecimal("12.99"),
				new Categoria(2l));
		HttpEntity<Produto> corpoRequisicaoAtualizado = new HttpEntity<>(produtoAtualizado);

		ResponseEntity<Produto> corpoRespostaAtualizado = testRestTemplate.exchange("/produto", HttpMethod.PUT,
				corpoRequisicaoAtualizado, Produto.class);

		assertEquals(HttpStatus.NOT_FOUND, corpoRespostaAtualizado.getStatusCode());

	}

	@Test
	@DisplayName("Listar todos os Produtos")
	public void deveMostrarTodosProdutos() {
		produtoRepository.save(new Produto(1l, "Dipirona 20mg", 50, new BigDecimal("10.99"), categoria));
		produtoRepository.save(new Produto(2l, "Paracetamol 500mg", 100, new BigDecimal("5.49"), categoria));
		produtoRepository.save(new Produto(3l, "Ibuprofeno 400mg", 75, new BigDecimal("12.89"), categoria));
		produtoRepository.save(new Produto(4l, "Amoxicilina 500mg", 60, new BigDecimal("20.00"), categoria));

		ResponseEntity<String> resposta = testRestTemplate.exchange("/produto", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar produto por id")
	public void deveMostrarProdutoPorId() {
		
		Produto produto = new Produto(null, "Dipirona 20mg", 10, new BigDecimal("10.99"), new Categoria(1l));
		HttpEntity<Produto> corpoRequisicao = new HttpEntity<>(produto);
		ResponseEntity<Produto> corpoResposta = testRestTemplate.exchange("/produto", HttpMethod.POST, corpoRequisicao,
				Produto.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

		ResponseEntity<String> resposta = testRestTemplate.exchange("/produto/"+corpoResposta.getBody().getId(), HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Listar produto por nome")
	public void deveMostrarProdutoPorNome() {
		criarProduto("Dipirona 20mg", 50, new BigDecimal("10.99"), categoria);
		criarProduto("Paracetamol 500mg", 100, new BigDecimal("5.49"), categoria);
		criarProduto("Ibuprofeno 400mg", 75, new BigDecimal("12.89"), categoria);
		criarProduto("Amoxicilina 500mg", 60, new BigDecimal("20.00"), categoria);
		criarProduto("Cenoura 1kg", 30, new BigDecimal("2.99"), categoria);

		ResponseEntity<String> resposta = testRestTemplate.exchange("/produto/nome/ro", HttpMethod.GET, null,
				String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Listar produto por categoria")
	public void deveMostrarProdutoPorCategoria() {
		Produto produto = criarProduto("Dipirona 20mg", 50, new BigDecimal("10.99"), categoria);
		criarProduto("Paracetamol 500mg", 100, new BigDecimal("5.49"), categoria);
		criarProduto("Ibuprofeno 400mg", 75, new BigDecimal("12.89"), categoria);
		criarProduto("Amoxicilina 500mg", 60, new BigDecimal("20.00"), categoria);
		criarProduto("Cenoura 1kg", 30, new BigDecimal("2.99"), categoria);

		ResponseEntity<String> resposta = testRestTemplate.exchange("/produto/categoria/"+produto.getCategoria().getId(), HttpMethod.GET, null,
				String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	@DisplayName("Erro 404 se id não existe")
	public void deveDar404SeIdNaoExiste() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/produto/16514", HttpMethod.GET, null,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	private Produto criarProduto(String nome, int estoque, BigDecimal preco, Categoria categoria) {
		Produto produto = new Produto(null, nome, estoque, preco, categoria);

		HttpEntity<Produto> requestEntity = new HttpEntity<>(produto);
		ResponseEntity<Produto> response = testRestTemplate.postForEntity("/produto", requestEntity, Produto.class);
		
		return response.getBody();
	}
}