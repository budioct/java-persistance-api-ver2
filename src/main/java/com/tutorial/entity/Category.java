package com.tutorial.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {

    /**
     * Class Entity wajib menambahkan annotation @Entity dan @Id, syarat untuk membuat table ORM
     * Class Entity adalah sebuah class Java Bean, dimana tiap attribute (yang memiliki getter dan setter)
     * di Class tersebut merepresentasikan kolom di table database
     *
     * @Table note: jika kita ingin membuat table namnya harus jamak, supaya berbeda dengan Entity seperti: Entity>Customer <=> Table>customers
     * @Column untuk merubah nama kolom pada table database
     * @GeneratedValue memiliki fitur membuat Primary Key yang diisi secara otomatis
     * // Generated Value Strategy --> untuk melakukan management Id, atau bahkan menggunakan UUID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    /**
     * Class Entity wajib memiliki default constructor yang tidak memiliki parameter, hal ini agar JPA bisa
     * membuat object baru tanpa parameter ketika melakukan mapping data dari table ke object Entity
     */
    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
