import java.util.*;

public class Capstone {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Team> teams = new ArrayList<>();
    private final Map<Team, Stats> table = new LinkedHashMap<>();

    public static void main(String[] args) { new Capstone().run(); }

    private void run() {
        System.out.println("=== League Simulator ===");

        int n = readInt("Enter number of teams (min 2): ", 2);
        Set<String> used = new HashSet<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name for Team " + (i + 1) + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = "Team " + (i + 1);
            name = uniqueName(name, used);
            used.add(name);
            Team t = new Team(name);
            teams.add(t);
            table.put(t, new Stats());
        }

        System.out.println("\n=== League Stage ===");
        int matchNo = 1;
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Team a = teams.get(i);
                Team b = teams.get(j);
                System.out.println("Match " + matchNo++ + ": " + a.getName() + " vs " + b.getName());
                int outcome = askOutcome(a, b);
                if (outcome == 1) { table.get(a).win(); table.get(b).loss(); }
                else if (outcome == 2) { table.get(a).loss(); table.get(b).win(); }
                else { table.get(a).draw(); table.get(b).draw(); }
            }
        }

        List<Map.Entry<Team, Stats>> standings = standings();
        printStandings(standings);

        if (teams.size() >= 4) playKnockouts(standings);
        else System.out.println("\nNot enough teams for semi-finals (need 4).");

        System.out.println("\nDone.");
    }

    private int readInt(String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v >= min) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a number >= " + min + ".");
        }
    }

    private String uniqueName(String name, Set<String> used) {
        if (!used.contains(name)) return name;
        int k = 2;
        while (used.contains(name + " (" + k + ")")) k++;
        return name + " (" + k + ")";
    }

    private Team askWinner(Team a, Team b) {
        while (true) {
            System.out.print("Who wins? (1: " + a.getName() + ", 2: " + b.getName() + "): ");
            String s = scanner.nextLine().trim();
            if ("1".equals(s)) return a;
            if ("2".equals(s)) return b;
            System.out.println("Enter 1 or 2.");
        }
    }

    private int askOutcome(Team a, Team b) {
        while (true) {
            System.out.print("Result? (1: " + a.getName() + " wins, 2: " + b.getName() + " wins, 3: draw): ");
            String s = scanner.nextLine().trim();
            if ("1".equals(s)) return 1;
            if ("2".equals(s)) return 2;
            if ("3".equals(s)) return 0;
            System.out.println("Enter 1, 2, or 3.");
        }
    }

    private List<Map.Entry<Team, Stats>> standings() {
        List<Map.Entry<Team, Stats>> rows = new ArrayList<>(table.entrySet());
        rows.sort((e1, e2) -> {
            Stats s1 = e1.getValue();
            Stats s2 = e2.getValue();
            if (s1.points != s2.points) return Integer.compare(s2.points, s1.points);
            if (s1.won != s2.won) return Integer.compare(s2.won, s1.won);
            return e1.getKey().getName().compareToIgnoreCase(e2.getKey().getName());
        });
        return rows;
    }

    private void printStandings(List<Map.Entry<Team, Stats>> rows) {
        System.out.println("\n=== Standings ===");
        System.out.println("Pos  Team                 P  W  D  L  Pts");
        int pos = 1;
        for (Map.Entry<Team, Stats> e : rows) {
            Stats s = e.getValue();
            System.out.printf(Locale.US, "%3d  %-20s %2d %2d %2d %2d %3d%n", pos++, e.getKey().getName(), s.played, s.won, s.drawn, s.lost, s.points);
        }
    }

    private void playKnockouts(List<Map.Entry<Team, Stats>> rows) {
        System.out.println("\n=== Knockout Stage ===");
        Team t1 = rows.get(0).getKey();
        Team t2 = rows.get(1).getKey();
        Team t3 = rows.get(2).getKey();
        Team t4 = rows.get(3).getKey();

        System.out.println("Qualified: " + t1.getName() + ", " + t2.getName() + ", " + t3.getName() + ", " + t4.getName());

        System.out.println("\nSemi-Final 1: " + t1.getName() + " vs " + t4.getName());
        Team sf1 = askWinner(t1, t4);

        System.out.println("Semi-Final 2: " + t2.getName() + " vs " + t3.getName());
        Team sf2 = askWinner(t2, t3);

        System.out.println("\nFinal: " + sf1.getName() + " vs " + sf2.getName());
        Team champion = askWinner(sf1, sf2);

        System.out.println("\nChampion: " + champion.getName());
    }
}

class Team {
    private final String name;
    Team(String name){ this.name = name; }
    String getName(){ return name; }
    public String toString(){ return name; }
    public boolean equals(Object o){ if(this==o) return true; if(!(o instanceof Team)) return false; Team t=(Team)o; return Objects.equals(name,t.name);} 
    public int hashCode(){ return Objects.hash(name); }
}

class Stats {
    int played;
    int won;
    int drawn;
    int lost;
    int points; // 2 points for a win, 1 for a draw
    void win(){ played++; won++; points += 2; }
    void draw(){ played++; drawn++; points += 1; }
    void loss(){ played++; lost++; }
}

class Match { final Team home; final Team away; Match(Team h, Team a){ home=h; away=a; } }
