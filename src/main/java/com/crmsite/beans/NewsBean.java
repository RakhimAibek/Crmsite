package com.crmsite.beans;

import com.crmsite.dao.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class NewsBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addNews(News news) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(news);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNews(News news) {

        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.remove(news);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editAndDeleteNews(News news) {
        try {
            Session session = sessionFactory.openSession();

            //Transaction нужен, когда вносим изменение в базу данных
            Transaction transaction = session.beginTransaction();
            session.update(news);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<News> getNewsList(){

        List<News> news = null;

        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<News> criteriaQuery = builder.createQuery(News.class);
            Root<News> newsTable = criteriaQuery.from(News.class);
            criteriaQuery.select(newsTable);

            Query query = session.createQuery(criteriaQuery);

            news = query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        }

        return news;
    }


}
