package com.tutorial.entity;

import com.tutorial.embeddedable.Name;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@Slf4j
public class CollectionUpdateTest {

    /**
     * Collection Update
     * ● Hati-hati ketika menggunakan Collection Element
     * ● Setiap kita mengubah data di Collection Element, JPA akan menghapus dulu seluruh data di table
     *   Collection nya, setelah itu akan melakukan insert data baru
     * ● Oleh karena itu, Id pada Collection Element akan selalu berubah
     * ● Jadi pastikan Id pada table Collection Element tidak digunakan sebagai Foreign Key di table lain
     * ● Lantas bagaimana jika kita ingin menggunakan table Collection Element sebagai FK di table lain?
     * ● Maka kita jangan menggunakan Collection Element, kita harus menggunakan JPA Entity
     * Relationship yang akan dibahas di bab khusus
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

        // update dengan Collection JPA tidak di rekomendasikan, karena id seblumnya akan di hapus dan akan di input dengan id yang baru

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 6);

        Assertions.assertEquals("marwan", member.getName().getFirstName());
        Assertions.assertEquals("marwanchamak@example.com", member.getEmail());

        member.setSkills(new HashMap<>());
        member.getSkills().put("Java", 90);
        member.getSkills().put("Java", 90);
        member.getSkills().put("Golang", 90);
        member.getSkills().put("PHP", 90);
        member.getSkills().put("Javascript", 90);

        entityManager.merge(member);

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
         * Hibernate:
         *     delete
         *     from
         *         skills
         *     where
         *         member_id=?
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
