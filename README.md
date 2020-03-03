# nstest-campaign
API para manipulação de usuários.

## Definição de arquitetura
A API foi desenvolvida utilizando um projeto Maven para gerenciamento de dependências, com Java 8 e Spring Boot 2.2.5, os frameworks utilizados foram Spring Data e Spring Web. O banco de dados utilizado é o MySql, e o versionamento com Git.

## Solução do teste
As associações de campanha/usuários são feitas através de uma terceira tabela que é a de "Times do Coração". Ela é responsável por fazer essa tarefa, então qualquer processo de alteração nas campanhas ou nos times, não existe necessidade de sincronização pois a mesma acontece automaticamente no banco de dados.

## Como iniciar
1. Clonar o repositório do Git.
2. Importar para alguma IDE, pode ser Intellij, Eclipse ou qualquer outra.
3. Baixar as dependencias do Maven.
4. O banco de dados é um MySql, então é necessário configurar um MySql local e se necessário, mudar as configurações de porta, nome do DB, usuário ou senha. Também não é necessário criar tabelas ou afins, o Spring já faz todo esse processo ao iniciar a aplicação.
5. Executar a classe UserApplication.java.
6. Aguardar a subida da aplicação, após isso a API está pronta para consumo, na porta 8070, caso não seja alterada a porta.

## Definição da aplicação

### [POST] /campaign<br/>
Funcionalidade que cadastra determinado usuário com base nos dados informados no body.<br/>
Request
```ruby
{
	"heartTeam": {
		"id": 1
	},
	"name": "Matheus",
	"email": "matheusbezulle@bol.com.br",
	"birthday": "1999-01-07"
}
```

Response
`[201] CREATED`<br/>
```ruby
{
    "success": true,
    "body": {
        "userId": 5,
        "heartTeam": {
            "id": 1
        },
        "name": "Matheus",
        "email": "matheusbezulle@gemail.com.br",
        "birthday": "1999-01-07"
    }
}
```
`[200] OK`<br/>
Caso já exista um usuário para determinado email.
```ruby
{
    "success": false,
    "message": "Já existe um usuário cadastrado para este email.",
    "body": [
        {
            "id": 1,
            "name": "Campanha 01",
            "validityInitDate": "2020-03-01",
            "validityFinalDate": "2020-03-03"
        }
    ]
}
```

`[400] BAD_REQUEST`<br/>
Os parâmetros informados são inválidos.

`[500] INTERNAL_SERVER_ERROR`<br/>
Ocorreu um erro na aplicação durante o processamento da requisição.
