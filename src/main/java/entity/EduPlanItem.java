package entity;

public record EduPlanItem(
        String eGuid,
        String eduProgramId,
        String docNumber,
        String deletedAt,
        String foreignGuid,
        Integer yearStart,
        Integer yearEnd
) {
}
