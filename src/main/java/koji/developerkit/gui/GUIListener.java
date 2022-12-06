package koji.developerkit.gui;

import com.cryptomorin.xseries.XMaterial;
import koji.developerkit.listener.KListener;
import koji.developerkit.utils.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class GUIListener extends KListener {

    /**
     * Registers the listener so it actually works
     */
    public static void startup() {
        Bukkit.getPluginManager().registerEvents(
                new GUIListener(), getPlugin()
        );
    }

    /**
     * Checks if there is a GUIClickable item to grab, if yes run its run function
     *
     * @param e The event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getSlot() < e.getInventory().getSize()) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != XMaterial.AIR.parseMaterial()) {
                NBTItem item = new NBTItem(e.getCurrentItem());
                if (item.hasKey("ClickItem")) {
                    GUIClickableItem guiItem =
                            GUIClickableItem.getItemsToRun().get(
                                    item.getString("ClickItem")
                            );
                    if (guiItem == null) return;
                    guiItem.run(e);
                    e.setCancelled(e.isCancelled() || !guiItem.canPickup());
                }
            }
        }
    }

    private final HashMap<Inventory, ArrayList<GUIClickableItem>> clickItems = new HashMap<>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        ArrayList<GUIClickableItem> clickables = new ArrayList<>();
        for(ItemStack item : e.getInventory().getContents()) {
            if(item != null) {
                NBTItem nbtItem = new NBTItem(item);
                if(nbtItem.hasKey("ClickItem")) {
                    clickables.add(
                            GUIClickableItem.getItemsToRun().get(
                                    nbtItem.getString("ClickItem")
                            )
                    );
                }
            }
        }
        clickItems.put(e.getInventory(), clickables);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(clickItems.containsKey(e.getInventory())) {
            clickItems.get(e.getInventory()).forEach(gui ->
                    GUIClickableItem.getItemsToRun().remove(gui.getUUID())
            );
        }
    }
}
