package be.ucll.da.dentravak.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private UUID sandwichId;
    private String name;
    private String breadType;
    private String creationDate;
    private BigDecimal price;
    private String mobilePhoneNumber;

    private Order() {}

    public Order(UUID sandwichId, String name, String breadType, BigDecimal price, String mobilePhoneNumber) {
        this.sandwichId = sandwichId;
        this.name = name;
        this.breadType = breadType;
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.price = price;

        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSandwichId() {
        return sandwichId;
    }

    public void setSandwichId(UUID sandwichId) {
        this.sandwichId = sandwichId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreadType() {
        return breadType;
    }

    public void setBreadType(String breadType) {
        this.breadType = breadType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }



    /*public static class OrderBuilder {

        private UUID sandwichId;
        private String name;
        private String breadType;
        private Date creationDate;
        private BigDecimal price;
        private String mobilePhoneNumber;

        private OrderBuilder() {}

        public static OrderBuilder anOrder() { return new OrderBuilder(); }

        public void withSandwichId(UUID sandwichId) {
            this.sandwichId = sandwichId;
        }

        public void withName(String name) {
            this.name = name;
        }

        public void withBreadType(String breadType) {
            this.breadType = breadType;
        }

        public void withCreationDate(Date creationDate) {
            this.creationDate = creationDate;
        }

        public void withPrice(BigDecimal price) {
            this.price = price;
        }

        public void withMobilePhoneNumber(String mobilePhoneNumber) {
            this.mobilePhoneNumber = mobilePhoneNumber;
        }

        public Order build() {
            Order order = new Order();
            order.sandwichId = sandwichId;
            order.name = name;
            order.breadType = breadType;
            order.creationDate = creationDate;
            order.price = price;
            order.mobilePhoneNumber = mobilePhoneNumber;
            return order;
        }

    }*/
}
