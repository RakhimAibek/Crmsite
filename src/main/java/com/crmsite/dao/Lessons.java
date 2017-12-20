package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lessons")
public class Lessons implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "post_date")
    private Date post_date;

    @Column(name = "active")
    private int active;

    public Lessons() {

    }

    public Lessons(Courses course_id, String title, String content, Date post_date, int active) {
        this.course_id = course_id;
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

    public Courses getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Courses course_id) {
        this.course_id = course_id;
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
        return "Lessons{" +
                "id=" + id +
                ", course_id=" + course_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", post_date=" + post_date +
                ", active=" + active +
                '}';
    }
}
