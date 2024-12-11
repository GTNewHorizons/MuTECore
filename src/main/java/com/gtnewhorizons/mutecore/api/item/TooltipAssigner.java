package com.gtnewhorizons.mutecore.api.item;

import java.util.List;

import com.badlogic.ashley.core.Component;

public interface TooltipAssigner extends Component {

    void assignTooltip(List<String> tooltip);
}
