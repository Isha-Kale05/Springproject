package com.mbi.demo.services;

import org.camunda.bpm.model.bpmn.instance.BusinessRuleTask;

import org.camunda.bpm.model.bpmn.instance.ManualTask;
import org.camunda.bpm.model.bpmn.instance.ReceiveTask;
import org.camunda.bpm.model.bpmn.instance.ScriptTask;
import org.camunda.bpm.model.bpmn.instance.SendTask;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private BpmnService bpmnService;

    @GetMapping("/bpmn")
    public List<String> getBpmnFiles() {
        String folderPath = "C:\\Users\\ADMIN\\eclipse-workspace\\demo\\src\\main\\resources"; // Replace with your folder path
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".bpmn"));

        List<String> fileNames = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }

    @GetMapping("/bpmn/{fileName}")
    public ResponseEntity<Map<String, Object>> getBpmnFileDetails(@PathVariable("fileName") String fileName) {
        try {
            Map<String, Object> details = bpmnService.getBpmnDetails(fileName);
            return ResponseEntity.ok(details);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/bpmn/{fileName}/subprocesses")
    public ResponseEntity<List<Map<String, String>>> getSubProcesses(@PathVariable("fileName") String fileName) {
        try {
            List<Map<String, String>> subprocesses = bpmnService.getSubProcesses(fileName);
            return ResponseEntity.ok(subprocesses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(Map.of("error", e.getMessage())));
        }
    }

    @GetMapping("/bpmn/{fileName}/callactivities")
    public ResponseEntity<List<Map<String, String>>> getCallActivities(@PathVariable("fileName") String fileName) {
        try {
            List<Map<String, String>> callActivities = bpmnService.getCallActivities(fileName);
            return ResponseEntity.ok(callActivities);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(Map.of("error", e.getMessage())));
        }
    }
    
    @GetMapping("/bpmn/{fileName}/tasks/{taskType}")
    public ResponseEntity<List<Map<String, String>>> getTasksByType(
            @PathVariable("fileName") String fileName,
            @PathVariable("taskType") String taskType) {
        try {
            Class<? extends Task> taskClass;
            switch (taskType.toLowerCase()) {
                case "usertask":
                    taskClass = UserTask.class;
                    break;
                case "servicetask":
                    taskClass = ServiceTask.class;
                    break;
                case "scripttask":
                    taskClass = ScriptTask.class;
                    break;
                case "manualtask":
                    taskClass = ManualTask.class;
                    break;
                case "receivetask":
                    taskClass = ReceiveTask.class;
                    break;
                case "sendtask":
                    taskClass = SendTask.class;
                    break;
                default:
                    return ResponseEntity.badRequest().body(List.of(Map.of("error", "Invalid task type")));
            }
            List<Map<String, String>> tasks = bpmnService.getTasksByType(fileName, taskClass);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(Map.of("error", e.getMessage())));
        }
    }

    @GetMapping("/bpmn/{fileName}/participants")
    public ResponseEntity<List<Map<String, String>>> getParticipants(@PathVariable("fileName") String fileName) {
        try {
            List<Map<String, String>> participants = bpmnService.getParticipants(fileName);
            return ResponseEntity.ok(participants);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(Map.of("error", e.getMessage())));
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
    }
}
