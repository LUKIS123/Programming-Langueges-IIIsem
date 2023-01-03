package pl.edu.pwr.lgawron.lab07.shop.repositories;

import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemTypeRepository implements IRepository<ItemTypeExtended> {
    private final List<ItemTypeExtended> itemTypeList;

    public ItemTypeRepository() {
        this.itemTypeList = new ArrayList<>();
    }

    @Override
    public List<ItemTypeExtended> getRepo() {
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
        // id 0 -> an item does not exist
        return first.orElseGet(() -> new ItemTypeExtended(0));
    }

}
