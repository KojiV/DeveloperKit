package koji.developerkit.reflection;

import lombok.Getter;
import lombok.Setter;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.nustaq.serialization.FSTObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Field;

public class SerializedField extends MethodHandleAssistant {
    private static final long serialVersionUID = -23945807347567L;

    public SerializedField(Field field) {
        this.field = field;

        setSerializedVariables();
    }

    @Getter @Setter private transient Field field;
    @Getter private Class<?> referenceClass, instanceClass;
    @Getter private String name;

    private void setSerializedVariables() {
        try {
            name = field.getName();
            referenceClass = field.getDeclaringClass();
            instanceClass = field.getType();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /*private void writeObject(ObjectOutputStream oos) throws IOException {
        setSerializedVariables();
        // default serialization
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();

        field = getFieldHandle(referenceClass, instanceClass, name);
    }*/

    public static class FSTFieldSerializer implements FSTObjectSerializer {
        @Override
        public void writeObject(FSTObjectOutput out, Object toWrite, FSTClazzInfo clzInfo, FSTClazzInfo.FSTFieldInfo referencedBy, int streamPosition) throws IOException {
            if(toWrite instanceof SerializedField) {
                SerializedField field = (SerializedField) toWrite;

                field.setSerializedVariables();
                out.writeClassTag(field.referenceClass);
                out.writeClassTag(field.instanceClass);
                out.writeStringUTF(field.name);
            }
        }

        @Override
        public void readObject(FSTObjectInput in, Object toRead, FSTClazzInfo clzInfo, FSTClazzInfo.FSTFieldInfo referencedBy) throws Exception {
            if(toRead instanceof SerializedField) {
                SerializedField field = (SerializedField) toRead;

                field.referenceClass = in.readClass().getClazz();
                field.instanceClass = in.readClass().getClazz();
                field.name = in.readStringUTF();

                field.field = getFieldHandle(field.referenceClass, field.instanceClass, field.name);
            }
        }

        @Override
        public boolean willHandleClass(Class cl) {
            return true;
        }

        @Override
        public boolean alwaysCopy() {
            return false;
        }

        @Override
        public Object instantiate(Class objectClass, FSTObjectInput fstObjectInput, FSTClazzInfo serializationInfo, FSTClazzInfo.FSTFieldInfo referencee, int streamPosition) throws Exception {
            return null;
        }

    }


}
