package DiamondMod.powers;

import DiamondMod.DiamondCore;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class StaggeredPower extends AbstractPower {
    public static final String POWER_ID = DiamondCore.makeID("Staggered");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractCreature source;

    public StaggeredPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.loadRegion("frail");
        this.source = source;
        canGoNegative = false;

    }

    @Override
    /*    */ public void atEndOfTurn(boolean isPlayer)
        /*    */ {

            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner,owner, this, 1));
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type)
        /*    */ {
        /* 80 */
        if (type == DamageInfo.DamageType.NORMAL)
            /*    */ {
                return (MathUtils.round(damage*1.6f));
        }
        return damage;
    }


    public float atDamageGive(float damage, DamageInfo.DamageType type)
  {
         if (type == DamageInfo.DamageType.NORMAL) {

             return damage * 0.6F;
       }
   return damage;
 }



    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }else if (this.amount >= 10){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new StunMonsterPower((AbstractMonster) this.owner, 1), 1));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, 10));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            if (owner != null && owner.isPlayer) {
                this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            }else{
                this.description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3];
            }
    }

}
