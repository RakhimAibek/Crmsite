package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Courses implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "cabinet_id")
    @Column(name = "cabinet_id")
    private Cabinets cabinet_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "groups_courses", joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "groupId")})
    private List<Groups> groups = new ArrayList<Groups>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "courses_users", joinColumns = {@JoinColumn(name = "courseId")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Users> users = new ArrayList<Users>();

    @OneToMany(targetEntity = Lessons.class, mappedBy = "course_id",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lessons> lessons = new ArrayList<Lessons>();

    public Courses() {

    }

    public Courses(Cabinets cabinet_id, String name, String description, int active) {
        this.cabinet_id = cabinet_id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public List<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lessons> lessons) {
        this.lessons = lessons;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", cabinet_id=" + cabinet_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
