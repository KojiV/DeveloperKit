package koji.developerkit.gui;

import com.cryptomorin.xseries.XMaterial;
import koji.developerkit.listener.KListener;
import koji.developerkit.utils.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

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
                try { NBTItem item = new NBTItem(e.getCurrentItem());
                    if (item.hasKey("ClickItem") && item.getString("ClickItem") != null) {
                        GUIClickableItem guiItem =
                                GUIClickableItem.getItemsToRun().get(
                                        item.getString("ClickItem")
                                );
                        if (guiItem == null) return;
                        try {
                            guiItem.run(e);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        e.setCancelled(e.isCancelled() || !guiItem.canPickup());
                    }
                } catch (NullPointerException ignored) {}
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getRawSlots().stream().findAny().isPresent()) {
            int slot = e.getRawSlots().stream().findAny().get();
            onInventoryClick(new InventoryClickEvent(
                    e.getView(), e.getView().getSlotType(slot), slot, ClickType.UNKNOWN, InventoryAction.UNKNOWN
            ));
        }
    }
}
