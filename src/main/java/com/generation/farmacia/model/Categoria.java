package com.generation.farmacia.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O campo descrição da categoria é obrigatório!")
	@Size(min = 2, max = 30, message="O campo descrição deve ter entre 2 e 30 caracteres!")
	private String descricao;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "categoria", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("categoria")
	private List<Produto> produto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public Categoria() {}
	
	public Categoria(Long id) {
		this.id = id;
	}

	public Categoria(Long id, String descricao, List<Produto> produto) {
		this.id = id;
		this.descricao = descricao;
		this.produto = produto;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
