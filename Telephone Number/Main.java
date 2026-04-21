import java.util.*;

public class Main 
{
    public static void main(String[] args) 
    {
        TreeMap<NrTelefoniczny, Wpis> phoneBook = new TreeMap<>();

        phoneBook.put(new NrTelefoniczny(48, 111222333), new Osoba("Jan", "Kowalski", "ul. Morska 5"));
        phoneBook.put(new NrTelefoniczny(48, 444555666), new Firma("TechCorp", "ul. Morska 10"));
        phoneBook.put(new NrTelefoniczny(42, 999888777), new Osoba("Anna", "Nowak", "ul. Polna 1"));
        phoneBook.put(new NrTelefoniczny(42, 555000111), new Osoba("Piotr", "Zalewski", "ul. Polna 15")); 

        System.out.println("--- Full Phone Book ---");
        printPhoneBook(phoneBook);

        removeDuplicateStreets(phoneBook);

        System.out.println("\n--- After removing duplicate streets ---");
        printPhoneBook(phoneBook);
    }


    private static void printPhoneBook(TreeMap<NrTelefoniczny, Wpis> map) 
    {
        Iterator<Map.Entry<NrTelefoniczny, Wpis>> it = map.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry<NrTelefoniczny, Wpis> entry = it.next();
            System.out.print(entry.getKey() + " -> "); 
            entry.getValue().opisz(); 
        }
    }


    private static void removeDuplicateStreets(TreeMap<NrTelefoniczny, Wpis> map) 
    {
        HashSet<String> seenStreets = new HashSet<>();
        
        Iterator<Map.Entry<NrTelefoniczny, Wpis>> it = map.entrySet().iterator();

        while (it.hasNext()) 
        {
            Map.Entry<NrTelefoniczny, Wpis> entry = it.next();
            
            String fullAddress = entry.getValue().getAddress();
            

            String streetName = extractStreetName(fullAddress);

            if (seenStreets.contains(streetName)) 
            {
                it.remove(); 
                System.out.println("Removed duplicate street: " + streetName);
            } 
            else 
            {
                seenStreets.add(streetName);
            }
        }
    }

    private static String extractStreetName(String address) 
    {
        if (address == null || address.isEmpty())
            {
            return "";
            }
        String[] parts = address.split(" ");
        if (parts.length >= 2) 
        {
            return parts[0] + " " + parts[1];
        }
        return address;
    }
}
