# Swagger

O Swagger é uma especificação que define um formato padrão para descrever 
APIs RESTful, ou seja, um conjunto de regras que descreve como a documentação 
de uma API deve ser escrita e formatada.

A documentação gerada pelo Swagger inclui informações detalhadas sobre os 
recursos da API, seus parâmetros, cabeçalhos, códigos de resposta, tipos 
de dados e muito mais. O Swagger também permite interagir com a API 
diretamente na documentação gerada.


### SpringDoc

O SpringDoc, é uma biblioteca do Spring Framework que fornece uma implementação 
da especificação OpenAPI para gerar a documentação da API automaticamente. Ele 
usa as anotações do Spring Framework para mapear a API e gerar a documentação 
da API no formato OpenAPI.

Em resumo, o Swagger é uma especificação para descrever APIs, enquanto o
SpringDoc é uma biblioteca para gerar a documentação da API usando a especificação
OpenAPI



### Instalando



Para começar a usar o SpringDoc precisamos acrescentar da seguinte dependência 
no arquivo `build.gradle`:

```
implementation 'org.springdoc:springdoc-openapi-ui:1.7.0'
```
<small>**Obs**: A versão do SpringDoc compatível com o Spring Boot 2.7.x é a 1.7.0.</small>



### Configurando



Após adicionar as dependências, é preciso configurar o Swagger no projeto. 
Para isso, é necessário criar uma classe de configuração para que possamos definir 
algumas informações da API (título, descrição, versão e endpoints). Nomeamos a 
classe como `SwaggerConfig`:

```java
@Configuration
public class SwaggerConfig{
}
```

Essa classe também pode ser usada para especificar outras configurações do Swagger,
como esquemas de autenticação e informações de contato.

A anotação `@Configuration` é usada para indicar que a classe contém a configuração
do SpringDoc/Swagger e que os métodos dessa classe são responsáveis por definir e 
configurar as propriedades necessárias para a geração da documentação da API.

<small>**Obs:** A anotação @Configuration é usada no Spring para indicar que uma 
classe é uma classe de configuração, ou seja, que ela contém métodos que 
configuram o ambiente de execução da aplicação.</small>

A partir desse daqui ja podemos acessar a documentação que o SpringDoc gerou para 
nossa API. O acesso pode ser feito no link: http://localhost:8080/swagger-ui/index.html.
Assim que acessarmos veremos a seguinte página: 

![Swagger Pagina Inicial](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/configuracao-swagger.png)

A mensagem: "No operations defined in spec!" indica que não foram encontradas operações
(métodos) definidos na especificação Swagger. Isso significa que não foi localizado
nenhum endpoint ou rota para ser documentado na api. Isso pode ocorrer por vários motivos,
entre eles: 

* A especificação OpenAPI/Swagger pode estar incompleta ou vazia. 
* Não há código fonte implementado que corresponda aos endpoints definidos na 
especificação.
* Os endpoints estão definidos em um arquivo YAML ou JSON que não foi 
configurado corretamente para o Swagger.

No caso deste tutorial, ainda não definimos endpoint para aplicação. Vamos definir
um Controller para expor um método get:

```java
@RestController
@RequestMapping("/api")
public class Controller{
    
    @GetMapping
    public String getAll(){
        return "GET ALL";
    }
}
```

Agora recarregando o projeto e a pagina da documentação poderemos visualizar 
os detalhes sobre endpoint definido:

![swagger/endpoint-get.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/endpoint-get.png)

A medida que adicionamos mais endpoints na aplicação o SpringDoc vai acrescentando
na documentação alguns detalhes sobre o endpoint automaticamente. Outros detalhes
podem ser editados através de algumas anotações (veremos algumas mais a frente).



### Definindo Informações Sobre a API



O método `apiInfo()` retorna um objeto `OpenAPI`, que representa a documentação da 
API. O método utiliza a classe `Info()` para definir informações gerais da API, 
como título, descrição, contato, versão, licença e termos de serviço.

```java
@Configuration
public class SwaggerConfig{
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Swagger API")
                                .description("")
                                .contact(new Contact()
                                        .name("DB")
                                        .email("info@dbserver.com.br")
                                        .url("https://db.tec.br/")
                                )
                                .version("1.0.0")
                                .license(new License())
                                .termsOfService("")
                );
    }
}
```
O que cada um dos métodos usado faz:

* `title()`: define o título da API como "Swagger API"
* `description()`: define uma descrição
* `contact()`: define as informações de contato, como nome, e-mail e uma URL
* `version()`: define a versão da API
* `license()`: define uma licença
* `termsOfService()`: define os termos de serviço

Como resultado teremos: 

![swagger/api-info.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/api-info.png)



### Detalhando retorno dos endpoints



#### `@Operation`

A anotação `@Operation` é usada para descrever as operações suportadas pela API, 
como `GET`, `POST`, `PUT`, `DELETE` etc. Essa anotação é usada para fornecer informações 
adicionais sobre a operação, como o nome, a descrição e os parâmetros de entrada.

Exemplo de uso: 

```java
public class Controller {
    
    @Operation(
            summary = "Retorna um dado da API",
            description = "Descrição detalhada da operação",
            tags = {"Usuários"}
    )
    @GetMapping("/{id}")
    public String get(@PathVariable("id") Long id) {
        return "GET id:" + id;
    }
}   
```
O atributo `tags` da anotação `@Operation` do Swagger é usado para especificar uma ou mais 
tags associadas a uma operação na API RESTful.

As tags são usadas para agrupar operações relacionadas na documentação da API e fornecer 
um meio para os usuários navegar facilmente pela documentação da API. Por exemplo, uma 
API pode ter tags como "Usuários", "Produtos" e "Pedidos", que agrupam operações relacionadas.

![swagger/tag-separada.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/tag-separada.png)

O atributo `summary` descreve a operação e o `description` serve para detalhar a operação descrita.

![swagger/anotacao-operation.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/anotacao-operation.png)


#### `@ApiResponses e @ApiResponse`

A anotação `@ApiResponses` é usada para definir as respostas HTTP que a API pode 
retornar. Essa anotação recebe uma lista de anotações `@ApiResponse`.

Já anotação `@ApiResponse` é usada para definir uma resposta específica 
para um código de status HTTP. Ela fornece informações adicionais sobre a resposta 
específica, como o código de status, a descrição e os tipos de mídia.

Exemplo de uso: 
```java
public class Controller{
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sucesso"),
            @ApiResponse(responseCode = "209", description = "conflito")
    })
    @PostMapping
    public String post(@RequestBody String any){
        return "POST any: " + any;
    }
}
```

Documentação: 

![swagger/responses.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/responses.png)



### Testando a API



Normalmente usamos outras ferramentas como Postman ou Insomnia para testar o retorno dos endpoints de uma API. Entretanto podemos testar o retorno da API diretamente da Swagger UI. Para isso basta selecionar qual operação vamos testar:

![swagger/tag-separada.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/tag-separada.png)

Clicamos em `Try it Out`:

![swagger/testando-get-0.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/testando-get-0.png)

A caixa para entrar com o parâmetro da requisição e um botão serão habilitados:

![swagger/testando-get-1.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/testando-get-1.png)

Após passar o paramento basta executar a operação e conferir a resposta retornada pela API:

![swagger/testando-get-2.png at main · systemagic-91/swagger (github.com)](https://github.com/systemagic-91/swagger/blob/main/src/main/resources/static/testando-get-2.png)

