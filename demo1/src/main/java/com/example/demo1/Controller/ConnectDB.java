package com.example.demo1.Controller;

import com.example.demo1.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ConnectDB {
    private static final SessionFactory sessionFactory;
    static {
        try{
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            System.out.println("Static Block success");
        }catch (Throwable ex){
            System.out.println("Static Block Fail");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static User Login(String mail, String pass){

        Session session = sessionFactory.openSession();
        EntityManager entityManager = session.unwrap(EntityManager.class);

        User user = null;

        try{
            entityManager.getTransaction().begin();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);

            Predicate email = builder.like(userRoot.get("email"),"%"+mail+"%");
            Predicate password = builder.like(userRoot.get("password"),"%"+pass+"%");

            criteriaQuery.select(userRoot);
            criteriaQuery.where(builder.and(email,password));

            user = session.createQuery(criteriaQuery).uniqueResult();

            entityManager.getTransaction().commit();

        }catch (Throwable e){
            System.out.println("Query fail");
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
        return user;

    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static void shutdown(){
        sessionFactory.close();
    }
}
