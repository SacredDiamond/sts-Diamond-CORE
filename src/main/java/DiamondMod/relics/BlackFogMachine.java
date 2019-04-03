package DiamondMod.relics;

import DiamondMod.DiamondCore;
import DiamondMod.powers.DecayPower;
import DiamondMod.powers.Toxin;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static DiamondMod.DiamondCore.makeRelicOutlinePath;
import static DiamondMod.DiamondCore.makeRelicPath;

public class BlackFogMachine extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("BlackFog");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BlackFogMachine() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {

        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, mo, new Toxin(mo, mo, 3),3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, mo, new DecayPower(mo, mo, 3),3));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, mo, new PoisonPower(mo, mo, 3),3));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
