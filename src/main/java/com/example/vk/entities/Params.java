package com.example.vk.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Params {
    private String name;
    private String value;
}
