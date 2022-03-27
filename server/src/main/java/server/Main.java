package server;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class Main {

    private static ConfigurableApplicationContext context;

    /**
     * Program entry point.
     * @param args command line arguments
     *      the only supported one is --port [portNr]
     */
    public static void main(String[] args) {
        int port = 8080;
        Arrays.stream(args).forEach(System.out::println);
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port specified, defaulting to 8080.");
            }
        }

        SpringApplication app = new SpringApplication(Main.class);
        app.setDefaultProperties(Collections.singletonMap(
                        "server.port", port
        ));
        context = app.run(args);
    }

    /**
     * Method restarts the application with the same arguments it was started.
     */
    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(Main.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }
}