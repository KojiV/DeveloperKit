package koji.developerkit.utils;

import com.cryptomorin.xseries.ReflectionUtils;
import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import koji.developerkit.reflection.MethodHandleAssistant;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("unused")
public class ItemBuilder extends MethodHandleAssistant {
    private static final long serialVersionUID = 1231246598235L;
    protected transient ItemStack im;
    private String compound;
    private XMaterial material;
    private int red, green, blue;

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

    public List<String> getLore() {
        return im.getItemMeta().getLore();
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
    private static final MethodHandle COPE, GET_ITEM, GET_NAME, LOCALE_LANGUAGE, GET;
    private static final String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PREFIX = OBC_PREFIX.replace(
            "org.bukkit.craftbukkit", "net.minecraft.server"
    );

    static {
        try {
            Class<?> localLanguage = ReflectionUtils.getNMSClass("locale", "LocaleLanguage");
            Class<?> nmsItemStackClass = ReflectionUtils.getNMSClass("world.item", "ItemStack");
            Class<?> nmsItemClass = ReflectionUtils.getNMSClass("world.item", "Item");

            COPE = getMethod(ReflectionUtils.getCraftClass("inventory.CraftItemStack"),
                    MethodType.methodType(nmsItemStackClass, ItemStack.class), true, "asNMSCopy"
            );
            GET_ITEM = getMethod(nmsItemStackClass, MethodType.methodType(nmsItemClass),
                    "getItem", "c"
            );
            GET_NAME = getMethod(nmsItemClass, MethodType.methodType(String.class),
                    "getName", "a"
            );
            LOCALE_LANGUAGE = getMethod(localLanguage, MethodType.methodType(localLanguage), true, "a");
            GET = getMethod(localLanguage, MethodType.methodType(String.class, String.class), "a");

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFriendlyName(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return "Air";

        try {

            Object nmsItemStack = COPE.invoke(itemStack);
            Object nmsItem = GET_ITEM.invoke(nmsItemStack);
            Object name = GET_NAME.invoke(nmsItem);
            Object got = GET.invoke(LOCALE_LANGUAGE.invoke(), name);

            Object localeString = name == null ? "" : GET.invoke(LOCALE_LANGUAGE.invoke(), name);
            return localeString.toString();
        } catch (Throwable e) {
            return capitalize(itemStack.getType().name().replace("_", " ").toLowerCase());
        }
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

        SkullMeta skullMeta = (SkullMeta) im.getItemMeta();
        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            GameProfile profile = (GameProfile) profileField.get(skullMeta);
            Collection<Property> textures = profile.getProperties().get("textures");
            return new ArrayList<>(textures).get(0).getValue();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return ((SkullMeta) im.getItemMeta()).getOwner();
    }

    public ItemBuilder setTexture(String texture) {
        return setTexture(texture, true);
    }

    public ItemBuilder setTexture(String texture, boolean stackable) {
        if(im.getType() != XMaterial.PLAYER_HEAD.parseMaterial() || texture == null) return this;

        SkullMeta hm = (SkullMeta) im.getItemMeta();
        GameProfile profile = new GameProfile(
                stackable ? UUID.nameUUIDFromBytes(texture.getBytes()) : UUID.randomUUID(),
                null
        );
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

    public Color getColor() {
        if (!im.getType().toString().startsWith("LEATHER_")) return null;
        
        LeatherArmorMeta lAM = (LeatherArmorMeta) im.getItemMeta();
        return lAM.getColor();
    }

    public ItemBuilder setColor(Color c) {
        if(!im.getType().toString().startsWith("LEATHER_") || c == null) return this;

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

    private void setSerializedVariables() {
        compound = getStringFromCompound();
        material = XMaterial.matchXMaterial(im.getType());
        Color color = im.getItemMeta() instanceof LeatherArmorMeta ?
                ((LeatherArmorMeta) im.getItemMeta()).getColor() : null;
        red = color != null ? color.getRed() : -1;
        green = color != null ? color.getGreen() : -1;
        blue = color != null ? color.getBlue() : -1;
    }

    /*private void writeObject(ObjectOutputStream out) throws IOException {
        compound = getStringFromCompound();
        material = XMaterial.matchXMaterial(im.getType());
        Color color = im.getItemMeta() instanceof LeatherArmorMeta ?
                ((LeatherArmorMeta) im.getItemMeta()).getColor() : null;
        red = color != null ? color.getRed() : -1;
        green = color != null ? color.getGreen() : -1;
        blue = color != null ? color.getBlue() : -1;

        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();


@Override
    public void write(Kryo kryo, Output output) {
        compound = getStringFromCompound();
        material = XMaterial.matchXMaterial(im.getType());
        Color color = im.getItemMeta() instanceof LeatherArmorMeta ?
                ((LeatherArmorMeta) im.getItemMeta()).getColor() : null;
        red = color != null ? color.getRed() : -1;
        green = color != null ? color.getGreen() : -1;
        blue = color != null ? color.getBlue() : -1;

        kryo.register(XMaterial.class, 9);

        output.writeString(compound);
        kryo.writeObject(output, material);
        output.writeInt(red);
        output.writeInt(green);
        output.writeInt(blue);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        kryo.register(XMaterial.class, 9);

        compound = input.readString();
        material = kryo.readObject(input, XMaterial.class);
        red = input.readInt();
        green = input.readInt();
        blue = input.readInt();

        im = new ItemStack(material.parseMaterial());
        applyCompoundFromString(compound, true);
        if(red != -1 && green != -1 && blue != -1) setColor(Color.fromRGB(red, green, blue));
    }

    }*/

    public static class ItemBuilderSerializer extends FSTBasicObjectSerializer {
        @Override
        public void writeObject(FSTObjectOutput out, Object toWrite, FSTClazzInfo clzInfo, FSTClazzInfo.FSTFieldInfo referencedBy, int streamPosition) throws IOException {
            if(toWrite instanceof ItemBuilder) {
                ItemBuilder ib = (ItemBuilder) toWrite;
                ib.setSerializedVariables();
            }
            out.defaultWriteObject(toWrite, clzInfo);
        }

        @Override
        public void readObject(FSTObjectInput in, Object toRead, FSTClazzInfo clzInfo, FSTClazzInfo.FSTFieldInfo referencedBy) throws Exception {
            in.defaultReadObject(referencedBy, clzInfo, toRead);
            if(toRead instanceof ItemBuilder) {
                ItemBuilder ib = (ItemBuilder) toRead;

                ib.im = new ItemStack(ib.material.parseMaterial());
                ib.applyCompoundFromString(ib.compound, true);
                if(ib.red != -1 && ib.green != -1 && ib.blue != -1)
                    ib.setColor(Color.fromRGB(ib.red, ib.green, ib.blue));
            }
        }
    }
}
