package com.tutorial.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    /**
     * Large Object
     * Kadang ada kasus kita membuat tipe kolom large object, yaitu tipe kolom yang digunakan untuk menampung data besar
     * Character Large Object, untuk menampung tipe data Text besar, dan
     * Binary Large Object, untuk menampung tipe data Binary besar (misal gambar, audio, dan lain-lain)
     *
     * Java Types
     * ● Namun, kadang menggunakan java.sql.Clob dan java.sql.Blob agak menyulitkan, karena harus membaca datanya secara manual menggunakan Java IO
     * ● JPA juga bisa digunakan untuk secara otomatis mengkonversi tipe data Large Object ke tipe data yang lebih familiar di Java
     * ● String dan char[] bisa digunakan untuk tipe data Character Large Object
     * ● byte[] bisa digunakan untuk tipe data Binary Large Object
     * ● Jika kita menggunakan tipe data ini, kita perlu menambahkan annotation Lob untuk memberi tahu ke JPA, bahkan ini adalah Large Object
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Lob
    private String description;

    @Lob
    private byte[] image;

    public Image() {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
