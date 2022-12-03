package koji.developerkit.gui;

import com.cryptomorin.xseries.XMaterial;
import koji.developerkit.listener.KListener;
import koji.developerkit.utils.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;

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
        println(e.getSlot(), e.getInventory().getSize(), e.getClickedInventory().getSize());
        if(e.getSlot() < e.getInventory().getSize()) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != XMaterial.AIR.parseMaterial()) {
                NBTItem item = new NBTItem(e.getCurrentItem());
                if (item.hasKey("ClickItem")) {
                    GUIClickableItem guiItem =
                            GUIClickableItem.itemsToRun.get(
                                    item.getString("ClickItem")
                            );
                    if (guiItem == null) return;
                    guiItem.run(e);
                    e.setCancelled(e.isCancelled() || !guiItem.canPickup());
                }
            }
        }
    }
}
