// Define o pacote onde a classe de teste está localizada.
package com.brunodias.product_service;

// Importações de classes necessárias para o teste.
import io.restassured.RestAssured; // Biblioteca para testar APIs REST.
import org.hamcrest.Matchers; // Biblioteca para criar asserções (verificações) legíveis.
import org.junit.jupiter.api.BeforeEach; // Anotação para um método que roda antes de cada teste.
import org.junit.jupiter.api.Test; // Anotação que marca um método como um caso de teste.
import org.springframework.boot.test.context.SpringBootTest; // Anotação principal para testes de integração com Spring Boot.
import org.springframework.boot.test.web.server.LocalServerPort; // Injeta a porta do servidor que foi iniciada aleatoriamente.
import org.springframework.boot.testcontainers.service.connection.ServiceConnection; // Facilita a conexão entre o Spring e um container de serviço.
import org.springframework.context.annotation.Import; // Permite importar configurações adicionais.
import org.springframework.stereotype.Service; // Anotação de serviço do Spring (não usada diretamente aqui, mas importada).
import org.testcontainers.containers.MongoDBContainer; // Classe do Testcontainers para gerenciar um container Docker do MongoDB.

/*
 * A anotação @Import é usada para carregar classes de configuração adicionais.
 * Neste caso, provavelmente importa configurações relacionadas ao Testcontainers.
 */
@Import(TestcontainersConfiguration.class)
/*
 * @SpringBootTest inicia o contexto completo do Spring Boot para um teste de integração.
 * 'webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT' sobe o servidor web em uma porta aleatória disponível,
 * evitando conflitos com outros processos.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	/*
	 * @ServiceConnection é uma anotação do Spring Boot 3.1+ que gerencia automaticamente a conexão
	 * com o serviço rodando no container (neste caso, o MongoDB). Ela configura as propriedades
	 * de conexão (como a URI do banco de dados) para que a aplicação se conecte ao container
	 * do Testcontainers sem configuração manual.
	 */
	@ServiceConnection
	// Declara um container do MongoDB usando a imagem "mongo:7.0.5".
	// 'static' garante que o mesmo container seja compartilhado por todos os testes nesta classe.
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:7.0.5");

	/*
	 * @LocalServerPort injeta a porta HTTP aleatória (definida em @SpringBootTest)
	 * na variável 'port', para que possamos usá-la nas chamadas da API.
	 */
	@LocalServerPort
	private Integer port;

	/*
	 * O bloco 'static {}' é um inicializador estático. Ele é executado apenas uma vez quando a classe é carregada,
	 * antes de qualquer teste. Aqui, ele é usado para iniciar o container do MongoDB.
	 */
	static {
		mongoDbContainer.start();
	}

	/*
	 * O método anotado com @BeforeEach é executado antes de cada método de teste (@Test).
	 * É usado para configurar o estado inicial para cada teste.
	 */
	@BeforeEach
	void setup() {
		// Configura o RestAssured para usar o host e a porta da nossa aplicação em teste.
		RestAssured.baseURI = "http://localhost"; // Corrigido de "htt://" para "http://"
		RestAssured.port = port;
	}

	// @Test marca este método como um caso de teste a ser executado pelo JUnit.
	// O nome do método descreve o que ele testa: a criação de um produto.
	@Test
	void deveCriarUmProduto() {
		// Cria o corpo da requisição (payload) em formato JSON usando um Text Block do Java.
		String requestBody = """
                {
                    "name": "Iphone 15",
                    "description": "Iphone 15 smartphone from apple",
                    "price": 1000
                }
                """;

		// Inicia a construção da requisição HTTP usando RestAssured.
		RestAssured.given()
				.contentType("application/json") // Define o cabeçalho 'Content-Type' como 'application/json'.
				.body(requestBody) // Adiciona o corpo JSON à requisição.
				.when() // Indica que a próxima ação é executar a requisição.
				.post("/api/product") // Envia uma requisição POST para o endpoint '/api/product'.
				.then() // Inicia a fase de validação da resposta.
				.statusCode(201) // Verifica se o status HTTP da resposta é 201 (Created).
				.body("id", Matchers.notNullValue()) // Verifica se o corpo da resposta contém um campo "id" que não é nulo.
				.body("name", Matchers.equalTo("Iphone 15")) // Verifica se o campo "name" é igual a "iphone 15".
				.body("description", Matchers.equalTo("Iphone 15 smartphone from apple")) // Verifica a descrição.
				.body("price", Matchers.equalTo(1000)); // Verifica se o campo "price" é igual a 1000.
	}
}