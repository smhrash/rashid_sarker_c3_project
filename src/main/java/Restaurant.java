import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime currentTime = getCurrentTime();
        if (currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime)) {
            return true;
        } else {
            return false;
        }
    }

    public LocalTime getCurrentTime() {

        return LocalTime.now();
    }

    public List<Item> getMenu() {

        return menu;
    }

    public List<String> getItemNames() {
        List<String> itemNames = new ArrayList<String>();
        for (Item item : menu) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }

    private Item findItemByName(String itemName) {
        for (Item item : menu) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name, price);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws ItemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new ItemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }

    public void displayDetails() {
        System.out.println("Restaurant:" + name + "\n"
                + "Location:" + location + "\n"
                + "Opening time:" + openingTime + "\n"
                + "Closing time:" + closingTime + "\n"
                + "Menu:" + "\n" + getMenu() + "\n"
                + "TotalBill:" + calculateOrderTotal(getItemNames()));

    }

    public String getName() throws RestaurantNotFoundException {
        return name;
    }

    public int calculateOrderTotal(List<String> itemNames) throws ItemNotFoundException {
        int total = 0;
        for (String itemName : itemNames) {
            Item item = findItemByName(itemName);
            if (item == null) {
                throw new ItemNotFoundException(itemName);
            }
            total += item.getPrice();
        }
        return total;
    }

}
