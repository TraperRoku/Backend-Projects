package com.Filip.Expense.Tracker.API.controller;

import com.Filip.Expense.Tracker.API.model.Expense;
import com.Filip.Expense.Tracker.API.model.User;
import com.Filip.Expense.Tracker.API.repository.ExpenseRepository;
import com.Filip.Expense.Tracker.API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private ExpenseRepository expenseRepository;

    private UserService userService;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository,UserService userService){
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createExpense(@RequestBody Expense expense, Principal principal){
        User user = userService.findByUsername(principal.getName());
        expense.setUser(user);
        expenseRepository.save(expense);
        return new ResponseEntity<>("Expense created", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listExpenses(Principal principal,
                                                @RequestParam(required = false)LocalDate startDate,
                                               @RequestParam(required = false) LocalDate endDate){
        User user = userService.findByUsername(principal.getName());

        List<Expense> listExp = expenseRepository.findByUserAndDateBetween(user
        ,startDate != null ? startDate : LocalDate.MIN,
        endDate != null ? endDate : LocalDate.now()
                );

        return ResponseEntity.ok(listExp);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody Expense updateExpense, Principal principal){
        User user = userService.findByUsername(principal.getName());

        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));

        if(!expense.getUser().equals(user)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this expense");
        }
        expense.setCategory(updateExpense.getCategory());
        expense.setDate(updateExpense.getDate());
        expense.setAmount(updateExpense.getAmount());
        expense.setDescription(updateExpense.getDescription());

        Expense saveExpense = expenseRepository.save(expense);

        return ResponseEntity.ok(saveExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable long id, Principal principal){
        User user = userService.findByUsername(principal.getName());
        Expense expense = expenseRepository.findById(id).orElseThrow(()->
                new RuntimeException("Expense not found"));

        if(!expense.getUser().equals(user)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this expense");
        }

        expenseRepository.deleteById(id);

        return ResponseEntity.ok("Expense deleted");

    }



}
