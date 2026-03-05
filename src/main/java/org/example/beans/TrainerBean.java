package org.example.beans;

import jakarta.annotation.PostConstruct;
import org.example.daos.TrainerDAO;
import org.example.entities.Trainer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class TrainerBean {

    @Inject
    private TrainerDAO trainerDAO;

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

    @PostConstruct
    public void init() {
        System.out.println("TrainerBean initialized!");
    }
}