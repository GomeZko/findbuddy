package com.example.findbuddy.model;


import jakarta.persistence.*;
import com.example.findbuddy.model.User;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "interests")
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

   public String getName() {
        return name;
   }

   public void setName(String name) {
        this.name = name;
   }
}
