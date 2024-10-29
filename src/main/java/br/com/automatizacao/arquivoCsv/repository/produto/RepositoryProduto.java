package br.com.automatizacao.arquivoCsv.repository.produto;


import br.com.automatizacao.arquivoCsv.model.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryProduto extends JpaRepository<Produto, Long> {
}
