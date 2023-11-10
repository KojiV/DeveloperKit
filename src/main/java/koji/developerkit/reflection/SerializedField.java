package koji.developerkit.reflection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

public class SerializedField extends MethodHandleAssistant implements Serializable {
    private transient Field field;
    private Class<?> referenceClass, instanceClass;
    private String name;

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            name = field.getName();
            referenceClass = field.getDeclaringClass();
            instanceClass = field.getType();

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        // default serialization
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();

        field = getFieldHandle(referenceClass, instanceClass, name);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
