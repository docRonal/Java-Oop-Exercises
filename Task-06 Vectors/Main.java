import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
    public static void main(String args[]) 
    {
        Scanner scanner = new Scanner(System.in);
        WektoryLogika logika = new WektoryLogika();
        
        while(true) 
        {
            System.out.println("Podaj 1 wektor:");
            String linia1 = scanner.nextLine();
            ArrayList<Integer> w1 = logika.wczytajWektor(linia1);
            
            System.out.println("Podaj 2 wektor:");
            String linia2 = scanner.nextLine();
            ArrayList<Integer> w2 = logika.wczytajWektor(linia2);
            
            try 
            {
                ArrayList<Integer> wynik = logika.dodajWectory(w1, w2);
                
                System.out.println("Wynik: " + wynik);
                break; 
                
            } catch(WektoryRoznejDlugosciException e) 
            {
                System.out.println("Błąd! Długość pierwszego wektora to " + e.getdlugosc1() +
                                   " a drugiego to " + e.getdlugosc2() + ". Spróbuj ponownie.\n");
            }
        } 
        
        scanner.close(); 
    }
}
