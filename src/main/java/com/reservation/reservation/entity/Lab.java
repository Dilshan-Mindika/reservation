package com.reservation.reservation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "labs")

public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String labType;
    private int labCapacity;
    private String labPhotoUrl;
    private String labDescription;

    @OneToMany(mappedBy = "lab", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reserving> reservings = new ArrayList<>();

    @Override
    public String toString() {
        return "Lab{" +
                "id=" + id +
                ", labType='" + labType + '\'' +
                ", labCapacity=" + labCapacity +
                ", labPhotoUrl='" + labPhotoUrl + '\'' +
                ", labDescription='" + labDescription + '\'' +
                '}';
    }
}
