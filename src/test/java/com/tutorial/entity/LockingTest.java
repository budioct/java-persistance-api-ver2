package com.tutorial.entity;

import com.tutorial.entity.relationship.onetomany.Brand;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@Slf4j
public class LockingTest {

    /**
     * Locking ada 2 jenis di JPA
     * ● Optimistic Locking adalah proses multiple transaksi, dimana tiap transaksi tidak melakukan lock
     *   terhadap data. Namun sebelum melakukan commit transaksi, tiap transaksi akan mengecek
     *   terlebih dahulu apakah data sudah berubah atau belum, jika sudah berubah dikarenakan transaksi
     *   lain, maka transaksi tersebut akan melakukan rollback
     * ● Pessimistic Locking adalah proses multiple transaksi, dimana tiap transaksi akan melakukan lock
     *   terhadap data yang digunakan. Hal ini menyebabkan tiap transaksi harus menunggu data yang
     *   akan digunakan jika data tersebut sedang di lock oleh transaksi lain.
     *
     *   Version Column
     * ● Saat menggunakan Optimistic Locking, kita wajib membuat version column yang digunakan
     *   sebagai tanda perubahan yang sudah terjadi di data
     * ● JPA mendukung dua jenis tipe data version, Number (Integer, Short, Long) dan Timestamp
     *   (java.sql.Timestamp, java.time.Instant)
     * ● Untuk menandai bahwa attribute tersebut adalah version column, kita perlu menambahkan
     *   annotation Version
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/version
     * ● JPA akan secara otomatis mengupdate attribut version setiap kali kita melakukan update data tersebut
     */

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        log.info("On Entity Manager Factory");
        this.entityManagerFactory = UtilEntityManagerFactory.getEntityManagerFactory();
    }

    @AfterEach
    void tearDown() {
        log.info("Off Entity Manager Factory");
        this.entityManagerFactory.close();
    }

    @Test
    void testInsertOptimisticLocking(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("nokia");
        brand.setName("Nokia");
        brand.setCreateAt(LocalDateTime.now());
        brand.setUpdateAt(LocalDateTime.now());
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         brands
         *         (create_at,description,name,update_at,version,id)
         *     values
         *         (?,?,?,?,?,?)
         */

    }

    @Test
    void testUpdateOptimisticLocking1() throws InterruptedException {

        /**
         * // kita akan update id nokia pada table brands dengan delay 10 detik, yang juga ada commit update ke id yang sama tanpa sleep
         * jika itu terjadi maka akan muncul pesan error
         * jakarta.persistence.RollbackException: Error while committing the transaction
         * Caused by: org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect)
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia");
        brand.setName("Nokia Updated");
        brand.setUpdateAt(LocalDateTime.now());

        Thread.sleep(10 * 1000L);

        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testUpdateOptimisticLocking2() throws InterruptedException {

        // kita akan update id nokia pada table brands dengan delay 10 detik, yang juga ada commit update ke id yang sama tanpa sleep

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia");
        brand.setName("Nokia Updated");
        brand.setUpdateAt(LocalDateTime.now());

        // Thread.sleep(10 * 1000L);

        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();

    }

    /**
     * Pessimistic Locking
     * ● Pada Optimistic Locking, pengecekan versi data dilakukan ketika melakukan commit
     * ● Pada Pessimistic Locking, data akan di lock ketika di select, dan ini menjadikan transaksi lain tidak
     *   bisa mengubah data sampai transaksi yang pertama melakukan lock selesai melakukan commit transaksi
     * ● JPA Mendukung banyak jenis tipe Lock, namun tetap saja, itu tergantung ke database yang
     *   digunakan, bisa saja database yang digunakan tidak mendukung semua jenis Lock yang terdapat di JPA
     * ● Semua jenis Lock terdapat di enum LockModeType
     */

    @Test
    void testUpdatePessimisticLocking1() throws InterruptedException {

        // kita akan update id nokia pada table brands dengan delay 10 detik, yang juga ada commit update ke id yang sama tanpa sleep

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Nokia Updated ke 1");
        brand.setUpdateAt(LocalDateTime.now());

         Thread.sleep(10 * 1000L);

        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testUpdatePessimisticLocking2() throws InterruptedException {

        // kita akan update id nokia pada table brands dengan delay 10 detik, yang juga ada commit update ke id yang sama tanpa sleep
        // commit ini akan di suruh menunggu commit yang awal hinga selesai baru giliran nya jalan

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Nokia Update ke 2");
        brand.setUpdateAt(LocalDateTime.now());

//        Thread.sleep(10 * 1000L);

        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();

    }







}
