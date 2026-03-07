package org.example.daos;

import org.example.entities.Player;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PlayerDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Player player) {
        em.persist(player);
    }

    public List<Player> findAll() {
        return em.createQuery("select t from Player t", Player.class)
                .getResultList();
    }
}
