package model;

import lombok.Data;

/**
 * Model for entity patient
 */
@Data
public class Patient {

    private int id;

    private String address;

    private String phoneNumber;

    private int insuranceNumber;
}