package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Local {
    @Id
    private String id;
    private String name;
    private String city;
    private LocalDate dateOfStartingPartnership;
    private boolean isActive;

    @Relationship(value = "IN_LOCAL", direction = Relationship.Direction.OUTGOING)
    private List<Table> table = new ArrayList<>();


    // Constructor
    public Local(String name, String city, LocalDate dateOfStartingPartnership, boolean isActive) {
        this.name = name;
        this.city = city;
        this.dateOfStartingPartnership = dateOfStartingPartnership;
        this.isActive = isActive;
    }

    // Getters and setters
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDateOfStartingPartnership() {
        return dateOfStartingPartnership;
    }

    public void setDateOfStartingPartnership(LocalDate dateOfStartingPartnership) {
        this.dateOfStartingPartnership = dateOfStartingPartnership;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
