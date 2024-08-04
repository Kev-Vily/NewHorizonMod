package newhorizon.expand.block.distribution;

import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.world.blocks.distribution.BufferedItemBridge;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.distribution.Junction;
import newhorizon.util.graphic.FloatPlatformDrawer;

import static mindustry.Vars.world;

public class FloatItemBridge extends BufferedItemBridge {
    public FloatItemBridge(String name) {
        super(name);
        placeableLiquid = true;
    }

    public class FloatItemBridgeBuild extends BufferedItemBridgeBuild {
        public Seq<Building> proximityBuild = new Seq<>();
        public boolean ab;
        public boolean[] edge, corner;

        @Override
        public void draw(){
            super.draw();
            if (tile.floor().isLiquid){
                FloatPlatformDrawer.drawPlatform(this, ab, edge, corner);
            }
        }

        public void updateDrawRegion(){
            ab = FloatPlatformDrawer.getEdgeAB(this);
            edge = FloatPlatformDrawer.getEdge(this);
            corner = FloatPlatformDrawer.getCorner(this);
        }

        public void updateProximityBuild(){
            tmpTiles.clear();
            proximityBuild.clear();

            for (Point2 point : Geometry.d8) {
                Building other = world.build(tile.x + point.x, tile.y + point.y);
                if (other == null || other.team != team) continue;
                if (other instanceof FloatConveyor.FloatConveyorBuild) {
                    tmpTiles.add(other);
                }
            }
            for (Building tile : tmpTiles) {
                proximityBuild.add(tile);
            }

            updateDrawRegion();
        }

        public void updateProximity() {
            super.updateProximity();

            updateProximityBuild();
            for (Building other : proximityBuild) {
                if (other instanceof FloatConveyor.FloatConveyorBuild) {
                    FloatConveyor.FloatConveyorBuild build = (FloatConveyor.FloatConveyorBuild) other;
                    build.updateProximityBuild();
                }
            }
        }

        @Override
        public void onRemoved(){
            for (Building other : proximityBuild) {
                if (other instanceof FloatConveyor.FloatConveyorBuild) {
                    FloatConveyor.FloatConveyorBuild build = (FloatConveyor.FloatConveyorBuild) other;
                    build.updateProximityBuild();
                }
            }
            super.onRemoved();
        }
    }
}
