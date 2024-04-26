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

    @OneToMany(mappedBy = "brick")
    private Set<Workplace> workplaces = new HashSet<>();

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkplaces(Set<Workplace> workplaces) {
        this.workplaces = workplaces;
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
