#Задание


1. Переделать строки RoleName в сущность Role

>1.1 Создать отдельную таблицу Role(id, name)
>
>1.2 Связать User <-> Role отношением ManyToMany

2. Поправить формирование ролей в MyCustomUserDetailsService

3. В SecurityFilterChain настроить:

>3.1 Стандартная форма логина
>
>3.2 Страницы с проектами доступны пользователям с ролью admin
>
>3.3 страницы с таймшитами доступны пользователям с ролью user
>
>3.4 REST-ресурсы доступны с ролью rest

4. **** Для REST-ресурсов не показывать форму логина