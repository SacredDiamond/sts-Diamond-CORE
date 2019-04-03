/*    */ package DiamondMod.powers;

import DiamondMod.DiamondCore;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

/*    */
/*    */
/*    */
/*    */
/*    */

/*    */ public class TempRegen extends TwoAmountPower
/*    */ {
	public static final String POWER_ID = DiamondCore.makeID("TempRegen");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	/*    */
	/*    */ public TempRegen(AbstractCreature owner, AbstractCreature source, int duration, int healAMT)
	/*    */ {
		/* 23 */ this.name = NAME;
		/* 25 */ this.owner = owner;
		/* 27 */ this.amount = duration;
		this.amount2 = healAMT;
		canGoNegative = false;
		this.ID = POWER_ID;
		this.updateDescription();
		/* 34 */ this.loadRegion("focus");
		/* 35 */ this.type = PowerType.BUFF;
		/*    */
		/* 37 */ this.isTurnBased = true;
		this.priority = 10;
		/*    */ }


	/*    */ @Override
	/*    */ public void stackPower(int stackAmount)
	/*    */ {
		/* 56 */ super.stackPower(stackAmount);
		/*    */ updateDescription();
		/* 58 */
		/* 59 */ if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
		/*    */ }

	/*    */ @Override
	/*    */ public void atEndOfTurn(boolean isPlayer)
	/*    */ {updateDescription();
				flashWithoutSound();
		
			if(this.amount>0) {

				AbstractDungeon.actionManager.addToBottom(
						new GainBlockAction(owner, owner, amount2));
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(this.owner, this.owner, new TempRegen(this.owner, this.owner, -1,0), -1));
			}else {
				
				AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
			}updateDescription();
			/*    */ }
    @Override
    public void updateDescription() {
		if (amount>1){
    	this.description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]; }
		else{
			this.description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3]; }
    }
		/*    */ }

/*
 * Location: C:\Program Files
 * (x86)\Steam\steamapps\common\SlayTheSpire\desktop-1.0.jar!\com\megacrit\
 * cardcrawl\powers\PoisonPower.class Java compiler version: 8 (52.0) JD-Core
 * Version: 0.7.1
 */