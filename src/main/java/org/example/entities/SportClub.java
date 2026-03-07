package org.example.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class SportClub {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "sportClub", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Trainer> trainers;

    public SportClub() {}//konstruktrius reikalingas del JPA

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Trainer> getTrainers() { return trainers; }
    public void setTrainers(List<Trainer> trainers) { this.trainers = trainers; }
}
