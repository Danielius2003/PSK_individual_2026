package org.example.daos;

import org.example.entities.Trainer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TrainerDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Trainer trainer) {
        em.persist(trainer);
    }

    public List<Trainer> findAll() {
        return em.createQuery("select t from Trainer t", Trainer.class)
                .getResultList();
    }
    @Transactional
    public void update(Trainer trainer) {
        em.merge(trainer);
    }

    public Trainer findById(Long id) {
        return em.find(Trainer.class, id);
    }

    public List<Trainer> findUnassignedTrainers() {
        return em.createQuery("SELECT t FROM Trainer t WHERE t.sportClub IS NULL", Trainer.class)
                .getResultList();
    }
}