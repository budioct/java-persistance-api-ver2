package com.tutorial.entity;

import com.tutorial.eventlisterner.UpdatedAtAware;
import com.tutorial.eventlisterner.UpdatedAtListener;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Table(name = "categories")
@EntityListeners({
        UpdatedAtListener.class
}) // eventListerner
public class Category implements UpdatedAtAware {

    /**
     * Class Entity wajib menambahkan annotation @Entity dan @Id, syarat untuk membuat table ORM
     * Class Entity adalah sebuah class Java Bean, dimana tiap attribute (yang memiliki getter dan setter)
     * di Class tersebut merepresentasikan kolom di table database
     *
     * @Table note: jika kita ingin membuat table namnya harus jamak, supaya berbeda dengan Entity seperti: Entity>Customer <=> Table>customers
     * @Column untuk merubah nama kolom pada table database
     * @GeneratedValue memiliki fitur membuat Primary Key yang diisi secara otomatis
     * // Generated Value Strategy --> untuk melakukan management Id, atau bahkan menggunakan UUID
     *
     * Date dan Time
     * direkomendasikan menggunakan package java.time
     * Namun jika menggunakan package java.util, disarankan menambahkan annotation Temporal,
     * untuk memberi tahu detail tipe data di database, apakah Date, Time atau Timestamp
     *
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Calendar createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

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

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
