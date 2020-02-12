package model;

import java.util.function.Predicate;

@FunctionalInterface
public interface TreeItemPredicate<T> {
 
    boolean test(FileTreeItem parent, T value);
 
    static <T> TreeItemPredicate<T> create(Predicate<T> predicate) {
        return (parent, value) -> predicate.test(value);
    }
 
}