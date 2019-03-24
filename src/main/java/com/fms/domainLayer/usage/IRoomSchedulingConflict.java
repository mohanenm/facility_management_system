package com.fms.domainLayer.usage;

import com.google.common.collect.Range;

import java.time.LocalDateTime;

public interface IRoomSchedulingConflict {

    Range<LocalDateTime> getRequestedRange();

    Range<LocalDateTime> getScheduledRange();

    void setRequestedRange(Range<LocalDateTime> requestedRange);

    void setScheduledRange(Range<LocalDateTime> scheduledRange);
}
