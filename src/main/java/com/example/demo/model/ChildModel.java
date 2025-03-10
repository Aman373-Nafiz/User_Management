package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("CHILD")
public class ChildModel extends UserModel {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ParentModel parent;
}