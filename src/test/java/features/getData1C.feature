#Feature: Проверка получения данных из 1С
#  Background:
#    Получение справочников 1С по параметру
#
#  Scenario Outline:
#    Given Создание предусловий с ключом "<queryKey>" и названием ключа "catalog"
#    When Отправка запроса на "/Coordinate_Catalogs/Catalogs"
#    Then Проверить что значение в поле "<field>" соответствует "<expected>"
#
#
#    Examples:
#      | queryKey     | field   | expected             |
#      | GROUPS       | data[0] | working_edu_plans    |
#      | SPECIALITIES | data[0] | education_level_guid |
#
#  Scenario Outline:
#    Given Создание предусловий с ключом "<guid>" и названием ключа "guid"
#    When Отправка запроса на "/Coordinate_Education/Plans_Structure"
#    Then Проверить что значение в поле "<field>" не пустое и код 200
#
#
#    Examples:
#      | guid                                 | field           |  |
#      | e6e5eaaf-a348-11ef-a764-005056941fa2 | data.doc_number |  |
