package com.tutorial.entity;

import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class CategoryTest {

    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp(){
      log.info("running Entity Manager Factory");
      this.entityManagerFactory = UtilEntityManagerFactory.getEntityManagerFactory();
    }

    @Test
    void testInsertCategory(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Category category = new Category();
        category.setName("pop");
        category.setDescription("kamu bisa dengerin banyak lagu pop");

        entityManager.persist(category);

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

}
