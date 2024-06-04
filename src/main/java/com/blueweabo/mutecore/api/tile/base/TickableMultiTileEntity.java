package com.blueweabo.mutecore.api.tile.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.blueweabo.mutecore.api.host.TaskHost;
import com.blueweabo.mutecore.api.logic.TickableTask;

public class TickableMultiTileEntity extends MultiTileEntity implements TaskHost {

    private final List<TickableTask<?>> tasks;
    private long timer = 0;

    public TickableMultiTileEntity() {
        super(true);
        tasks = new ArrayList<>();
    }

    @Override
    public void registerTask(TickableTask<?> task) {
        tasks.add(task);
        //tasks.sort(Comparator.naturalOrder());
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
            boolean isServerSide = !getWorld().isRemote;
        for (int i = 0; i < tasks.size(); i++) {
            if (isServerSide) {
                tasks.get(i).updateServer(timer);
            } else {
                tasks.get(i).updateClient(timer);
            }
        }
        timer++;
    }
}
