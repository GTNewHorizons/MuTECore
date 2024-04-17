package com.blueweabo.mutecore.api.logic.interfaces;



public interface PowerLogic {

    boolean consume(long tick);

    boolean generate(long tick);
}
