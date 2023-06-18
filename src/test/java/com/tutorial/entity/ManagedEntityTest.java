package com.tutorial.entity;

import com.tutorial.entity.relationship.onetomany.Brand;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class ManagedEntityTest {

    /**
     * Managed Entity
     * ● Salah satu fitur yang wajib dimengerti ketika menggunakan JPA adalah Managed Entity
     * ● Saat kita membuat Object Entity secara manual, mana bisa dibilang itu adalah Unmanaged Entity (Entity yang tidak di managed oleh JPA)
     * ● Saat Unmanaged Entity di simpan ke database menggunakan Entity Manager, secara otomatis objectnya berubah menjadi Managed Entity
     * ● Setiap perubahan yang terjadi pada Managed Entity, secara otomatis akan di update ke database
     * ketika kita melakukan commit, walaupun kita tidak melakukan update secara manual
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
    void testInsertManageEntity() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // Unmanaged entity
        Brand brand = new Brand();
        brand.setId("apple");
        brand.setName("Apple");

        entityManager.persist(brand); // managed entity

        brand.setName("Apple International"); // ini akan memangil query update. padahal tidak find()
        // karena setelah query insert
        // setelah itu akan query update

        entityTransaction.commit();
        entityManager.close();

        /**
         * query result:
         * Hibernate:
         *     insert
         *     into
         *         brands
         *         (create_at,description,name,update_at,version,id)
         *     values
         *         (?,?,?,?,?,?)
         * Hibernate:
         *     update
         *         brands
         *     set
         *         create_at=?,
         *         description=?,
         *         name=?,
         *         update_at=?,
         *         version=?
         *     where
         *         id=?
         *         and version=?
         */

    }

    /**
     * Find Managed Entity
     * ● Saat kita melakukan Find data ke database pun, secara otomatis object tersebut menjadi Managed Entity
     * ● Artinya setiap perubahan yang kita lakukan, secara otomatis akan di update ketika commit,
     * walaupun tidak melakukan update secara manual
     */

    @Test
    void testFindManageEntity() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");
        brand.setName("Apple Indonesia");

        // padalah kita tidak eksekusi dengan merge(); tetapi di otomatis di commit. dengan query update

        entityTransaction.commit();
        entityManager.close();

        /**
         * query result:
         * Hibernate:
         *     select
         *         b1_0.id,
         *         b1_0.create_at,
         *         b1_0.description,
         *         b1_0.name,
         *         b1_0.update_at,
         *         b1_0.version
         *     from
         *         brands b1_0
         *     where
         *         b1_0.id=?
         * Hibernate:
         *     update
         *         brands
         *     set
         *         create_at=?,
         *         description=?,
         *         name=?,
         *         update_at=?,
         *         version=?
         *     where
         *         id=?
         *         and version=?
         */

    }

    /**
     * Detach Entity
     * ● Kadang ada kasus kita ingin membuat Managed Entity menjadi Unmanaged Entity
     * ● Untuk kasus seperti itu, kita bisa menggunakan EntityManager.detach(entity)
     * ● otomatis yang terjadi di entity tidak akan di simpan otomatis saat melakukan commit
     */

    @Test
    void testFindDetachManageEntity() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");
        entityManager.detach(brand); // unmanaged entity
        brand.setName("Apple Singaparna");



        entityTransaction.commit();
        entityManager.close();

        /**
         * query result:
         * Hibernate:
         *     select
         *         b1_0.id,
         *         b1_0.create_at,
         *         b1_0.description,
         *         b1_0.name,
         *         b1_0.update_at,
         *         b1_0.version
         *     from
         *         brands b1_0
         *     where
         *         b1_0.id=?
         * Hibernate:
         *     update
         *         brands
         *     set
         *         create_at=?,
         *         description=?,
         *         name=?,
         *         update_at=?,
         *         version=?
         *     where
         *         id=?
         *         and version=?
         */

    }


}
