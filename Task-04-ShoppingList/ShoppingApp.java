import java.util.List;
import java.util.Scanner;

public class ShoppingApp 
{
    private final Scanner scanner;
    private final ProductCatalog catalog;
    private final ShoppingList shoppingList;

    public ShoppingApp() 
    {
        this.scanner = new Scanner(System.in);
        this.catalog = new ProductCatalog("produkty.txt");
        this.shoppingList = new ShoppingList("shopping_list.txt");
    }

    public void start() 
    {
        boolean running = true;
        while (running) 
        {
            printMenu();
            try 
            {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) 
                {
                    case 1 -> addProductFlow();
                    case 2 -> shoppingList.showAll();
                    case 3 -> showCategoryFlow();
                    case 4 -> shoppingList.clearAll();
                    case 5 -> removeCategoryFlow();
                    case 6 -> removeProductFlow();
                    case 7 -> shoppingList.saveToFile();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) 
            {
                System.out.println("Error: You must enter a number!");
            }
        }
        System.out.println("Goodbye!");
    }

    private void printMenu() 
    {
        System.out.println("\n--- SHOPPING LIST MENU ---");
        System.out.println("1. Add product");
        System.out.println("2. Show full shopping list");
        System.out.println("3. Show products from a specific category");
        System.out.println("4. Clear EVERYTHING");
        System.out.println("5. Remove an entire category from list");
        System.out.println("6. Remove a specific product");
        System.out.println("7. Save list to disk");
        System.out.println("0. Exit");
        System.out.print("Selection: ");
    }

    private void addProductFlow() 
    {
        if (catalog.isEmpty()) 
        {
            System.out.println("Catalog is empty!");
            return;
        }

        String category = selectItem(catalog.getCategories(), "Category");
        if (category == null) return;

        String product = selectItem(catalog.getProducts(category), "Product");
        if (product == null) return;

        shoppingList.addProduct(category, product);
    }

    private void showCategoryFlow() 
    {
        String category = selectItem(shoppingList.getCategories(), "Category");
        if (category != null) 
        {
            shoppingList.showCategory(category);
        }
    }

    private void removeCategoryFlow() {
        String category = selectItem(shoppingList.getCategories(), "Category to remove");
        if (category != null) 
        {
            shoppingList.removeCategory(category);
        }
    }

    private void removeProductFlow() 
    {
        String category = selectItem(shoppingList.getCategories(), "Category");
        if (category == null) return;

        String product = selectItem(shoppingList.getProducts(category), "Product to remove");
        if (product != null) 
        {
            shoppingList.removeProduct(category, product);
        }
    }

    private String selectItem(List<String> items, String prompt) 
    {
        if (items == null || items.isEmpty()) 
        {
            System.out.println("List is empty.");
            return null;
        }
        for (int i = 0; i < items.size(); i++) 
        {
            System.out.println(i + ". " + items.get(i));
        }
        System.out.print(prompt + " number: ");
        int idx = Integer.parseInt(scanner.nextLine());
        
        if (idx >= 0 && idx < items.size()) 
        {
            return items.get(idx);
        }
        System.out.println("Invalid number.");
        return null;
    }
}
