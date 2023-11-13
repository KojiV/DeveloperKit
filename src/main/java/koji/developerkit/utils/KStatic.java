package koji.developerkit.utils;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import koji.developerkit.KBase;
import koji.developerkit.gui.GUIClickableItem;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"unused", "deprecation"})
public class KStatic extends KBase {

    public static JavaPlugin getPlugin() {
        return KBase.getPlugin();
    }

    // File Stuff

    /**
     * Creates folders with the path
     *
     * @param path The main path
     * @return Whether it was successful
     */
    public static boolean createFolder(String path) {
        return KBase.createFolder(path);
    }

    /**
     * Creates folders with the path
     *
     * @param path  The main path
     * @param paths The added paths
     * @return returns if the folder was successfully made
     */
    public static boolean createFolder(String path, String[] paths) {
        return KBase.createFolder(path, paths);
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
        return KBase.getKeys(fc, key, deep);
    }

    // GUI Stuff

    /**
     * Gets the index of items if they were centered based on amount
     *
     * @param amount amount to be centered by
     * @param centerSlot the start slot (can go down lines after that if amount greater than 7)
     * @return the slot numbers
     * @see KBase#getCenteredSlots(int, int, boolean)
     */
    public static int[] getCenteredSlots(int amount, int centerSlot) {
        return KBase.getCenteredSlots(amount, centerSlot);
    }

    /**
     * Gets the index of items if they were centered based on amount
     *
     * @param amount amount to be centered by
     * @param centerSlot the start slot (can go down lines after that if amount greater than 7)
     * @param affectsRows if param is true, it moves the rows up rows / 2 (rounded down)
     * @return the slot numbers
     */
    public static int[] getCenteredSlots(int amount, int centerSlot, boolean affectsRows) {
        return KBase.getCenteredSlots(amount, centerSlot, affectsRows);
    }

    /**
     * Adds an item to an inventory unless it's full
     *
     * @param inv  The inventory it's attempting to add it to
     * @param item The item attempting to be added
     * @return Whether it successfully added the item or not
     * @see KBase#addItemUnlessFull(Inventory, ItemStack, Runnable)
     */
    public static boolean addItemUnlessFull(Inventory inv, ItemStack item) {
        return addItemUnlessFull(inv, item, () -> {
        });
    }

    /**
     * Adds an item to an inventory unless it's full
     *
     * @param inv  The inventory it's attempting to add it to
     * @param item The item attempting to be added
     * @param run  A runnable that will run when it is unable to add item
     * @return Whether it successfully added the item or not
     */
    public static boolean addItemUnlessFull(Inventory inv, ItemStack item, Runnable run) {
        return KBase.addItemUnlessFull(inv, item, run);
    }

    /**
     * Sets the inventory slot corresponding to the GUIClickable item's slot
     *
     * @param inv  The inventory
     * @param item The item
     */
    public static void set(Inventory inv, GUIClickableItem item) {
        KBase.set(inv, item);
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
        KBase.set(inv, item, slot);
    }

    /**
     * Gets the empty slots in a given inventory
     *
     * @param inventory The inventory to check
     * @return The amount of unclaimed slots
     */
    public static int getEmptySlots(Inventory inventory) {
        return KBase.getEmptySlots(inventory);
    }

    /**
     * Sets multiple slots with the same item
     *
     * @param inv   The inventory it's being set on
     * @param slots The slots it will set them to
     * @param is    The item all the slots are being set to
     */
    public static void setMultipleSlots(Inventory inv, int[] slots, ItemStack is) {
        KBase.setMultipleSlots(inv, slots, is);
    }

    /**
     * Sets multiple slots with the same item
     *
     * @param inv   The inventory it's being set on
     * @param slots The slots it will set them to
     * @param is    The item all the slots are being set to
     * @see KBase#setMultipleSlots(Inventory, int[], ItemStack)
     */
    public static void setMultipleSlots(Inventory inv, int[] slots, GUIClickableItem is) {
        KBase.setMultipleSlots(inv, slots, is);
    }

    /**
     * Sets the border of the inventory with the border item
     *
     * @param inv The inventory the border item is being set on
     */
    public static void setBorder(Inventory inv) {
        KBase.setBorder(inv);
    }

    /**
     * Adds an item to the inventory if it can
     *
     * @param is  The item
     * @param inv The inventory is will be added to
     */
    public static void addItem(ItemStack is, Inventory inv) {
        KBase.addItem(is, inv);
    }

    /**
     * Fills an inventory with the selected item
     *
     * @param inv The inventory to fill
     * @param is  The item that will fill it
     */
    public static void fill(Inventory inv, ItemStack is) {
        KBase.fill(inv, is);
    }

    /**
     * Fills an inventory with the selected GUI item
     *
     * @param inv The inventory to fill
     * @param is  The item that will fill it
     */
    public static void fill(Inventory inv, GUIClickableItem is) {
        KBase.fill(inv, is);
    }

    // Mob Stuff

    /**
     * Checks if any of the mob types match the type of the entity
     *
     * @param e     The entity
     * @param types The types to check
     * @return Whether or not any match
     */
    public static boolean isMobType(Entity e, EntityType... types) {
        return KBase.isMobType(e, types);
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
        return KBase.generateSphere(centerBlock, radius, hollow);
    }

    // String Stuff

    /**
     * Bolds text
     *
     * @param text The text to be bolded
     * @return The text bolded
     */
    public static String bold(String text) {
        return KBase.bold(text);
    }

    /**
     * Replaces the placeholders in param placeholder wherever they are in param original
     *
     * @param original    The list that will have replaced stuff
     * @param placeholder The stuff to replace
     * @return param original with the placeholders replaced
     */
    public static List<String> replacePlaceholders(List<String> original, HashMap<String, String> placeholder) {
        return KBase.replacePlaceholders(original, placeholder);
    }

    /**
     * Replaces the placeholders in param placeholder wherever they are in param original
     *
     * @param original    The list that will have replaced stuff
     * @param placeholder The stuff to replace
     * @return param original with the placeholders replaced
     */
    public static List<String> replacePlaceholder(List<String> original, HashMap<String, List<String>> placeholder) {
        return KBase.replacePlaceholder(original, placeholder);
    }

    /**
     * Replaces the placeholders in param placeholder wherever they are in param original
     *
     * @param original    The list that will have replaced stuff
     * @param placeholder The stuff to replace
     * @return param original with the placeholders replaced
     */
    public static List<String> replacePlaceholder(List<String> original, List<Placeholder> placeholder) {
        return KBase.replacePlaceholder(original, placeholder);
    }

    // Color Stuff

    /**
     * The string with the MC color code stuff
     *
     * @param s The string to translate
     * @return The string but colored
     */
    public static String color(String s) {
        return KBase.color(s);
    }

    /**
     * Converts a normal list to the colored version of the list
     *
     * @param s The list
     * @return The list but with proper MC color code stuff
     * @see #color(String)
     */
    public static List<String> coloredList(List<String> s) {
        return KBase.coloredList(s);
    }

    /**
     * Converts a string to RGB
     *
     * @param string The string, that should be formatted like "r,g,b"
     * @return The color
     */
    public static Color getColorByRGB(String string) {
        return KBase.getColorByRGB(string);
    }

    /**
     * Converts the string to a color
     *
     * @param string The string that supposedly is a color
     * @return The color corresponding, otherwise null
     */
    public static Color getColorByString(String string) {
        return KBase.getColorByString(string);
    }

    // Block Stuff

    /**
     * Gets the very center of the location
     *
     * @param loc The location
     * @return The location centered in the exact middle of the block
     */
    public static Location getBlockLocationCentered(Location loc) {
        return KBase.getBlockLocationCentered(loc);
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
        return KBase.blockMatches(b1, b2);
    }

    /**
     * Whether the two match (material wise)
     *
     * @param m1 The first material to be checked
     * @param m2 The second material to be checked
     * @return Whether they match or not
     */
    public static boolean blockMatches(MaterialData m1, MaterialData m2) {
        return KBase.blockMatches(m1, m2);
    }

    /**
     * Whether the two match (material wise)
     *
     * @param m1 The first material to be checked
     * @param b2 The second material to be checked
     * @return Whether they match or not
     */
    public static boolean blockMatches(MaterialData m1, Block b2) {
        return KBase.blockMatches(m1, b2);
    }

    /**
     * Sets the legacy data of a block if it is legacy
     *
     * @param b  The block that param by will be set to
     * @param by The byte to set
     * @return Whether it successfully set the data or not
     */
    public static boolean setData(Block b, byte by) {
        return KBase.setData(b, by);
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
        return KBase.checkEquals(comparisonObj, otherObj);
    }

    /**
     * Get if the object is a numeric class (a number)
     *
     * @param obj The object to check
     * @return Whether it is a number or not
     */
    public static boolean isNumeric(Object obj) {
        return KBase.isNumeric(obj);
    }

    /**
     * Safe way of turning a string into a int
     *
     * @param s the string to parse
     * @return an int from s
     */
    public static int parseInteger(String s) {
        return KBase.parseInteger(s);
    }

    /**
     * Safe way of turning a string into a double
     *
     * @param s the string to parse
     * @return a double from s
     */
    protected static double parseDouble(String s) {
        return KBase.parseDouble(s);
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
        return KBase.getRandom(array);
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
        return KBase.getRandom(list);
    }

    /**
     * Converts a series of T to an array
     *
     * @param array The series of T
     * @param <T>   The type of array
     * @return An array from the series of T
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
        return KBase.recursiveArray(size, array);
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
        return KBase.arrayList(array);
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
        return KBase.split(array, size);
    }

    /**
     * Turns an array to an arraylist
     *
     * @param array The array
     * @param <T>   The type of array
     * @return The arraylist of T type
     */
    public static <T> ArrayList<T> fromArray(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * Shuffles a list into a random order
     *
     * @param list The list to shuffle
     * @param <T>  The type of list
     * @return The list randomized
     */
    public static <T> List<T> shuffle(List<T> list) {
        return KBase.shuffle(list);
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
        return KBase.getOrDefault(list, index, def);
    }

    /**
     * Formats a string list into an acceptable string to be printed
     *
     * @param list The list to be formatted
     * @return A string that has , like a normal list you would see
     */
    public static String formatList(List<String> list) {
        return KBase.formatList(list);
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
     * Rounds to the decimal place indicated in frac
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
     * @param value   The initial value
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

    /**
     * Gets if the number is in range (inclusive)
     *
     * @param num    The number it's checking
     * @param range1 The range num 1
     * @param range2 The range num 2
     * @return Whether the number is in the range (inclusive)
     */
    public static boolean isInRange(double num, double range1, double range2) {
        return isInRange(num, range1, range2, true);
    }

    /**
     * Gets if the number is in range (inclusive according to boolean of same name)
     *
     * @param num    The number it's checking
     * @param range1 The range num 1
     * @param range2 The range num 2
     * @param inclusive whether the numbers range1 and range2 are included in the check or not
     * @return Whether the number is in the range (inclusive according to boolean of same name)
     */
    public static boolean isInRange(double num, double range1, double range2, boolean inclusive) {
        return KBase.isInRange(num, range1, range2, inclusive);
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
        return KBase.getOrCreateObj(board, name, criteria);
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

    /**
     * Adds the suffixes like K and M and the end of numbers
     *
     * @param value The number to be formatted
     * @return Returns the number simplified with the suffixes (K, M, etc.)
     */
    public static String formatNumberSuffixes(long value) {
        return KBase.formatNumberSuffixes(value);
    }

    // Roman Numeral Stuff

    /**
     * Converts numbers to roman integers
     *
     * @param num The number being put in
     * @return The number as a roman numeral
     */
    public static String toRomanNumeral(int num) {
        return KBase.toRomanNumeral(num);
    }

    /**
     * Converts roman integer to numbers
     *
     * @param roman The roman integer
     * @return The number that the roman integer is equal to
     */
    public static int romanToInteger(String roman) {
        return KBase.romanToInteger(roman);
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
        return KBase.capitalize(str, delimiters);
    }

    public static List<String> wrapLine(String line, int wrapLength) {
        return KBase.wrapLine(line, wrapLength);
    }

    // Internal Stuff


    /**
     * Will print all the objects attached separated with a space as a new line
     *
     * @param variables The objects to print
     * @see #print
     */
    public static void println(Object... variables) {
        KBase.println(variables);
    }

    /**
     * For arrays, use printArray() instead. This function causes a warning
     * because the new print(Object...) and println(Object...) functions can't
     * be reliably bound by the compiler.
     *
     * @param what What will be printed
     */
    public static void println(Object what) {
        KBase.println(what);
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
        KBase.print(variables);
    }

    /**
     * Does what it says, prints an array (wither proper formatting)
     *
     * @param what What it will print
     */
    public static void printArray(Object what) {
        KBase.printArray(what);
    }

    /**
     * Makes a random number between 0 and every number less than param high
     * If you cast this to an int, it would be 0 through param high - 1
     *
     * @param high The highest the number can go (I'm pretty sure it would never hit this)
     * @return A random number between 0 and the param high
     */
    public static float random(float high) {
        return KBase.random(high);
    }

    // Item Stuff

    /**
     * Returns whether the item is valid or not
     *
     * @param item The item being checked
     * @return If the item is not null and not air
     */
    public static boolean isValidItem(ItemStack item) {
        return item != null && item.getType() != XMaterial.AIR.parseMaterial();
    }

    /**
     * Dyes the item put in the color put in
     *
     * @param item  The item that will be colored
     * @param color The color the item will be colored
     * @return Returns the item dyed
     */
    public static ItemStack colorToArmor(ItemStack item, Color color) {
        return KBase.colorToArmor(item, color);
    }

    /**
     * The item's amount will be set to the item selected
     *
     * @param item   Item
     * @param amount The amount the item will be set to
     * @return The item x the amount
     */
    public static ItemStack setStackAmount(ItemStack item, int amount) {
        return KBase.setStackAmount(item, amount);
    }

    // Sound Stuff

    /**
     * Compact version of Player object's playSound
     *
     * @param p     The player and the location (it will play at the player's location)
     * @param sound The sound (as an XSound for multiversion)
     * @param pitch The pitch the sound will be played at)
     * @see Player#playSound(Location, Sound, float, float)
     */
    public static void playSound(Player p, XSound sound, float pitch) {
        p.playSound(p.getLocation(), sound.parseSound(), 100, pitch);
    }

    // Reflection Stuff

    /**
     * Gets the NMS world, which is the reflection equivalent
     * of ((CraftWorld) world).getHandle()
     *
     * @param world The world of which it will be getting for NMS
     * @return Returns the Object (that is the NMS world) for reflection
     */
    public static Object getNMSWorld(World world) {
        return KBase.getNMSWorld(world);
    }

    public static Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        return getPrivateField(object.getClass(), object, field);
    }

    public static Object getPrivateField(Class<?> clazz, Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        return KBase.getPrivateField(clazz, object, field);
    }
}
