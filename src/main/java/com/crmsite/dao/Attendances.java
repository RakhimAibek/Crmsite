package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "attendances")
public class Attendances implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "lesson_id")
    @Column(name = "lesson_id")
    private Lessons lesson_id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private Users user_id;

    @Column(name = "attendance_value")
    private int attendance_value;

    @Column(name = "attendance_note")
    private String attendance_note;

    public Attendances() {

    }

    public Attendances(Lessons lesson_id, Users user_id, int attendance_value, String attendance_note) {
        this.lesson_id = lesson_id;
        this.user_id = user_id;
        this.attendance_value = attendance_value;
        this.attendance_note = attendance_note;
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

    public int getAttendance_value() {
        return attendance_value;
    }

    public void setAttendance_value(int attendance_value) {
        this.attendance_value = attendance_value;
    }

    public String getAttendance_note() {
        return attendance_note;
    }

    public void setAttendance_note(String attendance_note) {
        this.attendance_note = attendance_note;
    }

    @Override
    public String toString() {
        return "Attendances{" +
                "id=" + id +
                ", lesson_id=" + lesson_id +
                ", user_id=" + user_id +
                ", attendance_value=" + attendance_value +
                ", attendance_note='" + attendance_note + '\'' +
                '}';
    }
}
