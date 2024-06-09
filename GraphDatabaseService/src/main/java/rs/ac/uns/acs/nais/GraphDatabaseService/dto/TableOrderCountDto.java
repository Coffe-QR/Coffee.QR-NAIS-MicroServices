package rs.ac.uns.acs.nais.GraphDatabaseService.dto;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

public class TableOrderCountDto{
    private String tableName;
    private long orderCount;

    public TableOrderCountDto(String tableName, long orderCount) {
        this.tableName = tableName;
        this.orderCount = orderCount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }
}