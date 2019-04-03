package DiamondMod.relics;

import DiamondMod.DiamondCore;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.rooms.*;

import static DiamondMod.DiamondCore.*;

public class DiaHeart extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("DiaHeart");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public static int secretNormals = 0;
    public static int secretElites = 0;
    public static int secretBosses = 0;
    public static int numofRares = 0;

    public static  int questNum = 0;
    public static  int[] quests  = new int[10];

    AbstractPlayer p = AbstractDungeon.player;
  static AbstractRoom room;

    public DiaHeart() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }



    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {

        if (counter >3){        flash();

            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 2),2));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MetallicizePower(p, 2),2));

        }
    }

    @Override
    public void onVictory(){
logger.info("room is: " + room);

  //    if(room.combatEvent){

          if(room instanceof MonsterRoomElite){
              if ( questNum == 1) {
                  /* quest #1 complete */
                  flash();
                  secretElites += 2;
                  logger.info("secret elites is: " + secretElites);
              }

          }else if(room instanceof MonsterRoomBoss){
              if ( questNum == 2) {
                  /* quest #2 complete */
                  secretBosses += 3;
                  logger.info("secret bosses is: " + secretBosses);
              }

          }else if(room instanceof MonsterRoom) {
              if (questNum == 0) {
                  /* quest #0 complete */
                  flash();
                  secretNormals++;
                  logger.info("secret normals is: " + secretNormals);
              }
          }
    //  }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if(c.rarity == AbstractCard.CardRarity.RARE){
            numofRares++;
        }
    }

    @Override
    public void onEnterRoom(AbstractRoom curr) {
       room = curr;
       logger.info("room is: " + room);


        if ((curr instanceof ShopRoom)) {
            if (questNum == 0) {
                /* claim quest #0 */
                counter += secretNormals;
                secretNormals = 0;
                quests[0] += 1;
            }

        }else if( curr instanceof  EventRoom){
            if (questNum == 1) {
                /* claim quest #1 */
                counter += secretElites;
                secretElites = 0;
                quests[0] += 1;
            }

        }else if( curr instanceof MonsterRoomElite){
            if (questNum == 2) {
                /* claim quest #2 */
                counter += secretBosses;
                secretBosses = 0;
                quests[2] += 1;
            }

        }else if (curr instanceof  MonsterRoom){

            if (numofRares >1) {
                if (questNum == 3) {
                    /* claim quest #3 */
                    counter += 2;
                    quests[3] += 1;
                }
            }
        }

        super.onEnterRoom(curr);
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
counter = secretBosses = secretElites = secretNormals = 0;

questNum = MathUtils.random(0, 3);
getUpdatedDescription();


    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]  + DESCRIPTIONS[questNum+2];
    }

}
