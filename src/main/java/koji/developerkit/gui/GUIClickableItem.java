package koji.developerkit.gui;

import com.cryptomorin.xseries.XMaterial;
import koji.developerkit.KBase;
import koji.developerkit.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public abstract class GUIClickableItem extends KBase implements GUIItem {
    public static final HashMap<String, GUIClickableItem> itemsToRun = new HashMap<>();

    public GUIClickableItem() {
        uuid = UUID.randomUUID().toString();
        itemsToRun.put(uuid, this);
    }

    private final String uuid;

    /**
     * Get the item fully made
     *
     * @return The item with all of its things set
     */
    public ItemStack getFinishedItem() {
        return new ItemBuilder(getItem()).setString("ClickItem", uuid).build();
    }

    public abstract void run(InventoryClickEvent e);

    /**
     * Get the barrier item most GUIs will use to close
     *
     * @param slot The slot it will be placed at
     * @return The barrier item
     */
    public static GUIClickableItem getCloseItem(int slot) {
        return new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                e.getWhoClicked().closeInventory();
            }

            @Override
            public int getSlot() {
                return slot;
            }

            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(XMaterial.BARRIER.parseMaterial());
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Close");
                item.setItemMeta(meta);
                return item;
            }
        };
    }

    /**
     * Sets an item to itself, but it can't be picked up in GUI
     *
     * @param is   The item
     * @param slot The slot it will be placed at
     * @return The finished item
     */
    public static GUIClickableItem cantPickup(ItemStack is, int slot) {
        return new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                e.setCancelled(true);
            }

            @Override
            public int getSlot() {
                return slot;
            }

            @Override
            public ItemStack getItem() {
                return is;
            }
        };
    }

    /**
     * The itemsToRun seen around the borders of GUIs
     *
     * @param slot The slot the item will be placed at
     * @return The finished item
     */
    public static GUIClickableItem getBorderItem(int slot) {
        return new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                e.setCancelled(true);
            }

            @Override
            public int getSlot() {
                return slot;
            }

            @Override
            public ItemStack getItem() {
                ItemStack item = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(" ");
                item.setItemMeta(meta);
                return item;
            }
        };
    }

    /**
     * The arrow that symbolises that it will move back a menu
     *
     * @param slot     The slot it will be placed at
     * @param back     The inventory it will move back to
     * @param pageName The name of the inventory it would go back to
     * @return The finished item
     */
    public static GUIClickableItem goBackItem(int slot, Inventory back, String pageName) {
        return new GUIClickableItem() {
            @Override
            public void run(InventoryClickEvent e) {
                e.getWhoClicked().openInventory(back);
            }

            @Override
            public int getSlot() {
                return slot;
            }

            @Override
            public ItemStack getItem() {
                ItemStack item = XMaterial.ARROW.parseItem();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Go Back");
                meta.setLore(arrayList(ChatColor.GRAY + "To " + pageName));
                item.setItemMeta(meta);
                return item;
            }
        };
    }
}
