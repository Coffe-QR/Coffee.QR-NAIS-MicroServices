package rs.ac.uns.acs.nais.GatewayService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import rs.ac.uns.acs.nais.GatewayService.GatewayServiceApplication;
import rs.ac.uns.acs.nais.GatewayService.model.Item;

@Service
public class ElasticSearchService {
    private final WebClient webClient;
    public ElasticSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://elasticsearch-database-service").build();
    }

    public Mono<Item> addItem(Item data) {
        return webClient.post()
                .uri("/items")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<Void> compensateAddItem(String itemId) {
        return webClient.delete()
                .uri("/items/{itemId}", itemId)
                .retrieve()
                .bodyToMono(Void.class);
    }


}
