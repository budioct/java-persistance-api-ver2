package com.tutorial.entity;

import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class EntityListenerTest {

    /**
     * Entity Listener
     * ● Saat kita memanipulasi data Entity, kadang kita ingin melakukan instruksi program yang sama
     * ● Misal, setiap data diubah, kita ingin mengubah kolom updated_at menjadi waktu saat ini misalnya
     * ● Jika dilakukan di semua kode program kita, maka akan menyulitkan
     * ● Untungnya JPA memiliki fitur Entity Listener, dimana kita bisa membuat sebuah class Listener,
     *   yang nanti akan dipanggil oleh JPA ketika sebuah operasi terjadi terhadap Entity nya
     *
     * Event Annotation Keterangan
     * @PrePersist      Dieksekusi sebelum melakukan persist entity
     * @PostPersist     Dieksekusi setelah melakukan persist entity
     * @PreRemove       Dieksekusi sebelum melakukan remove entity
     * @PostRemove      Dieksekusi setelah melakukan remove entity
     * @PreUpdate       Dieksekusi sebelum melakukan update entity
     * @PostUpdate      Dieksekusi setelah melakukan update entity
     * @PostLoad        Dieksekusi setelah melakukan load entity
     *
     */

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp(){
        log.info("On Entity Manager Factory");
        this.entityManagerFactory = UtilEntityManagerFactory.getEntityManagerFactory();
    }

    @AfterEach
    void tearDown(){
        log.info("Off Entity Manager Factory");
        this.entityManagerFactory.close();
    }

    @Test
    void testInsertEventListenerCategory(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Contoh Event Listener");

        entityManager.persist(category);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         categories
         *         (create_at,description,name,update_at)
         *     values
         *         (?,?,?,?)
         */

    }

}
