package com.crmsite.beans;

import com.crmsite.dao.Courses;
import com.crmsite.dao.Lessons;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class LessonBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addLesson(Lessons lesson) {
        try {
            Session ses = sessionFactory.openSession();
            Transaction beginTransaction = ses.beginTransaction();
            ses.save(lesson);
            beginTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Lessons> getLessonList(){

        List<Lessons> lessons = null;

        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Lessons> criteriaQuery = builder.createQuery(Lessons.class);
            Root<Lessons> lessonsRoot = criteriaQuery.from(Lessons.class);
            criteriaQuery.select(lessonsRoot);

            Query query = session.createQuery(criteriaQuery);

            lessons = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return lessons;
    }

    public void updateLesson(Lessons lesson) {
        try {
            Session session = sessionFactory.openSession();

            Transaction transaction = session.beginTransaction();
            session.merge(lesson);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Lessons getLessonById(Long id) {

        Lessons lesson = null;
        try {
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Lessons> criteriaQuery = builder.createQuery(Lessons.class);
            Root<Lessons> lessonsRoot = criteriaQuery.from(Lessons.class);
            criteriaQuery.select(lessonsRoot);

            Predicate courseId = builder.equal(lessonsRoot.get("id"), id);

            criteriaQuery.where(builder.and(courseId));
            Query query = session.createQuery(criteriaQuery);
            lesson = (Lessons) query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lesson;

    }

}
