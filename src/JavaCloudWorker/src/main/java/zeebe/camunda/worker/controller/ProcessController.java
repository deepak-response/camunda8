package zeebe.camunda.worker.controller;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeebe.camunda.worker.dto.ProcessStartRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/process")
public class ProcessController {

    @Autowired
    private ZeebeClient zeebeClient;


    // Start BPMN process with Business variables
    @PostMapping("/start")
    public String startProcess(@RequestBody ProcessStartRequest request) {
        // Prepare variables to send to BPMN process
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", request.getFirstName());
        variables.put("lastName", request.getLastName());

        // Start the process instance
        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("Process_script_task") // Use your process ID here
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        return "Process started for: " + request.getFirstName() + " " + request.getLastName();
    }

    // Start BPMN process and get Process Info
    @PostMapping("/initiateAndGetProcessInfo")
    public ResponseEntity<String> initiateAndGetProcessInfo(@RequestBody ProcessStartRequest request) {
        // Prepare variables to send to BPMN process
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", request.getFirstName());
        variables.put("lastName", request.getLastName());

        // Start the process instance and capture the result
        var processInstanceResult = zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("Process_script_task") // Your BPMN process ID
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        // Return details
        String response = String.format("✅ Process started!%nProcessInstanceKey: %d%nBPMNProcessId: %s%nVersion: %d%n",
                processInstanceResult.getProcessInstanceKey(),
                processInstanceResult.getBpmnProcessId(),
                processInstanceResult.getVersion());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/start1")
    public ResponseEntity<String> startProcess() {
        return ResponseEntity.ok("Process started");
    }


}

