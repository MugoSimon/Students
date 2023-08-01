package com.mugosimon.students.Students;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String studentCode;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    private String studentEmail;
    private String studentPhone;
    private String studentHome;
    //
    private LocalDate createdOn;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy = "ADMIN";
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character created = 'N';
    //
    private LocalDate deletedOn = null;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String deletedBy = "-";
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character deleted = 'N';
    //
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Character something_else = 'N';
}
