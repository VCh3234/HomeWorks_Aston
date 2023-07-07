package model;


import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Model for entity patient
 */

@Data
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "patient_sequence_id")
    private int id;

    @Column(name = "p_address", nullable = false)
    private String address;

    @Column(name = "p_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "p_insurance_number", nullable = false)
    private int insuranceNumber;

    @ManyToMany(mappedBy = "patientList", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Doctor> doctorList;
}