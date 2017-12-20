package com.crmsite.beans;

import com.crmsite.dao.Cabinets;
import com.crmsite.dao.Roles;
import com.crmsite.dao.SuperAdmins;
import com.crmsite.dao.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class SuperAdminBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //Add new company, if they bought
    public void addCabinet(Cabinets cabinet) {

        try {
            Session session = sessionFactory.openSession();

            Transaction trans = session.beginTransaction();
            session.save(cabinet);
            trans.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cabinets getCabinetByName(String name) {

        Cabinets cabinet = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cabinets> criteriaQuery = builder.createQuery(Cabinets.class);
            Root<Cabinets> cabinetsTable = criteriaQuery.from(Cabinets.class);
            criteriaQuery.select(cabinetsTable);

            Predicate predName = builder.equal(cabinetsTable.get("name"), name);

            criteriaQuery.where(builder.and(predName));
            Query query = session.createQuery(criteriaQuery);
            cabinet = (Cabinets) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cabinet;

    }

    public Cabinets getCabinetById(Long id) {

        Cabinets cabinets = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cabinets> criteriaQuery = builder.createQuery(Cabinets.class);
            Root<Cabinets> cabinetsTable = criteriaQuery.from(Cabinets.class);
            criteriaQuery.select(cabinetsTable);

            Predicate predId = builder.equal(cabinetsTable.get("id"), id);

            criteriaQuery.where(builder.and(predId));
            Query query = session.createQuery(criteriaQuery);
            cabinets = (Cabinets) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cabinets;

    }

    //Delete and edit cabinet
    public void updateCabinet(Cabinets cabinet) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.merge(cabinet);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Cabinets> getAllCabinetsList() {
        List<Cabinets> cabinetsList = null;

        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Cabinets> query = builder.createQuery(Cabinets.class);
            Root<Cabinets> cabinetsTable = query.from(Cabinets.class);
            query.select(cabinetsTable);

            Query query1 = session.createQuery(query);

            cabinetsList = query1.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cabinetsList;
    }

    //Add new users
    public void addUsers(Users user) {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception excep) {
            excep.printStackTrace();
        }

    }

    //Delete and edit user
    public void updateUser(Users user) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Users getUsersById(Long id) {

        Users users = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersTable = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersTable);

            Predicate predId = builder.equal(usersTable.get("id"), id);

            criteriaQuery.where(builder.and(predId));
            Query query = session.createQuery(criteriaQuery);
            users = (Users) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;

    }


    //get users by role and cabinet
    public Users getUsersByRoleAndCabinet(Roles role, Cabinets cabinet) {

        Users user = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersTable = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersTable);

            Predicate predRole = builder.equal(usersTable.get("role_id"), role);
            Predicate predCabinet = builder.equal(usersTable.get("cabinet_id"), cabinet);

            criteriaQuery.where(builder.and(predRole, predCabinet));
            Query query = session.createQuery(criteriaQuery);
            user = (Users) query.getSingleResult();

            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;

    }

    //get all list of users
    public List<Users> getUsersList(){

        List<Users> users = null;

        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
            Root<Users> usersTable = criteriaQuery.from(Users.class);
            criteriaQuery.select(usersTable);

            Query query = session.createQuery(criteriaQuery);

            users = query.getResultList();

        } catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }

    //Add superAdmin
    public void addSuperAdmin(SuperAdmins superAdmin) {

        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(superAdmin);
            transaction.commit();
        } catch (Exception excep) {
            excep.printStackTrace();
        }

    }

    //delete and edit super admin
    public void deleteAndEditSuperAdmin(SuperAdmins superAdmin) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.update(superAdmin);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public SuperAdmins getSuperAdminByLogin(String login, String password) {

        SuperAdmins superAdmin = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<SuperAdmins> criteriaQuery = builder.createQuery(SuperAdmins.class);
            Root<SuperAdmins> superAdminsTable = criteriaQuery.from(SuperAdmins.class);
            criteriaQuery.select(superAdminsTable);

            Predicate predlogin = builder.equal(superAdminsTable.get("login"), login);
            Predicate predpassword = builder.equal(superAdminsTable.get("password"), password);

            criteriaQuery.where(builder.and(predlogin, predpassword));
            Query query = session.createQuery(criteriaQuery);
            superAdmin = (SuperAdmins) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return superAdmin;

    }

    public SuperAdmins getSuperAdminById(Long id) {

        SuperAdmins superAdmin = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<SuperAdmins> criteriaQuery = builder.createQuery(SuperAdmins.class);
            Root<SuperAdmins> superAdminsTable = criteriaQuery.from(SuperAdmins.class);
            criteriaQuery.select(superAdminsTable);

            Predicate predId = builder.equal(superAdminsTable.get("id"), id);

            criteriaQuery.where(builder.and(predId));
            Query query = session.createQuery(criteriaQuery);
            superAdmin = (SuperAdmins) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return superAdmin;

    }

    public List<SuperAdmins> getSuperAdminList(){

        List<SuperAdmins> superAdmins = null;

        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<SuperAdmins> criteriaQuery = builder.createQuery(SuperAdmins.class);
            Root<SuperAdmins> superAdminsTable = criteriaQuery.from(SuperAdmins.class);
            criteriaQuery.select(superAdminsTable);

            Query query = session.createQuery(criteriaQuery);

            superAdmins = query.getResultList();

        } catch (Exception e){
            e.printStackTrace();
        }

        return superAdmins;
    }




}
