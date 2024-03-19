package com.lab2.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue
    private Long id;
    private String color;

    @OneToMany(mappedBy = "color")
    private List<Cat> cats;
}
