package koji.developerkit.events;

import koji.developerkit.listener.KListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class EventListener extends KListener {
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        koji.developerkit.events.InventoryClickEvent event =
                new koji.developerkit.events.InventoryClickEvent(e);
        Bukkit.getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
        e.setCurrentItem(event.getCurrentItem());
        e.setCursor(event.getCursor());
        event.smartSetContents(e.getInventory());
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e) {
        koji.developerkit.events.InventoryInteractEvent event =
                new koji.developerkit.events.InventoryInteractEvent(e);
        Bukkit.getPluginManager().callEvent(event);
        e.setResult(event.getResult());
        event.smartSetContents(e.getInventory());
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        StreamlinedDragEvent event = new StreamlinedDragEvent(e);
        Bukkit.getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
        e.setCursor(event.getCursor());
        e.setResult(event.getResult());
        e.getNewItems().clear();
        e.getNewItems().putAll(event.getNewItems());
        event.smartSetContents(e.getInventory());
    }


}
