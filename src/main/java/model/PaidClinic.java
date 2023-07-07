package model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model for entity paid clinic
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "paid_clinic")
public class PaidClinic extends HealthCareInstitution {

    @Column(name = "pc_onwer", nullable = false)
    private String owner;
}