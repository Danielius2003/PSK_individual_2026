package org.example.daos;

import org.example.entities.SportClub;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.entities.Trainer;

import java.util.List;

@ApplicationScoped
public class SportClubDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(SportClub sportClub) {
        em.persist(sportClub);
    }

    public List<SportClub> findAll() {
        return em.createQuery("select t from SportClub t", SportClub.class)
                .getResultList();
    }
}
