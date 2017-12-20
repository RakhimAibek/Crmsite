package com.crmsite.dao;

import jdk.nashorn.internal.runtime.logging.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Table(name = "lesson_attachements")
public class LessonAttachements implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "lesson_id")
    @Column(name = "lesson_id")
    private Lessons lesson_id;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private Users user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "mime")
    private String mime;

    @Column(name = "size", length = 20)
    private int size;

    @Column(name = "attachment")
    private Blob attachment;

    @Column(name = "download_date")
    private Date download_date;

    public LessonAttachements() {

    }

    public LessonAttachements(Lessons lesson_id, Users user_id, String name, String mime, int size, Blob attachment, Date download_date) {
        this.lesson_id = lesson_id;
        this.user_id = user_id;
        this.name = name;
        this.mime = mime;
        this.size = size;
        this.attachment = attachment;
        this.download_date = download_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lessons getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Lessons lesson_id) {
        this.lesson_id = lesson_id;
    }

    public Users getUser_id() {
        return user_id;
    }

    public void setUser_id(Users user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Blob getAttachment() {
        return attachment;
    }

    public void setAttachment(Blob attachment) {
        this.attachment = attachment;
    }

    public Date getDownload_date() {
        return download_date;
    }

    public void setDownload_date(Date download_date) {
        this.download_date = download_date;
    }

    @Override
    public String toString() {
        return "LessonAttachements{" +
                "id=" + id +
                ", lesson_id=" + lesson_id +
                ", user_id=" + user_id +
                ", name='" + name + '\'' +
                ", mime='" + mime + '\'' +
                ", size=" + size +
                ", attachment=" + attachment +
                ", download_date=" + download_date +
                '}';
    }
}
