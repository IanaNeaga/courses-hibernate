package com.sda.hibernate.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="student")
@NamedQueries({
        @NamedQuery(name="Student.getByLastName",
        query="SELECT s from Student s WHERE s.lastname= :numeFamilie")
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_gen")
    @SequenceGenerator(name="student_gen", sequenceName = "student_seq", allocationSize=1)
    private Long id;

    @Size(min=1,max=30)
    @Column(name="first_name")
    private String firstname;

    @Size(min=1,max=30)
    @Column(name="last_name")
    private String lastname;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;

    @ManyToMany
    @JoinTable(
            name="students_courses",
            joinColumns = {@JoinColumn(name="student_id")},
            inverseJoinColumns = {@JoinColumn(name="course_id")}
    )
    private Set<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
