package pl.edu.pwr.lgawron.lab07.shop.repositories;

import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ItemTypeExtended;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemTypeRepository implements IRepository<ItemTypeExtended> {
    private final List<ItemTypeExtended> itemTypeList;

    public ItemTypeRepository(List<ItemTypeExtended> loadedItems) {
        this.itemTypeList = new ArrayList<>();
        itemTypeList.addAll(loadedItems);
    }

    @Override
    public List<ItemTypeExtended> getRepositoryData() {
        return itemTypeList;
    }

    @Override
    public void addInstance(ItemTypeExtended itemTypeExtended) {
        itemTypeList.add(itemTypeExtended);
    }

    @Override
    public ItemTypeExtended getById(int id) {
        Optional<ItemTypeExtended> first = itemTypeList
                .stream()
                .filter(itemTypeExtended -> itemTypeExtended.getId() == id)
                .findFirst();
        // id = -1 -> an item does not exist
        return first.orElseGet(() -> new ItemTypeExtended(-1));
    }

}
