package com.yaroslavyankov.frontend.util;

import com.yaroslavyankov.frontend.dto.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Сущность типа заявки")
public enum OrderTypeUtil {
    ORDER_TYPE_LIMIT("Лимитная"),
    ORDER_TYPE_MARKET("Рыночная"),
    ORDER_TYPE_BESTPRICE("Лучшая цена");

    private final String russianName;

    OrderTypeUtil(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianValue() {
        return russianName;
    }

    public static List<String> getRussianValues() {
        var list = new ArrayList<String>();

        for (var i : OrderTypeUtil.values()) {
            list.add(i.russianName);
        }

        return list;
    }

    public static OrderTypeUtil getValue(String russianName) {
        for (var i : OrderTypeUtil.values()) {
            if (i.russianName.equalsIgnoreCase(russianName)) {
                return i;
            }
        }

        return ORDER_TYPE_LIMIT;
    }

//    public static OrderType getOrderTypeValue(OrderType orderType, String russianName) {
//        for (var i : orderType) {
//
//        }
//
//        return ORDER_TYPE_LIMIT;
//    }
}
