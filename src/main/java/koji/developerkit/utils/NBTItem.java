package koji.developerkit.utils;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends de.tr7zw.changeme.nbtapi.NBTItem {

    public NBTItem(ItemStack item) {
        super(item);
        getOrCreateCompound("ExtraAttributes");
    }

    public NBTItem(ItemStack item, boolean directApply) {
        super(item, directApply);
        getOrCreateCompound("ExtraAttributes");
    }

    public void setString(String key, String value) {
        getCompound("ExtraAttributes").setString(key, value);
    }

    public void setInteger(String key, int value) {
        getCompound("ExtraAttributes").setInteger(key, value);
    }

    public void setBoolean(String key, boolean value) {
        getCompound("ExtraAttributes").setBoolean(key, value);
    }

    public void setDouble(String key, double value) {
        getCompound("ExtraAttributes").setDouble(key, value);
    }

    public void setLong(String key, long value) {
        getCompound("ExtraAttributes").setLong(key, value);
    }

    public void setByte(String key, byte value) {
        getCompound("ExtraAttributes").setByte(key, value);
    }

    public void setShort(String key, short value) {
        getCompound("ExtraAttributes").setShort(key, value);
    }

    public void setFloat(String key, float value) {
        getCompound("ExtraAttributes").setFloat(key, value);
    }

    public void setByteArray(String key, byte[] value) {
        getCompound("ExtraAttributes").setByteArray(key, value);
    }

    public void setIntArray(String key, int[] value) {
        getCompound("ExtraAttributes").setIntArray(key, value);
    }

    public String getString(String key) {
        return getCompound("ExtraAttributes").getString(key);
    }

    public Integer getInteger(String key) {
        return getCompound("ExtraAttributes").getInteger(key);
    }

    public Boolean getBoolean(String key) {
        return getCompound("ExtraAttributes").getBoolean(key);
    }

    public Double getDouble(String key) {
        return getCompound("ExtraAttributes").getDouble(key);
    }

    public Long getLong(String key) {
        return getCompound("ExtraAttributes").getLong(key);
    }

    public Byte getByte(String key) {
        return getCompound("ExtraAttributes").getByte(key);
    }

    public Short getShort(String key) {
        return getCompound("ExtraAttributes").getShort(key);
    }

    public Float getFloat(String key) {
        return getCompound("ExtraAttributes").getFloat(key);
    }

    public byte[] getByteArray(String key) {
        return getCompound("ExtraAttributes").getByteArray(key);
    }

    public int[] getIntArray(String key) {
        return getCompound("ExtraAttributes").getIntArray(key);
    }

    public Boolean hasKey(String key) {
        return getCompound("ExtraAttributes").hasKey(key);
    }

    public void setUnbreakable(boolean boo) {
        super.setBoolean("Unbreakable", boo);
    }

    public void HideFlags(int flags) {
        super.setInteger("HideFlags", flags);
    }
}
