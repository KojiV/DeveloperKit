package koji.developerkit.reflection;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

public class SerializedField extends MethodHandleAssistant implements Serializable {
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

    private void writeObject(ObjectOutputStream oos) throws IOException {
        setSerializedVariables();
        // default serialization
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        // default deserialization
        ois.defaultReadObject();

        field = getFieldHandle(referenceClass, instanceClass, name);
    }
}
