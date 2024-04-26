package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private long visitId;

    @Column(name = "visit_date")
    private String date;

    @Column(name = "is_visited")
    private boolean isVisited;

    @Column(name = "is_double")
    private boolean isDouble;

    @Column(name = "is_locked")
    private boolean isLocked;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Visit() {
    }

    public long getVisitId() {
        return visitId;
    }

    public String getDate() {
        return date;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public Client getClient() {
        return client;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setDouble(boolean isDouble) {
        this.isDouble = isDouble;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return visitId == visit.visitId
                && isVisited == visit.isVisited
                && isLocked == visit.isLocked
                && isDouble == visit.isDouble
                && Objects.equals(date, visit.date)
                && Objects.equals(client, visit.client)
                && Objects.equals(workplace, visit.workplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitId, date, isVisited, isLocked, isDouble, client, workplace);
    }
}
