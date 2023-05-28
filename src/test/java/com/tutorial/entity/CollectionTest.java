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

import java.util.List;

@Slf4j
public class CollectionTest {

    /**
     * Collection
     * ● Saat kita membuat Data dalam bentuk Class, kita sering sekali membuat atribut dalam bentuk
     * Collection, dan itu sangat mudah
     * ● Namun hal itu tidak mudah dilakukan di Table, kita harus membuat table terpisah dengan relasi One to Many
     * ● JPA mendukung database relation, namun pada kasus yang sangat sederhana, kita bisa membuat
     * atribut dengan tipe Collection yang secara otomatis menggunakan Table Join untuk mengambil datanya
     *
     * Tipe Data Collection
     * ● JPA mendukung banyak tipe data collection, seperti List<T> atau Set<T>
     * ● Saat kita menggunakan tipe data collection, secara otomatis kita perlu membuat table untuk menyimpan data collection nya
     * ● Atribut collection harus kita tandai dengan annotation ElementCollection
     * ● Dan untuk menentukan table mana yang digunakan sebagai table untuk menyimpan Collection,
     * kita gunakan annotation CollectionTable
     * ● Dan untuk menentukan kolom mana di table collection yang kita ambil datanya, kita bisa gunakan annotation Column seperti biasaya
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
    void testInsertCollectionRelasiTableOneToMany(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Name name = new Name();
        name.setFirstName("nur");
        name.setMiddleName("halimah");
        name.setLastName("jumadi");

        Member member = new Member();
        member.setName(name);
        member.setEmail("nur@test.com");
        member.setHobbies(List.of("Coding", "Gaming", "Football", "Movie"));
//        member.setHobbies(new ArrayList<>());
//        member.getHobbies().add("Coding")

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
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         */

    }

    @Test
    void testGetByIdCollection(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2);
        Assertions.assertEquals("nur", member.getName().getFirstName());

        List<String> hobbies = List.of("Coding", "Gaming", "Football", "Movie");
        Assertions.assertEquals(hobbies, member.getHobbies());

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
         *     select
         *         h1_0.member_id,
         *         h1_0.name
         *     from
         *         hobbies h1_0
         *     where
         *         h1_0.member_id=?
         */

    }

    @Test
    void testUpdateCollection(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Member member = entityManager.find(Member.class, 2);
        Assertions.assertEquals("febi", member.getName().getFirstName());
        member.setHobbies(List.of("Macing", "Football", "Movie"));

        entityManager.persist(member);

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
         *         hobbies
         *     where
         *         member_id=?
         * Hibernate:
         *     insert
         *     into
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         hobbies
         *         (member_id,name)
         *     values
         *         (?,?)
         */

    }

}
