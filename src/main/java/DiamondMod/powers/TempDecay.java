/*    */
package DiamondMod.powers;

import CorruptedMod.powers.BlackArmorPower;
import CorruptedMod.powers.EvilCloudPower;
import DiamondMod.DiamondCore;
import DiamondMod.actions.DecayDamageAction;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */

/*    */ public class TempDecay extends AbstractPower implements HealthBarRenderPower
        /*    */ {
    public static final String POWER_ID = DiamondCore.makeID("TempDecay");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    /*    */
    /*    */
    public TempDecay(AbstractCreature owner, AbstractCreature source, int DecayAmt)
    /*    */ {
        /* 23 */
        this.name = NAME;
        /* 25 */
        this.owner = owner;
        /* 27 */
        this.amount = DecayAmt;
        canGoNegative = false;
        this.ID = POWER_ID;
        this.updateDescription();
        /* 34 */
        this.loadRegion("poison");
        /* 35 */
        this.type = PowerType.DEBUFF;
        /*    */
        /* 37 */
        this.isTurnBased = true;
        this.priority = 10;
        /*    */
    }

    @Override
    public int getHealthBarAmount() {
        int HpLoss = this.amount - this.owner.currentBlock;

        if (this.owner.hasPower(MetallicizePower.POWER_ID)
                && this.owner.getPower(MetallicizePower.POWER_ID).amount > 0) {
            HpLoss -= this.owner.getPower(MetallicizePower.POWER_ID).amount;
        }
        if (this.owner.hasPower(PlatedArmorPower.POWER_ID)
                && this.owner.getPower(PlatedArmorPower.POWER_ID).amount > 0) {
            HpLoss -= this.owner.getPower(PlatedArmorPower.POWER_ID).amount;
        }
        if (DiamondCore.hasTasha) {
            if (owner.hasPower(BlackArmorPower.POWER_ID)) {
                HpLoss = 0;
            }
        }

        if (HpLoss < 0) {
            HpLoss = 0;
        }
        return HpLoss;
    }

    @Override
    public Color getColor() {
        if (this.owner.isPlayer) {
            if (owner.getPower(this.ID).amount >= 5) {
                return Color.MAGENTA;
            } else if (owner.currentHealth <= owner.maxHealth * 0.45) {
                return Color.DARK_GRAY;
            } else {
                return Color.LIGHT_GRAY;
            }
        } else {
            return Color.FIREBRICK;
        }
    }

    /*    */
    /*    */
    public void updateDescription()
    /*    */ {
        /* 47 */
        if ((this.owner == null) || (this.owner.isPlayer)) {
            /* 48 */
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
            /*    */
        } else {
            /* 50 */
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
            /*    */
        }
        /*    */
    }

    /*    */
    @Override
    /*    */ public void stackPower(int stackAmount)
    /*    */ {
        /* 56 */
        super.stackPower(stackAmount);

        /* 59 */
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        /*    */
    }

    /*    */
    @Override
    /*    */ public void atEndOfTurn(boolean isPlayer)
    /*    */ {
            flashWithoutSound();

            AbstractDungeon.actionManager.addToBottom(new DecayDamageAction(false, owner, owner, amount, new DamageInfo(owner, amount, DamageType.THORNS)));

            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }
}