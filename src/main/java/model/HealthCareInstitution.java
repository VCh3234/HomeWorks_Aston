package model;


import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Model for entity healthCareInstitution
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "institution")
abstract public class HealthCareInstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "institution_generator")
    @SequenceGenerator(name = "institution_generator", sequenceName = "institution_sequence_id")
    private int id;

    @Column(name = "i_description", nullable = false)
    private String description;

    @Column(name = "i_address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "institution", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Doctor> doctorList;
}