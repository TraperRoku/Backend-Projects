package com.Filip.Expense.Tracker.API.model;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double amount;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category{
        GROCERIES,
        LEISURE,
        ELECTRONICS,
        UTILITIES,
        CLOTHING,
        HEALTH,
        OTHERS
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
