package com.tutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    /**
     * Class Entity wajib menambahkan annotation @Entity dan @Id, syarat untuk membuat table ORM
     * Class Entity adalah sebuah class Java Bean, dimana tiap attribute (yang memiliki getter dan setter)
     * di Class tersebut merepresentasikan kolom di table database
     *
     * note: jika kita ingin membuat table namnya harus jamak, supaya berbeda dengan Entity seperti: Entity>Customer <=> Table>customers
     */

    @Id
    private String id;

    private String name;

    /**
     * Class Entity wajib memiliki default constructor yang tidak memiliki parameter, hal ini agar JPA bisa
     * membuat object baru tanpa parameter ketika melakukan mapping data dari table ke object Entity
     */
    public Customer() {
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
}
