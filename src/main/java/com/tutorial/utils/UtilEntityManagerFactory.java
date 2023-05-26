package com.tutorial.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UtilEntityManagerFactory {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ASEKBRO");

    public static EntityManagerFactory getEntityManagerFactory(){

        return entityManagerFactory;
    }

}