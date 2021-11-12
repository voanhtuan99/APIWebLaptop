package com.example.apiweblaptop.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name="phone_number")
    private String phone_number;
    @Column(name = "address")
    private String address;
    @Column(name = "activestatus")
    private String activestatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private Role role;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Order> orders;
    public User(String trim, String s, String encode, String trim1, String phone_number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_number = this.phone_number;
        this.address = address;
    }

    public User() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivestatus() {
        return activestatus;
    }

    public void setActivestatus(String activestatus) {
        this.activestatus = activestatus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(String trim, String gender, String email, String password, String phone, String username, String encode) {

    }
}
