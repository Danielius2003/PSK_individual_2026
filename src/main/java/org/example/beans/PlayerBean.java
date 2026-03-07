package org.example.beans;

import jakarta.annotation.PostConstruct;
import org.example.daos.PlayerDAO;
import org.example.entities.Player;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.entities.Trainer;

import java.util.List;

@Named
@RequestScoped
public class PlayerBean {

    @Inject
    private PlayerDAO playerDAO;

    private Player player = new Player();

    public void save() {
        playerDAO.save(player);
        player = new Player(); // reset form
    }

    public List<Player> getAllPlayers() {
        return playerDAO.findAll();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @PostConstruct
    public void init() {
        System.out.println("PlayerBean initialized!");
    }
}

