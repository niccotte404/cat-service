package com.catservice.catmicroservice.services.interfaces;

import com.catservice.catmicroservice.models.Color;

import java.util.List;

public interface ColorService {
    void addColor(Color color);
    void updateColor(Color color);
    void deleteColor(Color color);
    Color getColorById(long colorId);
    List<Color> getAllColors();
}
