package io.github.mazegame.items;

import java.util.Map;
import java.util.HashMap;

/** Stores every item with their ID to make it easier to access items. */
public final class Items {
    public static Map<ItemID, Item> items =  new HashMap<ItemID, Item>() {{
        put(ItemID.RED_TEST_BOX,new RedTestBoxItem());
        put(ItemID.TEST_BOX,new TestBoxItem());
    }};

    public static Item get(ItemID ID) {
        return items.get(ID);
    }
}
