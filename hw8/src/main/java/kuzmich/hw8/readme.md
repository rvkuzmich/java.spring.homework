#Задание

1. В LoggingAspect добавить логирование типов и значений аргументов

2. ** Создать аспект, который аспектирует методы, помеченные аннотацией @Recover, и делает следующее:

>2.1 Если в процессе исполнения метода был exception, то его нужно залогировать и вернуть default значение наружу

3. В аннотацию @Recover добавить атрибут Class<?>[] noRecoverFor, в который можно записать список классов исключений,
которые не нужно отлавливать