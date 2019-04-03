package DiamondMod.relics;

import DiamondMod.DiamondCore;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.PenNib;

import static DiamondMod.DiamondCore.makeRelicOutlinePath;
import static DiamondMod.DiamondCore.makeRelicPath;

public class PenNob extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("PenNob");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    AbstractPlayer p = AbstractDungeon.player;


    public PenNob() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.counter = 0;
    }

    // Flash at the start of Battle.
    @Override
    public void atTurnStart() {
        this.counter++;
        if
        (this.counter % 5 == 0  && this.counter > 0 &&
                AbstractDungeon.player.hasRelic(PenNib.ID)&&
                AbstractDungeon.player.getRelic(PenNib.ID).counter < 9){

            AbstractDungeon.player.getRelic(PenNib.ID).counter++;
        }
        if
        (this.counter >= 10){

            for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                        new StrengthPower(mo, 3), 3));

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                            new LoseStrengthPower(mo, 2), 2));

            }

            setCounter(0);
        }
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
