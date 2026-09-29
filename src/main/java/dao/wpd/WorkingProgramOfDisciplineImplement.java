package dao.wpd;

import entity.WPDCriterias;
import util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WorkingProgramOfDisciplineImplement implements WorkingProgramOfDisciplineDao {
    private final Connection connection = Util.getjdbcConnection();
    @Override
    public List<WPDCriterias> getWPDWithLimit(int limit) {
        List<WPDCriterias> wpds = new ArrayList<>();
        String sql = "SELECT * FROM working_program_of_discipline WHERE p_status = 'APPROVED' AND edu_plan_id IS NOT NULL AND e_deleted_at IS NULL LIMIT " + limit;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String eGuid = resultSet.getString("e_guid");
                String pForeignGuid = resultSet.getString("p_foreign_guid");
                String pStatus = resultSet.getString("p_status");
                String eduProgramId = resultSet.getString("edu_program_id");
                String eduPlanId = resultSet.getString("edu_plan_id");
                String eDeletedAt = resultSet.getString("e_deleted_at");
                Boolean isBrs = resultSet.getBoolean("is_brs");

                WPDCriterias wpdCriterias = new WPDCriterias(eGuid, pForeignGuid, pStatus, eduProgramId, eduPlanId, eDeletedAt, isBrs);
                wpds.add(wpdCriterias);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wpds;
    }

    @Override
    public void insertIntoWPD(WPDCriterias wpdCriterias) {
        String sql = "INSERT INTO public.working_program_of_discipline (\n" +
                "    e_guid,\n" +
                "    p_foreign_guid,\n" +
                "    p_discipline_guid,\n" +
                "    p_goal,\n" +
                "    p_block_code,\n" +
                "    p_status,\n" +
                "    is_needed_to_qualification_work,\n" +
                "    is_needed_to_attestation,\n" +
                "    edu_program_id,\n" +
                "    edu_plan_id,\n" +
                "    base_program_id,\n" +
                "    p_lms_id,\n" +
                "    current_rating_parameters_id,\n" +
                "    p_need_reassign_accesses,\n" +
                "    e_deleted_at,\n" +
                "    p_loading_session_uid,\n" +
                "    p_need_send_to_lms,\n" +
                "    p_need_import_rating_from_aos,\n" +
                "    p_rating_imported_from_aos,\n" +
                "    p_rating_import_error,\n" +
                "    groups_count,\n" +
                "    is_elective,\n" +
                "    is_facultative,\n" +
                "    is_practice,\n" +
                "    is_gia,\n" +
                "    is_vkr,\n" +
                "    is_brs,\n" +
                "    is_distributed_practice,\n" +
                "    is_edu_practice,\n" +
                "    is_industrial_practice,\n" +
                "    practice_method\n" +
                ")\n" +
                "VALUES (\n" +
                "    ? ,\n" +
                "    '22222222-2222-4222-8222-222222222222',\n" +
                "    '33333333-3333-4333-8333-333333333333',\n" +
                "    'Моковая цель рабочей программы дисциплины',\n" +
                "    'Б.1.О.01',\n" +
                "    ?,\n" +
                "    FALSE,\n" +
                "    TRUE,\n" +
                "    '44444444-4444-4444-8444-444444444444',\n" +
                "    '55555555-5555-4555-8555-555555555555',\n" +
                "    '66666666-6666-4666-8666-666666666666',\n" +
                "    9999,\n" +
                "    '77777777-7777-4777-8777-777777777777',\n" +
                "    TRUE,\n" +
                "    NULL,\n" +
                "    '88888888-8888-4888-8888-888888888888',\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    1,\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    TRUE,\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    TRUE,\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    FALSE,\n" +
                "    NULL\n" +
                ");";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setObject(1, UUID.fromString(wpdCriterias.geteGuid()));
            statement.setString(2, wpdCriterias.getpStatus());
            statement.executeUpdate();
            connection.commit();


        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}