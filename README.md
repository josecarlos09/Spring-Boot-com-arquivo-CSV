spring.application.name=arquivoCsv

# Configurações do banco de dados no arquivo .properties:

#Acesso ao banco de dados

spring.datasource.url=jdbc:mysql://localhost:3306/produto

#Nome do usuario do banco de dados

spring.datasource.username=root

#Senha do banco de dados

spring.datasource.password=Carlos

# Altera a estrutura do banco caso a entidade tenha mudanças:
spring.jpa.hibernate.ddl-auto=update

# Caso o Spring Boot não encontre automaticamente o drive de conexão injetado pela dependência do MySQL:
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Exibe os logs de comandos SQL:
spring.jpa.show-sql=true

# Exibe os comandos SQL formatados no terminal:
spring.jpa.properties.hibernate.format_sql=true

# Atenção, para que as configurações do banco de dados funcione corretamente deve-se atender esses dois requisitos: 
   1- Crie uma base de dados Produto ou informe outra base de dados nas configurações a cima.
   
   2- As configurações feitas são para trabalhar com um banco de dados que use o SGBD MySQL, se o seu SGBD for diferente, as configurações serão outras, verifique na documentação.
     

# Dependências do projeto com MAVE:

    <dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.opencsv</groupId>
			<artifactId>opencsv</artifactId>
			<version>5.9</version>
		</dependency>
    </dependencies>
# Controller:

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

# Model:
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

# Repository:

    @Repository
    public interface RepositoryProduto extends JpaRepository<Produto, Long> {
    }

# Service: 

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
