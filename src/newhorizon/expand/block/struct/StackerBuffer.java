package newhorizon.expand.block.struct;

import arc.struct.Queue;
import arc.struct.Seq;
import mindustry.type.Item;
import mindustry.type.ItemStack;

public class StackerBuffer {
    public int bufferSize;
    public Queue<ItemStack> itemBuffer;
    public Queue<Float> nextDistance;
    public StackerBuffer(int size){
        bufferSize = size;
    }
}
