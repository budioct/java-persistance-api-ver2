package com.tutorial.entity.relationship.onetoone;

import com.tutorial.entity.relationship.onetoone.Credential;
import jakarta.persistence.*;

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

    // Relasi One to One dengan menggunakan Primary Key. di table tidak perlu ada FK di salah satu table relasi
    // method: mappedBy // nama dari pemetaan yang dibuat di table berlasi, harus sama dengan nama variable object referencenya!!!
    @OneToOne(mappedBy = "user")
    private Wallet wallet;

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
}
