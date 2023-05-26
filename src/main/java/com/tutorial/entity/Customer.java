package com.tutorial.entity;

import jakarta.persistence.Column;
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
     * @Table
     * note: jika kita ingin membuat table namnya harus jamak, supaya berbeda dengan Entity seperti: Entity>Customer <=> Table>customers
     *
     * @Column
     * untuk merubah nama kolom pada table database
     *
     * @GeneratedValue
     * memiliki fitur membuat Primary Key yang diisi secara otomatis
     * // Generated Value Strategy --> untuk melakukan management Id, atau bahkan menggunakan UUID
     *
     */

    @Id
//    @Column(name = "id_customers")
    private String id;

//    @Column(name = "name_customers")
    private String name;

    @Column(name = "primary_email")
    private String primaryEmail;

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

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }
}
