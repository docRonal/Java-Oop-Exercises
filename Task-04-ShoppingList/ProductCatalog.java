import java.io.*;
import java.util.*;

public class ProductCatalog {
    private final String fileName;
    private final Map<String, List<String>> availableProducts = new LinkedHashMap<>();

    public ProductCatalog(String fileName) {
        this.fileName = fileName;
        loadCatalog();
    }

    private void loadCatalog() 
    {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("ERROR: Catalog file not found!");
            return;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) 
        { //otwieranie pliku dla czytania i bezpieczne zamykanie
            String line;
            String currentCat = "Inne";
            availableProducts.put(currentCat, new ArrayList<>());

            while ((line = br.readLine()) != null) 
            {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("*")) 
                {
                    currentCat = line.substring(1).trim();
                    availableProducts.putIfAbsent(currentCat, new ArrayList<>());
                } else {
                    availableProducts.get(currentCat).add(line);
                }
            }
            if (availableProducts.get("Inne").isEmpty()) 
            {
                availableProducts.remove("Inne");
            }
        } catch (IOException e) 
        {
            System.out.println("Error loading catalog: " + e.getMessage());
        }
    }

    public boolean isEmpty() 
    {
        return availableProducts.isEmpty();
    }

    public List<String> getCategories()
    {
        return new ArrayList<>(availableProducts.keySet());
    }

    public List<String> getProducts(String category) 
    {
        return availableProducts.getOrDefault(category, new ArrayList<>());
    }
}
