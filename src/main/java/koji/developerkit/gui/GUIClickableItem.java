package koji.developerkit.gui;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import koji.developerkit.KBase;
import koji.developerkit.utils.ItemBuilder;
import koji.developerkit.utils.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public abstract class GUIClickableItem extends KBase implements GUIItem {
    static {
        KBase.getPlugin().getLogger().log(Level.INFO,
                "GUI items referenced for the first time, registering listener..."
        );
        GUIListener.startup();
    }

    private static final HashMap<String, GUIClickableItem> itemsToRun = new HashMap<>();

    public static HashMap<String, GUIClickableItem> getItemsToRun() {
        return itemsToRun;
    }

    public GUIClickableItem() {
        uuid = UUID.randomUUID().toString();
        itemsToRun.put(uuid, this);
    }

    private final String uuid;

    public String getUUID() {
        return uuid;
    }

    public static boolean isGUIClickable(ItemStack item) {
        return new NBTItem(item).hasKey("ClickItem") &&
                itemsToRun.containsKey(new NBTItem(item).getString("ClickItem"));
    }

    public static GUIClickableItem parse(ItemStack item) {
        return isGUIClickable(item) ? itemsToRun.get(new NBTItem(item).getString("ClickItem")) : null;
    }

    /**
     * Get the item fully made
     *
     * @return The item with all of its things set
     */
    public ItemStack getFinishedItem() {
        NBTItem item = new NBTItem(getItem());
        item.setString("ClickItem", uuid);
        return item.getItem();
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
                Player p = (Player) e.getWhoClicked();
                if (slot < p.getOpenInventory().getTopInventory().getSize() &&
                        p.getOpenInventory().getTopInventory().equals(e.getView().getTopInventory())
                ) {
                    e.getWhoClicked().closeInventory();
                }
            }

            @Override
            public int getSlot() {
                return slot;
            }

            @Override
            public ItemStack getItem() {
                return new ItemBuilder(XMaterial.BARRIER)
                        .setName(ChatColor.RED + "Close")
                        .build();
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
                return new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build();
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
                playSound((Player) e.getWhoClicked(), XSound.UI_BUTTON_CLICK, 1);
                e.getWhoClicked().openInventory(back);
            }

            @Override
            public int getSlot() {
                return slot;
            }

            @Override
            public ItemStack getItem() {
                return new ItemBuilder(XMaterial.ARROW)
                        .setName(ChatColor.GREEN + "Go Back")
                        .setLore(ChatColor.GRAY + "To " + pageName)
                        .build();
            }
        };
    }
}
