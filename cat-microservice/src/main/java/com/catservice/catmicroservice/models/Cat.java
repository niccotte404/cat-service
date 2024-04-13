package com.catservice.catmicroservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    private Date birthday;
    private String breed;

    private UUID ownerId;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cat_friends",
            joinColumns = @JoinColumn(name = "first_cat_id"),
            inverseJoinColumns = @JoinColumn(name = "second_cat_id")
    )
    private List<Cat> friends = new ArrayList<>();
}