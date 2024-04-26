package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "titles")
public class Title {

    @Id
    @GeneratedValue
    @Column(name = "title_id")
    private long titleId;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Title() {
    }

    public Title(String title, Client client) {
        this.title = title;
        this.client = client;
    }

    public long getTitleId() {
        return this.titleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
