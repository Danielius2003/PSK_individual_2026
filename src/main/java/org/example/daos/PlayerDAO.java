package org.example.daos;

import org.example.entities.Player;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.entities.Trainer;

import java.util.List;

@ApplicationScoped
public class PlayerDAO {

    @PersistenceContext //serveris sukuria em
    private EntityManager em;

    @Transactional
    public void save(Player player) {
        em.persist(player);
    }

    public List<Player> findAll() {
        return em.createQuery("select t from Player t", Player.class)
                .getResultList();
    }
    public Player findById(Long id) {
        return em.find(Player.class, id);
    }

    @Transactional
    public void update(Player player) {
        em.merge(player);
    }
    public List<Player> findAvailableForTrainer(Long trainerId) {
        return em.createQuery(
                        "SELECT p FROM Player p WHERE p.id NOT IN (" +
                                "SELECT pl.id FROM Trainer t JOIN t.players pl WHERE t.id = :trainerId" +
                                ")",
                        Player.class)
                .setParameter("trainerId", trainerId)
                .getResultList();
    }
}
