package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cabinets")
public class Cabinets implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private int active;

    @OneToMany(targetEntity = Users.class, mappedBy = "cabinet_id",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Users> users;

//    @OneToMany(targetEntity = Courdses.class, mappedBy = "cabinet_id",
//            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Courses> courses;

    public Cabinets() {

    }

    public Cabinets(String name, String description, int active) {
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

    public List<Users> getAdmins() {
        return users;
    }

    public void setAdmins(List<Users> users) {
        this.users = users;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

//    public List<Courses> getCourses() {
//        return courses;
//    }
//
//    public void setCourses(List<Courses> courses) {
//        this.courses = courses;
//    }

    @Override
    public String toString() {
        return "Cabinets{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
