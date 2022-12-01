package koji.developerkit.utils;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import koji.developerkit.KBase;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;

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
        setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.im.getItemMeta();
        meta.setLore(lore);
        im.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.im.getItemMeta();
        meta.setDisplayName(name);
        im.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return im.getItemMeta().getDisplayName();
    }

    public ItemBuilder setString(String string, String value) {
        NBTItem nbtItem = new NBTItem(im);
        nbtItem.setString(string, value);
        im = nbtItem.getItem();
        return this;
    }

    public ItemBuilder setInt(String key, int value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setInteger(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setBoolean(String key, boolean value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setBoolean(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setDouble(String key, double value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setDouble(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setLong(String key, long value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setLong(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setByte(String key, byte value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setByte(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setShort(String key, short value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setShort(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setFloat(String key, float value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setFloat(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setByteArray(String key, byte[] value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setByteArray(key, value);
        im = nbt.getItem();
        return this;
    }

    public ItemBuilder setIntArray(String key, int[] value) {
        NBTItem nbt = new NBTItem(im);
        nbt.setIntArray(key, value);
        im = nbt.getItem();
        return this;
    }

    public String getString(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getString(key);
    }

    public String getStringOrDefault(String key, String def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getStringOrDefault(key, def);
    }

    public int getInt(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getInteger(key);
    }

    public int getIntOrDefault(String key, int def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getIntegerOrDefault(key, def);
    }

    public boolean getBoolean(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getBoolean(key);
    }

    public boolean getBooleanOrDefault(String key, boolean def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getBooleanOrDefault(key, def);
    }

    public double getDouble(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getDouble(key);
    }

    public double getDoubleOrDefault(String key, double def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getDoubleOrDefault(key, def);
    }

    public long getLong(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getLong(key);
    }

    public long getLongOrDefault(String key, long def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getLongOrDefault(key, def);
    }

    public byte getByte(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByte(key);
    }

    public byte getByteOrDefault(String key, byte def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByteOrDefault(key, def);
    }

    public short getShort(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getShort(key);
    }

    public short getShortOrDefault(String key, short def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getShortOrDefault(key, def);
    }

    public float getFloat(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getFloat(key);
    }

    public float getFloatOrDefault(String key, float def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getFloatOrDefault(key, def);
    }

    public byte[] getByteArray(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByteArray(key);
    }

    public byte[] getByteArrayOrDefault(String key, byte[] def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getByteArrayOrDefault(key, def);
    }

    public int[] getIntArray(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getIntArray(key);
    }

    public int[] getIntArrayOrDefault(String key, int[] def) {
        NBTItem nbt = new NBTItem(im);
        return nbt.getIntArrayOrDefault(key, def);
    }

    public boolean hasKey(String key) {
        NBTItem nbt = new NBTItem(im);
        return nbt.hasKey(key);
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

    public ItemBuilder setTexture(String texture) {
        if(im.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return this;

        SkullMeta hm = (SkullMeta) im.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field field = hm.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(hm, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        im.setItemMeta(hm);
        return this;
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
        NBTItem nbt = new NBTItem(im);
        nbt.applyFromString(compoundAsString);
        im = nbt.getItem();
        return this;
    }
}
