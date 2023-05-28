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

@Slf4j
public class ListenerDiEntityClassTest {

    /**
     * Listener di Entity Class
     * ● Selain menggunakan annotation EntityListener, kita juga bisa langsung menambahkan Event annotation di Class Entity nya
     * ● Dengan begitu, kita tidak perlu membuat class listener lagi
     * ● Kekurangannya adalah, kita tidak bisa membuat listener yang bisa digunakan oleh Entity lain
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
    void testFindEventListenerEntityClass(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2);
        Assertions.assertNotNull(member);

        Assertions.assertEquals("Mrs. febi dwi sulistiana", member.getFullName());

        log.info("Fullname: {}", member.getFullName());

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         m1_0.id,
         *         m1_0.email,
         *         m1_0.first_name,
         *         m1_0.last_name,
         *         m1_0.middle_name,
         *         m1_0.title
         *     from
         *         members m1_0
         *     where
         *         m1_0.id=?
         * 2023-05-28 17:05:13.589 [main] INFO  c.t.entity.ListenerDiEntityClassTest - [] - Fullname: Mrs. febi dwi sulistiana
         */

    }


}
