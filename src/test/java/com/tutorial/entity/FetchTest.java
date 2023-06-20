package com.tutorial.entity;

import com.tutorial.entity.relationship.onetomany.Brand;
import com.tutorial.entity.relationship.onetomany.Product;
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
public class FetchTest {

    /**
     * Fetch (pengambilan sebuah data yang ada di table) Eager(sekaligus) Lazy(bertahap)
     * ● Saat kita membuat Entity Class yang kompleks dan banyak sekali relasinya, kita perlu berhati-hati
     * ● Hal ini karena secara default, beberapa jenis relasi memiliki value fetch EAGER, artinya saat kita
     *   melakukan find Entity, relasinya akan secara otomatis di JOIN, walaupun kita tidak membutuhkan relasinya
     * ● Kebalikan dari EAGER adalah LAZY, dimana artinya relasi akan di QUERY ketika kita memanggil
     *   attribute nya, jika tidak, maka tidak akan di QUERY
     *
     * Relation         Default Fetch
     * One to One       EAGER (akan di join)
     * One to Many      LAZY (tidak akan di join)
     * Many to One      EAGER
     * Many to Many     LAZY
     *
     * Mengubah Fetch
     * ● Jika kita ingin mengubah nilai default Fetch, kita bisa ubah di attribut di annotation relasinya
     * ● Semua relasi dari One to One, One to Many, Many to One dan Many to Many memiliki attribute
     *   fetch yang bisa kita ubah
     *
     * Mengubah Fetch
     * ● Jika kita ingin mengubah nilai default Fetch, kita bisa ubah di attribut di annotation relasinya
     * ● Semua relasi dari One to One, One to Many, Many to One dan Many to Many memiliki attribute
     *   fetch yang bisa kita ubah
     *
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
    void testFindFetchingProduct(){

        // Product @ManyToOne To Brand default fecthing adalah EAGER

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Product product = entityManager.find(Product.class, "p1");

        Assertions.assertNotNull(product);

        entityTransaction.commit();
        entityManager.close();

        /**
         * FETCH DEFAULT MANY TO ONE (EAGER)
         * result query FETCH EAGER:
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
         */
    }

    @Test
    void testFindFetchingBrand(){

        // Product @OneToMany

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "samsung");

        Assertions.assertNotNull(brand);

        entityTransaction.commit();
        entityManager.close();

        /**
         * FETCH DEFAULT MANY TO ONE (EAGER)
         * result query FETCH EAGER:
         * Hibernate:
         *     select
         *         b1_0.id,
         *         b1_0.description,
         *         b1_0.name
         *     from
         *         brands b1_0
         *     where
         *         b1_0.id=?
         */
    }

    @Test
    void testMerubahDefaultFetchProductMenjadiLAZY(){

        // Product @ManyToOne(fetch = FetchType.LAZY)

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Product product = entityManager.find(Product.class, "p1");

        Assertions.assertNotNull(product);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         p1_0.id,
         *         p1_0.brand_id,
         *         p1_0.description,
         *         p1_0.name,
         *         p1_0.price
         *     from
         *         products p1_0
         *     where
         *         p1_0.id=?
         */

    }


}
