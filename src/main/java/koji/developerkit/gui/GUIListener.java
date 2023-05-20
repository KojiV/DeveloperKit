package koji.developerkit.gui;

import koji.developerkit.listener.KListener;
import koji.developerkit.utils.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GUIListener extends KListener {

    public GUIListener(){
        timeSinceLastClick = new HashMap<>();
    }

    /**
     * Registers the listener so it actually works
     */
    public static void startup() {
        Bukkit.getPluginManager().registerEvents(
                new GUIListener(), getPlugin()
        );
    }

    private final HashMap<Inventory, HashMap<Integer, Long>> timeSinceLastClick;
    private final static long COOLDOWN = 100;

    /**
     * Checks if there is a GUIClickable item to grab, if yes run its run function
     *
     * @param e The event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getSlot() < e.getInventory().getSize()) {
            if (isValidItem(e.getCurrentItem())) {
                Inventory inv = e.getClickedInventory();
                if(inv != null && !timeSinceLastClick.containsKey(inv))
                    timeSinceLastClick.put(inv, new HashMap<>());
                try { NBTItem item = new NBTItem(e.getCurrentItem());
                    if (item.hasKey("ClickItem") && item.getString("ClickItem") != null) {
                        GUIClickableItem guiItem = GUIClickableItem.getItemsToRun().get(
                                item.getString("ClickItem")
                        );
                        if(guiItem != null) {
                            if(!timeSinceLastClick.get(inv).containsKey(e.getRawSlot()) ||
                                    timeSinceLastClick.get(inv).get(e.getRawSlot()) +
                                            COOLDOWN < System.currentTimeMillis()
                            ) {
                                try {
                                    guiItem.run(e);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                timeSinceLastClick.get(inv).put(e.getRawSlot(), System.currentTimeMillis());
                            }
                            e.setCancelled(e.isCancelled() || !guiItem.canPickup());
                        }
                    }
                } catch (NullPointerException ignored) {}
            }
        }
    }
}
