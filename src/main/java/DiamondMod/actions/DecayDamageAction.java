/*    */
package DiamondMod.actions;

import CorruptedMod.powers.BlackArmorPower;
import CorruptedMod.powers.DecayResist;
import CorruptedMod.powers.EvilCloudPower;
import CorruptedMod.powers.InfernalFormPower;
import DiamondMod.DiamondCore;
import DiamondMod.powers.DecayPower;
import DiamondMod.powers.ManaBlightPower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/*    */

/*    */
/*    */ public class DecayDamageAction extends AbstractGameAction
        /*    */ {
    int apply = 0;
    int remove = 0;
    DamageInfo info;
    DamageInfo NEWinfo;
    boolean isPlayer = false;
    boolean fromDecay = true;

    /*    */
    /*    */
    public DecayDamageAction(boolean fromDecay, AbstractCreature target, AbstractCreature source, int amount, DamageInfo info)
    /*    */ {
        /* 26 */
        this.actionType = ActionType.DAMAGE;
        /* 27 */
        this.duration = 0.2f;
        this.target = target;
        this.source = source;
        this.apply = amount;
        this.NEWinfo = this.info = info;
        if (this.target.isPlayer) {
            this.isPlayer = true;
        }
    }


    public void update()
        /*    */ {
        System.out.println("starting action");

        if (TashaCheck() && (NEWinfo.output > 0)) {


            if (hasInfernal()) {
                NEWinfo.output = (info.output / 2);
            }
            if (hasResist()){
                if(target.hasPower(DecayResist.POWER_ID)) {
                    System.out.println("checking if " + target + " has menacing.");

                    if (target.getPower(DecayResist.POWER_ID) instanceof TwoAmountPower) {
                        System.out.println("adding " + this.apply + "to " + target + "'s Menacing counter.");

                        target.getPower(DecayResist.POWER_ID).flash();
                      NEWinfo.output -= ((TwoAmountPower) target.getPower(DecayResist.POWER_ID)).amount2;
                    }
                }
            }
            if (hasBlack()) {
                AbstractDungeon.actionManager
                        .addToBottom(new GainBlockAction(this.target, this.target, NEWinfo.output, isPlayer));

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.target,
                        new BlackArmorPower(this.target, this.target, -1), -1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, NEWinfo, AttackEffect.POISON));
            }

        }

        isDone = true;
        /*    */
    }

    public boolean TashaCheck() {
        return DiamondCore.hasTasha;
    }

    public boolean hasBlack() {
        return target.hasPower(BlackArmorPower.POWER_ID) && (target.getPower(BlackArmorPower.POWER_ID).amount > 0);
    }

    public boolean hasInfernal() {
        return target.hasPower(InfernalFormPower.POWER_ID);
    }

    public boolean hasResist(){
            if (TashaCheck()) {
                return target.hasPower(DecayResist.POWER_ID);
            }else{
                return false;
            }
    }

    public Color getColor() {
        return Color.PURPLE;
    }
}