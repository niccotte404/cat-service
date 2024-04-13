package com.catservice.catmicroservice.services.impl;

import com.catservice.catmicroservice.exceptions.ColorNotFoundException;
import com.catservice.catmicroservice.models.Color;
import com.catservice.catmicroservice.repositories.ColorRepository;
import com.catservice.catmicroservice.services.interfaces.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public void addColor(Color color) {
        colorRepository.save(color);
    }

    @Override
    public void updateColor(Color color) {
        colorRepository.save(color);
    }

    @Override
    public void deleteColor(Color color) {
        colorRepository.delete(color);
    }

    @Override
    public Color getColorById(long colorId) {
        return colorRepository.findById(colorId).orElseThrow(() -> new ColorNotFoundException("Color with current id not found"));
    }

    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}
