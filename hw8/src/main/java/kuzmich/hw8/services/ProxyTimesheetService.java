package kuzmich.hw8.services;


import kuzmich.hw8.model.Timesheet;

import java.util.Optional;

public class ProxyTimesheetService extends TimesheetService {

  private final TimesheetService original;
  
  public ProxyTimesheetService(TimesheetService original) {
    super(original);
    this.original = original;
  }

  @Override
  public Optional<Timesheet> getById(Long id) {
    //BEFORE
    Optional<Timesheet> result = null;
    try {
      result = super.getById(id);
      //AFTER RETURNING
    } catch (Throwable e) {
      //AFTER THROWING
      throw e;
    } finally {
      //AFTER
      return result;
    }
  }
}
