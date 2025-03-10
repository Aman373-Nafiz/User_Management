package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Entity
@Data
@DiscriminatorValue("PARENT")
public class ParentModel extends UserModel {
    private String street;
    private String city;
    private String state;
    private String zip;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ChildModel> children;
}