package koji.developerkit.utils;

import koji.developerkit.KBase;

import java.io.Serializable;
import java.util.List;

public class Placeholder extends KBase implements Serializable {
    private final String placeholder;
    private final List<String> replaced;

    public Placeholder(String placeholder, List<String> replaced) {
        this.placeholder = placeholder;
        this.replaced = replaced;
    }

    public Placeholder(String placeholder, String replaced) {
        this(placeholder, arrayList(replaced));
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public List<String> getReplaced() {
        return replaced;
    }

    public String getReplacedSimple() {
        return getOrDefault(replaced, 0, null);
    }
}
