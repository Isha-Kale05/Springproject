package com.mbi.demo.services;

import org.camunda.bpm.model.bpmn.Bpmn;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BpmnService {

    private static final Logger logger = LoggerFactory.getLogger(BpmnService.class);

    public Map<String, Object> getBpmnDetails(String fileName) {
        String folderPath = "C:\\Users\\ADMIN\\eclipse-workspace\\demo\\src\\main\\resources"; // Replace with your folder path
        File file = new File(folderPath + File.separator + fileName);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        Map<String, Object> details = new HashMap<>();

        try {
            var bpmnModelInstance = Bpmn.readModelFromFile(file);
            var process = bpmnModelInstance.getModelElementsByType(Process.class).iterator().next();

            details.put("processId", process.getId());
            details.put("processName", process.getName());

            var tasks = getTasks(bpmnModelInstance);
            details.put("tasks", tasks);

            var participants = getParticipants(bpmnModelInstance);
            details.put("participants", participants);

            var lanes = getLanes(bpmnModelInstance);
            details.put("lanes", lanes);
            
            var subprocesses = getSubProcesses(bpmnModelInstance);
            details.put("subprocesses", subprocesses);

            var callActivities = getCallActivities(bpmnModelInstance);
            details.put("callActivities", callActivities);

        } catch (Exception e) {
            logger.error("Error processing BPMN file: " + fileName, e);
            throw new RuntimeException("Error processing BPMN file: " + fileName, e);
        }

        return details;
    }

    private Map<String, Map<String, String>> getTasks(BpmnModelInstance bpmnModelInstance) {
        var tasks = new HashMap<String, Map<String, String>>();
        for (Task task : bpmnModelInstance.getModelElementsByType(Task.class)) {
            var taskDetails = new HashMap<String, String>();
            taskDetails.put("id", task.getId());
            taskDetails.put("name", task.getName());
            taskDetails.put("type", task.getElementType().getTypeName());
            taskDetails.put("documentation", getDocumentation(task));
            tasks.put(task.getId(), taskDetails);
        }
        return tasks;
    }

    private Map<String, String> getParticipants(BpmnModelInstance bpmnModelInstance) {
        var participants = new HashMap<String, String>();
        for (Participant participant : bpmnModelInstance.getModelElementsByType(Participant.class)) {
            participants.put(participant.getId(), participant.getName());
        }
        return participants;
    }
    
    public List<Map<String, String>> getSubProcesses(String fileName) {
        BpmnModelInstance bpmnModelInstance = getModelInstance(fileName);
        return getSubProcesses(bpmnModelInstance);
    }

    public List<Map<String, String>> getCallActivities(String fileName) {
        BpmnModelInstance bpmnModelInstance = getModelInstance(fileName);
        return getCallActivities(bpmnModelInstance);
    }

    private List<Map<String, String>> getSubProcesses(BpmnModelInstance bpmnModelInstance) {
        return bpmnModelInstance.getModelElementsByType(SubProcess.class).stream()
                .map(subprocess -> {
                    var subprocessDetails = new HashMap<String, String>();
                    subprocessDetails.put("id", subprocess.getId());
                    subprocessDetails.put("name", subprocess.getName());
                    subprocessDetails.put("documentation", getDocumentation(subprocess));
                    return subprocessDetails;
                }).collect(Collectors.toList());
    }

    private List<Map<String, String>> getCallActivities(BpmnModelInstance bpmnModelInstance) {
        return bpmnModelInstance.getModelElementsByType(CallActivity.class).stream()
                .map(callActivity -> {
                    var callActivityDetails = new HashMap<String, String>();
                    callActivityDetails.put("id", callActivity.getId());
                    callActivityDetails.put("name", callActivity.getName());
                    callActivityDetails.put("documentation", getDocumentation(callActivity));
                    return callActivityDetails;
                }).collect(Collectors.toList());
    }

    private Map<String, String> getLanes(BpmnModelInstance bpmnModelInstance) {
        var lanes = new HashMap<String, String>();
        for (Lane lane : bpmnModelInstance.getModelElementsByType(Lane.class)) {
            lanes.put(lane.getId(), lane.getName());
        }
        return lanes;
    }

    private String getDocumentation(BaseElement element) {
        return element.getChildElementsByType(Documentation.class).stream()
                .map(Documentation::getTextContent)
                .findFirst()
                .orElse("");
    }
    public List<Map<String, String>> getTasksByType(String fileName, Class<? extends Task> taskType) {
        String folderPath = "D:\\Java Practice\\events\\src\\main\\resources"; // Replace with your folder path
        File file = new File(folderPath + File.separator + fileName);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        var bpmnModelInstance = Bpmn.readModelFromFile(file);
        return bpmnModelInstance.getModelElementsByType(taskType).stream()
                .map(task -> {
                    var taskDetails = new HashMap<String, String>();
                    taskDetails.put("id", task.getId());
                    taskDetails.put("name", task.getName());
                    taskDetails.put("type", task.getElementType().getTypeName());
                    taskDetails.put("documentation", getDocumentation(task));
                    return taskDetails;
                }).collect(Collectors.toList());
    }

    public List<Map<String, String>> getParticipants(String fileName) {
        BpmnModelInstance bpmnModelInstance = getModelInstance(fileName);
        return bpmnModelInstance.getModelElementsByType(Participant.class).stream()
                .map(participant -> {
                    var participantDetails = new HashMap<String, String>();
                    participantDetails.put("id", participant.getId());
                    participantDetails.put("name", participant.getName());
                    return participantDetails;
                }).collect(Collectors.toList());
    }

    private BpmnModelInstance getModelInstance(String fileName) {
        String folderPath = "D:\\Java Practice\\events\\src\\main\\resources"; // Replace with your folder path
        File file = new File(folderPath + File.separator + fileName);

        if (!file.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        return Bpmn.readModelFromFile(file);
    }
}
