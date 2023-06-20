package com.tutorial.entity.relationship.onetoone;

import com.tutorial.entity.relationship.onetomany.Product;
import com.tutorial.entity.relationship.onetoone.Credential;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Set;

@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    private String name;

    // Relasi One to One dengan menggunakan Primary Key. di table tidak perlu ada FK di salah satu table relasi
    // @OneToOne // menandakan relasi 1 ke 1 antara table
    // @PrimaryKeyJoinColumn // relasi melalui PrimaryKey tiap table
    // method: name(dari Table A) referencedColumnName(referesi ke Table B)
    @OneToOne
    @PrimaryKeyJoinColumn(
            name = "id",
            referencedColumnName = "id"
    )
    private Credential credential;

    // Relasi One to One dengan menggunakan Foreign Key. dengan relasi melalui column yang di sematkan sebagai relasi
    // method: mappedBy // nama dari pemetaan yang dibuat di table berlasi, harus sama dengan nama variable object referencenya!!!
    @OneToOne(mappedBy = "user")
    private Wallet wallet;



    // Relasi Many to Many dengan menggunakan. akan ada table tambahan di tengah2 relasi
    // @ManyToMany // menandakan relasi banyak ke banyak antara table
    // @JoinTable // akan di buatkan tableLike di tengah 2 relasi antara TableA dan TableB
    // method: inner @JoinTable,  name(nama table yang generate)
    //                            joinColumns (primary key TableA)
    //                            inverseJoinColumns (primary keyTableB)
    //         inner @JoinColumn, name (namaKolom)
    //                            referencedColumnName (referensi dari Table primary key)
    @ManyToMany
    @JoinTable(
            name = "users_like_products",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    private Set<Product> likes;

    public User() {

    }

    public User(String name, Credential credential, Wallet wallet, Set<Product> likes) {
        this.name = name;
        this.credential = credential;
        this.wallet = wallet;
        this.likes = likes;
    }

    public User(String id, String name, Credential credential, Wallet wallet, Set<Product> likes) {
        this.id = id;
        this.name = name;
        this.credential = credential;
        this.wallet = wallet;
        this.likes = likes;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
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

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Set<Product> getLikes() {
        return likes;
    }

    public void setLikes(Set<Product> likes) {
        this.likes = likes;
    }

}
