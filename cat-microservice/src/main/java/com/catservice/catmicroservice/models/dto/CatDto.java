package com.catservice.catmicroservice.models.dto;

import com.catservice.catmicroservice.models.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatDto {
    private String name;
    private Date birthday;
    private String breed;
    private Color color;
}
