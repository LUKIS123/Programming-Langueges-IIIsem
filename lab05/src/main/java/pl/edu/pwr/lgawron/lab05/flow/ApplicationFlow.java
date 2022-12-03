package pl.edu.pwr.lgawron.lab05.flow;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab05.actors.Assistant;
import pl.edu.pwr.lgawron.lab05.actors.Distributor;
import pl.edu.pwr.lgawron.lab05.actors.Feeder;
import pl.edu.pwr.lgawron.lab05.actors.Organism;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFlow {
    private final int organismNumber;
    private final int assistantNumber;
    private final int minSleepTime;
    private final VBox distributorBox;
    private final VBox labTechniciansBox;
    private final VBox nourishmentBox;
    private final VBox staminaBox;
    private Distributor distributor;
    private List<Assistant> assistants;
    private List<Feeder> feeders;
    private List<Organism> organisms;
    private Label distributorLabel;
    private List<Label> assistantLabels;
    private List<Label> feederLabels;
    private List<Label> organismLabels;

    public ApplicationFlow(int organismNumber, int assistantNumber, int minSleepTime,
                           VBox distributorBox, VBox labTechniciansBox, VBox nourishmentBox, VBox staminaBox) {

        this.organismNumber = organismNumber;
        this.assistantNumber = assistantNumber;
        this.minSleepTime = minSleepTime;
        this.distributorBox = distributorBox;
        this.labTechniciansBox = labTechniciansBox;
        this.nourishmentBox = nourishmentBox;
        this.staminaBox = staminaBox;

        // labels
        this.distributorLabel = new Label();
        distributorBox.getChildren().add(distributorLabel);

        this.assistantLabels = new ArrayList<>();
        this.fillLabelList(assistantLabels);
        this.addLabelsToBox(labTechniciansBox, assistantLabels);

        this.feederLabels = new ArrayList<>();
        this.fillLabelList(feederLabels);
        this.addLabelsToBox(nourishmentBox, feederLabels);

        this.organismLabels = new ArrayList<>();
        this.fillLabelList(organismLabels);
        this.addLabelsToBox(staminaBox, organismLabels);

        //this.grid = new RunnableActor[organismNumber][3];
        this.distributor = new Distributor(0, minSleepTime, distributorLabel);
        this.assistants = new ArrayList<>();
        this.feeders = new ArrayList<>();
        this.organisms = new ArrayList<>();
    }

    public void init() {
        for (int i = 0; i < organismNumber; i++) {
            if (i < assistantNumber) {
                assistants.add(i, new Assistant(i, i, minSleepTime, this, distributor));
            } else {
                assistants.add(i, null);
            }

            feeders.add(i, new Feeder(minSleepTime, i, feederLabels.get(i)));
            organisms.add(i, new Organism(feeders.get(i), minSleepTime, i, organismLabels.get(i)));
        }
        this.completeEmptyAssistantSpaces();
    }

    private void fillLabelList(List<Label> labels) {
        for (int i = 0; i < organismNumber; i++) {
            labels.add(i, new Label());
        }
    }

    private void addLabelsToBox(VBox vBox, List<Label> labels) {
        labels.forEach(label -> vBox.getChildren().add(label));
    }

    private void completeEmptyAssistantSpaces() {
        for (Label label : assistantLabels) {
            if (label.getText().isEmpty()) {
                label.setText("|_______|");
            }
        }
    }

    // getters
    public Distributor getDistributor() {
        return distributor;
    }

    public List<Assistant> getAssistants() {
        return assistants;
    }

    public List<Feeder> getFeeders() {
        return feeders;
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public Label getDistributorLabel() {
        return distributorLabel;
    }

    public List<Label> getAssistantLabels() {
        return assistantLabels;
    }

    public List<Label> getFeederLabels() {
        return feederLabels;
    }

    public List<Label> getOrganismLabels() {
        return organismLabels;
    }
}
