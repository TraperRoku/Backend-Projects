package com.Filip.Expense.Tracker.API.repository;

import com.Filip.Expense.Tracker.API.model.Expense;
import com.Filip.Expense.Tracker.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate,LocalDate endDate);
}
