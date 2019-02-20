package maintain;
import java.time.*;

public class Schedule extends Maintenance{
    private static LocalDate startMaintenance;
    private static LocalDate endMaintenance;
    private static LocalDateTime startQuickMaintenance;//to be used if a maintenance request being made lasts less than a full work day
    private static LocalDateTime endQuickMaintenance;

    public void ScheduleMaintenance(LocalDate startMaintenance, LocalDate endMaintenance){
        this.startMaintenance = startMaintenance;
        this.endMaintenance = endMaintenance;
    }

    public void ScheduleQuickMaintenance(LocalDateTime startQuickMaintenance, LocalDateTime endQuickMaintenance){
        this.startQuickMaintenance = startQuickMaintenance;
        this.endQuickMaintenance = endQuickMaintenance;
    }
}
