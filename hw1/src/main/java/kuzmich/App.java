package kuzmich;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuzmich.model.Person;

/*
Условие:
Создать проект с использованием Maven или Gradle, добавить в него несколько
зависимостей и написать код, использующий эти зависимости.
Пример решения:
1. Создайте новый Maven или Gradle проект, следуя инструкциям из блока 1 или
блока 2.
2. Добавьте зависимости org.apache.commons:commons-lang3:3.12.0 и
com.google.code.gson:gson:2.8.6.
3. Создайте класс Person с полями firstName, lastName и age.
4. Используйте библиотеку commons-lang3 для генерации методов toString,
equals и hashCode.
5. Используйте библиотеку gson для сериализации и десериализации объектов
класса Person в формат JSON.
 */

public class App 
{
    public static void main( String[] args ){
        Person person1 = new Person("Roman", "Kuzmich", 38, "Moscow");
        Person person2 = new Person("Alexey", "Bobylev", 33, "Moscow");
        Person person3 = new Person("Nadezhda", "Grishina", 48, "Ufa");

        Gson gson1 = new Gson();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        Gson gson3 = gsonBuilder.create();

        String serializedPerson1 = gson1.toJson(person1);
        String serializedPerson2 = gson2.toJson(person2);
        String serializedPerson3 = gson3.toJson(person3);

        Person deserializedPerson1 = gson1.fromJson(serializedPerson1, Person.class);
        Person deserializedPerson3 = gson3.fromJson(serializedPerson3, Person.class);

        System.out.printf("Simple JSON: %s\n", serializedPerson1);
        System.out.printf("Pretty JSON: %s\n", serializedPerson2);
        System.out.printf("JSON without expose field: %s\n", serializedPerson3);

        System.out.printf("person1 equals deserializedPerson: %s\n", person1.equals(deserializedPerson1));


        System.out.printf("Deserialized person: %s\n", deserializedPerson1); // all fields expected to be filled

        System.out.printf("Deserialized person without expose field: %s\n", deserializedPerson3); // city expected as null
    }
}
