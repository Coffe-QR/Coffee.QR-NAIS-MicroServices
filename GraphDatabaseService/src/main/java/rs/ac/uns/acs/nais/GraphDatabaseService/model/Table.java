package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

public class Table {
    @Id
    private String id;
    private String name;
    private long capacity;
    private boolean isSmokingArea;
    private long localId;
    private Local place;

    @Relationship(value = "ON_TABLE", direction = Relationship.Direction.OUTGOING)
    private List<Order> orders = new ArrayList<>();

    // Constructor
    public Table(String name, long capacity, boolean isSmokingArea, long localId) {
        this.name = name;
        this.capacity = capacity;
        this.isSmokingArea = isSmokingArea;
        this.localId = localId;
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

    public long getCapacity() {
        return capacity;
    }

    public boolean isSmokingArea() {
        return isSmokingArea;
    }

    public long getLocalId() {
        return localId;
    }

    public Local getPlace() {
        return place;
    }

    public void setPlace(Local place) {
        this.place = place;
    }
}
