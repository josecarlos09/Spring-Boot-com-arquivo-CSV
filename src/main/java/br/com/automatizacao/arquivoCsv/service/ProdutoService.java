package br.com.automatizacao.arquivoCsv.service;

import br.com.automatizacao.arquivoCsv.model.produto.Produto;
import br.com.automatizacao.arquivoCsv.repository.produto.RepositoryProduto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private RepositoryProduto repositoryProduto;

    public void importarCsv(MultipartFile file) throws Exception{
        CsvToBean<Produto> csvToBean = new CsvToBeanBuilder<Produto>(new InputStreamReader(file.getInputStream()))
                .withType(Produto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<Produto> produtos = csvToBean.parse(); // Criação da lista de produtos que recebe o arquivo CSV

        repositoryProduto.saveAll(produtos); // Cadastra o arquivo CSV no banco de dados
    }
}

