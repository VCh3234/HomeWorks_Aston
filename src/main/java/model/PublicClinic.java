package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model for entity public clinic
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "public_clinic")
public class PublicClinic extends HealthCareInstitution {

    @Column(name = "puc_state", nullable = false)
    private String state;
}