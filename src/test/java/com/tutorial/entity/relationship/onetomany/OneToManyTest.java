package com.tutorial.entity.relationship.onetomany;

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
public class OneToManyTest {

    /**
     * One to Many Relationship
     * ● Untuk membuat Entity yang memiliki relasi One to Many dengan Entity lain, kita bisa menggunakan annotation OneToMany
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/onetomany
     * ● Dan atribut di Entity, tipe datanya harus dibungkus dalam collection, misal List<T> atau Set<T>
     * ● Relasi OneToMany jika dilihat dari arah sebaliknya adalah relasi ManyToOne, oleh karena itu,
     * nanti di Class Entity sebelahnya, relasinya adalah ManyToOne
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/manytoone
     * ● Cara penggunaannya hampir mirip dengan relasi OneToOne, kita bisa gunakan JoinColumn pada
     * Entity yang memiliki kolom Foreign Key nya yang menggunakan ManyToOne, dan cukup gunakan attribute mappedBy pada attribute yang menggunakan OneToMany
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
    void testInsertOneToManyRelationship() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = Brand.builder()
                .id("samsung ver2")
                .name("Samsung")
//                .products(List.of(product1, product2))
                .description("brand Samsung")
                .build();

        Product product1 = Product.builder()
                .id("p3")
                .name("Samsung Galaxy 3")
                .price(1_000_000L)
                .brand(brand)
                .description("product p3 Samsung Galaxy 3")
                .build();

        Product product2 = Product.builder()
                .id("p4")
                .name("Samsung Galaxy 4")
                .price(2_000_000L)
                .brand(brand)
                .description("product p4 Samsung Galaxy 4")
                .build();

        entityManager.persist(brand);
        entityManager.persist(product1);
        entityManager.persist(product2);

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     insert
         *     into
         *         brands
         *         (description,name,id)
         *     values
         *         (?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         products
         *         (brand_id,description,name,price,id)
         *     values
         *         (?,?,?,?,?)
         * Hibernate:
         *     insert
         *     into
         *         products
         *         (brand_id,description,name,price,id)
         *     values
         *         (?,?,?,?,?)
         */

    }

    @Test
    void testFindOneToManyRelationship() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand samsungVer2 = entityManager.find(Brand.class, "samsung ver2");
        Assertions.assertNotNull(samsungVer2.getId());
        Assertions.assertEquals(2, samsungVer2.getProducts().size());
        for (Product data : samsungVer2.getProducts()) {
            log.info("id:       {}", data.getId());
            log.info("brand_id: {}", data.getBrand().getId());
            log.info("name:     {}", data.getName());
            log.info("price:    {}", data.getPrice());
            log.info("description: {}", data.getDescription());
        }

        entityTransaction.commit();
        entityManager.close();

        /**
         * result query:
         * Hibernate:
         *     select
         *         b1_0.id,
         *         b1_0.description,
         *         b1_0.name
         *     from
         *         brands b1_0
         *     where
         *         b1_0.id=?
         * Hibernate:
         *     select
         *         p1_0.brand_id,
         *         p1_0.id,
         *         p1_0.description,
         *         p1_0.name,
         *         p1_0.price
         *     from
         *         products p1_0
         *     where
         *         p1_0.brand_id=?
         */

    }


}
