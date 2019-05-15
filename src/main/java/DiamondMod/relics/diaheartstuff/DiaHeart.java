package DiamondMod.relics.diaheartstuff;

import DiamondMod.DiamondCore;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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


    private static final int maxquests = 1;

    public static int activeQuest = 0;
    public static int[] questComplete = new int[maxquests];
    public static int[] questTracker = new int[maxquests];

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
    }

    @Override
    public void onVictory() {

        if (room instanceof MonsterRoom) {

            if (room instanceof MonsterRoomElite) {
questTracker[1]++;
logger.info("questTracker[1] is: " + questTracker[1]);
            } else if (room instanceof MonsterRoomBoss) {
                questTracker[2]++;
                logger.info("questTracker[2] is: " + questTracker[2]);

            } else {
                if (activeQuest == 0) {

                    questTracker[0]++;
                    logger.info("questTracker[0] is: " + questTracker[0]);

                    if (questTracker[0] >=2){

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
        counter = 0;

        activeQuest = MathUtils.random(0, maxquests - 1);
        logger.info("active quest num is: " + activeQuest);
        getUpdatedDescription();


    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[activeQuest + 2];
    }

    @Override
    public void onRightClick() {

        if (questTracker[0] >= 2 && activeQuest == 0){
this.counter+=2;
questTracker[0]=0;
this.pulse = false;
        }
    }

}
