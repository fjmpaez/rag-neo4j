package com.adictosaltrabajo.rag;

import com.adictosaltrabajo.rag.ai.CrimeExpertAgent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AssistantRunner implements CommandLineRunner {

    private final CrimeExpertAgent cinemaExpertAgent;


    public AssistantRunner(CrimeExpertAgent cinemaExpertAgent) {
        this.cinemaExpertAgent = cinemaExpertAgent;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Crime Assistant! Type 'exit' to quit.");

        while (true) {
            System.out.print("Ask the expert->: ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Goodbye!");
                break;
            }

            // Simulate assistant response
            String response = getAssistantResponse(input);

            System.out.println("A continuación muestro la respuesta a la pregunta: " + input);
            System.out.println();
            System.out.println("Assistant: " + response);
            System.out.println("****************************************************");

        }

        scanner.close();
    }

    private String getAssistantResponse(String input) {
        return "AI-> " + cinemaExpertAgent.ask(input);
    }
}
