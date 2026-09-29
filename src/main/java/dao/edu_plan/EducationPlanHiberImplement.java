package dao.edu_plan;

import entity.EduPlanItemHiber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;
import java.util.UUID;

public class EducationPlanHiberImplement {
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public EduPlanItemHiber getEduPlanByGuid(String eduPlanGuid) {
        //String sql = "SELECT * FROM education_plan WHERE e_guid = :guid";
        String sql = "select e.guid from EduPlanItemHiber e where e.guid = :guid";

        Session session = sessionFactory.openSession();
        try {
            Query<EduPlanItemHiber> query = session.createQuery(sql, EduPlanItemHiber.class);
            query.setParameter("guid", UUID.fromString(eduPlanGuid));
            return query.uniqueResultOptional().orElse(null);
        } finally {
            session.close();
        }
    }

    public String getEduPlanStatusByGuid(String eduPlanGuid) {
        //String sql = "SELECT * FROM education_plan WHERE e_guid = :guid";
        String sql = "select e.status from EduPlanItemHiber e where e.guid = :guid";

        Session session = sessionFactory.openSession();
        try {
            Query<String> query = session.createQuery(sql, String.class);
            query.setParameter("guid", UUID.fromString(eduPlanGuid));
            return query.uniqueResultOptional().orElse(null);
        } finally {
            session.close();
        }
    }

    public List<String> getEducationPlanWithLimit(int limit) {
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT * FROM education_plan WHERE p_status = 'Утвержден' AND e_deleted_at IS NULL LIMIT " + limit;
            return session.createNativeQuery(sql, EduPlanItemHiber.class).getResultList()
                    .stream()
                    .map(EduPlanItemHiber::getForeignGuid)
                    .toList();
        }
    }
    @Test
    public void main() {
        List<String> educationPlanWithLimit = getEducationPlanWithLimit(5);
        educationPlanWithLimit.forEach(System.out::println);
    }
}


