package newhorizon.expand.block.graph;

import arc.func.Boolf;
import arc.func.Func;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.gen.Building;
import newhorizon.expand.block.AdaptBlock;
import newhorizon.expand.block.AdaptBuilding;

import static newhorizon.expand.block.graph.GraphUpdater.graphEntities;

/**A graph used for builds.
 *
 * @see mindustry.world.blocks.power.PowerGraph
 * */
public class GraphEntity<T extends AdaptBuilding>{

    private final Queue<T> queue = new Queue<>();

    public final Seq<T> allBuildings = new Seq<>(false, 16);
    public final Seq<T> horizontalBuildings = new Seq<>(false, 16);
    public final Seq<T> verticalBuildings = new Seq<>(false, 16);

    public static int lastID = 0;
    public final int graphID;

    protected transient boolean added;


    public GraphEntity() {
        graphID = lastID++;
        createGraph();
    }

    public void mergeGraph(GraphEntity<T> graph) {
        if (graph == this) return;

        //merge into other graph instead.
        if (allBuildings.size > graph.allBuildings.size) {
            graph.allBuildings.each(this::addBuild);
            graph.removeGraph();
        } else {
            allBuildings.each(graph::addBuild);
            removeGraph();
        }
    }

    @SuppressWarnings("unchecked")
    public void addBuild(T building) {
        if (!allBuildings.contains(building)) {
            //add this block to it
            allBuildings.add(building);
            building.graph = (GraphEntity<AdaptBuilding>) this;
        }
    }

    public void clear() {
        allBuildings.clear();
    }

    public void remove(T building, Boolf<T> isTarget) {

        //go through all the connections of this tile
        for (T other : targetBuilds(building)) {
            //Log.info("target" + targetBuilds(building).size);

            //check if it contains the graph or is the target graph that can be merged
            if (!isTarget.get(other)) continue;
            if (other.graph != this) continue;

            //create graph for this branch
            GraphEntity<T> graph = new GraphEntity<>();
            graph.addBuild(other);

            //BFS time
            queue.clear();
            queue.addLast(other);
            while (queue.size > 0) {
                //get child from queue
                T child = queue.removeFirst();
                //add it to the new branch graph
                graph.addBuild(child);
                //go through connections
                for (T next : targetBuilds(child)) {
                    //make sure it hasn't looped back, and that the new graph being assigned hasn't already been assigned
                    //also skip closed tiles
                    if (next != building && next.graph != graph) {
                        graph.addBuild(next);
                        queue.addLast(next);
                    }
                }
            }
            //Log.info(graphID + "+" + allBuildings.size);
        }
        //implied empty graph here
        removeGraph();
    }

    @SuppressWarnings("unchecked")
    public void createGraph() {
        if (!added) {
            graphEntities.put(graphID, (GraphEntity<AdaptBuilding>) this);
            added = true;
        }
    }

    public void removeGraph() {
        if (added) {
            clear();
            graphEntities.remove(graphID);
            added = false;
        }
    }

    @SuppressWarnings("unchecked")
    private Seq<T> targetBuilds(Building building){
        Seq<T> builds = new Seq<>();
        building.proximity.each(build -> build instanceof AdaptBuilding, build -> builds.add((T)build));
        return builds;
    }
}
