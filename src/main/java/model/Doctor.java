package model;

import lombok.Data;

/**
 * Model for entity doctor*/
@Data
public class Doctor {

    private int id;

    private int salary;

    private String phoneNumber;

    private String description;
}