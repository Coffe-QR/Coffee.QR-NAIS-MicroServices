package rs.ac.uns.acs.nais.GraphDatabaseService.dto;


public class ItemCountPerOrder {
    private String orderId;
    private Long itemCount;

    public ItemCountPerOrder(String orderId, Long itemCount) {
        this.orderId = orderId;
        this.itemCount = itemCount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getItemCount() {
        return itemCount;
    }

    public void setItemCount(Long itemCount) {
        this.itemCount = itemCount;
    }

}