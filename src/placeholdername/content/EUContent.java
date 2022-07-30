package placeholdername.content;

import arc.audio.Sound;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Rect;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.part.FlarePart;
import mindustry.entities.part.RegionPart;
import mindustry.gen.MechUnit;
import mindustry.gen.Sounds;
import mindustry.gen.TankUnit;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.unit.ErekirUnitType;
import mindustry.type.unit.TankUnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PayloadAmmoTurret;
import mindustry.world.blocks.units.UnitAssembler;
import mindustry.world.blocks.units.UnitAssemblerModule;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawTurret;
import placeholdername.types.CloakStatus;
import placeholdername.types.CloakUnit;

import static mindustry.type.ItemStack.with;

public class EUContent {

    public static Block advancedAssemblerModule, shatter, rend;

    public static UnitType ruin, cynaeus, diminish;

    public static Sound heavyLaser; //yes it's all in a single file cry about it

    public static StatusEffect cloaked;

    public static void load(){

        cloaked = new CloakStatus("cloaked"/*, StatusEffects.burning*/){{
                show = true;
        }};

        //Sounds
        heavyLaser = Vars.tree.loadSound("heavylaser");

        //Blocks
        advancedAssemblerModule = new UnitAssemblerModule("advanced-assembler-module"){{
            requirements(Category.units, with(Items.carbide, 300, Items.thorium, 500, Items.oxide, 200, Items.phaseFabric, 400));
            consumePower(4f);
            localizedName = "Advanced Assembler Module";
            regionSuffix = "-dark";
            tier = 2;
            size = 5;
        }};

        //Units
        ruin = new TankUnitType("ruin"){{
            constructor = TankUnit::create;
            localizedName = "Ruin";
            hitSize = 35;
            speed = .6f;
            rotateSpeed = 0.8f;
            health = 30000f;
            armor = 35f;
            treadPullOffset = 6;
            treadFrames = 3;
            crushDamage = 40f / 5f;
            outlineColor = Color.valueOf("ffffff").a(0);

            treadRects = new Rect[]{new Rect(-98.5f/4f, -89.5f/4f, 44, 88), new Rect(97.5f/4f, -89.5f/4f, 44, 88)};

            abilities.add(new ShieldRegenFieldAbility(60, 1200, 120, 0));

            parts.add(new RegionPart("-glow"){{
                color = Color.red;
                blending = Blending.additive;
                outline = mirror = false;
            }});

            weapons.add(new Weapon("eu-java-ruin-heavy"){{
                x = y = 0;
                reload = 110f;
                layerOffset = 0.1f;
                shootY = 40f;
                shake = 0.5f;
                rotate = true;
                rotateSpeed = 0.6f;
                mirror = false;
                shadow = 50f;
                shootWarmupSpeed = 0.06f;
                cooldownTime = 10f;
                minWarmup = 0.9f;
                continuous = true;

                bullet = new ContinuousLaserBulletType(){{ //flame bullets suck ass so I emulate them with the FlarePart
                    damage = 140f;
                    lifetime = 16f * 60f;
                    length = 15f * 8f;
                    width = 0.9f * 8f;
                    range = 12f * 8f;
                    knockback = 2f;
                    pierceCap = 4;
                    shake = 0.5f;

                    colors = new Color[]{Color.valueOf("d2816b").a(0.6f), Color.valueOf("fdd37f").a(0.8f), Color.white};
                    lightColor = hitColor = colors[1];
                    shootSound = Sounds.fire;
                }};

                parts.addAll(
                new RegionPart("-flamer-glow"){{
                    color = Color.red;
                    blending = Blending.additive;
                    outline = mirror = false;
                    layerOffset = 0.4f;
                }},
                new RegionPart("-flamer"){{ //had to do a funny to draw the weapon higher up
                    layerOffset = 0.3f;
                    mirror = false;
                }},
                new RegionPart("-flamer-cans"){{
                   layerOffset = 0.1f;
                   mirror = false;
                }},
                new RegionPart("-flamer-fuel"){{
                    progress = PartProgress.smoothReload;
                    heatProgress = PartProgress.smoothReload.inv();
                    heatLayerOffset = 0.2f;
                    layerOffset = 0f;
                    mirror = true;
                    moveX = -11f;
                    moveY = 11f;
                    heatColor = Color.red;
                }},
                new RegionPart("-flamer-fuel-drain"){{
                    progress = p -> 0; //couldn't bother fixing it so it stays 0 now ¯\_('~')_/¯
                    layerOffset = 0f;
                    mirror = true;
                    x = -11f;
                    y = 11f;
                    moveX = 11f;
                    moveY = -11f;
                }},
                new FlarePart(){{
                    color1 = Color.valueOf("fdd37f");
                    progress = PartProgress.recoil;
                    radius = 0f;
                    radiusTo = 35f;
                    stroke = 3f;
                    y = 37f;
                }});
            }});
        }};

        cynaeus = new ErekirUnitType("cynaeus"){{
            constructor = MechUnit::create;
            localizedName = "Cynaeus";
            speed = 0.35f;
            hitSize = 31f;
            rotateSpeed = 1.65f;
            health = 24000;
            armor = 14f;
            mechStepParticles = true;
            stepShake = 0.75f;
            drownTimeMultiplier = 6f;
            mechFrontSway = 2.5f;
            mechSideSway = 0.7f;
            ammoType = new PowerAmmoType();
            groundLayer = 99;
            shadowElevation = 0.7f;

            float orbY = -3.5f;

            weapons.add(
                new Weapon("eu-java-cynaeus-sidearm"){{
                    top = false;
                    x = 28f;
                    shootY = 22f;
                    reload = 20f;
                    recoil = 5f;
                    shake = 2f;
                    ejectEffect = Fx.casing4;
                    cooldownTime = 40f;
                    heatColor = Color.red;
                    shootSound = heavyLaser;

                    bullet = new LaserBulletType(){{
                        largeHit = true;
                        length = 20f * 8;
                        width = 3.5f * 8f;
                        damage = 210f;
                        lifetime = 65f;
                        sideAngle = 15f;
                        sideWidth = 0.2f * 8f;
                        sideLength = 1.5f * 8f;
                        colors = new Color[]{Pal.techBlue.cpy().a(0.4f), Pal.techBlue, Color.white};
                        lightColor = Pal.techBlue;
                    }};
                }}
            );

            weapons.add(
                new Weapon("eu-java-pew-pew"){{
                    x = 0;
                    y = orbY;
                    mirror = false;
                    reload = 100f;
                    recoil = 0f;
                    cooldownTime = 120f;
                    bullet = new BasicBulletType(){{
                        damage = 110f;
                        speed = 5f;
                        lifetime = 60f;
                        width = 16f;
                        height = 16f;
                        shrinkX = 0f;
                        shrinkY = 0f;
                        weaveScale = 8f;
                        weaveMag = 2f;
                        homingPower = 10f;
                        trailWidth = 6f;
                        trailLength = 200;
                        sprite = "circle-bullet";
                        trailColor = backColor = Pal.techBlue;
                        frontColor = Color.white;
                        shootEffect = Fx.none;
                    }};
                    parts.addAll(
                        new RegionPart("-body"){{
                            y = -orbY;
                            heatProgress = PartProgress.heat;
                            heatColor = Color.white.cpy();
                        }},
                        new RegionPart("-orb"){{
                            color = Pal.techBlue;
                            blending = Blending.additive;
                            heatProgress = PartProgress.warmup;
                            heatColor = Pal.techBlue;
                        }}
                    );
                }}
            );
        }};

        shatter = new ItemTurret("shatter"){{
            requirements(Category.turret, with(Items.tungsten, 150, Items.silicon, 200, Items.oxide, 40, Items.beryllium, 400));
            size = 7;
            outlineColor = Pal.darkOutline;
            shootWarmupSpeed = 0.04f;
            shootY = 0;
            minWarmup = 0.99f;
            coolant = consume(new ConsumeLiquid(Liquids.water, 15f / 60f));
            coolantMultiplier = 6f;
            shake = 2f;
            rotateSpeed = 2.5f;
            reload = 240f; //45
            recoil = 2f;

            ammo(
                Items.carbide, new BasicBulletType(11f, 500){{
                    lifetime = 30f;
                    range = 330f;
                    width = 12f;
                    hitSize = 7f;
                    height = 20f;
                    smokeEffect = Fx.shootBigSmoke;
                    ammoMultiplier = 1;
                    pierceCap = 7;
                    pierce = true;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = Color.valueOf("#89769a");
                    frontColor = Color.white;
                    trailWidth = 2.1f;
                    trailLength = 10;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    buildingDamageMultiplier = 0.3f;
                }},
                Items.surgeAlloy, new BasicBulletType(13f, 750){{
                    lifetime = 35f;
                    range = 455;
                    width = 12f;
                    hitSize = 7f;
                    height = 20f;
                    smokeEffect = Fx.shootBigSmoke;
                    ammoMultiplier = 1;
                    pierceCap = 7;
                    pierce = true;
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = Pal.accent;
                    frontColor = Color.white;
                    trailWidth = 2.1f;
                    trailLength = 10;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    buildingDamageMultiplier = 0.3f;
                    fragBullets = 4;
                    fragBullet = new LightningBulletType(){{
                        damage = 30;
                        collidesAir = false;
                        ammoMultiplier = 1f;
                        lightningColor = Pal.accent;
                        lightningLength = 6;
                        lightningLengthRand = 3;
                    }};
                }}
            );

            drawer = new DrawTurret("reinforced-"){{
                parts.addAll(
                    new RegionPart("-body"){{
                        layerOffset = 0f;
                        outlineLayerOffset = -0.3f;
                        heatProgress = PartProgress.warmup;
                        heatColor = Pal.berylShot.cpy().a(0.25f);
                    }},

                    new RegionPart("-wing"){{
                        progress = heatProgress = PartProgress.warmup.curve(Interp.slowFast).curve(Interp.bounce);
                        heatColor = Pal.berylShot.cpy().a(0.40f);
                        layerOffset = -0.1f;
                        moveX = 15f * 0.25f;
                        moveY = -13f * 0.25f;
                        turretShading = true;
                        mirror = true;
                        outlineLayerOffset = -0.2f;
                    }},
                    new RegionPart("-barrel"){{
                        layerOffset = -0.2f;
                        progress = PartProgress.warmup.curve(Interp.circleIn).curve(Interp.bounce);
                        moveX = 12f * 0.25f;
                        moveY = 17f * 0.25f;
                        turretShading = true;
                        mirror = true;
                        outlineLayerOffset = -0.1f;
                        heatColor = Pal.turretHeat.cpy();
                        heatLayerOffset = -0.2f;
                        moves.add(new PartMove(PartProgress.recoil.curve(Interp.bounce), 0f, -3f, -2));
                    }}
                );
            }};
        }};

        rend = new PayloadAmmoTurret("rend"){{
            requirements(Category.turret, with(Items.tungsten, 150, Items.silicon, 200, Items.oxide, 40, Items.beryllium, 400));
            size = 7;
            outlineColor = Pal.darkOutline;
            shootWarmupSpeed = 0.04f;
            shootY = 0;
            minWarmup = 0.99f;
            coolant = consume(new ConsumeLiquid(Liquids.water, 15f / 60f));
            coolantMultiplier = 6f;
            shake = 2f;
            rotateSpeed = 2.5f;
            reload = 45f;
            recoil = 2f;

            ammo(
                    Blocks.shieldedWall, new BasicBulletType(11f, 500){{
                        lifetime = 30f;
                        range = 330f;
                        width = 12f;
                        hitSize = 7f;
                        height = 20f;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        pierceCap = 7;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Color.valueOf("#89769a");
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;
                    }},
                    Blocks.reinforcedSurgeWallLarge, new BasicBulletType(13f, 750){{
                        lifetime = 35f;
                        range = 455;
                        width = 12f;
                        hitSize = 7f;
                        height = 20f;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        pierceCap = 7;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.accent;
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;
                        fragBullets = 4;
                        fragBullet = new LightningBulletType(){{
                            damage = 30;
                            collidesAir = false;
                            ammoMultiplier = 1f;
                            lightningColor = Pal.accent;
                            lightningLength = 6;
                            lightningLengthRand = 3;
                        }};
                    }}
            );

            drawer = new DrawTurret("reinforced-"){{
                parts.addAll(
                        new RegionPart("-body"){{
                            layerOffset = 0f;
                            outlineLayerOffset = -0.3f;
                            heatProgress = PartProgress.warmup;
                            heatColor = Pal.berylShot.cpy().a(0.25f);
                        }},

                        new RegionPart("-wing"){{
                            progress = heatProgress = PartProgress.warmup.curve(Interp.slowFast).curve(Interp.bounce);
                            heatColor = Pal.berylShot.cpy().a(0.40f);
                            layerOffset = -0.1f;
                            moveX = 10f * 0.25f;
                            moveY = -10f * 0.25f;
                            turretShading = true;
                            mirror = true;
                            outlineLayerOffset = -0.2f;
                        }},
                        new RegionPart("-barrel"){{
                            layerOffset = -0.2f;
                            progress = PartProgress.warmup.curve(Interp.circleIn).curve(Interp.bounce);
                            moveX = 20f * 0.25f;
                            moveY = 20f * 0.25f;
                            turretShading = true;
                            mirror = true;
                            outlineLayerOffset = -0.1f;
                            heatColor = Pal.turretHeat.cpy();
                            heatLayerOffset = -0.2f;
                            moves.add(new PartMove(PartProgress.recoil.sustain(1f, 0.1f, 0.1f), 0f, -4f, 0f));
                        }}
                );
            }};
        }};

        //Misc
        ((UnitAssembler) Blocks.mechAssembler).plans.add(new UnitAssembler.AssemblerUnitPlan(cynaeus, 60 * 60 * 5, PayloadStack.list(UnitTypes.anthicus, 5, Blocks.reinforcedSurgeWallLarge, 16, Blocks.largePlasmaBore, 8)));
        ((UnitAssembler) Blocks.tankAssembler).plans.add(new UnitAssembler.AssemblerUnitPlan(ruin, 60 * 60 * 5, PayloadStack.list(UnitTypes.precept, 5, Blocks.shieldedWall, 14, Blocks.sublimate, 7)));
        UnitTypes.elude.constructor = CloakUnit::create;
        for(int i = 0; i < 1; i++){
            UnitTypes.tecta.weapons.get(i).shootSound = EUContent.heavyLaser;
        }
        ((ItemTurret)shatter).drawer = ((PayloadAmmoTurret)rend).drawer;
    }
}