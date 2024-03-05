package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.Person;

import java.util.List;

public class PersonRepository {
    private final List<Person> personList;

    public PersonRepository(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

}
