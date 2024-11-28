package com.example._6.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TestEntitly {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String title;
    private String content;


}
