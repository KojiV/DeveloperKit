package koji.developerkit.inventory;

import koji.developerkit.KBase;
import koji.developerkit.gui.GUIClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class KInventory extends KBase {
    private final int size;
    private final Inventory inv;
    private final String title;

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

    public abstract Inventory getConstantInventory();

    public static class PlayerInstance extends KBase {
        private final Player player;
        private final KInventory base;
        private final Inventory inventory;
        private String title;

        public PlayerInstance(Player player, KInventory base) {
            this.player = player;
            this.base = base;
            this.inventory = base.getConstantInventory();
            this.title = base.getTitle();
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
            ItemStack[] currentContents = inventory.getContents();
            ItemStack[] newContents = base.getConstantInventory().getContents();

            for (int i = 0; currentContents.length > i; i++) {
                ItemStack oldStack = currentContents[i];
                ItemStack newStack = newContents[i];

                if (oldStack == null && newStack != null) {
                    currentContents[i] = newStack;
                } else if (oldStack != null && newStack != null) {
                    if (!oldStack.isSimilar(newStack)) {
                        currentContents[i] = newStack;
                    }
                } else {
                    currentContents[i] = null;
                }
            }
            inventory.setContents(currentContents);
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
}
