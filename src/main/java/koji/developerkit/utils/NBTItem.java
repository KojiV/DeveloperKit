package koji.developerkit.utils;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
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
        setString(key, value, false);
    }

    public void setString(String key, String value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setString(key, value);
    }

    public void setInteger(String key, int value) {
        setInteger(key, value, false);
    }

    public void setInteger(String key, int value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setInteger(key, value);
    }

    public void setBoolean(String key, boolean value) {
        setBoolean(key, value, false);
    }

    public void setBoolean(String key, boolean value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setBoolean(key, value);
    }

    public void setDouble(String key, double value) {
        setDouble(key, value, false);
    }

    public void setDouble(String key, double value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setDouble(key, value);
    }

    public void setLong(String key, long value) {
        setLong(key, value, false);
    }

    public void setLong(String key, long value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setLong(key, value);
    }

    public void setByte(String key, byte value) {
        setByte(key, value, false);
    }

    public void setByte(String key, byte value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setByte(key, value);
    }

    public void setShort(String key, short value) {
        setShort(key, value, false);
    }

    public void setShort(String key, short value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setShort(key, value);
    }

    public void setFloat(String key, float value) {
        setFloat(key, value, false);
    }

    public void setFloat(String key, float value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setFloat(key, value);
    }

    public void setByteArray(String key, byte[] value) {
        setByteArray(key, value, false);
    }

    public void setByteArray(String key, byte[] value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setByteArray(key, value);
    }

    public void setIntArray(String key, int[] value) {
        setIntArray(key, value, false);
    }

    public void setIntArray(String key, int[] value, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.setIntArray(key, value);
    }

    public String getString(String key) {
        return getCompound("ExtraAttributes").getString(key);
    }

    public String getStringOrDefault(String key, String def) {
        return hasKey(key) ? getString(key) : def;
    }

    public Integer getInteger(String key) {
        return getCompound("ExtraAttributes").getInteger(key);
    }

    public Integer getIntegerOrDefault(String key, Integer def) {
        return hasKey(key) ? getInteger(key) : def;
    }

    public Boolean getBoolean(String key) {
        return getCompound("ExtraAttributes").getBoolean(key);
    }

    public Boolean getBooleanOrDefault(String key, Boolean def) {
        return hasKey(key) ? getBoolean(key) : def;
    }

    public Double getDouble(String key) {
        return getCompound("ExtraAttributes").getDouble(key);
    }

    public Double getDoubleOrDefault(String key, Double def) {
        return hasKey(key) ? getDouble(key) : def;
    }

    public Long getLong(String key) {
        return getCompound("ExtraAttributes").getLong(key);
    }

    public Long getLongOrDefault(String key, Long def) {
        return hasKey(key) ? getLong(key) : def;
    }

    public Byte getByte(String key) {
        return getCompound("ExtraAttributes").getByte(key);
    }

    public Byte getByteOrDefault(String key, Byte def) {
        return hasKey(key) ? getByte(key) : def;
    }

    public Short getShort(String key) {
        return getCompound("ExtraAttributes").getShort(key);
    }

    public Short getShortOrDefault(String key, Short def) {
        return hasKey(key) ? getShort(key) : def;
    }

    public Float getFloat(String key) {
        return getCompound("ExtraAttributes").getFloat(key);
    }

    public Float getFloatOrDefault(String key, Float def) {
        return hasKey(key) ? getFloat(key) : def;
    }

    public byte[] getByteArray(String key) {
        return getCompound("ExtraAttributes").getByteArray(key);
    }

    public byte[] getByteArrayOrDefault(String key, byte[] def) {
        return hasKey(key) ? getByteArray(key) : def;
    }

    public int[] getIntArray(String key) {
        return getCompound("ExtraAttributes").getIntArray(key);
    }

    public int[] getIntArrayOrDefault(String key, int[] def) {
        return hasKey(key) ? getIntArray(key) : def;
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

    public String getCompoundToString() {
        return toString();
    }

    public void applyFromString(String compound) {
        mergeCompound(new NBTContainer(compound));
    }
}
