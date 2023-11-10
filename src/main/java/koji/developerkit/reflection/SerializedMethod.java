package koji.developerkit.reflection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class SerializedMethod extends MethodHandleAssistant implements Serializable {
    private static final Field MEMBER, NAME;
    private static final MethodHandle IS_STATIC, GET_DECLARING;

    static {
        try {
            Class<?> memberName = Class.forName("java.lang.invoke.MemberName");
            Class<?> methodHandle = Class.forName("java.lang.invoke.DirectMethodHandle");

            MEMBER = getField(methodHandle, memberName, "member");
            IS_STATIC = getMethodHandle(
                    memberName, "isStatic", MethodType.methodType(boolean.class)
            );
            GET_DECLARING = getMethodHandle(
                    memberName, "getDeclaringClass", MethodType.methodType(Class.class)
            );
            NAME = getField(memberName, String.class, "name");

            if(MEMBER == null)
                throw new RuntimeException("MemberName that should be there is null!");
            if(IS_STATIC == null)
                throw new RuntimeException("isStatic method that should be there is null!");
            if (GET_DECLARING == null)
                throw new RuntimeException("getDeclaringClass method that should be there is null!");
            if(NAME == null)
                throw new RuntimeException("Field \"name\" that should be there is null!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private transient MethodHandle handle;
    private Class<?> referenceClass, returnTypeClass;
    private Class<?>[] paramClasses;
    private boolean isStatic;
    private String name;

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            Object memberName = MEMBER.get(handle);
            returnTypeClass = handle.type().returnType();
            paramClasses = handle.type().parameterArray();
            referenceClass = (Class<?>) GET_DECLARING.invoke(memberName);

            isStatic = (boolean) IS_STATIC.invoke(memberName);
            this.name = (String) NAME.get(memberName);

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
