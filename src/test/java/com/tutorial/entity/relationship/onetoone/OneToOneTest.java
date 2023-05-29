package com.tutorial.entity.relationship.onetoone;

import com.tutorial.entity.relationship.onetoone.Credential;
import com.tutorial.entity.relationship.onetoone.User;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class OneToOneTest {

    /**
     * Table Relationship
     * ● Salah satu fitur yang paling banyak digunakan saat menggunakan Relational Database adalah Table Relationship
     * ● Di kelas MySQL, kita sudah bahas secara lengkap tentang jenis-jenis relasi table, dari One to One, One to Many dan Many to Many
     * ● JPA juga bisa menangani Table Relationship secara otomatis, hal ini menjadikan memanipulasi data Entity yang berhubungan akan lebih mudah
     *
     * One to One Relationship
     * ● One to One Relationship adalah relasi yang paling mudah, dimana satu table berelasi dengan satu data di table lain
     * ● Ada beberapa cara melakukan relasi pada one to one, dengan Foreign Key atau dengan Primary Key yang sama
     * ● JPA mendukung keduanya, baik itu menggunakan Foreign Key ataupun menggunakan Primary Key yang sama
     *
     * OneToOne Annotation
     * ● Untuk menambah atribut di Entity yang berelasi dengan Entity lain, kita perlu menambahkan menggunakan annotation OneToOne
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/onetoone
     * ● Dan untuk memberi tahu JPA tentang kolom yang digunakan untuk melakukan JOIN Foreignn Key, kita perlu tambahkan annotation JoinColumn
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/joincolumn
     * ● Namun jika JOIN nya menggunakan primary key yang sama, kita bisa gunakan annotation PrimaryKeyJoinColumn
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/primarykeyjoincolumn
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
    void testInsertOneToOnePrimaryKey(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Credential credential = new Credential();
        credential.setId("budhi");
        credential.setEmail("budhi@test.com");
        credential.setPassword("rahasia");
        entityManager.persist(credential);

        User user = new User();
        user.setId("budhi");
        user.setName("budhi octaviansyah");
//        user.setCredential(credential);
        entityManager.persist(user);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         credentials
         *         (email,password,id)
         *     values
         *         (?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         users
         *         (name,id)
         *     values
         *         (?,?)
         */

    }

    @Test
    void testFindOneToOnePrimaryKey(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "budhi");
        Assertions.assertEquals("budhi octaviansyah", user.getName());
        Assertions.assertEquals("budhi@test.com", user.getCredential().getEmail());
        Assertions.assertEquals("rahasia", user.getCredential().getPassword());

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         u1_0.id,
         *         c1_0.id,
         *         c1_0.email,
         *         c1_0.password,
         *         u1_0.name
         *     from
         *         users u1_0
         *     left join
         *         credentials c1_0
         *             on c1_0.id=u1_0.id
         *     where
         *         u1_0.id=?
         */
    }

    @Test
    void testInsertOneToOneForeignKey(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "budhi");
        Assertions.assertEquals("budhi octaviansyah", user.getName());
        Assertions.assertEquals("budhi@test.com", user.getCredential().getEmail());
        Assertions.assertEquals("rahasia", user.getCredential().getPassword());

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(4_500_000L);

        entityManager.persist(wallet);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         u1_0.id,
         *         c1_0.id,
         *         c1_0.email,
         *         c1_0.password,
         *         u1_0.name,
         *         w1_0.id,
         *         w1_0.balance
         *     from
         *         users u1_0
         *     left join
         *         credentials c1_0
         *             on c1_0.id=u1_0.id
         *     left join
         *         wallets w1_0
         *             on u1_0.id=w1_0.user_id
         *     where
         *         u1_0.id=?
         * Hibernate:
         *     insert
         *     into
         *         wallets
         *         (balance,user_id)
         *     values
         *         (?,?)
         */

    }

    @Test
    void testFindOneToOneForeignKey(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "budhi");
        Assertions.assertEquals("budhi octaviansyah", user.getName());
        Assertions.assertEquals("budhi@test.com", user.getCredential().getEmail());
        Assertions.assertEquals("rahasia", user.getCredential().getPassword());

        Assertions.assertEquals(1_000_000, user.getWallet().getBalance());
        Assertions.assertEquals("budhi", user.getWallet().getUser().getId());

        //log.info("user_id 1: {}", user.getWallet().getUser().getId());
        //log.info("user_id 2: {}", user.getId());

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         u1_0.id,
         *         c1_0.id,
         *         c1_0.email,
         *         c1_0.password,
         *         u1_0.name,
         *         w1_0.id,
         *         w1_0.balance
         *     from
         *         users u1_0
         *     left join
         *         credentials c1_0
         *             on c1_0.id=u1_0.id
         *     left join
         *         wallets w1_0
         *             on u1_0.id=w1_0.user_id
         *     where
         *         u1_0.id=?
         */

    }

}
