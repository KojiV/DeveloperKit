package koji.developerkit.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvent extends org.bukkit.event.inventory.InventoryClickEvent {
    public InventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent e) {
        super(e.getView(), e.getSlotType(), e.getSlot(), e.getClick(), e.getAction(), e.getHotbarButton());
    }

    public Player getPlayer() {
        return (Player) super.getWhoClicked();
    }

    public InventoryClickEvent smartSetContents(Inventory inv) {
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

    public InventoryClickEvent smartSetContents(ItemStack[] items) {
        for(int i = 0; i < getInventory().getSize(); i++) {
            ItemStack oldItem = getInventory().getItem(i);
            ItemStack newItem = items[i];

            if(oldItem != null && newItem != null) {
                if(!oldItem.equals(newItem)) {
                    getInventory().setItem(i, newItem);
                }
            } else getInventory().setItem(i, newItem);
        }
        return this;
    }
}
