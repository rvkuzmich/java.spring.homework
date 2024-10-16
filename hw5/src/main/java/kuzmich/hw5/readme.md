#1 Создать класс Employee с полями:
> 1.1 Идентификатор <br>
> 1.2 Имя <br> 
> 1.3 Фамилия <br> 
> 1.4 Создать контроллер-сервис-репозиторий

#2 В Timesheet добавить поле Employee (employeeId тип Long)

#3. Создать ресурс /employees/{id}/timesheets - получить все таймшиты по сотруднику

#4. Связать Project <-> Employee отношением ManyToMany, т.е. на проекте может быть несколько сотрудников
или один сотрудник может быть на нескольких проектах

#5 Дополнительно:
>1. Подключить swagger к проекту <br>
>2. Добавить описание ручек (название на русском языке и описание, что делают) <br>
>3. Описать параметры и возвращаемые значения <br>