package com.crmsite.dao;

import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "news")
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "cabinet_id")
    @Column(name = "cabinet_id")
    private Cabinets cabinet_id;

//    @OneToOne
//    @JoinColumn(name = "admin_id")
    @Column(name = "user_id")
    private Users user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "post_date")
    private Date post_date;

    @Column(name = "active")
    private int active;

    public News() {

    }

    public News(Cabinets cabinet_id, Users user_id, String title, String content, Date post_date, int active) {
        this.cabinet_id = cabinet_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.post_date = post_date;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cabinets getCabinet_id() {
        return cabinet_id;
    }

    public void setCabinet_id(Cabinets cabinet_id) {
        this.cabinet_id = cabinet_id;
    }

    public Users getUser_id() {
        return user_id;
    }

    public void setUser_id(Users user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", cabinet_id=" + cabinet_id +
                ", admin_id=" + user_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", post_date=" + post_date +
                ", active=" + active +
                '}';
    }
}
