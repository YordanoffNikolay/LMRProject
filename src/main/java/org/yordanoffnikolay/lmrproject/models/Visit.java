package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "visits")
public class Visit extends Task{

    @Column(name = "is_double")
    private boolean isDouble;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;

    public Visit() {
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
        if (!super.equals(o)) return false;
        Visit visit = (Visit) o;
        return isDouble == visit.isDouble &&
                Objects.equals(client, visit.client) &&
                Objects.equals(workplace, visit.workplace);
    }
}
