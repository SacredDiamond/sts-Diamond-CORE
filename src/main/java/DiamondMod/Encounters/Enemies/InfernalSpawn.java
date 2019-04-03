package DiamondMod.Encounters.Enemies;

import DiamondMod.actions.ManaBlightTriggerAction;
import DiamondMod.powers.BarricadeRemoveCountdown;
import DiamondMod.powers.ManaBlightPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

public class InfernalSpawn extends AbstractMonster {

    public static String NAME = "Infernal_Spawn";
    public static final String ID = "corrupted:" + NAME;
    private static final MonsterStrings MONSTER_STRINGS = CardCrawlGame.languagePack.getMonsterStrings(ID);
    private static final int HP = 95;
    private int blurAMT;
    private float particleTimer;
    private int numTurns;
    private boolean firstTurn;
    private int StartupDAMAGE = 15;
    private int multiDAMAGE = 2;
    private int multiTIMES = 4;
    private int miniDAMAGE = 5;


    private int BigBlight = 6;
    private int BigBlightTRIGGER = 3;
    private int SmallBlight = 3;
    private int SmallBlightTRIGGER = 2;

    private Bone eye;
    private Bone back;

    public InfernalSpawn() {

        super(NAME, ID, HP, 40.0f, -30.0f, 460.0f, 250.0f, null, 0, 0);
        this.loadAnimation("images/monsters/theForest/awakenedOne/skeleton.atlas", "images/monsters/theForest/awakenedOne/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle_1", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.stateData.setMix("Hit", "Idle_1", 0.3f);
        this.stateData.setMix("Hit", "Idle_2", 0.2f);
        this.stateData.setMix("Attack_1", "Idle_1", 0.2f);
        this.stateData.setMix("Attack_2", "Idle_2", 0.2f);
        this.state.getData().setMix("Idle_1", "Idle_2", 1.0f);
        this.eye = this.skeleton.findBone("Eye");

        this.back = this.skeleton.findBone("Hips");
        this.type = EnemyType.ELITE;
        this.dialogX = -200.0f * Settings.scale;
        this.dialogY = 10.0f * Settings.scale;

        this.particleTimer = 0.0f;
        this.numTurns = 0;
        this.firstTurn = true;
        this.type = AbstractMonster.EnemyType.BOSS;
        this.dialogX = -400.0f * Settings.scale;
        this.dialogY = 200.0f * Settings.scale;
        this.damage.add(new DamageInfo(this, this.StartupDAMAGE));
        this.damage.add(new DamageInfo(this, this.multiDAMAGE));
        this.damage.add(new DamageInfo(this, this.miniDAMAGE));

    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
                new BarricadeRemoveCountdown(this, this, 5), 5));
    }

    @Override
    public void takeTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        switch (this.nextMove) {
            case 0:                 AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
                    new BarricadeRemoveCountdown(this,this, 2), 2));

                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AWAKENED_POUNCE"));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_1"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                        this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, this,
                        new ManaBlightPower(p, this, BigBlight), BigBlight));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect
                        (this.hb.cX, this.hb.cY, new Color(1.0f, 1.0f, 1.0f, 1.0f),
                                ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3f));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect
                        (this.hb.cX, this.hb.cY, new Color(1.0f, 1.0f, 1.0f, 1.0f),
                                ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0f));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect
                        (this.hb.cX, this.hb.cY, new Color(1.0f, 1.0f, 1.0f, 1.0f),
                                ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3f));

                AbstractDungeon.actionManager.addToBottom(new DamageAction(p,
                        this.damage.get(1), AbstractGameAction.AttackEffect.POISON));

                AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(p, this, SmallBlightTRIGGER, 0));
                AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(p, this, SmallBlightTRIGGER, 0));
                AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(p, this, SmallBlightTRIGGER, 0));


                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect
                        (this.hb.cX, this.hb.cY, new Color(1.5f, 1.5f, 1.5f, 1.0f),
                                ShockWaveEffect.ShockWaveType.CHAOTIC), 3.0f));

                AbstractDungeon.actionManager.addToBottom(new DamageAction(p,
                        this.damage.get(2), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p,
                        this.damage.get(2), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p,
                        this.damage.get(2), AbstractGameAction.AttackEffect.FIRE));

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, this,
                        new ManaBlightPower(p, this, SmallBlight), SmallBlight));
                AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(p, this, BigBlightTRIGGER, 0));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, this,
                        new ManaBlightPower(p, this, SmallBlight), SmallBlight));
                AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(p, this, BigBlightTRIGGER, 0));
                break;

            case 4:  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this,
                    new BarricadeRemoveCountdown(this,this, 2), 2));

                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect
                        (this.hb.cX, this.hb.cY, new Color(2.0f, 2.0f, 2.0f, 1.0f),
                                ShockWaveEffect.ShockWaveType.CHAOTIC), 0.2f));

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, this,
                        new ManaBlightPower(p, this, BigBlight), BigBlight));
                AbstractDungeon.actionManager.addToBottom(new ManaBlightTriggerAction(p, this, BigBlightTRIGGER, 0));

                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));


        }
    }

    @Override
    protected void getMove(final int num) {

        if (this.firstTurn) {
            this.setMove("Blighten", (byte) 0, Intent.ATTACK_DEBUFF, StartupDAMAGE);
            this.firstTurn = false;
        }
            if (this.lastMove((byte) 0) || this.firstTurn == false) {
                int rng = (MathUtils.random(2, 7));
                if (num < 40) {
                    this.setMove("Cursed_Shockwave", (byte)2, Intent.ATTACK_DEBUFF,multiDAMAGE,multiTIMES,true);
                } else if (num > 45 && num < 65) {
                    this.setMove("Blighten", (byte) 3, Intent.ATTACK_DEBUFF, miniDAMAGE);

                } else {

                    this.setMove("Armor_up", (byte) 4, Intent.DEFEND_DEBUFF);
                }
            }

        this.firstTurn = false;
    }
/*
    private static String Blighten;

    private static String Armor_up ;

    private static String Cursed_Shockwave ;

    private static String Name ;

    static {
        Blighten = InfernalSpawn.MOVES[0];
        Armor_up = InfernalSpawn.MOVES[2];
        Cursed_Shockwave = InfernalSpawn.MOVES[1];
        Name = InfernalSpawn.NAME;
    }
    */
}
