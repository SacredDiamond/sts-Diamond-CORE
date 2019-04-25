package DiamondMod.relics;

import DiamondMod.DiamondCore;
import DiamondMod.powers.Toxin;
import DiamondMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static DiamondMod.DiamondCore.makeRelicOutlinePath;
import static DiamondMod.DiamondCore.makeRelicPath;

public class UnnamedToxinRelic1 extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DiamondCore.makeID("ToxinRelic1");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public UnnamedToxinRelic1() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.tips.add(new PowerTip("Toxin", "Toxin is a #rDebuff that #rreduces your #rMax #rHp at the start of your turn."));
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
       AbstractPlayer p = AbstractDungeon.player;
        flash();
        AbstractDungeon.player.maxHealth += 12;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Toxin(p, p, 5)));
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
