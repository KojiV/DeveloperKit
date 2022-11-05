package koji.developerkit;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import koji.developerkit.gui.GUIClickableItem;
import koji.developerkit.gui.GUIListener;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

/**
 * Class that (by recommended use) extends each used class for easy access to these methods
 *
 * @author Koji
 */
public class KBase {

    private static final JavaPlugin plugin;

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    static {
        plugin = JavaPlugin.getProvidingPlugin(KBase.class);

        plugin.getLogger().log(Level.INFO, "Connecting Koji Dev Kit...");
        GUIListener.startup();

        MinecraftVersion.getVersion();
    }

    // File Stuff

    /**
     * Creates folders with the path
     *
     * @param path The main path
     */
    public static void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * Creates folders with the path
     *
     * @param path  The main path
     * @param paths The added paths
     */
    public static void createFolder(String path, String[] paths) {
        for (String s : paths) {
            File file = new File(path + s);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    /**
     * Gets the keys of the param key
     *
     * @param fc   The file to check
     * @param key  The key to check
     * @param deep Whether it includes the children or not
     * @return The keys it did find
     */
    public static List<String> getKeys(FileConfiguration fc, String key, boolean deep) {
        List<String> list = new ArrayList<>();
        for (String keys : fc.getKeys(true)) {
            if (keys.startsWith(key)) {
                if (!deep) {
                    boolean skip = false;
                    for (String alreadyExisting : list) {
                        if (keys.startsWith(alreadyExisting)) {
                            skip = true;
                            break;
                        }
                    }
                    if (skip) continue;
                }
                list.add(keys);
            }
        }
        return list;
    }

    // GUI Stuff

    /**
     * Sets the inventory slot corresponding to the GUIClickable item's slot
     *
     * @param inv  The inventory
     * @param item The item
     */
    public static void set(Inventory inv, GUIClickableItem item) {
        inv.setItem(item.getSlot(), item.getFinishedItem());
    }

    /**
     * Sets the inventory slot corresponding to the slot specified
     *
     * @param inv  The inventory
     * @param item The item
     * @param slot the slot
     * @see KBase#set(Inventory, GUIClickableItem)
     */
    public static void set(Inventory inv, GUIClickableItem item, int slot) {
        inv.setItem(slot, item.getFinishedItem());
    }

    /**
     * Gets the empty slots in a given inventory
     *
     * @param inventory The inventory to check
     * @return The amount of unclaimed slots
     */
    public static int getEmptySlots(Inventory inventory) {
        int i = 0;
        for (ItemStack is : inventory.getContents()) {
            if (is == null)
                continue;
            i++;
        }
        return i;
    }

    /**
     * Sets multiple slots with the same item
     *
     * @param inv   The inventory it's being set on
     * @param slots The slots it will set them to
     * @param is    The item all the slots are being set to
     */
    public static void setMultipleSlots(Inventory inv, int[] slots, ItemStack is) {
        for (int slot : slots) {
            inv.setItem(slot, is);
        }
    }

    /**
     * Sets the border of the inventory with the border item
     *
     * @param inv The inventory the border item is being set on
     */
    public static void setBorder(Inventory inv) {
        int size = inv.getSize();
        if (size < 27) return;

        for (int i = 0; i < 9; i++) {
            set(inv, GUIClickableItem.getBorderItem(i));
        }

        for (int i = 0; i < Math.ceil(size / 10.0); i++) {
            set(inv, GUIClickableItem.getBorderItem(8 + (9 * i)));
            if (9 + (9 * i) > size - 1) continue;
            set(inv, GUIClickableItem.getBorderItem(9 + (9 * i)));
        }

        for (int i = size - 9; i < size; i++) {
            set(inv, GUIClickableItem.getBorderItem(i));
        }
    }

    /**
     * Adds an item to the inventory if it can
     *
     * @param is  The item
     * @param inv The inventory is will be added to
     */
    public static void addItem(ItemStack is, Inventory inv) {
        if (inv.firstEmpty() != -1) {
            inv.addItem(is);
        }
    }

    /**
     * Fills an inventory with the selected item
     *
     * @param inv The inventory to fill
     * @param is  The item that will fill it
     */
    public static void fill(Inventory inv, ItemStack is) {
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, is);
        }
    }

    /**
     * Fills an inventory with the selected GUI item
     *
     * @param inv The inventory to fill
     * @param is  The item that will fill it
     */
    public static void fill(Inventory inv, GUIClickableItem is) {
        for (int i = 0; i < inv.getSize(); i++) {
            set(inv, is, i);
        }
    }

    // Mob Stuff

    /**
     * Checks if any of the mob types match the type of the entity
     *
     * @param e     The entity
     * @param types The types to check
     * @return Whether or not any match
     */
    public boolean isMobType(Entity e, EntityType... types) {
        return Arrays.stream(types).anyMatch(type -> type == e.getType());
    }

    // Block Stuff

    /**
     * Generates a sphere corresponding to the arguments
     *
     * @param centerBlock The center point
     * @param radius      The radius
     * @param hollow      Whether it is hollow or not
     * @return The locations of the sphere
     */
    public static List<Location> generateSphere(Location centerBlock, int radius, boolean hollow) {

        List<Location> circleBlocks = new ArrayList<Location>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {

                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));

                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

                        Location l = new Location(centerBlock.getWorld(), x, centerBlock.getY(), z);

                        if (!circleBlocks.contains(l)) circleBlocks.add(l);
                    }

                }
            }
        }

        return circleBlocks;
    }

    // String Stuff

    /**
     * Bolds text
     *
     * @param text The text to be bolded
     * @return The text bolded
     */
    public static String bold(String text) {
        return ChatColor.BOLD + text;
    }

    /**
     * Replaces the placeholders in param placeholder wherever they are in param original
     *
     * @param original    The list that will have replaced stuff
     * @param placeholder The stuff to replace
     * @return param original with the placeholders replaced
     */
    public static ArrayList<String> replacePlaceholder(ArrayList<String> original, HashMap<String, List<String>> placeholder) {
        ArrayList<String> lore = new ArrayList<>();
        for (String str : original) {
            boolean more = false;
            String holder = "";
            for (String place : placeholder.keySet()) {
                if (str.contains(place)) {
                    str = str.replace(place, placeholder.get(place).get(0));
                    if (placeholder.get(place).size() != 1) {
                        more = true;
                    }
                    holder = place;
                }
            }
            lore.add(str);
            if (more) {
                for (int i = 1; i < placeholder.get(holder).size(); i++) {
                    lore.add(placeholder.get(holder).get(i));
                }
            }
        }
        return lore;
    }

    // Color Stuff

    /**
     * The string with the MC color code stuff
     *
     * @param s The string to translate
     * @return The string but colored
     */
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Converts a normal list to the colored version of the list
     *
     * @param s The list
     * @return The list but with proper MC color code stuff
     * @see #color(String)
     */
    public static ArrayList<String> coloredList(List<String> s) {
        ArrayList<String> list = new ArrayList<>();
        s.forEach(line -> list.add(color(line)));
        return list;
    }

    /**
     * Converts a string to RGB
     *
     * @param string The string, that should be formatted like "r,g,b"
     * @return The color
     */
    public static Color getColorByRGB(String string) {
        return Color.fromRGB(
                Integer.parseInt(string.split(",")[0]),
                Integer.parseInt(string.split(",")[1]),
                Integer.parseInt(string.split(",")[2])
        );
    }

    /**
     * Converts the string to a color
     *
     * @param string The string that supposedly is a color
     * @return The color corresponding, otherwise null
     */
    public static Color getColorByString(String string) {
        switch (string.toLowerCase()) {
            case "aqua":
                return Color.AQUA;
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "fuchsia":
                return Color.FUCHSIA;
            case "gray":
                return Color.GRAY;
            case "green":
                return Color.GREEN;
            case "lime":
                return Color.LIME;
            case "maroon":
                return Color.MAROON;
            case "navy":
                return Color.NAVY;
            case "olive":
                return Color.OLIVE;
            case "orange":
                return Color.ORANGE;
            case "purple":
                return Color.PURPLE;
            case "red":
                return Color.RED;
            case "silver":
                return Color.SILVER;
            case "teal":
                return Color.TEAL;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            default:
                try {
                    return Color.fromRGB(Integer.parseInt(string));
                } catch (Exception ex) {
                    return null;
                }
        }
    }

    // Block Stuff

    /**
     * Gets the very center of the location
     *
     * @param loc The location
     * @return The location centered in the exact middle of the block
     */
    public static Location getBlockLocationCentered(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        Location returnLoc = new Location(loc.getWorld(), x, y, z);
        returnLoc.add(x >= 0 ? 0.5 : -0.5, 0.0, z >= 0 ? 0.5 : -0.5);
        return returnLoc;
    }

    // Data Stuff

    /**
     * Whether the two match (material wise)
     *
     * @param b1 The first material to be checked
     * @param b2 The second material to be checked
     * @return Whether they match or not
     */
    public static boolean blockMatches(Block b1, Block b2) {
        if (XMaterial.supports(13)) {
            return b1.getType() == b2.getType();
        } else {
            return b1.getType() == b2.getType() && b1.getData() == b2.getData();
        }
    }

    /**
     * Whether the two match (material wise)
     *
     * @param m1 The first material to be checked
     * @param m2 The second material to be checked
     * @return Whether they match or not
     */
    public static boolean blockMatches(MaterialData m1, MaterialData m2) {
        if (XMaterial.supports(13)) {
            return m1.getItemType() == m2.getItemType();
        } else {
            return m1.getItemType() == m2.getItemType() && m1.getData() == m2.getData();
        }
    }

    /**
     * Whether the two match (material wise)
     *
     * @param m1 The first material to be checked
     * @param b2 The second material to be checked
     * @return Whether they match or not
     */
    public static boolean blockMatches(MaterialData m1, Block b2) {
        if (XMaterial.supports(13)) {
            return m1.getItemType() == b2.getType();
        } else {
            return m1.getItemType() == b2.getType() && m1.getData() == b2.getData();
        }
    }

    /**
     * Sets the legacy data of a block if it is legacy
     *
     * @param b  The block that param by will be set to
     * @param by The byte to set
     * @return Whether it successfully set the data or not
     */
    public static boolean setData(Block b, byte by) {
        try {
            Method setData = b.getClass().getMethod("setData", byte.class);
            setData.invoke(b, by);
            return true;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
            return false;
        }
    }

    // Object Stuff

    /**
     * Check if the list of other objects is equal to the first
     *
     * @param comparisonObj The object the rest will be compared to
     * @param otherObj      The series of objects that will be checked with param otherObj
     * @return Whether any of the param otherObj equal comparisonObj
     */
    public static boolean checkEquals(Object comparisonObj, Object... otherObj) {
        for (Object o : otherObj) {
            if (comparisonObj.equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get if the object is a numeric class (a number)
     *
     * @param obj The object to check
     * @return Whether it is a number or not
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            double d = (double) obj;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // List Stuff

    /**
     * Gets a random value in the array
     *
     * @param array The array
     * @param <T>   The type of array
     * @return A random value in the array
     */
    public static <T> T getRandom(T[] array) {
        return array[new Random().nextInt(array.length)];
    }

    /**
     * Gets a random value in the arraylist
     *
     * @param list The arraylist
     * @param <T>  The type of arraylist
     * @return A random value in the arraylist
     * @see #getRandom(Object[])
     */
    public static <T> T getRandom(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    /**
     * Converts a series of itemsToRun to an array
     *
     * @param array The series of itemsToRun
     * @param <T>   The type of array
     * @return An array from the series of itemsToRun
     * @see #arrayList(Object[])
     */
    @SafeVarargs
    public static <T> T[] array(T... array) {
        return array;
    }

    /**
     * Makes an array the selected size that all contain param array
     *
     * @param size  The size of the array
     * @param array The thing that will be recurring throughout the entire array
     * @param <T>   The type
     * @return An array that all contain the same value
     */
    public static <T> T[] recursiveArray(int size, T array) {
        @SuppressWarnings("unchecked")
        T[] t = (T[]) Array.newInstance(array.getClass(), size);
        for (int i = 0; i < size; i++) {
            t[i] = array;
        }
        return t;
    }

    /**
     * Converts a series of itemsToRun into an arraylist
     *
     * @param array The series of itemsToRun
     * @param <T>   The type of item
     * @return An arraylist with the itemsToRun
     */
    @SafeVarargs
    public static <T> ArrayList<T> arrayList(T... array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * Splits the array into chunks of the selected size
     *
     * @param array The array to split
     * @param size  The amount that it is being split into
     * @param <T>   The type of array
     * @return A list of arrays that contain the split contents
     */
    public static <T> ArrayList<T[]> split(T[] array, int size) {
        if (array.length == 0) return new ArrayList<>();

        Class<?> clazz = array[0].getClass();

        int splitSize = (int) Math.ceil(array.length / (double) size);
        ArrayList<T[]> returnArray = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T[] split = (T[]) Array.newInstance(clazz, splitSize);
            for (int j = 0; j < splitSize; j++) {
                if (i * splitSize + j < array.length) {
                    split[j] = array[i * splitSize + j];
                }
            }

            returnArray.add(split);
        }
        return returnArray;
    }

    /**
     * Turns an array to an arraylist
     *
     * @param array The array
     * @param <T>   The type of array
     * @return The arraylist of T type
     */
    public static <T> ArrayList<T> fromArray(T[] array) {
        return new ArrayList<T>(Arrays.asList(array));
    }

    /**
     * Shuffles a list into a random order
     *
     * @param list The list to shuffle
     * @param <T>  The type of list
     * @return The list randomized
     */
    public static <T> List<T> shuffle(List<T> list) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = list.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            T t = list.get(index);
            list.set(index, list.get(i));
            list.set(i, t);
        }
        return list;
    }

    /**
     * Tries to get the element in the selected index or returns the default selected value
     *
     * @param list  The list to check
     * @param index The index to check
     * @param def   The value to default to
     * @param <T>   The kind of list
     * @return Either the slot in the list with that index or the default value
     * @see HashMap#getOrDefault(Object, Object)
     */
    public static <T> T getOrDefault(List<T> list, int index, T def) {
        if (index < 0 || index >= list.size())
            return def;
        return list.get(index);
    }

    /**
     * Formats a string list into an acceptable string to be printed
     *
     * @param list The list to be formatted
     * @return A string that has , like a normal list you would see
     */
    public static String formatList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Get the last element of the array
     *
     * @param list The array to be checked
     * @param <T>  The object type
     * @return The final value in the array
     * @see #getLast(List)
     */
    public static <T> T getLast(T[] list) {
        return getLast(fromArray(list));
    }

    /**
     * Get the last element of the arraylist
     *
     * @param list The array to be checked
     * @param <T>  The object type
     * @return The final value in the arraylist
     */
    public static <T> T getLast(List<T> list) {
        return list.get(list.size() - 1);
    }

    // Number Stuff

    /**
     * Rounds the number aligning with the fraction
     *
     * @param value The value being rounded
     * @param frac  The fraction
     * @return The rounded number
     */
    public static double round(double value, int frac) {
        return Math.round(Math.pow(10.0, frac) * value) / Math.pow(10.0, frac);
    }

    /**
     * Adds the percent of the number to the number
     *
     * @param value The initial value
     * @param percent The percent that it's adding
     * @return The number + percent% of the number
     */
     public static double addPercent(double value, double percent) {
        return value + (percent / 100 * value);
     }

     /**
     * Checks if the put in number is negative or not
     *
     * @param number The number that will be checked
     * @return Returns if the number is negative
     */
    public static boolean isNegative(double number) {
        return number < 0;
    }

    //Scoreboard Stuff

    /**
     * Gets the scoreboard objective or create it if it doesn't exist
     *
     * @param board    The scoreboard
     * @param name     The objective
     * @param criteria The criteria if it has to be newly created
     * @return The objective gotten or created
     */
    public static Objective getOrCreateObj(Scoreboard board, String name, String criteria) {
        if (board.getObjective(name) == null)
            return board.registerNewObjective(name, criteria);
        return board.getObjective(name);
    }


    // Formatting Stuff

    /**
     * Formats number in terms of removing .0 at the end of strings
     *
     * @param thing The number to be formatted
     * @return The number formatted
     */
    public static String num(String thing) {
        return thing.replace(".0", "");
    }

    /**
     * Formats number in terms of removing .0 at the end of strings
     *
     * @param thing The number to be formatted
     * @return The number formatted
     * @see #num(String)
     */
    public static String num(double thing) {
        return num(thing + "");
    }

    private static final NumberFormat COMMA_FORMAT = NumberFormat.getInstance();

    static {
        COMMA_FORMAT.setGroupingUsed(true);
    }

    /**
     * Adds commas to numbers
     *
     * @param i The number being formatted
     * @return Formatted number
     */
    public static String commaify(int i) {
        return commaify((double) i);
    }

    /**
     * Adds commas to numbers
     *
     * @param i The number being formatted
     * @return Formatted number
     */
    public static String commaify(double i) {
        return COMMA_FORMAT.format(i);
    }


    /**
     * Adds a space between each capital letter
     *
     * @param thing The string to be affected
     * @return Returns the string with spaces between each capital letter
     */
    public static String space(String thing) {
        return thing.replaceAll("(.)([A-Z])", "$1 $2");
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    /**
     * Adds the suffixes like K and M and the end of numbers
     *
     * @param value The number to be formatted
     * @return Returns the number simplified with the suffixes (K, M, etc.)
     */
    public static String formatNumberSuffixes(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatNumberSuffixes(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatNumberSuffixes(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    // Roman Numeral Stuff

    /**
     * Converts numbers to roman integers
     *
     * @param num The number being put in
     * @return The number as a roman numeral
     */
    public static String toRomanNumeral(int num) {
        StringBuilder sb = new StringBuilder();
        int times;
        String[] romans = new String[]{"I", "IV", "V", "IX", "X", "XL", "L",
                "XC", "C", "CD", "D", "CM", "M"};
        int[] ints = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500,
                900, 1000};
        for (int i = ints.length - 1; i >= 0; i--) {
            times = num / ints[i];
            num %= ints[i];
            while (times > 0) {
                sb.append(romans[i]);
                times--;
            }
        }
        return sb.toString();
    }

    /**
     * Converts roman integer to numbers
     *
     * @param roman The roman integer
     * @return The number that the roman integer is equal to
     */
    public static int romanToInteger(String roman) {
        Map<Character, Integer> numbersMap = new HashMap<>();
        numbersMap.put('I', 1);
        numbersMap.put('V', 5);
        numbersMap.put('X', 10);
        numbersMap.put('L', 50);
        numbersMap.put('C', 100);
        numbersMap.put('D', 500);
        numbersMap.put('M', 1000);

        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            char ch = roman.charAt(i);      // Current Roman Character

            //Case 1
            if (i > 0 && numbersMap.get(ch) > numbersMap.get(roman.charAt(i - 1))) {
                result += numbersMap.get(ch) - 2 * numbersMap.get(roman.charAt(i - 1));
            }

            // Case 2: just add the corresponding number to result.
            else
                result += numbersMap.get(ch);
        }

        return result;
    }

    // Text Stuff

    /**
     * Capitalizes the first letter of each word
     *
     * @param str The string to capitalize
     * @return The string with the first letter of each word being capitalized
     * @see #capitalize(String, char[])
     */
    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    /**
     * Capitalizes the first letter of each word
     *
     * @param str        The string to capitalize
     * @param delimiters The delimiters to check
     * @return The string with the first letter of each word being capitalized
     */
    public static String capitalize(String str, char[] delimiters) {
        int delimLen = (delimiters == null ? -1 : delimiters.length);
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuilder buffer = new StringBuilder(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * Checks if the selected character is in the delimiters
     *
     * @param ch         The character being checked
     * @param delimiters The delimiters
     * @return If the character is a delimiter
     */
    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (char delimiter : delimiters) {
            if (ch == delimiter) {
                return true;
            }
        }
        return false;
    }

    // Internal Stuff


    /**
     * Will print all the objects attached separated with a space as a new line
     *
     * @param variables The objects to print
     * @see #print
     */
    public static void println(Object... variables) {
        print(variables);
        println();
    }

    /**
     * For arrays, use printArray() instead. This function causes a warning
     * because the new print(Object...) and println(Object...) functions can't
     * be reliably bound by the compiler.
     *
     * @param what What will be printed
     */
    public static void println(Object what) {
        if (what == null) {
            System.out.println("null");
        } else if (what.getClass().isArray()) {
            printArray(what);
        } else {
            System.out.println(what);
            System.out.flush();
        }
    }

    /**
     * Will print a new line
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Will print all the objects attached separated with a space
     *
     * @param variables The objects to print
     */
    public static void print(Object... variables) {
        StringBuilder sb = new StringBuilder();
        for (Object o : variables) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            if (o == null) {
                sb.append("null");
            } else {
                sb.append(o);
            }
        }
        System.out.print(sb);
    }

    /**
     * Does what it says, prints an array (wither proper formatting)
     *
     * @param what What it will print
     */
    public static void printArray(Object what) {
        if (what == null) {
            // special case since this does fuggly things on > 1.1
            System.out.println("null");

        } else {
            String name = what.getClass().getName();
            if (name.charAt(0) == '[') {
                switch (name.charAt(1)) {
                    case 'L':
                        // print a 1D array of objects as individual elements
                        Object[] poo = (Object[]) what;
                        for (int i = 0; i < poo.length; i++) {
                            if (poo[i] instanceof String) {
                                System.out.println("[" + i + "] \"" + poo[i] + "\"");
                            } else {
                                System.out.println("[" + i + "] " + poo[i]);
                            }
                        }
                        break;

                    case 'Z':  // boolean
                        boolean[] zz = (boolean[]) what;
                        for (int i = 0; i < zz.length; i++) {
                            System.out.println("[" + i + "] " + zz[i]);
                        }
                        break;

                    case 'B':  // byte
                        byte[] bb = (byte[]) what;
                        for (int i = 0; i < bb.length; i++) {
                            System.out.println("[" + i + "] " + bb[i]);
                        }
                        break;

                    case 'C':  // char
                        char[] cc = (char[]) what;
                        for (int i = 0; i < cc.length; i++) {
                            System.out.println("[" + i + "] '" + cc[i] + "'");
                        }
                        break;

                    case 'I':  // int
                        int[] ii = (int[]) what;
                        for (int i = 0; i < ii.length; i++) {
                            System.out.println("[" + i + "] " + ii[i]);
                        }
                        break;

                    case 'J':  // int
                        long[] jj = (long[]) what;
                        for (int i = 0; i < jj.length; i++) {
                            System.out.println("[" + i + "] " + jj[i]);
                        }
                        break;

                    case 'F':  // float
                        float[] ff = (float[]) what;
                        for (int i = 0; i < ff.length; i++) {
                            System.out.println("[" + i + "] " + ff[i]);
                        }
                        break;

                    case 'D':  // double
                        double[] dd = (double[]) what;
                        for (int i = 0; i < dd.length; i++) {
                            System.out.println("[" + i + "] " + dd[i]);
                        }
                        break;

                    default:
                        System.out.println(what);
                }
            } else {  // not an array
                System.out.println(what);
            }
        }
        System.out.flush();
    }

    static Random internalRandom;

    /**
     * Makes a random number between 0 and every number less than param high
     * If you cast this to an int, it would be 0 through param high - 1
     *
     * @param high The highest the number can go (I'm pretty sure it would never hit this)
     * @return A random number between 0 and the param high
     */
    public static float random(float high) {
        // avoid an infinite loop when 0 or NaN are passed in
        if (high == 0 || high != high) {
            return 0;
        }

        if (internalRandom == null) {
            internalRandom = new Random();
        }

        // for some reason (rounding error?) Math.random() * 3
        // can sometimes return '3' (once in ~30 million tries)
        // so a check was added to avoid the inclusion of 'howbig'
        float value = 0;
        do {
            value = internalRandom.nextFloat() * high;
        } while (value == high);
        return value;
    }

    // Item Stuff

    /**
     * Dyes the item put in the color put in
     *
     * @param item  The item that will be colored
     * @param color The color the item will be colored
     * @return Returns the item dyed
     */
    public static ItemStack colorToArmor(ItemStack item, Color color) {
        if (!item.getType().name().contains("LEATHER_")) return item;
        LeatherArmorMeta im = (LeatherArmorMeta) item.getItemMeta();
        im.setColor(color);
        item.setItemMeta(im);
        return item;
    }

    /**
     * The item's amount will be set to the item selected
     *
     * @param item   Item
     * @param amount The amount the item will be set to
     * @return The item x the amount
     */
    public static ItemStack setStackAmount(ItemStack item, int amount) {
        ItemStack returnType = new ItemStack(item);
        returnType.setAmount(amount);
        return returnType;
    }

    /**
     * Sets the texture of the item (player head) to the string
     *
     * @param item    The item (must be a player head)
     * @param texture The texture the skull will be set
     * @return Returns the skull with the texture set
     */
    public static ItemStack setTexture(ItemStack item, String texture) {
        if (item.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return item;
        SkullMeta hm = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field field = hm.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(hm, profile);
        } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        item.setItemMeta(hm);
        return item;
    }
}
