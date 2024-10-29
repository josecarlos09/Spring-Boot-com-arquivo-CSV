package br.com.automatizacao.arquivoCsv.controller;

import br.com.automatizacao.arquivoCsv.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/importar_csv") // rota: http://localhost:8080/importar_csv
    public ResponseEntity importarCsv(@RequestParam("arquivo") MultipartFile arquivo) {
        try {
            /* Se o arquivo CSV for importado com sucesso:
                - Será exibido a mensagem: Arquivo CSV importado com sucesso
                - será chamado o método importarCsv que salvara as informações em um banco de dados
             */
            produtoService.importarCsv(arquivo);
            return ResponseEntity.ok("Arquivo CSV importado com sucesso!");
        } catch (Exception e) {
            // Caso a importação não acontessa será retornada
            return ResponseEntity.status(500).body("Erro ao importar o arquivo CSV: " + e.getMessage());
        }
    }
}
