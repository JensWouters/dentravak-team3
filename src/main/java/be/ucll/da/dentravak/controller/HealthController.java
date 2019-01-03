package be.ucll.da.dentravak.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HealthController {

    @Inject
    private DiscoveryClient discoveryClient;

    @GetMapping("/health-check")
    public ResponseEntity<String> myCustomCheck() {
        String message = "Testing my health check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}