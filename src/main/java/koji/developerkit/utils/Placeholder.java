package koji.developerkit.utils;

import koji.developerkit.KBase;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class Placeholder extends KBase implements Serializable {
    @Getter @Setter private String placeholder;
    @Getter @Setter private List<String> replaced;

    public Placeholder(String placeholder, List<String> replaced) {
        this.placeholder = placeholder;
        this.replaced = replaced;
    }

    public Placeholder(String placeholder, String replaced) {
        this(placeholder, arrayList(replaced));
    }

    public String getReplacedSimple() {
        return getOrDefault(replaced, 0, null);
    }
}
