package com.tutorial.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtilEntityManagerFactory {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO"); // static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) // datasource environment jpa hibernate

    public static EntityManagerFactory getEntityManagerFactory(){

        return entityManagerFactory;
    }

}