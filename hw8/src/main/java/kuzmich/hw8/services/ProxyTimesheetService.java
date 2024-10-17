package kuzmich.hw8.services;


import kuzmich.hw8.model.Timesheet;

public class ProxyTimesheetService extends TimesheetService {
  public ProxyTimesheetService(TimesheetService original) {
    super(original);
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
