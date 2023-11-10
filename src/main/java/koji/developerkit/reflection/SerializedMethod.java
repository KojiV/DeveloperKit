package koji.developerkit.reflection;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SerializedMethod extends MethodHandleAssistant implements Serializable {
    public SerializedMethod(Method method) {
        this(getMethod(method));
    }

    public SerializedMethod(MethodHandle handle) {
        this.handle = handle;

        setSerializedVariables();
    }

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

    @Getter @Setter private transient MethodHandle handle;
    @Getter private Class<?> referenceClass, returnTypeClass;
    @Getter private Class<?>[] paramClasses;
    @Getter private boolean isStatic;
    @Getter private String name;

    private void setSerializedVariables() {
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
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        setSerializedVariables();
        // default serialization
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();

        MethodType methodType = MethodType.methodType(returnTypeClass, paramClasses);
        handle = getMethodHandle(referenceClass, name, methodType, isStatic);
    }

    public Method getAsMethod() throws NoSuchMethodException {
        return referenceClass.getMethod(name, paramClasses);
    }
}
