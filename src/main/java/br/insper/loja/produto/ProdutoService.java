package br.insper.produto.produto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
public class ProdutoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://produto:8082/api/produto";

    public Produto saveProduto(Produto produto) {
        return restTemplate.postForObject(BASE_URL, produto, Produto.class);
    }

    public List<Produto> getProdutos() {
        return Arrays.asList(restTemplate.getForObject(BASE_URL, Produto[].class));
    }

    public Produto getProdutoById(String id) {
        try {
            return restTemplate.getForObject(BASE_URL + "/" + id, Produto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Produto diminuirEstoque(String id, int quantidade) {
        try {
            return restTemplate.postForObject(BASE_URL + "/" + id + "/diminuir?quantidade=" + quantidade,
                    null, Produto.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
