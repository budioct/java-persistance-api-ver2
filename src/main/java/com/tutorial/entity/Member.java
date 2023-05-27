package com.tutorial.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "members")
public class Member {

    /**
     * @Embedded
     * Saat kita menggunakan atribut class Embedded di Class Entity, kita wajib menggunakan annotation Embedded untuk menandai bahwa itu ada class Embedded
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Name name; // class yang bisa di Embbed dan di manfaatkan field nya

    private String email;


    public Member() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
