package zeebe.camunda.worker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zeebe.camunda.worker.dto.ProcessStartRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/custom")
public class CustomController {
    static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @PostMapping("/checkKYC")
    public ResponseEntity<Map<String, Object>> checkKyc(@RequestBody Map<String, Object> payload) {
        Integer age = (Integer) payload.get("age");
        Double salary = Double.valueOf(payload.get("salary").toString());
        String employmentType = (String) payload.get("employmentType");
        logger.info("Evaluation KYC Check for Customer having age: " + age + " ,Salary: "+salary+ " ,employmentType: "+employmentType);


        String status = (age >= 21 && salary >= 30000 && !"Unemployed".equals(employmentType))
                ? "APPROVED"
                : "REJECTED";

        Map<String, Object> response = new HashMap<>();
        response.put("kycStatus", status);
        logger.info("KYC Status: " + status);
        return ResponseEntity.ok(response);
    }
}
