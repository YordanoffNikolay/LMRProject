package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workplace_id")
    private Long id;

    @Column(name = "workplace_name")
    private String name;

    @Column(name = "workplace_address")
    private String address;

    @ManyToOne
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

    public Brick getBrick() {
        return brick;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workplace workplace = (Workplace) o;
        return Objects.equals(id, workplace.id)
                && Objects.equals(name, workplace.name)
                && Objects.equals(brick, workplace.brick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brick);
    }
}
