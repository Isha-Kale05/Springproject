package com.mbi.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class TableAssignmentService {

	 @Autowired
	    private TableAssignmentRepository repository;

	    public List<TableAssignmentDetails> findAll() {
	        return repository.findAll();
	    }

	    public TableAssignmentDetails save(TableAssignmentDetails details) {
	        return repository.save(details);
	    }

	    public TableAssignmentDetails findById(Long id) {
	        return repository.findById(id).orElse(null);
	    }

	    public void deleteById(Long id) {
	        repository.deleteById(id);
	    }
}
