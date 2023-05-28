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

@Slf4j
public class EmbeddedTest {

    /**
     * Embedded
     * ● Saat membuat Class Entity, praktek yang baik adalah membuat jenis Class sesuai dengan yang
     *   dibutuhkan, Misal jika dalam Class Member, terdapat informasi seperti firstName, middleName,
     *   lastName, title, mungkin lebih baik digabung dalam satu Class Name misalnya
     * ● Namun jika kolom nya masih di table yang sama, kadang menyulitkan jika kita harus tetap
     *   membuat atribut nya pada Class Entity yang sama, karena lama-lama atribut nya akan semakin banyak
     * ● Untungnya di JPA terdapat fitur Embedded, dimana kita bisa menambahkan atribut berupa Class lain, namun tetap mapping ke table yang sama
     *
     * Embeddable
     * ● Untuk menandai bahwa sebuah class itu bisa digunakan sebagai atribut di Class Entity atau
     * istilahnya embedded atribut, maka kita wajib menambahkan annotation Embeddable di Class nya
     * ● Selanjutnya isi atribut pada Class Embedded tersebut, bisa kita tambahkan annotation Column seperti biasanya
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/embeddable
     *
     * Embedded
     * ● Saat kita menggunakan atribut class Embedded di Class Entity, kita wajib menggunakan annotation
     * Embedded untuk menandai bahwa itu ada class Embedded
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/embedded
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
    void testInsertEmbedded(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Name name = new Name();
        name.setTitle("Mrs");
        name.setFirstName("febi");
        name.setMiddleName("dwi");
        name.setLastName("sulistiana");

        Member member = new Member();
        member.setEmail("febi@test.com");
        member.setName(name);

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
         */

    }

}
