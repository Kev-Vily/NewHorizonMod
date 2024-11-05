package newhorizon.content.blocks;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import newhorizon.content.NHItems;
import newhorizon.content.Recipes;
import newhorizon.expand.block.distribution.AdaptItemBridge;
import newhorizon.expand.block.energy.XenConduit;
import newhorizon.expand.block.env.TiledFloor;
import newhorizon.expand.block.production.Assembler;

import static mindustry.type.ItemStack.with;
import static newhorizon.content.blocks.DistributionBlock.compositeReloadConveyor;
import static newhorizon.content.blocks.DistributionBlock.hyperLinkConveyor;

public class TestBlock {
    public static Block xenConduit, xenCrafter, irayroudRefinery, xenFactory, orePatch;
    public static AdaptItemBridge compositeReloadBridge, hyperLinkBridge;
    public static TiledFloor ancientEnergyFloor;
    //steepCliff,
    //highAltitude;


    public static void load(){
        xenConduit = new XenConduit("xen-conduit"){{
            requirements(Category.defense, with(Items.copper, 6));
        }};

        xenCrafter = new Assembler("xen-crafter"){{
            requirements(Category.production, with(Items.copper, 6));

            size = 3;
            xenArea = 20f;
            recipeSeq.add(Recipes.xenAlphaCraft, Recipes.xenBetaCraft, Recipes.xenGammaCraft);
        }};

        xenFactory = new Assembler("xen-factory"){{
            requirements(Category.production, with(Items.copper, 6));

            size = 4;
            xenArea = 20f;
            recipeSeq.add(Recipes.xenAlphaCraft, Recipes.xenBetaCraft, Recipes.xenGammaCraft);
        }};

        irayroudRefinery = new Assembler("irayroud-refinery"){{
            requirements(Category.production, with(Items.copper, 6));

            size = 4;
            xenArea = 20f;
            recipeSeq.add(Recipes.xenAlphaCraft, Recipes.xenBetaCraft, Recipes.xenGammaCraft);
        }};

        compositeReloadBridge = new AdaptItemBridge("composite-reload-bridge"){{
            requirements(Category.distribution, with(Items.titanium, 20, Items.copper, 20));
            itemCapacity = 8;
            conveyor = compositeReloadConveyor;
        }};

        hyperLinkBridge = new AdaptItemBridge("hyper-link-bridge"){{
            requirements(Category.distribution, with(NHItems.multipleSteel, 10, NHItems.presstanium, 20));
            itemCapacity = 30;
            conveyor = hyperLinkConveyor;
        }};

        //steepCliff = new SteepCliff("steep-cliff");
        //highAltitude = new HighAltitudeMarker("high-altitude-marker");
    }
}
