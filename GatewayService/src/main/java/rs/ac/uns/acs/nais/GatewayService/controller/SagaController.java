package rs.ac.uns.acs.nais.GatewayService.controller;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import rs.ac.uns.acs.nais.GatewayService.model.Item;
import rs.ac.uns.acs.nais.GatewayService.service.ElasticSearchService;
import rs.ac.uns.acs.nais.GatewayService.service.GraphService;

@RestController
@RequestMapping("/saga")
public class SagaController {

    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    GraphService graphService;

    @PostMapping("/addItem")
    public Mono<ResponseEntity<?>> addItem(@RequestBody Item data) {
        return elasticSearchService.addItem(data)
                .flatMap(result -> graphService.addItem(result)//TREBA RESPONSE NEKAKO DA SE DOBAVI
                .map(result1 -> ResponseEntity.ok().body(result))
                .onErrorResume(error -> elasticSearchService.compensateAddItem(result.getId())
                        .then(Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))));
    }


}
