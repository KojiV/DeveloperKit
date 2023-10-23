package koji.developerkit.utils;

import koji.developerkit.KBase;

import java.util.List;

public class OrderedReplacements extends KBase {
    String placeholder;
    List<String> replaced;

    public OrderedReplacements(String placeholder, List<String> replaced) {
        this.placeholder = placeholder;
        this.replaced = replaced;
    }

    public OrderedReplacements(String placeholder, String replaced) {
        this(placeholder, arrayList(replaced));
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public List<String> getReplaced() {
        return replaced;
    }
}
