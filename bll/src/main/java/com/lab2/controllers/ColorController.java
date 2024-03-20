package com.lab2.controllers;

import com.lab2.entities.ResponseEntity;
import com.lab2.entities.enums.ResponseStatus;
import com.lab2.exceptions.ArgumentException;
import com.lab2.models.Color;
import com.lab2.repositories.impl.ColorRepositoryImpl;
import com.lab2.repositories.interfaces.ColorRepository;

public class ColorController {
    private final ColorRepository colorRepository = new ColorRepositoryImpl();

    public ResponseEntity<Color> getColorById(Long id) {
        ResponseEntity<Color> responseEntity = new ResponseEntity<>();
        try {
            responseEntity.setResultClass(colorRepository.getById((id)).orElseThrow(() -> new ArgumentException("Color with this ID does not exists")));
            responseEntity.setStatus(ResponseStatus.OK);
        }
        catch (Exception ex) {
            responseEntity.setStatus(ResponseStatus.FAILED);
        }

        return responseEntity;
    }

    public boolean addColor(String colorName) {
        Color color = new Color();
        color.setColor(colorName);
        try {
            colorRepository.insert(color);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteColor(Long id) {
        try {
            Color color = colorRepository.getById(id).orElseThrow(() -> new ArgumentException("Color with this ID does not exists"));
            colorRepository.delete(color);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean updateColor(Long id) {
        try {
            Color color = colorRepository.getById(id).orElseThrow(() -> new ArgumentException("Color with this ID does not exists"));
            colorRepository.update(color);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
