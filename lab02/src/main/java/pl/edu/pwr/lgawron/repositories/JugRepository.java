package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.Jug;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JugRepository {
    private final List<Jug> jugList;

    public JugRepository(List<Jug> jugList) {
        this.jugList = jugList;
    }

    public List<Jug> getJugList() {
        return jugList;
    }

    public Optional<Jug> getByFlavourId(int flavourId) {
        List<Jug> byFlavour = jugList.stream().filter(jugs -> jugs.getFlavourId() == flavourId).collect(Collectors.toList());
        return byFlavour.stream().filter(jug -> jug.getVolume() != 0).findFirst();
    }

    public List<Jug> getByFlavour(int flavourId) {
        return jugList.stream().filter(jugs -> jugs.getFlavourId() == flavourId).collect(Collectors.toList());
    }
}
