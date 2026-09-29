#Feature: Проверка получения данных РПД
#  Background:
#  Получение справочников 1С по параметру
#
#  Scenario Outline:
#    Given Получение данных РПД по guid "<guid>"
#    When Отправка запроса 2 на "internal/wpd/{guid}"
#    Then Проверить что поле "<key>" РПД и параметр "<expectedValue>" совпадают и код 200
#
#    Examples:
#      | guid                                 | key     | expectedValue     |
#      | 0b12a130-1671-43a2-af3d-2d0d963ac9a6 | data.id | 0b12a130-1671-43a2-af3d-2d0d963ac9a6 |