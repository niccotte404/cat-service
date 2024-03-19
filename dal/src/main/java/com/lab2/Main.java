package com.lab2;

import com.lab2.models.Color;
import com.lab2.repositories.impl.ColorRepositoryImpl;
import com.lab2.repositories.interfaces.ColorRepository;

public class Main {
    public static void main(String[] args) {
        ColorRepository colorRepository = new ColorRepositoryImpl();

        Color color = new Color();
        color.setColor("BLACK");
        colorRepository.insert(color);
    }
}