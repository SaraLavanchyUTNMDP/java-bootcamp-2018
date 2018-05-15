package models;

import lombok.Data;

import java.util.Date;

@Data
public class Teacher {
    private String name;
    private String surname;
    private Date birthDate;
}
