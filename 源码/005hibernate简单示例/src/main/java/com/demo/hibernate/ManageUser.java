package com.demo.hibernate;

import com.demo.hibernate.bean.UserBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Iterator;
import java.util.List;

public class ManageUser {
    private static SessionFactory factory;

    public Integer addUser(UserBean userBean){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userId= null;
        try{
            tx = session.beginTransaction();
            System.out.println(userBean);
            userId = (Integer) session.save(userBean);
            tx.commit();
        }catch (HibernateException ex){
            if(tx != null){
                tx.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return userId;
    }

    public void listUsers(){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List userBeans = session.createQuery("FROM UserBean").list();
            for(Iterator iterator=userBeans.iterator();iterator.hasNext();){
                UserBean userBean = (UserBean)iterator.next();
                System.out.println(userBean.toString());
            }
            tx.commit();
         }catch (HibernateException ex){
            if(tx != null){
                tx.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateUser(UserBean userBean){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(userBean);
            tx.commit();
        }catch (HibernateException ex){
            if(tx != null){
                tx.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void deleteUser(UserBean userBean){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete(userBean);
            tx.commit();
        }catch (HibernateException ex){
            if(tx != null){
                tx.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
    }

    public static void main(String [] args){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Exception ex){
            System.err.println("Failed to create sessionFactory object." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
        ManageUser mu = new ManageUser();
        UserBean userBean = new UserBean(3,"test","1234","www","ttt");
        Integer userId = mu.addUser(userBean);
        mu.listUsers();

    }
}
