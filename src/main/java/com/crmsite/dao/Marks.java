package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "marks")
public class Marks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @ManyToOne
    @Column(name = "lesson_id")
    private Lessons lesson_id;

//    @ManyToMany
    @Column(name = "user_id")
    private Users user_id;

    @Column(name = "mark_value")
    private int mark_value;

    @Column(name = "mark_notes")
    private String mark_notes;

    public Marks() {

    }

    public Marks(Lessons lesson_id, Users user_id, int mark_value, String mark_notes) {
        this.lesson_id = lesson_id;
        this.user_id = user_id;
        this.mark_value = mark_value;
        this.mark_notes = mark_notes;
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

    public int getMark_value() {
        return mark_value;
    }

    public void setMark_value(int mark_value) {
        this.mark_value = mark_value;
    }

    public String getMark_notes() {
        return mark_notes;
    }

    public void setMark_notes(String mark_notes) {
        this.mark_notes = mark_notes;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "id=" + id +
                ", lesson_id=" + lesson_id +
                ", user_id=" + user_id +
                ", mark_value=" + mark_value +
                ", mark_notes='" + mark_notes + '\'' +
                '}';
    }
}

