package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "groups")
public class Groups implements Serializable {

    @Id
    @Column(name = "groupId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cabinet_id")
    private Cabinets cabinet_id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private Date created_date;

    @Column(name = "active")
    private int active;

    @ManyToMany
    @JoinTable(name = "groups_users", joinColumns = {@JoinColumn(name = "groupId")},
    inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Users> users = new ArrayList<Users>();

    @ManyToMany
    @JoinTable(name = "groups_courses", joinColumns = {@JoinColumn(name = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Courses> courses = new ArrayList<Courses>();

    public Groups() {

    }

    public Groups(Cabinets cabinet_id, String name, Date created_date, int active) {
        this.cabinet_id = cabinet_id;
        this.name = name;
        this.created_date = created_date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", cabinet_id=" + cabinet_id +
                ", name='" + name + '\'' +
                ", created_date=" + created_date +
                ", active=" + active +
                '}';
    }
}

