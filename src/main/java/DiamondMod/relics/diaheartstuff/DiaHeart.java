package DiamondMod.relics.diaheartstuff;

import DiamondMod.DiamondCore;
import DiamondMod.powers.Toxin;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import static DiamondMod.DiamondCore.*;

public class DiaHeart extends CustomRelic implements ClickableRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("DiaHeart");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DiamondHeart.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DiamondHeart.png"));


    private static final int maxquests = 2;

    public static int activeQuest = 0;
public static int questTracker = 0;
    AbstractPlayer p = AbstractDungeon.player;
   public static AbstractRoom room;

    public DiaHeart() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {

        if (counter >= 2) {
            flash();

            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 2), 2));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MetallicizePower(p, 2), 2));

        }
        if(counter >= 5) {
            for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Toxin(mo, mo,4)));
            }
        }
    }

    @Override
    public void onVictory() {

        if (room instanceof MonsterRoom) {


            if (room instanceof MonsterRoomElite) {

                if (activeQuest == 1){
                    questTracker++;
                    logger.info("questTracker is: " + questTracker);

                    if (questTracker >=2){
                        flash();

                        this.pulse = true;
                    }

            } else if (room instanceof MonsterRoomBoss) {
}

            } else {
                if (activeQuest == 0) {

                    questTracker++;
                    logger.info("questTracker is: " + questTracker);

                    if (questTracker >=3){
                        flash();

                        this.pulse = true;
                    }
                }
            }
        }

    }

    @Override
    public void onObtainCard(AbstractCard c) {
        /*
        if(c.rarity == AbstractCard.CardRarity.RARE){
            numofRares++;
        }
         */
    }

    @Override
    public void onEnterRoom(AbstractRoom curr) {

       room = AbstractDungeon.getCurrRoom();
        /*
        if (curr instanceof  MonsterRoom){


            if (numofRares >1) {
                if (activeQuest == 3) {
                    counter += 2;
                    quests[3] += 1;
                }
            }
        }
*/
        super.onEnterRoom(curr);
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {

rollQuest();
    }
public void rollQuest(){

    counter = 0;

    activeQuest = MathUtils.random(0, maxquests - 1);
    logger.info("active quest num is: " + activeQuest);
    getUpdatedDescription();

}
    // Description
    @Override
    public String getUpdatedDescription() {
        switch (activeQuest){

            case 1: return DESCRIPTIONS[3] + questTracker + DESCRIPTIONS[4] + DESCRIPTIONS[0];

        case 0:
        default: return DESCRIPTIONS[1] + questTracker + DESCRIPTIONS[2] + DESCRIPTIONS[0];
        }
    }

    @Override
    public void onRightClick() {

        if (questTracker >= 3 && activeQuest == 0){
this.counter+=2;
questTracker=0;
this.pulse = false;
rollQuest();
            flash();

        }else if(questTracker >= 2 && activeQuest == 1){
            this.counter+=3;
            questTracker=0;
            this.pulse = false;
            rollQuest();
            flash();

        }
    }

}
