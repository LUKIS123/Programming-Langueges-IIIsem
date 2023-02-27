package pl.edu.pwr.lgawron.lab05.flow;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab05.actorresources.Distributor;
import pl.edu.pwr.lgawron.lab05.actorresources.Feeder;
import pl.edu.pwr.lgawron.lab05.actors.*;
import pl.edu.pwr.lgawron.lab05.frameutility.render.LabelTextRenderer;

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
    private final Distributor distributor;
    private final List<Feeder> feederList;
    private final List<Assistant> assistantList;
    private final List<Organism> organismList;
    private final List<Label> assistantLabels;
    private final List<Label> feederLabels;
    private final List<Label> organismLabels;

    public ApplicationFlow(int organismNumber, int assistantNumber, int minSleepTime,
                           VBox distributorBox, VBox labTechniciansBox, VBox nourishmentBox, VBox staminaBox) {

        // input
        this.organismNumber = organismNumber;
        this.assistantNumber = assistantNumber;
        this.minSleepTime = minSleepTime;

        // boxes
        this.distributorBox = distributorBox;
        this.labTechniciansBox = labTechniciansBox;
        this.nourishmentBox = nourishmentBox;
        this.staminaBox = staminaBox;

        // labels & list of labels
        Label distributorLabel = new Label();
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

        // resources
        this.distributor = new Distributor(minSleepTime, distributorLabel);
        this.feederList = new ArrayList<>();

        // actors
        this.assistantList = new ArrayList<>();
        this.organismList = new ArrayList<>();
    }

    public void initialize() {
        for (int i = 0; i < organismNumber; i++) {
            if (i < assistantNumber) {
                assistantList.add(i, new Assistant(i, i, minSleepTime, distributor, assistantLabels, assistantList, feederList, organismList));
            } else {
                assistantList.add(i, null);
            }

            feederList.add(i, new Feeder(minSleepTime, feederLabels.get(i), i));
            organismList.add(i, new Organism(feederList.get(i), minSleepTime, i, organismLabels.get(i)));
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

    public void endSimulation() {
        for (Assistant assistant : assistantList) {
            if (assistant != null) {
                assistant.setExit(true);
            }
        }

        organismList.forEach(organism -> organism.setExit(false));
    }

    public void clearLabels() {
        distributorBox.getChildren().clear();
        labTechniciansBox.getChildren().clear();
        nourishmentBox.getChildren().clear();
        staminaBox.getChildren().clear();
    }

    private void completeEmptyAssistantSpaces() {
        for (Label label : assistantLabels) {
            if (label.getText().isEmpty()) {
                LabelTextRenderer.renderEmptyAssistantsSpaces(label);
            }
        }
    }
}
