package com.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getAllByDescricao(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<List<Produto>> getAllByCategoria(@PathVariable Long id) {
		return ResponseEntity.ok(produtoRepository.findAllByCategoriaId(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		Optional<Produto> produtoBd = produtoRepository.findById(id);

		if (produtoBd.isPresent()) {
			return ResponseEntity.ok(produtoBd.get());
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este id de produto não existe no banco de dados.");
	}

	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		validarCategoria(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		if (produtoRepository.existsById(produto.getId()) == false) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este produto não existe no banco de dados!");
		}

		validarCategoria(produto);
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);

		if (produto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este id de produto não existe no banco de dados.");
		}
		produtoRepository.deleteById(id);
	}

	private void validarCategoria(Produto produto) {
		if (produto.getCategoria() == null || !categoriaRepository.existsById(produto.getCategoria().getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A categoria deste produto não existe no banco de dados!");
		}
	}
}
