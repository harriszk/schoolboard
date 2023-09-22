package Exception;

public class ItemDoesNotExistException extends Exception {
    public ItemDoesNotExistException(String itemName) {
        super(itemName + " does not exist");
    }
}