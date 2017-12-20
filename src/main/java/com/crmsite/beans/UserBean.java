package com.crmsite.beans;

import com.crmsite.dao.Roles;
import com.crmsite.dao.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.jws.soap.SOAPBinding;
import javax.management.relation.Role;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;
import java.util.List;

public class UserBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(Users user) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(Users user) {

        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateUser(Users user) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Users getUserLoginAndPassword(String login, String password) {
        Users user = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersTable = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersTable);

            Predicate predlogin = builder.equal(usersTable.get("login"), login);
            Predicate predpassword = builder.equal(usersTable.get("password"), password);

            criteriaQuery.where(builder.and(predlogin, predpassword));
            Query query = session.createQuery(criteriaQuery);
            user = (Users) query.getSingleResult();

            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;

    }

    public Users getUserById(Long id) {

        Users users = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersRoot = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersRoot);

            Predicate predId = builder.equal(usersRoot.get("id"), id);

            criteriaQuery.where(builder.and(predId));
            Query query = session.createQuery(criteriaQuery);
            users = (Users) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;

    }

    public List<Users> getUsersList(){

        List<Users> users = null;

        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersTable = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersTable);

            Query query = session.createQuery(criteriaQuery);

            users = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }

    public List<Roles> getRolesList(){

        List<Roles> roles = null;

        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Roles> criteriaQuery = builder.createQuery(Roles.class);
            Root<Roles> rolesRoot = criteriaQuery.from(Roles.class);
            criteriaQuery.select(rolesRoot);

            Query query = session.createQuery(criteriaQuery);

            roles = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return roles;
    }

    public Users getUserByLogin(String login) {

        Users users = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersRoot = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersRoot);

            Predicate predlogin = builder.equal(usersRoot.get("login"), login);

            criteriaQuery.where(builder.and(predlogin));
            Query query = session.createQuery(criteriaQuery);
            users = (Users) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public Roles getRoleByName(String name) {

        Roles role = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Roles> criteriaQuery = builder.createQuery(Roles.class);
            Root<Roles> rolesRoot = criteriaQuery.from(Roles.class);
            criteriaQuery.select(rolesRoot);

            Predicate predName = builder.equal(rolesRoot.get("name"), name);

            criteriaQuery.where(builder.and(predName));
            Query query = session.createQuery(criteriaQuery);
            role = (Roles) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;

    }
}
