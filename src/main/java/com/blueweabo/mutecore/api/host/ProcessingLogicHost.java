package com.blueweabo.mutecore.api.host;

import javax.annotation.Nonnull;

import com.blueweabo.mutecore.api.logic.ProcessingLogic;

public interface ProcessingLogicHost<P extends ProcessingLogic<P>> {

    @Nonnull P getProcessing();
}
