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
}