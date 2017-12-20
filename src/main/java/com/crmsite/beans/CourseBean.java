package com.crmsite.beans;

import com.crmsite.dao.Courses;
import com.crmsite.dao.Groups;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CourseBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCourse(Courses course) {
        try {
            Session session = sessionFactory.openSession();

            Transaction transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCourse(Courses course) {
        try {
            Session session = sessionFactory.openSession();

            Transaction trans = session.beginTransaction();

            session.remove(course);
            trans.commit();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    }

    public List<Courses> getCoursesAllList(){

        List<Courses> courses = null;

        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Courses> criteriaQuery = builder.createQuery(Courses.class);
            Root<Courses> coursesTable = criteriaQuery.from(Courses.class);
            criteriaQuery.select(coursesTable);

            Query query = session.createQuery(criteriaQuery);

            courses = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return courses;
    }

    public Courses getCourseByName(String name) {

        Courses course = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Courses> criteriaQuery = builder.createQuery(Courses.class);
            Root<Courses> coursesRoot = criteriaQuery.from(Courses.class);
            criteriaQuery.select(coursesRoot);

            Predicate predName = builder.equal(coursesRoot.get("name"), name);

            criteriaQuery.where(builder.and(predName));
            Query query = session.createQuery(criteriaQuery);
            course = (Courses) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;

    }

    public void addGroup(Groups group) {

        try {
            Session session = sessionFactory.openSession();

            Transaction transaction = session.beginTransaction();
            session.save(group);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Groups> getGroupList(){

        List<Groups> groups = null;

        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Groups> criteriaQuery = builder.createQuery(Groups.class);
            Root<Groups> groupsRoot = criteriaQuery.from(Groups.class);
            criteriaQuery.select(groupsRoot);

            Query query = session.createQuery(criteriaQuery);

            groups = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return groups;
    }

    public Groups getGroupById(Long id) {

        Groups group = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Groups> criteriaQuery = builder.createQuery(Groups.class);
            Root<Groups> groupsRoot = criteriaQuery.from(Groups.class);
            criteriaQuery.select(groupsRoot);

            Predicate groupId = builder.equal(groupsRoot.get("id"), id);

            criteriaQuery.where(builder.and(groupId));
            Query query = session.createQuery(criteriaQuery);
            group = (Groups) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return group;

    }

    public void updateGroup(Groups group) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.merge(group);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateCourse(Courses course) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.merge(course);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Groups getGroupByName(String name) {

        Groups group = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Groups> criteriaQuery = builder.createQuery(Groups.class);
            Root<Groups> groupsRoot = criteriaQuery.from(Groups.class);
            criteriaQuery.select(groupsRoot);

            Predicate predName = builder.equal(groupsRoot.get("name"), name);

            criteriaQuery.where(builder.and(predName));
            Query query = session.createQuery(criteriaQuery);
            group = (Groups) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return group;

    }

    public Courses getCourseById(Long id) {

        Courses course = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Courses> criteriaQuery = builder.createQuery(Courses.class);
            Root<Courses> coursesRoot = criteriaQuery.from(Courses.class);
            criteriaQuery.select(coursesRoot);

            Predicate courseId = builder.equal(coursesRoot.get("id"), id);

            criteriaQuery.where(builder.and(courseId));
            Query query = session.createQuery(criteriaQuery);
            course = (Courses) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;

    }
}
