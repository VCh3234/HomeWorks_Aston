package model;

import lombok.Data;

/**
 * Model for entity healthCareInstitution
 */
@Data
public class HealthCareInstitution {

    private int id;

    private String description;

    private String address;

    private boolean free;
}