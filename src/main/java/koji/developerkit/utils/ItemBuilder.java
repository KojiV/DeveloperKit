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
        NBTItem nbti = new NBTItem(im);
        nbti.setInteger(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setBoolean(String key, boolean value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setBoolean(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setDouble(String key, double value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setDouble(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setLong(String key, long value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setLong(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setByte(String key, byte value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setByte(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setShort(String key, short value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setShort(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setFloat(String key, float value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setFloat(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setByteArray(String key, byte[] value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setByteArray(key, value);
        im = nbti.getItem();
        return this;
    }

    public ItemBuilder setIntArray(String key, int[] value) {
        NBTItem nbti = new NBTItem(im);
        nbti.setIntArray(key, value);
        im = nbti.getItem();
        return this;
    }

    public String getString(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getString(key);
    }

    public int getInt(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getInteger(key);
    }

    public boolean getBoolean(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getBoolean(key);
    }

    public double getDouble(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getDouble(key);
    }

    public long getLong(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getLong(key);
    }

    public byte getByte(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getByte(key);
    }

    public short getShort(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getShort(key);
    }

    public float getFloat(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getFloat(key);
    }

    public byte[] getByteArray(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getByteArray(key);
    }

    public int[] getIntArray(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.getIntArray(key);
    }

    public boolean hasKey(String key) {
        NBTItem nbti = new NBTItem(im);
        return nbti.hasKey(key);
    }

    public ItemBuilder HideFlags(int flags) {
        NBTItem nbti = new NBTItem(im);
        nbti.HideFlags(flags);
        im = nbti.getItem();
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
