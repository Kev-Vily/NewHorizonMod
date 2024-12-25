package newhorizon.expand.block.floodv3;

import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import newhorizon.expand.block.flood.FloodBase;
import newhorizon.expand.block.flood.FloodGraph;

import static mindustry.Vars.world;
import static newhorizon.NHVars.rectControl;

public class SyntherVein extends Block {

    public SyntherVein(String name) {
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        canOverdrive = false;
        envEnabled = Env.any;
        update = false;
        drawCracks = false;

        placeSound = Sounds.none;
        placeEffect = Fx.none;
    }

    public class SyntherVeinBuild extends Building implements SyntherBuildingEntity {
        public SyntherGraph graph;
        public Seq<Tile> expandCandidate;

        @Override
        public void created() {
            super.created();

            expandCandidate = new Seq<>();
            createGraph();
        }

        public void updateExpandCandidate(){
            expandCandidate.clear();
            /*
            for (Point2 p: Edges.getEdges(size)){
                Tile candidate = world.tile(tileX() + p.x, tileY() + p.y);
                if (rectControl.getPos(tileX() + p.x, tileY() + p.y) == 0) continue;
                if (candidate != null && !candidate.dangerous() && !candidate.solid() && candidate.build == null){
                    expandCandidate.add(candidate);
                }
            }

            if (!expandCandidate.isEmpty())return;

             */

            for (Point2 p: Edges.getEdges(size)){
                Tile candidate = world.tile(tileX() + p.x, tileY() + p.y);
                if (candidate != null && !candidate.dangerous() && !candidate.solid() && candidate.build == null){
                    expandCandidate.add(candidate);
                }
            }
        }

        @Override
        public void onProximityAdded() {
            super.onProximityAdded();
            for (Building other : proximity) {
                if (other instanceof SyntherVeinBuild){
                    graph.mergeGraph(((SyntherVeinBuild)other).graph);
                }
            }
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            updateGraph();
        }

        @Override
        public void onProximityRemoved() {
            super.onProximityRemoved();
            removeGraph();
        }

        @Override
        public SyntherGraph graph() {
            return graph;
        }

        @Override
        public void setGraph(SyntherGraph graph) {
            this.graph = graph;

        }

        @Override
        public void createGraph() {
            graph = new SyntherGraph();
            graph.addBuild(this);
        }

        @Override
        public void updateGraph() {
            updateExpandCandidate();

            if (expandCandidate.isEmpty()){
                graph.expandCandidate.remove(this);
            }else {
                graph.expandCandidate.put(this, expandCandidate);
            }
        }

        @Override
        public void removeGraph() {
            graph.remove(this);
        }
    }
}
