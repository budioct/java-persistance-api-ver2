package com.tutorial.entity.relationship.onetomany;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    private String id;

    private String name;

    private String description;

    // Relasi One to many dengan menggunakan Primary Key. di table tidak perlu ada FK di salah satu table relasi
    // method: mappedBy // nama dari pemetaan yang dibuat di table berlasi, harus sama dengan nama variable object referencenya!!!
    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    public Brand() {

    }

    public Brand(String name, String descriptions, List<Product> products) {
        this.name = name;
        this.description = descriptions;
        this.products = products;
    }

    public Brand(String id, String name, String descriptions, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = descriptions;
        this.products = products;
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

    public String getDescriptions() {
        return description;
    }

    public void setDescriptions(String descriptions) {
        this.description = descriptions;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
