package koji.developerkit.inventory;

import koji.developerkit.KBase;
import koji.developerkit.gui.GUIClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public abstract class KInventory extends KBase {
    private final int size;
    private final Inventory inv;
    private final String title;

    private final HashMap<Player, PlayerInstance> playerInstances = new HashMap<>();

    public KInventory(String title, int size) {
        this.size = size;
        this.title = title;

        inv = Bukkit.createInventory(null, size, title);
    }

    public String getTitle() {
        return title;
    }

    public Inventory getBaseCreatedInventory() {
        return inv;
    }

    public int getSize() {
        return size;
    }

    public HashMap<Player, PlayerInstance> getPlayerInstances() {
        return playerInstances;
    }

    public PlayerInstance getPlayerInstance(Player p) {
        return playerInstances.get(p);
    }

    public abstract Inventory getConstantInventory();

    public abstract List<Integer> getIgnoreSlots();

    public static class PlayerInstance extends KBase {
        private final Player player;
        private final KInventory base;
        private final Inventory inventory;
        private String title;

        public PlayerInstance(Player player, KInventory base) {
            this.player = player;
            this.base = base;

            Inventory inv = Bukkit.createInventory(null, base.getSize(), base.getTitle());
            inv.setContents(base.getConstantInventory().getContents());

            this.inventory = inv;
            this.title = base.getTitle();

            base.getPlayerInstances().put(player, this);
        }

        public Player getPlayer() {
            return player;
        }

        public KInventory getBaseKInventory() {
            return base;
        }

        public Inventory getInventory() {
            return inventory;
        }

        public String getTitle() {
            return title;
        }

        public PlayerInstance setTitle(String title) {
            if(player.getOpenInventory().getTopInventory().equals(inventory)) {
                InventoryUpdate.updateInventory(player, title);
                this.title = title;
            }
            return this;
        }

        public PlayerInstance reset(String title) {
            setTitle(title);
            for(int i = 0; i < inventory.getSize(); i++) {
                ItemStack oldItem = inventory.getItem(i);
                ItemStack newItem = base.getConstantInventory().getItem(i);

                if(base.getIgnoreSlots().contains(i)) continue;

                if(oldItem != null && newItem != null) {
                    if(!isEqualOrNotApplicable(oldItem, newItem)) {
                        inventory.setItem(i, newItem);
                    }
                } else inventory.setItem(i, newItem);
            }
            return this;
        }

        public PlayerInstance set(int slot, ItemStack item) {
            inventory.setItem(slot, item);
            return this;
        }

        public PlayerInstance set(GUIClickableItem item) {
            set(inventory, item);
            return this;
        }
    }

    private static boolean isEqualOrNotApplicable(ItemStack item1, ItemStack item2) {
        GUIClickableItem gui1 = GUIClickableItem.parse(item1);
        GUIClickableItem gui2 = GUIClickableItem.parse(item2);
        if(gui1 == null || gui2 == null)
            return item1.equals(item2);
        return gui1.equals(gui2);
    }
}
