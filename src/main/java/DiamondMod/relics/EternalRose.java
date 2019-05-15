package DiamondMod.relics;

import DiamondMod.DiamondCore;
import DiamondMod.powers.PlatedThorns;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static DiamondMod.DiamondCore.makeRelicOutlinePath;
import static DiamondMod.DiamondCore.makeRelicPath;

public class EternalRose extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("EternalRose");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Rose.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Rose.png"));

    AbstractPlayer p = AbstractDungeon.player;

    public EternalRose() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
        this.tips.add(new PowerTip("Plated Thorns", "Upon receiving attack damage, the attacker takes damage equal to the amount of Plated THorns you have. if it is unblocked damage, reduce Plated Thorns by 1."));

    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedThorns(p, p, counter), counter));
    }
    @Override
    public void onEquip() {

        counter = 1;
    }
    @Override
    public void instantObtain() {
        if (AbstractDungeon.player.hasRelic(EternalRose.ID)) {

            EternalRose rose = (EternalRose) AbstractDungeon.player.getRelic(EternalRose.ID);

            rose.counter++;
            rose.flash();

        } else {
            super.instantObtain();
        }
    }
    @Override
    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip) {
        if (AbstractDungeon.player.hasRelic(EternalRose.ID)) {

            EternalRose rose = (EternalRose) AbstractDungeon.player.getRelic(EternalRose.ID);

            rose.counter++;
            rose.flash();


            isDone = true;
            isObtained = true;
            discarded = true;
        } else {
            super.instantObtain(p, slot, callOnEquip);
        }
    }
    @Override
    public void obtain() {

        if (AbstractDungeon.player.hasRelic(EternalRose.ID)) {

            EternalRose rose = (EternalRose) AbstractDungeon.player.getRelic(EternalRose.ID);

            rose.counter++;
            rose.flash();

        } else {
            super.obtain();
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
