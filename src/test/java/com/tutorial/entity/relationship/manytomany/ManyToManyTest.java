package com.tutorial.entity.relationship.manytomany;

import com.tutorial.entity.relationship.onetomany.Brand;
import com.tutorial.entity.relationship.onetomany.Product;
import com.tutorial.entity.relationship.onetoone.User;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ManyToManyTest {

    /**
     * Many To Many Relationship
     * ● Di kelas MySQL, kita sudah bahas tentang relasi Many to Many
     * ● Pada relasi Many to Many, kita tidak bisa hanya menggunakan dua table, biasanya kita akan
     *   menambahkan table ditengah sebagai jembatan relasi antara table pertama dan kedua
     * ● JPA juga mendukung relasi Many to Many
     *
     * Join Table
     * ● Untuk menggunakan relasi Many to Many, kita bisa menggunakan annotation ManyToMany
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/manytomany
     * ● Yang membedakan dengan relasi lain, karena Many to Many butuh table tambahan ditengah
     *   sebagai jembatan, oleh karena itu untuk melakukan join, kita menggunakan annotation JoinTable
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/jointable
     * ● Untuk table yang ditengah sebagai jembatan, kita tidak butuh membuat Class Entity nya
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
    void testInsertManyToManyRelationship(){

        //  Insert dan Find User, Product

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "budhi"); // get id
        user.setLikes(new HashSet<>()); // instance
        user.getLikes().add(entityManager.find(Product.class, "p1")); // add data by id
        user.getLikes().add(entityManager.find(Product.class, "p2"));
        user.getLikes().add(entityManager.find(Product.class, "p3"));
        user.getLikes().add(entityManager.find(Product.class, "p4"));

        entityManager.merge(user); // update

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
         *     select
         *         p1_0.id,
         *         b1_0.id,
         *         b1_0.description,
         *         b1_0.name,
         *         p1_0.description,
         *         p1_0.name,
         *         p1_0.price
         *     from
         *         products p1_0
         *     left join
         *         brands b1_0
         *             on b1_0.id=p1_0.brand_id
         *     where
         *         p1_0.id=?
         * Hibernate:
         *     select
         *         p1_0.id,
         *         b1_0.id,
         *         b1_0.description,
         *         b1_0.name,
         *         p1_0.description,
         *         p1_0.name,
         *         p1_0.price
         *     from
         *         products p1_0
         *     left join
         *         brands b1_0
         *             on b1_0.id=p1_0.brand_id
         *     where
         *         p1_0.id=?
         * Hibernate:
         *     delete
         *     from
         *         users_like_products
         *     where
         *         user_id=?
         * Hibernate:
         *     insert
         *     into
         *         users_like_products
         *         (user_id,product_id)
         *     values
         *         (?,?)
         * Hibernate:
         *     insert
         *     into
         *         users_like_products
         *         (user_id,product_id)
         *     values
         *         (?,?)
         */

    }

    @Test
    void testUpdateManyToManyRelationship(){

        // contoh menghapus many to many:  Find and update Product (delete collection)

        /**
         * NOTE:
         * ketika table relasi di delete dia tidak akan menghapus semuanya dulu (jadi ini amanya jika menggunakan relasi entity JPA)
         * 1 to 1, M  to M,
         * berbeda jika menggunakan Collection dia di hapus semua dulu baru di insert ulang semunya
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "budhi"); // get id
//        user.getLikes().forEach(product -> System.out.println(product.getName())); // print semua nama product

        Product product = null;
        for (Product item : user.getLikes()){
            product = item;
            break;
        }

        user.getLikes().remove(product); // hanya menghapus 1 product id di table relasi many to many antara table users dan product
        entityManager.merge(user);

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
         *     select
         *         l1_0.user_id,
         *         l1_1.id,
         *         l1_1.brand_id,
         *         l1_1.description,
         *         l1_1.name,
         *         l1_1.price
         *     from
         *         users_like_products l1_0
         *     join
         *         products l1_1
         *             on l1_1.id=l1_0.product_id
         *     where
         *         l1_0.user_id=?
         * Hibernate:
         *     delete
         *     from
         *         users_like_products
         *     where
         *         user_id=?
         *         and product_id=?
         */

    }

}
