package koji.developerkit.utils;

import koji.developerkit.KBase;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MethodHandleAssistant extends KBase implements Serializable {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    protected static Field getField(Class<?> refc, Class<?> instc, String name, String... extraNames) {
        Field handle = getFieldHandle(refc, instc, name);
        if (handle != null) return handle;

        if (extraNames != null && extraNames.length > 0) {
            if (extraNames.length == 1) return getField(refc, instc, extraNames[0]);
            return getField(refc, instc, extraNames[0], removeFirst(extraNames));
        }

        return null;
    }

    private static String[] removeFirst(String[] array) {
        int length = array.length;

        String[] result = new String[length - 1];
        System.arraycopy(array, 1, result, 0, length - 1);

        return result;
    }

    protected static Field getFieldHandle(Class<?> refc, Class<?> inscofc, String name) {
        Set<Field> fields = new HashSet<>(Arrays.asList(refc.getFields()));
        fields.addAll(new HashSet<>(Arrays.asList(refc.getDeclaredFields())));

        for (Field field : fields) {
            field.setAccessible(true);

            if (!field.getName().equals(name)) continue;

            if (field.getType().isInstance(inscofc) || field.getType().isAssignableFrom(inscofc)) {
                return field;
            }
        }
        return null;
    }

    protected static MethodHandle getConstructor(Class<?> refc, Class<?>... types) {
        try {
            Constructor<?> constructor = refc.getDeclaredConstructor(types);
            constructor.setAccessible(true);
            return LOOKUP.unreflectConstructor(constructor);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    protected static MethodHandle getMethod(Class<?> refc, MethodType type, String name, String... extraNames) {
        return getMethod(refc, type, false, name, extraNames);
    }

    protected static MethodHandle getMethod(Class<?> refc, MethodType type, boolean isStatic, String name, String... extraNames) {
        MethodHandle handle = getMethodHandle(refc, name, type, isStatic);
        if(handle != null) return handle;

        for(String names : extraNames) {
            handle = getMethodHandle(refc, names, type, isStatic);
            if(handle != null) return handle;
        }

        return null;
    }

    protected static MethodHandle getMethodHandle(Class<?> refc, String name, MethodType type) {
        return getMethodHandle(refc, name, type, false);
    }

    protected static MethodHandle getMethodHandle(Class<?> refc, String name, MethodType type, boolean isStatic) {
        try {
            if (isStatic) return LOOKUP.findStatic(refc, name, type);
            return LOOKUP.findVirtual(refc, name, type);
        } catch (NoSuchMethodException | IllegalAccessException exception) {
            //exception.printStackTrace();
            return null;
        }
    }
}
