package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.Person;

import java.util.List;
import java.util.Optional;

public class PersonRepository {
    private final List<Person> personList;

    public PersonRepository(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public Optional<Person> getById(int id) {
        return personList.stream().filter(person -> person.getId() == id).findFirst();
    }
}
