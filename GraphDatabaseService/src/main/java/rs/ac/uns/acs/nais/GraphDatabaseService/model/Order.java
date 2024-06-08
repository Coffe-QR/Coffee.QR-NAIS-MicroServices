package rs.ac.uns.acs.nais.GraphDatabaseService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    @Id
    private String id;
    private double price;
    private String description;
    private long tableId;
    private Table tableOrigin;
    private long localId;
    private Local local;
    private LocalDate date;
    private boolean isActive;

    @Relationship(value = "ORDERED", direction = Relationship.Direction.OUTGOING)
    private List<Item> items = new ArrayList<>();


    // Constructor
    public Order(double price, String description, long tableId, long localId, LocalDate date, boolean isActive) {
        this.price = price;
        this.description = description;
        this.tableId = tableId;
        this.localId = localId;
        this.date = date;
        this.isActive = isActive;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public long getTableId() {
        return tableId;
    }

    public Table getTableOrigin() {
        return tableOrigin;
    }

    public long getLocalId() {
        return localId;
    }

    public Local getLocal() {
        return local;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
