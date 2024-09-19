# Напрограммировать электронную очередь

> 1. Класс TicketNumberGenerator - бин, у которого есть метод createNewNumber()
> * Метод createNewNumber() возвращает строки вида "Ticket #X", где X - UUID

>2. Класс Ticket - не бин с полями:
> * String number - номер билета
> * LocalDateTime createdAt - дата создания
> * Любые другие поля...

> 3. Класс Board - бин, у которого есть поле ticketNumberGenerator и метод newTicket(),
который создает объект класса Ticket со значениями полей:
> * number - результат вызова TicketNumberGenerator.createNewNumber()
> * createdAt - LocalDateTime.now()
> * ...