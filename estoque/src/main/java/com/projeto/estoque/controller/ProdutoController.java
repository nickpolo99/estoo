package com.projeto.estoque.controller;

import com.projeto.estoque.model.Lote;
import com.projeto.estoque.model.Produto;
import com.projeto.estoque.repository.LoteRepository;
import com.projeto.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private LoteRepository loteRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "formulario";
    }

    @PostMapping("/salvar")
    public String salvar(Produto produto, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path caminho = Paths.get("upload-dir/" + nomeArquivo);
            Files.createDirectories(caminho.getParent());
            Files.write(caminho, file.getBytes());
            produto.setImagem(nomeArquivo);
        }
        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/{id}/lotes")
    public String gerenciarLotes(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).get();
        model.addAttribute("produto", produto);
        model.addAttribute("novoLote", new Lote());
        return "lotes";
    }

    @PostMapping("/{id}/lotes/salvar")
    public String salvarLote(@PathVariable Long id, Lote lote) {
        Produto produto = produtoRepository.findById(id).get();
        lote.setProduto(produto);
        loteRepository.save(lote);
        return "redirect:/produtos/" + id + "/lotes";
    }

    @GetMapping("/{id}/baixa")
    public String paginaBaixa(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoRepository.findById(id).get());
        return "baixa";
    }

    @PostMapping("/{id}/baixa")
    public String darBaixa(@PathVariable Long id, @RequestParam Double quantidade) {
        List<Lote> lotes = loteRepository.findByProdutoIdOrderByValidadeAsc(id);
        
        Double qtdParaBaixar = quantidade;

        for (Lote lote : lotes) {
            if (qtdParaBaixar <= 0) break;

            if (lote.getQuantidade() > qtdParaBaixar) {
                lote.setQuantidade(lote.getQuantidade() - qtdParaBaixar);
                loteRepository.save(lote);
                qtdParaBaixar = 0.0;
            } else {
                qtdParaBaixar -= lote.getQuantidade();
                loteRepository.delete(lote);
            }
        }
        return "redirect:/produtos";
    }
}