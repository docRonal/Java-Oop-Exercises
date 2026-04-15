import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Błąd: Podaj dokładnie jeden argument (limit N).");
            return;
        }
        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Błąd: Parametr musi być liczbą całkowitą.");
            return;
        }
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int wylosowanaLiczba = random.nextInt(n + 1);
        int liczbaProb = 0;

        System.out.println("Wylosowałem liczbę od 0 do " + n + ". Zgadnij jaką!");
        while (true) {
            System.out.print("Twój strzał: ");

            if (!scanner.hasNextInt()) 
            {
                System.out.println("To nie jest liczba! Spróbuj ponownie.");
                scanner.next();
                continue;
            }

            int mojStrzal = scanner.nextInt();	
            if (mojStrzal < 0 || mojStrzal > n) {
    		System.out.println("Błąd: Podaj liczbę z zakresu 0 do " + n + "!");
    		continue;
		}
            liczbaProb++;
            if (mojStrzal < wylosowanaLiczba) {
                System.out.println("(Bigger)");
            } else if (mojStrzal > wylosowanaLiczba) {
                System.out.println("(Lower)");
            } else {
                System.out.println("Zgadłeś w " + liczbaProb + " próbach.");

                System.out.print("Chcesz zagrać jeszcze raz? (t/n): ");
                String decyzja = scanner.next();
                if (decyzja.equalsIgnoreCase("t")) {
                    wylosowanaLiczba = random.nextInt(n + 1);
                    liczbaProb = 0;
                    System.out.println("\nLosuję ponownie...");
                } else {
                    System.out.println("Koniec gry.");
                    break;
                }
            }
        }
        scanner.close();
    }
}
