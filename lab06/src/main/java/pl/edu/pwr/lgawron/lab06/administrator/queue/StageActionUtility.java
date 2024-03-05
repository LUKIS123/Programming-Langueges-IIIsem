package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;

public interface StageActionUtility {
    int executeAction(PlayerRequest playerRequest);

    void tryToSleep(int milliSeconds);
}
