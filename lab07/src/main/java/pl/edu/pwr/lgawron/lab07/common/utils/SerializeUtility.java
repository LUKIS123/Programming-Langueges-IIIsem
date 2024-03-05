package pl.edu.pwr.lgawron.lab07.common.utils;

import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ItemTypeExtended;

import java.util.ArrayList;
import java.util.List;

public class SerializeUtility {
    public static String serializeItems(List<ItemTypeExtended> listToSerialize) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ItemTypeExtended itemType : listToSerialize) {
            stringBuilder
                    .append(itemType.getId())
                    .append(",")
                    .append(itemType.getName())
                    .append(",")
                    .append(itemType.getPrice())
                    .append(",")
                    .append(itemType.getCategory()).append(";");
        }
        return stringBuilder.toString();
    }

    public static List<ItemTypeExtended> deserializeItems(String stringToDeserialize) {
        List<ItemTypeExtended> itemList = new ArrayList<>();
        String[] items = stringToDeserialize.split(";");
        for (String item : items) {
            String[] split = item.split(",");
            ItemTypeExtended i = new ItemTypeExtended(
                    Integer.parseInt(split[0]),
                    split[1],
                    Float.parseFloat(split[2]),
                    Integer.parseInt(split[3])
            );
            itemList.add(i);
        }
        return itemList;
    }

}
