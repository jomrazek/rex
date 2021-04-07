package org.jboss.pnc.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jboss.pnc.scheduler.common.enums.Mode;
import org.jboss.pnc.scheduler.common.enums.State;
import org.jboss.pnc.scheduler.common.enums.StopFlag;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    public String name;

    public RemoteLinksDTO links;

    public Mode mode;

    public State state;

    public StopFlag stopFlag;

    public String payload;

    public Set<String> dependants = new HashSet<>();

    public Set<String> dependencies = new HashSet<>();
}
