package dao.edu_plan;

import entity.EduPlanItem;

public interface EducationPlanDao {
    EduPlanItem getEduPlanByGuid(String eduPlanGuid);
}
