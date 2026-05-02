import java.util.ArrayList;

public class WektoryLogika 
{
public ArrayList<Integer> wczytajWektor(String linia) 
{
        ArrayList<Integer> vectors = new ArrayList<>();
        String slowa[] = linia.split(" ");
        for(String slowo: slowa)
            {
            try
            {
                int liczba = Integer.parseInt(slowo);
                vectors.add(liczba);
            }
            catch(NumberFormatException e)
            {

            }
        }
        return vectors;
    }
    public ArrayList<Integer> dodajWectory(ArrayList<Integer> w1, ArrayList<Integer> w2)
        throws WektoryRoznejDlugosciException 
        {
        if(w1.size() != w2.size())
            {
            throw new WektoryRoznejDlugosciException(w1.size(), w2.size());
        }
        ArrayList<Integer> wynik = new ArrayList<>();
        for (int i = 0; i < w1.size(); i++) 
            {
            wynik.add(w1.get(i) + w2.get(i));
        }
        return wynik;     
        }
}

