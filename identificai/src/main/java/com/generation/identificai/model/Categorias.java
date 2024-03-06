package com.generation.identificai.model;

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
public class Categorias {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O atributo tipo é obrigatório")
    @Size(min = 1, max = 255, message = "O atributo tipo é obrigatório e deve ter no máximo 255 caracteres.")
    private String tipo;
    
    @NotBlank(message = "O Atributo Descrição é obrigatório")
    @Size(min = 1, max = 500, message = "O atributo descricao é obrigatório e deve ter no máximo 500 caracteres.")
    private String descricao;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("categoria")
    private List<Servicos> servicos;

	public Categorias(Long id, String tipo, String descricao,List<Servicos> servicos) {
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.servicos = servicos;
	}

	public Categorias() {
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Servicos> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servicos> servicos) {
		this.servicos = servicos;
	}

	public void setIdAdm(Long idAdm) {
		
		
	}

	public void setNome(String string) {
		
	}

    
}
