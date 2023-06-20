package com.tutorial.entity.relationship.onetomany;

import com.tutorial.entity.relationship.inheritance.mappedsuperclass.AuditableEntity;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
@Table(name = "brands")
// set named query, dengan name sebagai alias query. untuk mengakses nya tinggal panggil nama aliasnya
@NamedQueries({
        @NamedQuery(name = "Brand.findAll", query = "select b from Brand b"),
        @NamedQuery(name = "Brand.findAllByName", query = "select b from Brand b where b.name = :name"),
})
@NamedNativeQueries(
        @NamedNativeQuery(name = "Brand.native.findAll", query = "select * from brands", resultClass = Brand.class)
)
public class Brand extends AuditableEntity<String> {

    @Id
    private String id;

    private String name;

    private String description;

    // Relasi One to many dengan menggunakan Foreign Key. table yang beralis akan dibuat kolom tambahan untuk relasi antar table
    // method: mappedBy // nama dari pemetaan yang dibuat di table berlasi, harus sama dengan nama variable object referencenya!!!
    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    // Locking jenis Optimistic
    @Version
    private Long version;

    public Brand() {

    }

    public Brand(String name, String description, List<Product> products, Long version) {
        this.name = name;
        this.description = description;
        this.products = products;
        this.version = version;
    }

    public Brand(String id, String name, String description, List<Product> products, Long version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
        this.version = version;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
