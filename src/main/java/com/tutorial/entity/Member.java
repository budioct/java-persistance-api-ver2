package com.tutorial.entity;

import com.tutorial.embeddedable.Name;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "members")
public class Member {

    /**
     * @Embedded Saat kita menggunakan atribut class Embedded di Class Entity, kita wajib menggunakan annotation Embedded untuk menandai bahwa itu ada class Embedded
     * <p>
     * Collection
     * JPA mendukung database relation, namun pada kasus yang sangat sederhana, kita bisa membuat atribut dengan tipe Collection yang secara otomatis menggunakan
     * Table Join untuk mengambil datanya.
     * <p>
     * Tipe Data Collection
     * ● JPA mendukung banyak tipe data collection, seperti List<T> atau Set<T>
     * ● Saat kita menggunakan tipe data collection, secara otomatis kita perlu membuat table untuk menyimpan data collection nya
     * ● Atribut collection harus kita tandai dengan annotation @ElementCollection
     * ● Dan untuk menentukan table mana yang digunakan sebagai table untuk menyimpan Collection, kita gunakan annotation @CollectionTable
     * ● Dan untuk menentukan kolom mana di table collection yang kita ambil datanya, kita bisa gunakan annotation @Column seperti biasaya
     * Kekurangan Collection saat kita mau update table relasi (FK) makan data sebelumnya akan di hapus semua oleh hibernate lalu di insert ulang semua,
     * mengakibatka id nya ter couter naik agkanya maka nya datanya jadi rusak
     * <p>
     * Map Collection
     * ● Selain List<T> dan Set<T>, kita juga bisa menggunakan collection Map<K, V> sebagai atribut di Class Entity
     * ● Cara penggunaannya hampir mirip dengan List<T> atau Set<T>, namun karena pada Map<K, V>
     * terdapat key dan value, maka kita harus memberi tahu JPA untuk kolom key dan kolom value
     * menggunakan annotation MapKeyColumn, sedangkan untuk value nya tetap menggunakan annotation Column.
     * <p>
     * Kekurangan Collection saat kita mau update table relasi (FK) makan data sebelumnya akan di hapus semua oleh hibernate lalu di insert ulang semua,
     * mengakibatka id nya ter couter naik agkanya maka nya datanya jadi rusak
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Name name; // class yang bisa di Embbed dan di manfaatkan field nya

    private String email;

    // @ElementCollection // menandai collection JPA
    // @CollectionTable // mementukan table untuk di simpan data
    // @JoinColumn // relasi table dengan referensi tableB antara FK dan PK pada relasi tableA
    // @Column // kolom mana yang akan di simpan
    @ElementCollection // menandai collection JPA
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @Column(name = "name")
    private List<String> hobbies;

    // @MapKeyColumn(name = "name") // menandai key Map
    // @Column(name = "value") // menandai value Map
    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, Integer> skills;

    // memanfaatkan field yang tidak ada column di table
    @Transient // mengabaikan field yang tidak ada di column table
    private String fullName;

    @PostLoad // Listener di Entity Class
    public void postLoad() {
        fullName = name.getTitle() + ". " + name.getFirstName()
                + " " + name.getMiddleName() + " " + name.getLastName();
    }

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

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
