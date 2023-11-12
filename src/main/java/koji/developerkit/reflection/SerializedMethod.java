package koji.developerkit.reflection;

import lombok.Getter;
import lombok.Setter;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class SerializedMethod extends MethodHandleAssistant {
    private static final long serialVersionUID = -987876512335L;

    public SerializedMethod(Method method) {
        this(getMethod(method));
    }

    public SerializedMethod(MethodHandle handle) {
        this.handle = handle;

        setSerializedVariables();
    }

    private static final Field MEMBER, NAME;

    static {
        try {
            Class<?> memberName = Class.forName("java.lang.invoke.MemberName");
            Class<?> methodHandle = Class.forName("java.lang.invoke.DirectMethodHandle");

            MEMBER = getField(methodHandle, memberName, "member");
            NAME = getField(memberName, String.class, "name");

            if(MEMBER == null)
                throw new RuntimeException("MemberName that should be there is null!");
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

            Class<?>[] classes = handle.type().parameterArray();
            paramClasses = Arrays.copyOfRange(classes, 1, classes.length);
            referenceClass = MethodHandles.lookup().revealDirect(handle).getDeclaringClass();

            isStatic = Modifier.isStatic(MethodHandles.lookup().revealDirect(handle).getModifiers());
            this.name = (String) NAME.get(memberName);

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Method getAsMethod() throws NoSuchMethodException {
        return referenceClass.getMethod(name, paramClasses);
    }

    /*private void writeObject(ObjectOutputStream oos) throws IOException {
        setSerializedVariables();
        // default serialization
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();

        MethodType methodType = MethodType.methodType(returnTypeClass, paramClasses);
        handle = getMethodHandle(referenceClass, name, methodType, isStatic);
    }*/

    public static class FSTMethodSerializer extends FSTBasicObjectSerializer {
        @Override
        public void writeObject(FSTObjectOutput out, Object toWrite, FSTClazzInfo clzInfo, FSTClazzInfo.FSTFieldInfo referencedBy, int streamPosition) throws IOException {
            if (toWrite instanceof SerializedMethod) {
                SerializedMethod method = (SerializedMethod) toWrite;
                method.setSerializedVariables();

                out.writeObject(method.referenceClass, Class.class);
                out.writeObject(method.returnTypeClass, Class.class);
                out.writeObject(method.paramClasses);
                out.writeBoolean(method.isStatic);
                out.writeStringUTF(method.name);
            }

        }

        @Override
        public void readObject(FSTObjectInput in, Object toRead, FSTClazzInfo clzInfo, FSTClazzInfo.FSTFieldInfo referencedBy) throws Exception {
            if (toRead instanceof SerializedMethod) {
                SerializedMethod method = (SerializedMethod) toRead;

                method.referenceClass = (Class<?>) in.readObject(Class.class);
                method.returnTypeClass = (Class<?>) in.readObject(Class.class);
                method.paramClasses = (Class<?>[]) in.readObject(Class[].class);
                method.isStatic = in.readBoolean();
                method.name = in.readStringUTF();

                MethodType methodType = MethodType.methodType(method.returnTypeClass, method.paramClasses);
                method.handle = getMethodHandle(method.referenceClass, method.name, methodType, method.isStatic);
            }
        }
    }
}
