package org.example.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.transaction.Transactional;
import org.example.daos.SportClubDAO;
import org.example.daos.TrainerDAO;
import org.example.entities.SportClub;
import org.example.entities.Trainer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped //naujas instance su kiekviena http requestu
public class SportClubBean {

    @Inject //CDI sukuria ojekta ir ji iterpia, valdo jo gyvavimo laika ir zino kad egzistuoja
    //dao yra sukurtas per em, tai be inject em naudojimas visad rodys klaida

    private SportClubDAO sportClubDAO;

    @Inject
    private TrainerDAO trainerDAO;

    private SportClub sportClub = new SportClub();

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    private Long clubId; // parametras iš URL

    public Long getSelectedTrainerId() {
        return selectedTrainerId;
    }

    public void setSelectedTrainerId(Long selectedTrainerId) {
        this.selectedTrainerId = selectedTrainerId;
    }

    private Long selectedTrainerId;

    @Transactional
    public void saveSportClub() {
        sportClubDAO.save(sportClub);
        sportClub = new SportClub();
    }

    public List<SportClub> getAllSportClubs() {
        return sportClubDAO.findAll();
    }

    public SportClub getSportClub() {
        if (clubId != null) {
            // load from DB if editing an existing club
            sportClub = sportClubDAO.findById(clubId);
        }
        // if clubId is null or DB fetch failed, keep the existing sportClub
        return sportClub;
    }

    // ========== ADD TRAINER ==========
    @Transactional
    public void addTrainerToClub() {
        Trainer trainer = trainerDAO.findById(selectedTrainerId);
        if (trainer.getSportClub() != null) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Trainer already assigned!", null));
            return;
        }

        SportClub club = sportClubDAO.findById(clubId);
        trainer.setSportClub(club);
        trainerDAO.update(trainer);

        // refresh club data
        sportClub = sportClubDAO.findById(clubId);
    }

    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }

    public void setSportClub(SportClub sportClub) {
        this.sportClub = sportClub;
    }

    public List<Trainer> getAvailableTrainers() {
        return trainerDAO.findUnassignedTrainers();
    }




}
