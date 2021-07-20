package org.jboss.pnc.scheduler.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jboss.pnc.scheduler.common.enums.Mode;
import org.jboss.pnc.scheduler.model.Request;

@Builder
@AllArgsConstructor
@ToString
public class InitialTask {

    @Getter
    private final String name;

    @Getter
    private final Request remoteStart;

    @Getter
    private final Request remoteCancel;

    @Getter
    private final Request callerNotifications;

    @Getter
    private final Mode controllerMode;
}
