package com.projeto.estoque.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String unidade;
    private Double estoqueMinimo;
    private String imagem;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Lote> lotes = new ArrayList<>();

    public Double getQuantidadeTotal() {
        return lotes.stream().mapToDouble(Lote::getQuantidade).sum();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public Double getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(Double estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
    public List<Lote> getLotes() { return lotes; }
    public void setLotes(List<Lote> lotes) { this.lotes = lotes; }
}