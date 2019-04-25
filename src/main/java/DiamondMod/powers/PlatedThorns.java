package DiamondMod.powers;

import DiamondMod.DiamondCore;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class PlatedThorns extends AbstractPower {
    public static final String POWER_ID = DiamondCore.makeID("PlatedThorns");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractCreature source;
    private DamageInfo thornsInfo;

    public PlatedThorns(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.loadRegion("thorns");
        this.source = source;
        canGoNegative = false;

    }


    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));

                 this.fontScale = 8.0F;
                 this.amount += stackAmount;
                 this.thornsInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
                 updateDescription();

        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner)) {
            flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
        }

        if ((info.owner != null) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.type != DamageInfo.DamageType.THORNS) && (damageAmount > 0) &&
                (!this.owner.hasPower("Buffer"))) {
            flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

        return damageAmount;
    }

}