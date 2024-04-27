package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "brick")
public class Brick {
    @Id
    @Column(name = "brick_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brick_name")
    private String name;

    @OneToMany(mappedBy = "brick", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Workplace> workplaces = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "brick_user",
            joinColumns = @JoinColumn(name = "brick_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    public Brick() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Workplace> getWorkplaces() {
        return workplaces;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkplaces(Set<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brick brick = (Brick) o;
        return Objects.equals(id, brick.id)
                && Objects.equals(name, brick.name)
                && Objects.equals(workplaces, brick.workplaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, workplaces);
    }
}
