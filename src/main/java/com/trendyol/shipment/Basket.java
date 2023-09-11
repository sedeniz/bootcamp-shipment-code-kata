package com.trendyol.shipment;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {

        if (products.isEmpty())
        {
            throw new RuntimeException("The basket is empty!");
        }

        Map<ShipmentSize, Integer> numOfEachSizeInBasketMap = new HashMap<>();

        for (Product product : products) {
            // Increase the number of that size if there is recurrence
            // If the size is not in map yet, its defaultValue is 0
            numOfEachSizeInBasketMap.put(product.getSize(), numOfEachSizeInBasketMap.getOrDefault(product.getSize(), 0) +1);
        }

        if (products.size() < 3) {
            // If num of products is less than 3, return the max size among them
            return getMaxShipmentSize(numOfEachSizeInBasketMap);
        }

        for (ShipmentSize size : numOfEachSizeInBasketMap.keySet()) {
            Integer count = numOfEachSizeInBasketMap.get(size);
            if(count >= 3) {
                // If there are 3 of the same size, upgrade to a larger one than that size
                return upgradeShipmentSize(size);
            }
        }

        // If num of products > 3 & there are no three of the same size, return the max size among them
        return getMaxShipmentSize(numOfEachSizeInBasketMap);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private ShipmentSize getMaxShipmentSize(Map<ShipmentSize, Integer> numOfEachSizeInBasketMap) {
        if (numOfEachSizeInBasketMap.containsKey(ShipmentSize.X_LARGE)) return ShipmentSize.X_LARGE;
        if (numOfEachSizeInBasketMap.containsKey(ShipmentSize.LARGE)) return ShipmentSize.LARGE;
        if (numOfEachSizeInBasketMap.containsKey(ShipmentSize.MEDIUM)) return ShipmentSize.MEDIUM;
        return ShipmentSize.SMALL;
    }

    private ShipmentSize upgradeShipmentSize(ShipmentSize currentSize) {
        return switch (currentSize) {
            case SMALL -> ShipmentSize.MEDIUM;
            case MEDIUM -> ShipmentSize.LARGE;
            case LARGE, X_LARGE -> ShipmentSize.X_LARGE;
        };
    }
}
