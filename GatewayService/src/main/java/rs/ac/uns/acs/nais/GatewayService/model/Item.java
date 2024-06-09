package rs.ac.uns.acs.nais.GatewayService.model;




public class Item {

    public enum ItemType {
        FOOD,
        DRINK
    }

    //@Id
    private String id;
    private ItemType type;
    private String name;
    private String description;
    private double price;
    private String picture;
    private String localId;

    public Item(String id,ItemType type, String name, String description, double price, String picture,String localId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.localId = localId;
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
