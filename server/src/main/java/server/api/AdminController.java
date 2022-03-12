package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.Main;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PostMapping(value = "/restart")
    public ResponseEntity<?> restartServer(){
        System.out.println("Restart has been started");
        Main.restart();
        return ResponseEntity.ok("Restarted successfully");
    }
}
