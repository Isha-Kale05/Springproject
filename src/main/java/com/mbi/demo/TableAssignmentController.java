package com.mbi.demo;

import java.util.List;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component("tableAssignmentController")
@RestController
@RequestMapping("/api/table-assignment-details")
public class TableAssignmentController implements JavaDelegate {

    @Autowired
    private TableAssignmentService service;

    @GetMapping
    public List<TableAssignmentDetails> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TableAssignmentDetails getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @PostMapping
    public TableAssignmentDetails create(@RequestBody TableAssignmentDetails details) {
        return service.save(details);
    }

    @PutMapping("/{id}")
    public TableAssignmentDetails update(@PathVariable("id") Long id, @RequestBody TableAssignmentDetails details) {
        TableAssignmentDetails existingDetails = service.findById(id);
        if (existingDetails != null) {
            existingDetails.setTableNumber(details.getTableNumber());
            existingDetails.setRestaurantName(details.getRestaurantName());
            existingDetails.setRestaurantStatus(details.getRestaurantStatus());
            return service.save(existingDetails);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Extract variables from DelegateExecution
        String tableNumber = (String) execution.getVariable("tableNumber");
        String restaurantName = (String) execution.getVariable("restaurantName");
        String restaurantStatus = (String) execution.getVariable("restaurantStatus");

        // Create a new TableAssignmentDetails object
        TableAssignmentDetails details = new TableAssignmentDetails();
        details.setTableNumber(tableNumber);
        details.setRestaurantName(restaurantName);
        details.setRestaurantStatus(restaurantStatus);

        // Save the details using the service
        service.save(details);

        // Optional: Log the details for debugging purposes
        System.out.println("Table Assignment Details saved: " + details);
    }
}
