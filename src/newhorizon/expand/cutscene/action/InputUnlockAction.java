package newhorizon.expand.cutscene.action;

import newhorizon.expand.cutscene.components.Action;

import static mindustry.Vars.*;
import static newhorizon.NHVars.cutsceneUI;

public class InputUnlockAction extends Action {
    public InputUnlockAction() {
        super(0f);
    }

    @Override
    public void end() {
        if (headless) return;
        cutsceneUI.controlOverride = false;
    }

    @Override
    public void skip(){
        if (headless) return;
        end();
    }
}
