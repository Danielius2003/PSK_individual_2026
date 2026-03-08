package org.example.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationBean {

    private Long trainerId;
    private Long clubId;

    // --- optional: setters from pages ---
    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    // --- navigation methods ---

    /**
     * Go back to trainer page with trainerId in URL
     */
    public String backToTrainer() {
        if (trainerId != null) {
            return "trainerInfo?faces-redirect=true&trainerId=" + trainerId;
        }
        return "trainerList?faces-redirect=true"; // fallback
    }

    /**
     * Go back to sport club page with clubId in URL
     */
    public String backToClub() {
        if (clubId != null) {
            return "sportClubInfo?faces-redirect=true&clubId=" + clubId;
        }
        return "sportClubList?faces-redirect=true"; // fallback
    }
}