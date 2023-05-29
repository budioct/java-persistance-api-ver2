package com.tutorial.entity.relationship.onetomany;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;

    private String name;

    private Long price;

    private String description;

    // Relasi many to One dengan menggunakan Foreign Key. dengan relasi melalui column yang di sematkan sebagai relasi
    // @ManyToOne // menandakan relasi banyak ke 1 antara table
    // @JoinColumn// relasi melalui Foreign Key (yang reference ke table relasi)
    // method: name(dari Table A) referencedColumnName(referesi ke Table B)
    @ManyToOne
    @JoinColumn(
            name = "brand_id",
            referencedColumnName = "id"
    )
    private Brand brand;

    public Product() {

    }

    public Product(String name, Long price, String description, Brand brand) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.brand = brand;
    }

    public Product(String id, String name, Long price, String description, Brand brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.brand = brand;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
