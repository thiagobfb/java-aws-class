# Geocoding API Tests

Exemplos para testar o endpoint:

`GET /api/geocoding/search?street={street}&city={city}&state={state}`

Base URL local:

`http://localhost:8080`

## Requisicoes de exemplo

```bash
curl "http://localhost:8080/api/geocoding/search?street=Avenida%20Paulista%2C%201000&city=Sao%20Paulo&state=SP"
curl "http://localhost:8080/api/geocoding/search?street=Praca%20da%20Se&city=Sao%20Paulo&state=SP"
curl "http://localhost:8080/api/geocoding/search?street=Rua%20da%20Quitanda%2C%2086&city=Rio%20de%20Janeiro&state=RJ"
curl "http://localhost:8080/api/geocoding/search?street=Esplanada%20dos%20Ministerios&city=Brasilia&state=DF"
curl "http://localhost:8080/api/geocoding/search?street=Avenida%20Afonso%20Pena%2C%201377&city=Belo%20Horizonte&state=MG"
curl "http://localhost:8080/api/geocoding/search?street=Rua%20XV%20de%20Novembro%2C%20320&city=Curitiba&state=PR"
```

## Enderecos sugeridos

- `street=Avenida Paulista, 1000` `city=Sao Paulo` `state=SP`
- `street=Praca da Se` `city=Sao Paulo` `state=SP`
- `street=Rua da Quitanda, 86` `city=Rio de Janeiro` `state=RJ`
- `street=Esplanada dos Ministerios` `city=Brasilia` `state=DF`
- `street=Avenida Afonso Pena, 1377` `city=Belo Horizonte` `state=MG`
- `street=Rua XV de Novembro, 320` `city=Curitiba` `state=PR`

## Casos de erro

Sem resultado provavel:

```bash
curl -i "http://localhost:8080/api/geocoding/search?street=Rua%20Inexistente%20123456&city=Sao%20Paulo&state=SP"
```

Parametro obrigatorio faltando:

```bash
curl -i "http://localhost:8080/api/geocoding/search?city=Sao%20Paulo&state=SP"
```
