import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        // create a mock object of the Restaurant class
        Restaurant mockRestaurant = Mockito.spy(restaurant);

        // Set the current time to be 12:00 PM
        LocalTime fixedTime = LocalTime.parse("12:00:00");
        Mockito.doReturn(fixedTime).when(mockRestaurant).getCurrentTime();

        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        // create a mock object of the Restaurant class
        Restaurant mockRestaurant = Mockito.spy(restaurant);

        // mock the current time to 23:00:40
        LocalTime fixedTime = LocalTime.parse("23:00:40");
        Mockito.doReturn(fixedTime).when(mockRestaurant).getCurrentTime();

        assertFalse(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);

        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws ItemNotFoundException {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");

        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(ItemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void calculate_order_total_with_valid_item_names_should_return_correct_total() throws ItemNotFoundException {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sizzling brownie", 319);

        List<String> itemNames = new ArrayList<>();
        itemNames.add("Sweet corn soup");
        itemNames.add("Sizzling brownie");

        int total = restaurant.calculateOrderTotal();
        assertEquals(438, total);
    }

    @Test
    public void calculate_order_total_with_invalid_item_name_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        List<String> itemNames = new ArrayList<>();
        itemNames.add("Sweet corn soup");
        itemNames.add("French fries");

        assertThrows(ItemNotFoundException.class, () -> restaurant.calculateOrderTotal());
    }

    @Test
    public void calculate_order_total_with_empty_list_should_return_zero() throws ItemNotFoundException {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        List<String> itemNames = new ArrayList<>();
        int total = restaurant.calculateOrderTotal();
        assertEquals(0, total);
    }

}