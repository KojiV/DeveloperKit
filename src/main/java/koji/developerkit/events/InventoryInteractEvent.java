package koji.developerkit.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryInteractEvent extends org.bukkit.event.inventory.InventoryInteractEvent {
    public InventoryInteractEvent(org.bukkit.event.inventory.InventoryInteractEvent e) {
        super(e.getView());
    }

    public Player getPlayer() {
        return (Player) super.getWhoClicked();
    }

    public InventoryInteractEvent smartSetContents(Inventory inv) {
        for(int i = 0; i < inv.getSize(); i++) {
            ItemStack oldItem = inv.getItem(i);
            ItemStack newItem = getInventory().getItem(i);

            if(oldItem != null && newItem != null) {
                if(!oldItem.equals(newItem)) {
                    inv.setItem(i, newItem);
                }
            } else inv.setItem(i, newItem);
        }
        return this;
    }
}
