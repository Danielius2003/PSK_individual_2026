package org.example.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.transaction.Transactional;
import org.example.daos.PlayerDAO;
import org.example.daos.SportClubDAO;
import org.example.daos.TrainerDAO;
import org.example.entities.Player;
import org.example.entities.SportClub;
import org.example.entities.Trainer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class TrainerBean {

    @Inject
    private SportClubDAO sportClubDAO;
    @Inject
    private TrainerDAO trainerDAO;

    @Inject
    private PlayerDAO playerDAO;

    private Trainer trainer = new Trainer();

    public void save() {
        trainerDAO.save(trainer);
        trainer = new Trainer(); // reset form
    }

    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    private Long selectedTrainerId;
    private Long selectedClubId;

    private Long selectedPlayerId;

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    private Long trainerId;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    private Long playerId;


    public void assignTrainerToClub() {
        Trainer trainer = trainerDAO.findById(selectedTrainerId);
        SportClub club = sportClubDAO.findById(selectedClubId);

        trainer.setSportClub(club);

        trainerDAO.update(trainer);
    }

    public List<Player> getAllPlayers() {
        return playerDAO.findAll();
    }

    @Transactional
    public void addPlayerToTrainer() {
        // 1. load managed entities
        Trainer trainer = trainerDAO.findById(trainerId);
        Player player = playerDAO.findById(playerId);

        // 2. initialize collections if null
        if (trainer.getPlayers() == null) trainer.setPlayers(new ArrayList<>());
        if (player.getTrainers() == null) player.setTrainers(new ArrayList<>());

        // 3. add if not already assigned
        if (!trainer.getPlayers().contains(player)) {
            trainer.getPlayers().add(player);
            player.getTrainers().add(trainer);
        }

        // 4. merge owning side
        trainerDAO.update(trainer);

        // 5. reload trainer for JSF view
        trainer = trainerDAO.findById(trainerId);

        // 6. reset dropdown
        playerId = null;
    }

    public List<Player> getAvailablePlayers() {
        if (trainer == null) return new ArrayList<>();
        return playerDAO.findAvailableForTrainer(trainerId);
    }

    @PostConstruct
    public void init() {
        String previousUrl = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestHeaderMap()
                .get("referer");
    }

    public void loadTrainer() {
        if (trainerId != null) {
            trainer = trainerDAO.findById(trainerId);
        }
    }
}