package dao.wpd;

import entity.WPDCriterias;

import java.util.List;

public interface WorkingProgramOfDisciplineDao {
    List<WPDCriterias> getWPDWithLimit(int limit);
    void insertIntoWPD(WPDCriterias wpdCriterias);
}
