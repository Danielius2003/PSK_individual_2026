package org.example.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Trainer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "trainer_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private SportClub sportClub;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainer_player",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )

    private List<Player> players = new ArrayList<>();
    public Trainer() {}

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SportClub getSportClub() { return sportClub; }
    public void setSportClub(SportClub sportClub) { this.sportClub = sportClub; }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
}
