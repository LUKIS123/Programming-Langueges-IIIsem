package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.Jug;

import java.util.List;
import java.util.Optional;

public class JugRepository {
    private final List<Jug> jugList;

    public JugRepository(List<Jug> jugList) {
        this.jugList = jugList;
    }

    public List<Jug> getJugList() {
        return jugList;
    }

    public int getAllJugsVolume() {
        return jugList.stream().mapToInt(Jug::getVolume).sum();
    }

    public List<Jug> getByFlavourId(int flavourId) {
        return jugList.stream().filter(jugs -> jugs.getFlavourId() == flavourId).toList();
    }

    public Optional<Jug> getById(int id) {
        return jugList.stream().filter(jugs -> jugs.getId() == id).findFirst();
    }
}
