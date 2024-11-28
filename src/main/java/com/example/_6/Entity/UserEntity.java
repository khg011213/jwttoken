package com.example._6.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserEntity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String username;
    private String password;
    private String role;


}
