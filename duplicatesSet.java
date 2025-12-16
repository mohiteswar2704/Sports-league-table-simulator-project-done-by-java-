package project;
import java.util.*;

public class duplicatesSet {
    public static void main(String[] args) {
        System.out.println("id:2500080124");
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter numbers separated by spaces:");
        String input = sc.nextLine();

        String[] parts = input.split(" ");
        List<Integer> numbers = new ArrayList<>();

        for (String p : parts) {
            numbers.add(Integer.parseInt(p));
        }

        Set<Integer> unique = new HashSet<>(numbers);

        System.out.println("Original List: " + numbers);
        System.out.println("After Removing Duplicates: " + unique);
        sc.close();
    }
}