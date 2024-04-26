package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private long clientId;

    @Column(name = "client_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_workplaces",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "workplace_id")
    )
    private Set<Workplace> workplaces = new HashSet<>();

    public Client() {
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Workplace> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(Set<Workplace> workplaces) {
        this.workplaces = workplaces;
    }
}
