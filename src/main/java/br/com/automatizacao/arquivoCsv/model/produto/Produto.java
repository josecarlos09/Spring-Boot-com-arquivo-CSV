package br.com.automatizacao.arquivoCsv.model.produto;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Produto") // Nome da entidade
@Table(name = "produto") // Nome da tabela no banco
@EqualsAndHashCode(of = "id")// Indicaa a chave primary-key
// Construtores
@NoArgsConstructor
@AllArgsConstructor
// Métodos acessores e modificadores
@Getter
@Setter
// Modelo
public class Produto {
    //Atenção: Os nomes dos atributos da classe Produto podem ser diferentes dos campos do arquivo, mas as colunas da base de dados devem ser iguais.    @Id // Indica qual é o atributo chave primaria
    @Id // Indica qual é o atributo chave primaria
    @GeneratedValue(strategy = GenerationType.AUTO)// Gera números sequenciais
    @CsvBindByName(column = "ProductID", required = true)
    private Long id;
    @CsvBindByName(column = "ProductName", required = true)
    private String nome;
    @CsvBindByName(column = "Description", required = true)
    private String descricacao;
    @CsvBindByName(column = "Price", required = true)
    private BigDecimal preco;
    @CsvBindByName(column = "Category", required = true)
    private String categoria;
}
