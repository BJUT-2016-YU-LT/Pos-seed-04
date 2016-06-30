package com.thoughtworks.pos.domains;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ShoppingChart {
    private ArrayList<Item> items = new ArrayList<Item>();

    public void add(Item item) {
        this.items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
