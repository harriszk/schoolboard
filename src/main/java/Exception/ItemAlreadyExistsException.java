package Exception;

public class ItemAlreadyExistsException extends Exception {
    public ItemAlreadyExistsException(String itemName) {
        super(itemName + " already exists");
    }
}
