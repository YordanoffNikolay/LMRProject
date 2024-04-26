package org.yordanoffnikolay.lmrproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private long clientId;



    @Column(name = "client_name")
    private String name;

    private long brickId;

    private long workplaceId;
}
