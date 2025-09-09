/**
 *
 * @author Arda AKDUYGU,İlker Cihan DURMUŞ
 *
 *
 */
package spellchecker;

public class DictHashTable<Value> {

    private Object[] table;
    private int size;

    public DictHashTable() {
        this(1000);
    }

    public DictHashTable(int capacity) {
        table = new Object[capacity];
        size = 0;
    }

    public void addValue(Value value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        if (size >= table.length) {
            resize();
        }

        int index = hash(value);
        while (table[index] != null) {
            index = (index + 1) % table.length;
        }

        table[index] = value;
        size++;
    }

    public boolean containsValue(Value value) {
        int index = findIndex(value);
        return index != -1;
    }

    public void delete(Value value) {
        int index = findIndex(value);
        if (index != -1) {
            table[index] = null;
            size--;
            resize();
        }
    }

    private int findIndex(Value value) {
        if (value == null) {
            return -1;
        }

        int index = hash(value);
        while (table[index] != null) {
            if (table[index].equals(value)) {
                return index;
            }
            index = (index + 1) % table.length;
        }

        return -1;
    }

    private int hash(Value value) {
        return (value.hashCode() & 0x7fffffff) % table.length;
    }

    private void resize() {
        Object[] oldTable = table;
        table = new Object[table.length * 2];
        size = 0;

        for (Object v : oldTable) {
            if (v != null) {
                addValue((Value) v);
            }

        }

    }
}
