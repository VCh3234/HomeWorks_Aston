package model;


import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Model for entity doctor
 */
@Data
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "doctor_sequence_id")
    private int id;

    @Column(name = "d_salary", nullable = false)
    private int salary;

    @Column(name = "d_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "d_description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "institution_id")
    private HealthCareInstitution institution;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "doctor_patient",
            joinColumns = @JoinColumn(name = "d_id"),
            inverseJoinColumns = @JoinColumn(name = "p_id"))
    private List<Patient> patientList;
}