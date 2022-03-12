package server.api;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This is a utility mapping that will probably be integrated into the admin stuff later
 */
@RestController
@RequestMapping("/api/admin")
public class UtilityController {

    private final ConfigurableApplicationContext context;

    /**
     * Constructor
     * @param context Spring application context
     */
    UtilityController(ConfigurableApplicationContext context) {
        this.context = context;
    }

    /**
     * Gracefully closes the Spring application.
     * @param message Quit message
     */
    @PostMapping("/quit")
    public void close(String message) {
        System.out.println("Closing the application with message:\n"+message);
        context.close();
    }
}
