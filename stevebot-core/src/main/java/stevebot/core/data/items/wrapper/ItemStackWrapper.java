package stevebot.core.data.items.wrapper;

public class ItemStackWrapper {

    private final int slot;
    private final int size;
    private final ItemWrapper item;

    public ItemStackWrapper(final ItemWrapper item, final int size, final int slot) {
        this.item = item;
        this.size = size;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public int getSize() {
        return size;
    }

    public ItemWrapper getItem() {
        return item;
    }

}
