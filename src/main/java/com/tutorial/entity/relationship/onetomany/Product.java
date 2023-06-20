package com.tutorial.entity.relationship.onetomany;

import com.tutorial.entity.relationship.onetoone.User;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;
import java.util.Set;

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
    // @JoinColumn// akan membuat kolom foreign brand_id table products. relasi melalui Foreign Key (yang reference ke table relasi)
    // method: name(dari Table A) referencedColumnName(referesi ke Table B)
    // method: fetch adalah bentuk pengambilan data table EAGER(sekaligus dengan table relasinya) / LAZY(betahap, tidak langsung sekaligus dengan table relasinya)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "brand_id",
            referencedColumnName = "id"
    )
    private Brand brand;

    // Relasi Many to Many dengan menggunakan Table tambahan sebagai perantara TableA dan TableB
    // method: mappedBy // nama dari pemetaan yang dibuat di table berlasi, harus sama dengan nama variable object referencenya!!!
    @ManyToMany(mappedBy = "likes")
    private Set<User> likeBy;

    public Product() {

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

    public Product(String name, Long price, String description, Brand brand, Set<User> likeBy) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.likeBy = likeBy;
    }

    public Product(String id, String name, Long price, String description, Brand brand, Set<User> likeBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.likeBy = likeBy;
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

    public Set<User> getLikeBy() {
        return likeBy;
    }

    public void setLikeBy(Set<User> likeBy) {
        this.likeBy = likeBy;
    }
}
