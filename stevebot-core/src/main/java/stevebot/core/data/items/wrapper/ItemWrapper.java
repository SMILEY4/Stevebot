package stevebot.core.data.items.wrapper;

public class ItemWrapper {


    private final int id;
    private final String name;


    /**
     * @param id   the id of the item
     * @param name the name of the item
     */
    public ItemWrapper(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * @return the id of the item
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name of the item ("minecraft:item_name")
     */
    public String getName() {
        return name;
    }


    public boolean isBlock() {
        return false; // TODO: item instanceof ItemBlock;
    }


    public boolean isTool() {
        return false; // TODO item instanceof ItemTool;
    }

}
