import java.io.*;
import java.util.*;

public class ShoppingList 
{
    private final String fileName;
    private final Map<String, List<String>> items = new LinkedHashMap<>();

    public ShoppingList(String fileName) 
    {
        this.fileName = fileName;
        loadFromFile();
    }

    public void addProduct(String category, String product) 
    {
        items.putIfAbsent(category, new ArrayList<>());
        items.get(category).add(product);
        System.out.println("Added: " + product);
    }

    public void showAll() 
    {
        if (items.isEmpty()) 
        {
            System.out.println("Your shopping list is empty.");
            return;
        }
        System.out.println("\nYOUR CURRENT LIST:");
        items.forEach((cat, prods) -> System.out.println("[" + cat + "]: " + String.join(", ", prods)));
    }

    public void showCategory(String category) 
    {
        System.out.println("Items in " + category + ": " + items.get(category));
    }

    public void clearAll() 
    {
        items.clear();
        System.out.println("Shopping list has been cleared.");
    }

    public void removeCategory(String category) 
    {
        items.remove(category);
        System.out.println("Removed category: " + category);
    }

    public void removeProduct(String category, String product) 
    {
        List<String> prods = items.get(category);
        if (prods != null && prods.remove(product)) 
        {
            System.out.println("Removed: " + product);
            if (prods.isEmpty()) 
            {
                items.remove(category);
            }
        }
    }

    public List<String> getCategories() 
    {
        return new ArrayList<>(items.keySet());
    }

    public List<String> getProducts(String category) 
    {
        return items.getOrDefault(category, new ArrayList<>());
    }

    public void saveToFile() 
    {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
            for (var entry : items.entrySet()) 
            {
                pw.println("*" + entry.getKey());
                for (String item : entry.getValue()) 
                {
                    pw.println(item);
                }
            }
            System.out.println("List successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error while saving: " + e.getMessage());
        }
    }

    private void loadFromFile() 
    {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) 
        {
            String line;
            String currentCat = null;
            while ((line = br.readLine()) != null) 
            {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("*")) 
                {
                    currentCat = line.substring(1).trim();
                    items.putIfAbsent(currentCat, new ArrayList<>());
                } else if (currentCat != null) 
                {
                    items.get(currentCat).add(line);
                }
            }
            System.out.println("Last saved shopping list loaded.");
        } catch (IOException e) 
        {
            System.out.println("Failed to load previous shopping list.");
        }
    }
}
