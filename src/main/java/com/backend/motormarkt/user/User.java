package com.backend.motormarkt.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(columnDefinition = "VARCHAR(255)")
    private String address1;

    @Column(columnDefinition = "VARCHAR(255)")
    private String address2;

    @Column(columnDefinition = "VARCHAR(100)")
    private String city;

    @Column(columnDefinition = "VARCHAR(10)")
    private String postcode;

    @Column(columnDefinition = "VARCHAR(100)")
    private String country;

    @Column(columnDefinition = "VARCHAR(20)")
    private String phone;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean admin;

    @Column(nullable = false)
    private Boolean client;

    @CreationTimestamp
    private LocalDateTime createdAt; // Se establecerá automáticamente al crear

    @UpdateTimestamp
    private LocalDateTime updatedAt; // Se actualizará automáticamente al modificar

    public User() {
    }

    public User(String name, String email, String password, String address1, String address2, String city, String postcode, String country, String phone, Boolean active, Boolean admin, Boolean client, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.phone = phone;
        this.active = active;
        this.admin = admin;
        this.client = client;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(Long id, String name, String email, String password, String address1, String address2, String city, String postcode, String country, String phone, Boolean active, Boolean admin, Boolean client, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.phone = phone;
        this.active = active;
        this.admin = admin;
        this.client = client;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean isClient() {
        return client;
    }

    public void setClient(Boolean client) {
        this.client = client;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                ", admin=" + admin +
                ", client=" + client +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
