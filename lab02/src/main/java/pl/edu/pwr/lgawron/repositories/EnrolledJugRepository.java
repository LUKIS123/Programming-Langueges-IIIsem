package pl.edu.pwr.lgawron.repositories;

import pl.edu.pwr.lgawron.models.EnrolledJug;

import java.util.ArrayList;
import java.util.List;

public class EnrolledJugRepository {
    private List<EnrolledJug> enrolledJugs = new ArrayList<>();

    public EnrolledJugRepository(List<EnrolledJug> enrolledJugs) {
        this.enrolledJugs = enrolledJugs;
    }

    public List<EnrolledJug> getEnrolledJugs() {
        return enrolledJugs;
    }

    public void addEnrolledJug(){

    }
}
