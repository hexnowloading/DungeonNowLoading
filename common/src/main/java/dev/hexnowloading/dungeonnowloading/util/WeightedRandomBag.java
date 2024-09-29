package dev.hexnowloading.dungeonnowloading.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandomBag<T extends Object> {
    private class Entry {
        double accumulatedWeight;
        T object;
    }

    private List<Entry> entries = new ArrayList<>();
    private double accumulatedWeight;
    private Random rand = new Random();

    public void addEntry(T object, double weight) {
        accumulatedWeight += weight;
        Entry e = new Entry();
        e.object = object;
        e.accumulatedWeight = accumulatedWeight;
        entries.add(e);
    }

    public void removeEntry(T object) {
        entries.remove(object);
    }

    public T getRandom() {
        double r = rand.nextDouble() * accumulatedWeight;

        for (Entry entry: entries) {
            if (entry.accumulatedWeight >= r) {
                return entry.object;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
