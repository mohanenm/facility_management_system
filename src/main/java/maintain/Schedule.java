package maintain;
import java.time.*;

public class Schedule {
    private LocalDate startMaintenance;
    private LocalDate endMaintenance;
    private LocalDateTime startQuickMaintenance;//to be used if a maintenance request being made lasts less than a full work day
    private LocalDateTime endQuickMaintenance;

    public Schedule(){}

    public LocalDate getStartMaintenance(){
        return startMaintenance;
    }

    public LocalDate getEndMaintenance(){
        return endMaintenance;
    }

    public LocalDateTime getStartQuickMaintenance(){
        return startQuickMaintenance;
    }

    public LocalDateTime getEndQuickMaintenance(){
        return endQuickMaintenance;
    }

    public void ScheduleMaintenance(LocalDate startMaintenance, LocalDate endMaintenance){
        this.startMaintenance = startMaintenance;
        this.endMaintenance = endMaintenance;
    }

    public void ScheduleQuickMaintenance(LocalDateTime startQuickMaintenance, LocalDateTime endQuickMaintenance){
        this.startQuickMaintenance = startQuickMaintenance;
        this.endQuickMaintenance = endQuickMaintenance;
    }
}
