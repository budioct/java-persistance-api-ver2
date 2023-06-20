package com.tutorial.entity;

import com.tutorial.entity.relationship.onetomany.Brand;
import com.tutorial.entity.relationship.onetomany.Product;
import com.tutorial.entity.relationship.onetoone.User;
import com.tutorial.utils.UtilEntityManagerFactory;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class JpaQueryLangaugeTest {

    /**
     * ● Selanjutnya kita akan bahas tentang JPA Query Language
     * ● Untuk melakukan Query ke database, JPA memiliki standarisasi Query Language, jadi tidak
     * menggunakan SQL yang spesifik ke database yang digunakan
     * ● Hal ini dikarenakan dengan menggunakan JPA, kita bisa mengganti-ganti database yang akan kita
     * gunakan, sehingga untuk Query Language nya pun perlu dibuat standar
     * ● Namun tidak perlu khawatir, karena JPA Query Language sangat mirip dengan SQL
     * <p>
     * Query
     * ● Saat kita menggunakan JPA QL, object yang dihasilkan adalah object dari class Query
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/query
     * ● Class Query mirip seperti PreparedStatement, dimana kita bisa menambahkan parameter jika JPAQL yang kita buat membutuhkan parameter
     * <p>
     * TypedQuery<T>
     * ● Class Query akan mengembalikan Object sehingga kita perlu melakukan konversi tipe data secara manual
     * ● Jika kita melakukan query yang sudah jelas Entity nya, sangat disarankan menggunakan TypedQuery
     * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/typedquery
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

    /**
     *
     */

    @Test
    void testSelectJPQL() {

        /**
         * Select Query
         * ● Untuk melakukan select query di JPA QL, kita tidak menyebutkan nama table, melainkan nama Entity nya
         * ● Selain itu, dalam JPA jika ingin men-select semua attribute, kita tidak menggunakan tanda *
         *   (bintang), melakukan menggunakan nama alias dari Entity nya
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b", Brand.class); // <T> TypedQuery<T> createQuery(String var1, Class<T> var2); // untuk set jpql
        List<Brand> brands = query.getResultList(); // List<X> getResultList(); // get data jika lebih dari 1 record

        for (Brand brand : brands) {
            System.out.println(brand.getId());
            System.out.println(brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testWhereClauseJPQL() {

        /**
         * Where Clause
         * ● Saat kita ingin menggunakan where clause pada select query, kita bisa menggunakan nama
         *   attribute di Entity nya, bukan nama kolom di table
         * ● Jika attribute nya berupa embedded class, kita bisa menyebut object di dalam nya menggunakan tanda . (titik)
         *
         *  Parameter
         * ● Jika membutuhkan parameter pada where clause, kita bisa gunakan tanda : (titik dua) diikuti dengan nama parameter
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.name.firstName = :firstName " +
                "and m.name.lastName = :lastName", Member.class);// <T> TypedQuery<T> createQuery(String var1, Class<T> var2); // untuk set jpql

        // set parameter yang telah di select firstname dan lastname
        query.setParameter("firstName", "budhi");
        query.setParameter("lastName", "octaviansyah");

        List<Member> members = query.getResultList(); // List<X> getResultList(); // get data jika lebih dari 1 record

        for (Member member : members) {
            System.out.println("id: " + member.getId() + " name: " + member.getName().getFirstName() + " " + member.getName().getLastName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testJoinJPQL() {

        /**
         * Join Clause
         * ● Melakukan join di JPA QL sangat mudah, karena informasi join nya sudah terdapat di Entity Class nya
         * ● Kita hanya cukup gunakan perintah join diikuti attribute di Entity
         *
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Product> query = entityManager.createQuery("select p from Product p join p.brand b where b.name = :brand", Product.class);// <T> TypedQuery<T> createQuery(String var1, Class<T> var2); // untuk set jpql

        // set parameter yang telah di select firstname dan lastname
        query.setParameter("brand", "Samsung");

        List<Product> products = query.getResultList(); // List<X> getResultList(); // get data jika lebih dari 1 record

        for (Product product : products) {
            System.out.println("name product: " + product.getName());
            System.out.println("name brand: " + product.getBrand().getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testJoinFetchJPQL() {

        /**
         * Join Clause
         * ● Melakukan join di JPA QL sangat mudah, karena informasi join nya sudah terdapat di Entity Class nya
         * ● Kita hanya cukup gunakan perintah join diikuti attribute di Entity
         *
         * Join Fetch
         * ● Kita pernah membahas tentang fetch pada relasi Entity
         * ● Secara default, saat melakukan join, kita juga bisa memaksa data table yang di join untuk di select
         *   sehingga tidak perlu melakukan select lagi untuk mendapatkan datanya
         *
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<User> query = entityManager.createQuery("select u from User u join fetch u.likes p where p.name = :product", User.class); // <T> TypedQuery<T> createQuery(String var1, Class<T> var2); // untuk set jpql

        // set parameter yang telah di select firstname dan lastname
        query.setParameter("product", "Samsung Galaxy 1");

        List<User> users = query.getResultList(); // List<X> getResultList(); // get data jika lebih dari 1 record

        for (User user : users) {
            System.out.println("User: " + user.getName());
            for (Product product : user.getLikes()) {
                System.out.println("Product: " + product.getName());
            }
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testOrderByJPQL() {

        /**
         * Order By Clause
         * ● Sama seperti di SQL, di JPA QL juga bisa menambahkan Order By Clause
         * mengambil data secara ASC atau DSC
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.name desc ", Brand.class);

        List<Brand> brands = query.getResultList();
        for (Brand brand : brands) {
            System.out.println(brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testLimitOffsetJPQL() {

        /**
         * Limit Offset
         * ● Untuk menambah Limit Offset di JPA QL Query, kita bisa menggunakan method di Query atau TypedQuery<T>
         * ● setMaxResults(n) untuk mengatur limit, dan
         * ● setFirstResult(n) untuk mengatur offset
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.name desc ", Brand.class);

        query.setFirstResult(0);
        query.setMaxResults(2);

        List<Brand> brands = query.getResultList();
        for (Brand brand : brands) {
            System.out.println(brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testNamedQueryJPQL() {

        /**
         * Named Query
         * ● JPA memiliki fitur bernama Named Query, dimana kita bisa membuat alias untuk JPA QL yang kita buat
         * ● Salah satu keuntungan menggunakan Named Query adalah, kita bisa menggunakan JPA QL yang
         *   sudah kita buat berkali-kali hanya dengan menyebutkan nama alias nya
         * ● Biasanya Named Query akan di tempatkan di Entity Class yang sesuai dengan Query nya
         * ● Untuk membuat Named Query, kita bisa gunakan annotation NamedQuery
         * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/namedquery
         * ● Dan untuk memanggilnya, kita bisa gunakan EntityManager.createNamedQuery(alias, class)
         */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createNamedQuery("Brand.findAllByName", Brand.class); // <T> TypedQuery<T> createNamedQuery(String var1, Class<T> var2); akses named query yang sudah di set di class entity dengan call aliasnya query nya
        query.setParameter("name", "Xiaomi"); // TypedQuery<X> setParameter(String var1, Object var2); // pairing where clause parameter dengan record data

        List<Brand> brands = query.getResultList(); // List<X> getResultList(); mengambil data bentuk list
        for (Brand brand : brands) {
            System.out.println(brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testSelectWithObjectArrayJPQL() {

        /**
         * Select Some Fields
         * ● Saat melakukan Query, kadang ada kasus kita hanya ingin men-select beberapa field saja
         * ● Pada kasus seperti ini, kita bisa ganti mapping ke Object[]
         * ● Namun, karena hasilnya adalah Object[], agak tidak aman karena kita harus konversi ke tipe data yang aslinya secara manual
         * */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery("select b.id, b.name from Brand b where b.name = :name", Object[].class);
        query.setParameter("name", "Xiaomi");

        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
            System.out.println("id: " + object[0]);
            System.out.println("name: " + object[1]);
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testConstructorExpressionJPQL() {

        /**
         * Constructor Expression
         * ● JPA memiliki fitur dimana kita bisa memanggil constructor sebuah class ketika melakukan select
         * ● Sehingga dibanding menggunakan tipe data Object[], kita bisa mapping field yang kita select, ke constructor sebuah class
         * ● Kita bisa gunakan query :
         * ● select new nama.package.Class(field, field, …)
         * */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<SimpleBrand> query = entityManager.createQuery("select new com.tutorial.entity.SimpleBrand(b.id, b.name) from Brand b where b.name = :name ", SimpleBrand.class);
        query.setParameter("name", "Xiaomi");

        List<SimpleBrand> simpleBrands = query.getResultList();
        for (SimpleBrand simpleBrand : simpleBrands) {
            System.out.println("id: " + simpleBrand.getId());
            System.out.println("name: " + simpleBrand.getName());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testAggregateQueryJPQL() {

        /**
         * Aggregate Query
         * ● JPA juga mendukung banyak sekali aggregate function seperti MIN, MAX, AVERAGE, dan lain-lain
         * ● Keuntungan menggunakan aggregate function di JPA QL, dia bisa otomatis mentranslate ke aggregate function di database yang kita gunakan
         * ● Untuk mengecek aggregate function yang didukung oleh JPA QL, kita harus lihat implementasi dari JPA Provider nya
         * ● Contohnya di Hibernate ORM, kita bisa lihat daftar aggregate function yang didukung disini :
         * ● https://docs.jboss.org/hibernate/orm/6.2/userguide/html_single/Hibernate_User_Guide.html#hql-aggregate-functions
         * */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery("select min(p.price), max(p.price), avg(p.price) from Product p", Object[].class);

        Object[] result = query.getSingleResult();
        System.out.println("Min: " + result[0]);
        System.out.println("Max: " + result[1]);
        System.out.println("Average: " + result[2]);


        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testGroupByandHavingJPQL() {

        /**
         * Group By dan Having
         * ● Sama seperti di SQL, di JPA QL juga mendukung Group By dan Having pada Aggregate Query
         * ● Cara penggunaannya pun sama seperti di SQL
         * */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery("select b.id, min(p.price), max(p.price), avg(p.price) from Product p " +
                "join p.brand b group by b.id having min(p.price) > :min", Object[].class);

        query.setParameter("min", 500_000L);

        List<Object[]> objects = query.getResultList();
        for (Object[] object : objects) {
        System.out.println("Brand: " + object[0]);
        System.out.println("Min: " + object[1]);
        System.out.println("Max: " + object[2]);
        System.out.println("Average: " + object[3]);
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testNativeQueryJPQL() {

        /**
         * Native Query
         * ● Pada kasus tertentu, kadang ada Query yang akhirnya harus dibuat langsung spesifik
         *   menggunakan SQL database yang kita gunakan
         * ● Walaupun hal ini tidak disarankan, karena bisa membuat kita kesulitan ketika akan mengubah
         *   database yang sedang digunakan, namun fitur ini juga bisa dilakukan di JPA
         * ● Kita bisa menggunakan method EntityManager.createNativeQuery(query, class)
         * */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Query query = entityManager.createNativeQuery("select * from brands where brands.create_at is not null", Brand.class); // Query createNativeQuery(String var1, Class var2); // bisa use query native

        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println("id: " + brand.getId());
            System.out.println("name: " + brand.getName());
            System.out.println("create_at: " + brand.getCreateAt());
        }

        entityTransaction.commit();
        entityManager.close();

    }

    @Test
    void testNamedNativeQueriesJPQL() {

        /**
         * Named Native Queries
         * ● Native Query juga bisa kita buat dalam Named Native Queries, sehingga kita bisa gunaka alias
         *   untuk menggunakan Native Query nya
         * ● Kita bisa gunakan annotation NamedNativeQuery
         * ● https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/namednativequery
         * */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createNamedQuery("Brand.native.findAll", Brand.class);

        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println("id: " + brand.getId());
            System.out.println("name: " + brand.getName());
            System.out.println("create_at: " + brand.getCreateAt());
        }

        entityTransaction.commit();
        entityManager.close();

    }





}
