package DiamondMod.cards.Curses;

import DiamondMod.DiamondCore;
import DiamondMod.cards.AbstractDynamicCard;
import DiamondMod.powers.Toxin;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DiamondMod.DiamondCore.makeCardPath;

public class BrokenSoul extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DiamondCore.makeID("BrokenSoul");
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static  String[] Descriptions = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    // /STAT DECLARATION/


    public BrokenSoul() {
        super(ID, "Broken Sould", IMG, -2, Descriptions[1], TYPE, COLOR, RARITY, TARGET);
magicNumber = baseMagicNumber = 2;
isEthereal = false;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Toxin(AbstractDungeon.player, AbstractDungeon.player, magicNumber)));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((!this.dontTriggerOnUseCard) && (p.hasRelic("Blue Candle"))) {
            useBlueCandle(p);
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(p, new DamageInfo(p, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = true;
            rawDescription = Descriptions[0] + Descriptions[1];
            initializeDescription();
        }
    }
}
