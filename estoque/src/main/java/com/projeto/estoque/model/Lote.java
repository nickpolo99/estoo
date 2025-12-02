package com.projeto.estoque.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Lote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantidade;
    private LocalDate validade;
    private Double precoCusto;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }
    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }
    public Double getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(Double precoCusto) { this.precoCusto = precoCusto; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}