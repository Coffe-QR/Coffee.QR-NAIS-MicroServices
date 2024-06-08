package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "locals")
public class Local {
    @Id
    private String id;
    private String name;
    private String description;
    private String city;
    private String country;
    private int capacity;

    public Local(String id, String name, String description, String city, String country, int capacity){
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.country = country;
        this.capacity = capacity;
    }

    public Local() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
