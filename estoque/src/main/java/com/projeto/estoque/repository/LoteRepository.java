package com.projeto.estoque.repository;
import com.projeto.estoque.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByProdutoIdOrderByValidadeAsc(Long produtoId);
}