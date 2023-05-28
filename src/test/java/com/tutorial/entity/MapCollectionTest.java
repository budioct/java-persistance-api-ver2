package com.tutorial.entity;

import com.tutorial.embeddedable.Name;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@Slf4j
public class MapCollectionTest {

    /**
     * Map Collection
     * ● Selain List<T> dan Set<T>, kita juga bisa menggunakan collection Map<K, V> sebagai atribut di Class Entity
     * ● Cara penggunaannya hampir mirip dengan List<T> atau Set<T>, namun karena pada Map<K, V>
     * terdapat key dan value, maka kita harus memberi tahu JPA untuk kolom key dan kolom value
     * menggunakan annotation MapKeyColumn, sedangkan untuk value nya tetap menggunakan
     * annotation Column
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
    void testInsertMapCollection() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Name name = new Name();
        name.setFirstName("marwan");
        name.setMiddleName("chamak");
        name.setLastName("007");

        Member member = new Member();
        member.setName(name);
        member.setEmail("marwanchamak@example.com");

        member.setSkills(new HashMap<>());
        member.getSkills().put("Java", 90);
        member.getSkills().put("Java", 90);
        member.getSkills().put("Golang", 90);
        member.getSkills().put("PHP", 90);

        entityManager.persist(member);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         members
         *         (email,first_name,last_name,middle_name,title)
         *     values
         *         (?,?,?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         skills
         *         (member_id,name,value)
         *     values
         *         (?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         skills
         *         (member_id,name,value)
         *     values
         *         (?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         skills
         *         (member_id,name,value)
         *     values
         *         (?,?,?)
         */

    }
}
