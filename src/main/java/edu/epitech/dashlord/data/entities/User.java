package edu.epitech.dashlord.data.entities;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="email", length = 150)
    private String email;
    @Column(name="password", length = 100)
    private String password;

    @Column(name="username", length = 50, unique=true)
    private String username;

    private Role role;

    private LocalDateTime createdAt;

    public User() { }

    public User(String username, String email, String password, Role role) {
        this.email = email;
        this.password = DigestUtils.sha1Hex(password);;
        this.username = username;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password).equals(this.password);
    }
    public Integer getId() {
        return id;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
