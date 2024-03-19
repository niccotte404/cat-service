package com.lab2.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cat_friends",
            joinColumns = @JoinColumn(name = "first_cat_id"),
            inverseJoinColumns = @JoinColumn(name = "second_cat_id", referencedColumnName = "id")
    )
    private Set<Cat> friends;
}
