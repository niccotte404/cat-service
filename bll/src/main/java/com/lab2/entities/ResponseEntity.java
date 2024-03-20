package com.lab2.entities;

import com.lab2.entities.enums.ResponseStatus;
import lombok.Data;

@Data
public class ResponseEntity<TClass> {
    private TClass resultClass;
    private ResponseStatus status;
}
