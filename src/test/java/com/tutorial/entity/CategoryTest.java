package com.tutorial.entity;

import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Calendar;

@Slf4j
public class CategoryTest {

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
    void testInsertCategory() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category.setName("jazz");
        category.setDescription("kamu bisa dengerin lagu jazz yang kamu sukai di sini");

        entityManager.persist(category); // void persist(Object var1) // Untuk menyimpan entity

        Assertions.assertNotNull(category.getId());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         categories
         *         (description,name)
         *     values
         *         (?,?)
         */

    }

    @Test
    void testFindCategory() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = entityManager.find(Category.class, 1); // <T> T find(Class<T> var1, Object var2) // Untuk mendapatkan entity berdasarkan id
        Assertions.assertEquals("pop", category.getName());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         c1_0.id,
         *         c1_0.description,
         *         c1_0.name
         *     from
         *         categories c1_0
         *     where
         *         c1_0.id=?
         */

    }

    @Test
    void testUpdateCategory() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = entityManager.find(Category.class, 1); // <T> T find(Class<T> var1, Object var2) // Untuk mendapatkan entity berdasarkan id
        category.setName("pop");

        entityManager.merge(category); // <T> T merge(T var1) // Untuk mengupdate entity

        Assertions.assertEquals("mamamia", category.getName());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         c1_0.id,
         *         c1_0.description,
         *         c1_0.name
         *     from
         *         categories c1_0
         *     where
         *         c1_0.id=?
         * Hibernate:
         *     update
         *         categories
         *     set
         *         description=?,
         *         name=?
         *     where
         *         id=?
         */

    }

    @Test
    void testDeleteCategory() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = entityManager.find(Category.class, 3); // <T> T find(Class<T> var1, Object var2) // Untuk mendapatkan entity berdasarkan id

        entityManager.remove(category); // void remove(Object var1) // Untuk menghapus entity

        //Assertions.assertNull(category.getId());
        //Assertions.assertNull(category.getName());
        //Assertions.assertNull(category.getDescription());

        entityTransaction.commit();
        entityManager.close();
//        entityManagerFactory.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         c1_0.id,
         *         c1_0.description,
         *         c1_0.name
         *     from
         *         categories c1_0
         *     where
         *         c1_0.id=?
         * Hibernate:
         *     delete
         *     from
         *         categories
         *     where
         *         id=?
         */

    }

}
