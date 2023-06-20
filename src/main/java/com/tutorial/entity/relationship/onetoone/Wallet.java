package com.tutorial.entity.relationship.onetoone;

import jakarta.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long balance;

    // Relasi One to One dengan menggunakan Foreign Key. dengan relasi melalui column yang di sematkan sebagai relasi
    // @OneToOne // menandakan relasi 1 ke 1 antara table
    // @JoinColumn// akan membuat kolom id table wallets. relasi melalui Foreign Key user (yang reference ke table relasi)
    // method: name(dari Table A) referencedColumnName(referesi ke Table B)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
