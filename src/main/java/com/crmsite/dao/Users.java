package com.crmsite.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cabinet_id")
    private Cabinets cabinet_id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role_id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "active")
    private int active;

    @ManyToMany
    @JoinTable(name = "groups_users", joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "groupId")})
    private List<Groups> groups = new ArrayList<Groups>();

    @ManyToMany
    @JoinTable(name = "courses_users", joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "courseId")})
    private List<Courses> courses = new ArrayList<Courses>();

    public Users() {

    }

    public Users(Cabinets cabinet_id, String login, String password, Roles role_id, String name, String surname, int active) {
        this.cabinet_id = cabinet_id;
        this.login = login;
        this.password = password;
        this.role_id = role_id;
        this.name = name;
        this.surname = surname;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole_id() {
        return role_id;
    }

    public void setRole_id(Roles role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", cabinet_id=" + cabinet_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", active=" + active +
                '}';
    }
}
