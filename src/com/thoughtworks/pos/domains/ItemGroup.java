package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ItemGroup {
    private List<Item> items;

    public ItemGroup(List<Item> items) {
        this.items = items;
    }

    public String groupName() {
        return items.get(0).getName();
    }

    public int groupSize() {
        return items.size();
    }

    public String groupUnit() {
        return items.get(0).getUnit();
    }

    public double groupPrice() {
        return items.get(0).getPrice();
    }

    public boolean GroupIsPromotion(){return items.get(0).getIsPromotion();}

    public double subTotal() {
        double result = 0.00;
        for (Item item : items) {
            if(Pos.VIPactive)
                result += item.getPrice() * item.getDiscount()*item.getVipDiscount();
            else
                result += item.getPrice() * item.getDiscount();
        }
        if(GroupIsPromotion()&&groupSize()>=2)
            result-=groupPrice();
        return result;
    }

    public double saving() {
        double result = 0.00;
        for (Item item : items) {
            if(Pos.VIPactive)
      result+=item.getPrice()*(1-item.getDiscount()*item.getVipDiscount());
            else
                result+=item.getPrice()*(1-item.getDiscount());
        }
        if(GroupIsPromotion()&&groupSize()>=2)
            result+=groupPrice();
        return result;
    }
}
