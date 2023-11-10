package koji.developerkit.reflection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class SerializedMethod extends MethodHandleAssistant implements Serializable {
    private transient MethodHandle handle;
    private Class<?> referenceClass, returnTypeClass;
    private Class<?>[] paramClasses;
    private boolean isStatic;
    private String name;

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            returnTypeClass = handle.type().returnType();
            paramClasses = handle.type().parameterArray();
            Class<?> clazz = Class.forName("java.lang.invoke.MemberName");
            Field field = getField(handle.getClass(), clazz, "member");
            MethodHandle isStaticMethod = getMethodHandle(
                    clazz, "isStatic", MethodType.methodType(boolean.class)
            );
            Field name = getField(clazz, String.class, "name");

            if(field == null)
                throw new RuntimeException("MemberName that should be there is null!");
            if(isStaticMethod == null)
                throw new RuntimeException("isStatic method that should be there is null!");
            if(name == null)
                throw new RuntimeException("Field \"name\" that should be there is null!");
            isStatic = (boolean) isStaticMethod.invoke(field.get(handle));
            this.name = (String) name.get(field.get(handle));

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        // default serialization
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();

        MethodType methodType = MethodType.methodType(returnTypeClass, paramClasses);
        handle = getMethodHandle(referenceClass, name, methodType, isStatic);
    }

    public MethodHandle getHandle() {
        return handle;
    }

    public void setHandle(MethodHandle handle) {
        this.handle = handle;
    }
}
