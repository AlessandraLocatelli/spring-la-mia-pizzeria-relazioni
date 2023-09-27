package org.learning.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name cannot be empty.")
    @Size(max= 255)
    private String name;

    @NotBlank(message = "description cannot be empty.")
    @Size(max= 255)
    @Column(columnDefinition = "tinytext")
    private String description;

    @DecimalMax(value = "20.00", message = "price cannot be higher than 20€.")
    @DecimalMin(value = "5.00", message = "price cannot be less than 5€.")
    @Digits(integer = 4, fraction = 2, message= "max four digits")
    @Column( columnDefinition = "decimal(4,2)" , nullable = false)
    private BigDecimal price;

    @URL(message = "Insert an url.")
    @NotBlank(message = "insert url image")
    private String image;

    @OneToMany (mappedBy = "pizza", cascade = {CascadeType.REMOVE})
    private List<Offer> offers;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
