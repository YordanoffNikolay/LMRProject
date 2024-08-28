package org.yordanoffnikolay.lmrproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workplaces")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workplace_id")
    private Long id;

    @Column(name = "workplace_name")
    private String name;

    @Column(name = "workplace_address")
    private String address;

    @OneToMany(mappedBy = "workplace", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"workplace"})
    private List<Client> clients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brick_id")
    private Brick brick;

    public Workplace() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workplace workplace = (Workplace) o;
        return Objects.equals(id, workplace.id)
                && Objects.equals(name, workplace.name)
                && Objects.equals(address, workplace.address)
                && Objects.equals(clients, workplace.clients);
    }
}
