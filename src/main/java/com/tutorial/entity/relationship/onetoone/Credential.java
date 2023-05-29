package com.tutorial.entity.relationship.onetoone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    private String id;

    private String email;

    private String password;

    // Relasi One to One dengan menggunakan Primary Key. di table tidak perlu ada FK di salah satu table relasi
    // method: mappedBy // nama dari pemetaan yang dibuat di table berlasi, harus sama dengan nama variable object referencenya!!!
    @OneToOne(mappedBy = "credential")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
