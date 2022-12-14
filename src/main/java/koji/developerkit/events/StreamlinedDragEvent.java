package koji.developerkit.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class StreamlinedDragEvent extends InventoryDragEvent {
    public StreamlinedDragEvent(InventoryDragEvent e) {
        super(
                e.getView(),
                e.getCursor(),
                e.getOldCursor(),
                e.getType() == DragType.SINGLE,
                e.getNewItems()
        );
    }
    public ItemStack getItem() {
        if(getRawSlots().stream().findAny().isPresent()) {
            return getNewItems().get(
                    getRawSlots().stream().findAny().get()
            );
        }
        return null;
    }
    public StreamlinedDragEvent smartSetContents(Inventory inv) {
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
