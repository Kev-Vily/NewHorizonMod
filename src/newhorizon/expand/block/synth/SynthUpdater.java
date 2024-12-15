package newhorizon.expand.block.synth;

import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Point2;
import arc.math.geom.Rect;
import arc.struct.ByteSeq;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.gen.Building;
import mindustry.gen.Shieldc;

import java.util.PriorityQueue;

import static mindustry.Vars.world;

public class SynthUpdater {
    public static final float UPDATE_INTERVAL = 15; //update 4fps should be enough
    public float updateTimer;

    //used to control the max altitude for tiles.
    public Seq<Building> nodes = new Seq<>();
    public Seq<Shieldc> bridge = new Seq<>();


    public Rand rand = new Rand();

    //when new game about to load, worldReset everything.
    public void worldReset(){

    }

    //when blocks are added, set things.
    public void worldInit(){

    }
}
