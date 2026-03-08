package org.example.daos;

import org.example.entities.Player;
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

    public List<Player> findAvailablePlayers(Trainer trainer) {
        return em.createQuery("SELECT p FROM Player p WHERE :trainer NOT MEMBER OF p.trainers", Player.class)
                .setParameter("trainer", trainer)
                .getResultList();
    }

    @Transactional
    public void addPlayerToTrainer(Long trainerId, Long playerId) {

        Trainer trainer = em.find(Trainer.class, trainerId);
        Player player = em.find(Player.class, playerId);

        trainer.getPlayers().add(player);
        player.getTrainers().add(trainer);
    }

    public EntityManager getEntityManager() {
        return em;
    }

}