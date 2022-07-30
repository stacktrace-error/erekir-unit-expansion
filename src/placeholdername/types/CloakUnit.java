package placeholdername.types;

import mindustry.game.Team;
import mindustry.gen.PayloadUnit;
import placeholdername.content.EUContent;

public class CloakUnit extends PayloadUnit{
    @Override
    public String toString() {
        return "CloakUnit#" + id;
    }

    public CloakUnit(){}

    @Override
    public boolean targetable(Team targeter) {
        if(this.hasEffect(EUContent.cloaked)){
            return false;
        } else {
            return super.targetable(targeter);
        }
    }
}
