package com.RehanaGroups.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(unique = true)
    private int roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private double price;

    private int capacity;

    private boolean active;

    private String description;

}
