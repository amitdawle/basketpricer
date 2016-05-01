package com.ad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;


public class Basket {

    private List<Item> items ;

    public Basket() {
        items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(requireNonNull(item,"Item cannot be null"));
    }

    public Collection<Item> getItems(){
        return  Collections.unmodifiableCollection(items);
    }
}
