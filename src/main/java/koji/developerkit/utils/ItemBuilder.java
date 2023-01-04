package koji.developerkit.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class ItemBuilder extends KBase {
    protected ItemStack im;

    public ItemBuilder(ItemStack item, short data) {
        im = new ItemStack(item.getType(), 1, data);
    }

    public ItemBuilder(ItemStack item, short data, int amount) {
        im = new ItemStack(item.getType(), amount, data);
    }

    public ItemBuilder(XMaterial material) {
        im = new ItemStack(material.parseMaterial(), 1, material.getData());
    }

    public ItemBuilder(XMaterial material, int amount) {
        im = new ItemStack(material.parseMaterial(), amount, material.getData());
    }

    public ItemBuilder(ItemStack item) {
        im = item;
    }

    public ItemStack build() {
        return im;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.im.getItemMeta();
        meta.setLore(coloredList(lore));
        im.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.im.getItemMeta();
        meta.setDisplayName(color(name));
        im.setItemMeta(meta);
        return this;
    }

    public String getName() {
        if(im.getItemMeta() != null && im.getItemMeta().hasDisplayName()) {
            return im.getItemMeta().getDisplayName();
        }
        return getFriendlyName(im);
    }

    private static Class<?> localeClass = null;
    private static Class<?> craftItemStackClass = null, nmsItemStackClass = null, nmsItemClass = null;
    private static final String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PREFIX = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "net.minecraft.server"
    );

    private static String getFriendlyName(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return "Air";

        try {
            if (craftItemStackClass == null)
                craftItemStackClass = Class.forName(OBC_PREFIX + ".inventory.CraftItemStack");
            Method nmsCopyMethod = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);

            if (nmsItemStackClass == null) nmsItemStackClass = Class.forName(NMS_PREFIX + ".ItemStack");
            Object nmsItemStack = nmsCopyMethod.invoke(null, itemStack);

            Object itemName;
            Method getItemMethod = nmsItemStackClass.getMethod("getItem");
            Object nmsItem = getItemMethod.invoke(nmsItemStack);

            if (nmsItemClass == null) nmsItemClass = Class.forName(NMS_PREFIX + ".Item");

            Method getNameMethod = nmsItemClass.getMethod("getName");
            String localItemName = (String) getNameMethod.invoke(nmsItem);

            if (localeClass == null) localeClass = Class.forName(NMS_PREFIX + ".LocaleI18n");
            Method getLocaleMethod = localeClass.getMethod("get", String.class);

            Object localeString = localItemName == null ? "" : getLocaleMethod.invoke(null, localItemName);
            itemName = ("" + getLocaleMethod.invoke(null, localeString.toString() + ".name")).trim();

            return itemName.toString();
        } catch (Exception ignored) {}
        return capitalize(itemStack.getType().name().replace("_", " ").toLowerCase());
    }

    public ItemBuilder setString(String string, String value) {
        return setString(string, value, false);
    }

    public ItemBuilder setString(String string, String value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setString(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setInt(String key, int value) {
        return setInt(key, value, false);
    }

    public ItemBuilder setInt(String string, int value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setInteger(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setBoolean(String key, boolean value) {
        return setBoolean(key, value, false);
    }

    public ItemBuilder setBoolean(String string, boolean value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setBoolean(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setDouble(String key, double value) {
        return setDouble(key, value, false);
    }

    public ItemBuilder setDouble(String string, double value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setDouble(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setLong(String key, long value) {
        return setLong(key, value, false);
    }

    public ItemBuilder setLong(String string, long value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setLong(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setByte(String key, byte value) {
        return setByte(key, value, false);
    }

    public ItemBuilder setByte(String string, byte value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setByte(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setShort(String key, short value) {
        return setShort(key, value, false);
    }

    public ItemBuilder setShort(String string, short value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setShort(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setFloat(String key, float value) {
        return setFloat(key, value, false);
    }

    public ItemBuilder setFloat(String string, float value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setFloat(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setByteArray(String key, byte[] value) {
        return setByteArray(key, value, false);
    }

    public ItemBuilder setByteArray(String string, byte[] value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setByteArray(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setIntArray(String key, int[] value) {
        return setIntArray(key, value, false);
    }

    public ItemBuilder setIntArray(String string, int[] value, boolean ignoreCompound) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setIntArray(string, value, ignoreCompound);
        im = nbtItem.getItem();
        return this;
    }

    public String getString(String key) {
        return getString(key, false);
    }

    public String getString(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getString(key, ignoreCompound);
    }

    public String getStringOrDefault(String key, String def) {
        return getStringOrDefault(key, def, false);
    }

    public String getStringOrDefault(String key, String def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getStringOrDefault(key, def, ignoreCompound);
    }

    public int getInt(String key) {
        return getInt(key, false);
    }

    public int getInt(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getInteger(key, ignoreCompound);
    }

    public int getIntOrDefault(String key, int def) {
        return getIntOrDefault(key, def, false);
    }

    public int getIntOrDefault(String key, int def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getIntegerOrDefault(key, def, ignoreCompound);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getBoolean(key, ignoreCompound);
    }

    public boolean getBooleanOrDefault(String key, boolean def) {
        return getBooleanOrDefault(key, def, false);
    }

    public boolean getBooleanOrDefault(String key, boolean def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getBooleanOrDefault(key, def, ignoreCompound);
    }

    public double getDouble(String key) {
        return getDouble(key, false);
    }

    public double getDouble(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getDouble(key, ignoreCompound);
    }

    public double getDoubleOrDefault(String key, double def) {
        return getDoubleOrDefault(key, def, false);
    }

    public double getDoubleOrDefault(String key, double def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getDoubleOrDefault(key, def, ignoreCompound);
    }

    public long getLong(String key) {
        return getLong(key, false);
    }

    public long getLong(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getLong(key, ignoreCompound);
    }

    public long getLongOrDefault(String key, long def) {
        return getLongOrDefault(key, def, false);
    }

    public long getLongOrDefault(String key, long def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getLongOrDefault(key, def, ignoreCompound);
    }

    public byte getByte(String key) {
        return getByte(key, false);
    }

    public byte getByte(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByte(key, ignoreCompound);
    }

    public byte getByteOrDefault(String key, byte def) {
        return getByteOrDefault(key, def, false);
    }

    public byte getByteOrDefault(String key, byte def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByteOrDefault(key, def, ignoreCompound);
    }

    public short getShort(String key) {
        return getShort(key, false);
    }

    public short getShort(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getShort(key, ignoreCompound);
    }

    public short getShortOrDefault(String key, short def) {
        return getShortOrDefault (key, def, false);
    }

    public short getShortOrDefault(String key, short def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getShortOrDefault(key, def, ignoreCompound);
    }

    public float getFloat(String key) {
        return getFloat(key, false);
    }

    public float getFloat(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getFloat(key, ignoreCompound);
    }

    public float getFloatOrDefault(String key, float def) {
        return getFloatOrDefault(key, def, false);
    }

    public float getFloatOrDefault(String key, float def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getFloatOrDefault(key, def, ignoreCompound);
    }

    public byte[] getByteArray(String key) {
        return getByteArray(key, false);
    }

    public byte[] getByteArray(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByteArray(key, ignoreCompound);
    }

    public byte[] getByteArrayOrDefault(String key, byte[] def) {
        return getByteArrayOrDefault(key, def, false);
    }

    public byte[] getByteArrayOrDefault(String key, byte[] def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByteArrayOrDefault(key, def, ignoreCompound);
    }

    public int[] getIntArray(String key) {
        return getIntArray(key, false);
    }

    public int[] getIntArray(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getIntArray(key, ignoreCompound);
    }

    public int[] getIntArrayOrDefault(String key, int[] def) {
        return getIntArrayOrDefault(key, def, false);
    }

    public int[] getIntArrayOrDefault(String key, int[] def, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getIntArrayOrDefault(key, def, ignoreCompound);
    }

    public boolean hasKey(String key) {
        return hasKey(key, false);
    }

    public boolean hasKey(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        return nbt.hasKey(key, ignoreCompound);
    }

    public ItemBuilder HideFlags(int flags) {
        NBTItem nbt = new NBTItem(im);
        nbt.HideFlags(flags);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setUnbreakable(boolean bol) {
        NBTItem nbt = new NBTItem(im);
        nbt.setUnbreakable(bol);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder removeKey(String key) {
        return removeKey(key, false);
    }

    public ItemBuilder removeKey(String key, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        nbt.removeKey(key, ignoreCompound);
        im = nbt.getItem();
        return this;
    }

    public NBTCompound getCompound(String compound) {
        NBTItem nbt = new NBTItem(im, arrayList(compound));
        return nbt.getCompound("ExtraAttributes").getOrCreateCompound(compound);
    }

    public String getTexture() {
        if (im.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return "";

        return ((SkullMeta) im.getItemMeta()).getOwner();
    }

    public ItemBuilder setTexture(String texture) {
        if(im.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return this;

        de.tr7zw.changeme.nbtapi.NBTItem item = new de.tr7zw.changeme.nbtapi.NBTItem(im);
        NBTListCompound compound = item
                .addCompound("SkullOwner")
                .addCompound("Properties")
                .getCompoundList("textures")
                .addCompound();
        compound.setString("Value", texture);
        im = item.getItem();
        /*SkullMeta hm = (SkullMeta) im.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field field = hm.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(hm, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        im.setItemMeta(hm);*/
        return this;
    }

    public Color getColor() {
        if (!im.getType().toString().startsWith("LEATHER_")) return null;
        
        LeatherArmorMeta lAM = (LeatherArmorMeta) im.getItemMeta();
        return lAM.getColor();
    }

    public ItemBuilder setColor(Color c) {
        if(!im.getType().toString().startsWith("LEATHER_")) return this;

        LeatherArmorMeta is = (LeatherArmorMeta) im.getItemMeta();
        is.setColor(c);
        im.setItemMeta(is);
        return this;
    }

    public String getStringFromCompound() {
        NBTItem nbt = new NBTItem(im);
        return nbt.getCompoundToString();
    }

    public ItemBuilder applyCompoundFromString(String compoundAsString) {
        return applyCompoundFromString(compoundAsString, false);
    }

    public ItemBuilder applyCompoundFromString(String compoundAsString, boolean ignoreCompound) {
        NBTItem nbt = new NBTItem(im);
        nbt.applyFromString(compoundAsString, ignoreCompound);
        im = nbt.getItem();
        return this;
    }
}
