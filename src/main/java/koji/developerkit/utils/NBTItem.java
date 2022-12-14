package koji.developerkit.utils;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("unused")
public class NBTItem extends de.tr7zw.changeme.nbtapi.NBTItem {

    public NBTItem(ItemStack item, boolean directApply) {
        super(item, directApply);
        getOrCreateCompound("ExtraAttributes");
    }

    public NBTItem(ItemStack item) {
        this(item, false);
    }

    public NBTItem(ItemStack item, boolean directApply, List<String> compounds) {
        this(item, directApply);
        compounds.forEach(this::getOrCreateCompound);
    }

    public NBTItem(ItemStack item, List<String> compounds) {
        this(item, false, compounds);
    }

    public void setString(String key, String value) {
        setString(key, value, false);
    }

    public void setString(String key, String value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setString(key, value);
        } else {
            getCompound("ExtraAttributes").setString(key, value);
        }
    }

    public void setInteger(String key, int value) {
        setInteger(key, value, false);
    }

    public void setInteger(String key, int value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setInteger(key, value);
        } else {
            getCompound("ExtraAttributes").setInteger(key, value);
        }
    }

    public void setBoolean(String key, boolean value) {
        setBoolean(key, value, false);
    }

    public void setBoolean(String key, boolean value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setBoolean(key, value);
        } else {
            getCompound("ExtraAttributes").setBoolean(key, value);
        }
    }

    public void setDouble(String key, double value) {
        setDouble(key, value, false);
    }

    public void setDouble(String key, double value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setDouble(key, value);
        } else {
            getCompound("ExtraAttributes").setDouble(key, value);
        }
    }

    public void setLong(String key, long value) {
        setLong(key, value, false);
    }

    public void setLong(String key, long value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setLong(key, value);
        } else {
            getCompound("ExtraAttributes").setLong(key, value);
        }
    }

    public void setByte(String key, byte value) {
        setByte(key, value, false);
    }

    public void setByte(String key, byte value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setByte(key, value);
        } else {
            getCompound("ExtraAttributes").setByte(key, value);
        }
    }

    public void setShort(String key, short value) {
        setShort(key, value, false);
    }

    public void setShort(String key, short value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setShort(key, value);
        } else {
            getCompound("ExtraAttributes").setShort(key, value);
        }
    }

    public void setFloat(String key, float value) {
        setFloat(key, value, false);
    }

    public void setFloat(String key, float value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setFloat(key, value);
        } else {
            getCompound("ExtraAttributes").setFloat(key, value);
        }
    }

    public void setByteArray(String key, byte[] value) {
        setByteArray(key, value, false);
    }

    public void setByteArray(String key, byte[] value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setByteArray(key, value);
        } else {
            getCompound("ExtraAttributes").setByteArray(key, value);
        }
    }

    public void setIntArray(String key, int[] value) {
        setIntArray(key, value, false);
    }

    public void setIntArray(String key, int[] value, boolean ignoreCompound) {
        if (ignoreCompound) {
            super.setIntArray(key, value);
        } else {
            getCompound("ExtraAttributes").setIntArray(key, value);
        }
    }

    public String getString(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getString(key);
        } else {
            return getCompound("ExtraAttributes").getString(key);
        }
    }

    public String getString(String key) {
        return getString(key, false);
    }

    public String getStringOrDefault(String key, String def) {
        return getStringOrDefault(key, def, false);
    }

    public String getStringOrDefault(String key, String def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getString(key, ignoreCompound) : def;
    }

    public Integer getInteger(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getInteger(key);
        } else {
            return getCompound("ExtraAttributes").getInteger(key);
        }
    }

    public Integer getInteger(String key) {
        return getInteger(key, false);
    }

    public Integer getIntegerOrDefault(String key, Integer def) {
        return getIntegerOrDefault(key, def, false);
    }

    public Integer getIntegerOrDefault(String key, Integer def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getInteger(key, ignoreCompound) : def;
    }

    public Boolean getBoolean(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getBoolean(key);
        } else {
            return getCompound("ExtraAttributes").getBoolean(key);
        }
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public Boolean getBooleanOrDefault(String key, Boolean def) {
        return hasKey(key) ? getBoolean(key) : def;
    }

    public Boolean getBooleanOrDefault(String key, Boolean def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getBoolean(key, ignoreCompound) : def;
    }

    public Double getDouble(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getDouble(key);
        } else {
            return getCompound("ExtraAttributes").getDouble(key);
        }
    }

    public Double getDouble(String key) {
        return getDouble(key, false);
    }

    public Double getDoubleOrDefault(String key, Double def) {
        return hasKey(key) ? getDouble(key) : def;
    }

    public Double getDoubleOrDefault(String key, Double def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getDouble(key, ignoreCompound) : def;
    }

    public Long getLong(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getLong(key);
        } else {
            return getCompound("ExtraAttributes").getLong(key);
        }
    }

    public Long getLong(String key) {
        return getLong(key, false);
    }

    public Long getLongOrDefault(String key, Long def) {
        return hasKey(key) ? getLong(key) : def;
    }

    public Long getLongOrDefault(String key, Long def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getLong(key, ignoreCompound) : def;
    }

    public Byte getByte(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getByte(key);
        } else {
            return getCompound("ExtraAttributes").getByte(key);
        }
    }

    public Byte getByte(String key) {
        return getByte(key, false);
    }

    public Byte getByteOrDefault(String key, Byte def) {
        return hasKey(key) ? getByte(key) : def;
    }

    public Byte getByteOrDefault(String key, Byte def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getByte(key, ignoreCompound) : def;
    }

    public Short getShort(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getShort(key);
        } else {
            return getCompound("ExtraAttributes").getShort(key);
        }
    }

    public Short getShort(String key) {
        return getShort(key, false);
    }

    public Short getShortOrDefault(String key, Short def) {
        return hasKey(key) ? getShort(key) : def;
    }

    public Short getShortOrDefault(String key, Short def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getShort(key, ignoreCompound) : def;
    }

    public Float getFloat(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getFloat(key);
        } else {
            return getCompound("ExtraAttributes").getFloat(key);
        }
    }

    public Float getFloat(String key) {
        return getFloat(key, false);
    }

    public Float getFloatOrDefault(String key, Float def) {
        return hasKey(key) ? getFloat(key) : def;
    }

    public Float getFloatOrDefault(String key, Float def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getFloat(key, ignoreCompound) : def;
    }

    public byte[] getByteArray(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getByteArray(key);
        } else {
            return getCompound("ExtraAttributes").getByteArray(key);
        }
    }

    public byte[] getByteArray(String key) {
        return getByteArray(key, false);
    }

    public byte[] getByteArrayOrDefault(String key, byte[] def) {
        return hasKey(key) ? getByteArray(key) : def;
    }

    public byte[] getByteArrayOrDefault(String key, byte[] def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getByteArray(key, ignoreCompound) : def;
    }

    public int[] getIntArray(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.getIntArray(key);
        } else {
            return getCompound("ExtraAttributes").getIntArray(key);
        }
    }

    public int[] getIntArray(String key) {
        return getIntArray(key, false);
    }

    public int[] getIntArrayOrDefault(String key, int[] def) {
        return hasKey(key) ? getIntArray(key) : def;
    }

    public int[] getIntArrayOrDefault(String key, int[] def, boolean ignoreCompound) {
        return hasKey(key, ignoreCompound) ? getIntArray(key, ignoreCompound) : def;
    }

    public Boolean hasKey(String key) {
        return hasKey(key, false);
    }

    public Boolean hasKey(String key, boolean ignoreCompound) {
        if (ignoreCompound) {
            return super.hasTag(key);
        } else {
            return getCompound("ExtraAttributes").hasTag(key);
        }
    }

    public void removeKey(String key) {
        removeKey(key, false);
    }

    public void removeKey(String key, boolean ignoreCompound) {
        if(ignoreCompound) {
            super.removeKey(key);
        } else {
            getCompound("ExtraAttributes").removeKey(key);
        }
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
        applyFromString(compound, false);
    }

    public void applyFromString(String compoundName, boolean ignoreCompound) {
        NBTCompound compound = ignoreCompound ? this : getCompound("ExtraAttributes");
        compound.mergeCompound(new NBTContainer(compoundName));
    }
}
