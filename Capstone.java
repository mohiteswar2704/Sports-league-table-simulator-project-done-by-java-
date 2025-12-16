package project;

import java.util.*;

public class Capstone {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----IPL Tournament Scheduler----");

        int count = 0;
        while (count < 2) {
            System.out.print("Enter number of teams (min 2): ");
            try {
                String input = scanner.nextLine().trim();
                count = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        List<String> teams = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.print("Enter name for Team " + (i + 1) + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = "Team " + (i + 1);
            }
            teams.add(name);
        }

        System.out.println("\n=== FIXTURES ===");
        int matchCount = 1;
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                String home = teams.get(i);
                String away = teams.get(j);
                System.out.println("Match " + matchCount + ": " + home + " vs " + away);
                matchCount++;
            }
        }
        
        scanner.close();
    }
}