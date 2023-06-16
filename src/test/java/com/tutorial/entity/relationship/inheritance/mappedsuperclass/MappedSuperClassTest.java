package com.tutorial.entity.relationship.inheritance.mappedsuperclass;

import com.tutorial.entity.relationship.onetomany.Brand;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@Slf4j
public class MappedSuperClassTest {

    /**
     * Mapped Superclass
     * ● Saat membuat Class Entity, kadang ada beberapa Class Entity yang memiliki attribute yang sama, namun bukan bagian dari IS-A Relationship
     * ● Pada kasus OOP, biasanya kita membuat Parent Class sebagai class yang berisikan attribute-attribute yang sama
     * ● Pada kasus Entity, kita bisa membuat Parent Class juga, namun Kita perlu memberi annotation
     *   MapperSuperclass untuk memberi tahu ini hanya sebuah Parent Class tanpa IS-A Relationship
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/mappedsuperclass
     *
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
    void testInsertMappedSuperClass(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("xiami");
        brand.setName("Xiaomi");
        brand.setDescriptions("Xiaomi Global");
        brand.setCreateAt(LocalDateTime.now());
        brand.setUpdateAt(LocalDateTime.now());
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query;
         * Hibernate:
         *     insert
         *     into
         *         brands
         *         (create_at,description,name,update_at,id)
         *     values
         *         (?,?,?,?,?)
         */

    }


}
