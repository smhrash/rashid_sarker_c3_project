public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String itemName) {
        super(itemName);
    }
}
