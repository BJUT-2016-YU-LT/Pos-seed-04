package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Report{
    private List<ItemGroup> itemGroupies;

    public Report(List<ItemGroup> itemGroupies){
        this.itemGroupies = itemGroupies;
    }

    public List<ItemGroup> getItemGroupies() {
        return itemGroupies;
    }

    public double getTotal(){
        double result = 0.00;
        for (ItemGroup itemGroup : itemGroupies)
            result += itemGroup.subTotal();
        return result;
    }

    public double getSaving(){
        double result = 0.00;
        for (ItemGroup itemGroup : itemGroupies)
            result += itemGroup.saving();
        return result;
    }
}
