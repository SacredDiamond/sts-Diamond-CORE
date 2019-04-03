package DiamondMod.relics;

import DiamondMod.DiamondCore;
import DiamondMod.RelicSpeech;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static DiamondMod.DiamondCore.*;

public class ComplimentaryTicket extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("ComplimentaryTicket");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public static  String player = CardCrawlGame.playerName;

    public String line2 = DESCRIPTIONS[2] + player + DESCRIPTIONS[3];


    public ComplimentaryTicket() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        int RNG = MathUtils.random(0,100);
        if(RNG < 12) {
            int choose = MathUtils.random(1, DESCRIPTIONS.length-1);

logger.info("RNG is: " + RNG);
logger.info("choose is: " + choose);
            switch (choose) {
                case 2:
                case 3:
                    AbstractDungeon.effectList.add(new RelicSpeech(this.hb.cX, this.hb.cY, line2));
                    break;

                default:
                    AbstractDungeon.effectList.add(new RelicSpeech(this.hb.cX, this.hb.cY, DESCRIPTIONS[choose]));

            }
        }
    }

    @Override
    public void atBattleStart() {

    }
    @Override
    public int getPrice() {
        return 10;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
