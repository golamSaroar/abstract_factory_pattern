# Abstract Factory

## Intent
Abstract Factory provides an interface for creating families of related or dependent objects without specifying their concrete classes.

## Also Known As
Kit

## Motivation

### Problem

Imagine that you are creating an application of a furniture store. Your code consists of:

1. Family of related products, say: Chair, Table, Bed.
2. Several variants of this family. For example, Chair, Table, Bed are available in these variants: Hatil, Otobi, Partex.

You need a way to create individual furniture objects so that they match other objects of the same family. Customers get quite frustrated when they receive non-matching furniture.

Also, you do not want to change existing code when adding new products or families of products to the program. Furniture vendors update their catalogs very often, and you do not want to change the core code each time it happens.

### Solution

The first thing that Abstract Factory pattern suggests is to go over all distinct products and force their variants to follow common interfaces. For example, all chair variants must follow the Chair interface; all tables must implement the Table interface, etc.

The second step is to create the AbstractFactory, a base interface that declares methods for creating all products that make a product family (i.e. createChair, createTable and createBed). The important thing here is to make these methods to return abstract product types represented by interfaces we extracted previously: Chair, Table, Bed etc.

The third step is to implement concrete factories. Factories are classes that return products of a particular kind. For example, HatilFactory, will only return HatilChair, HatilTable and HatilBed objects. All factories must follow the AbstractFactory interface while creating the same variety of products.

Client code has to work with factories and products only through their abstract interfaces. This way you can alter the type of products used in client code by passing it a different factory object.

So, when client code asks a factory to produce a chair, it must not be aware of the factory's concrete class. It must not be aware of the concrete class of chair it will get either. Whether it will be a modern Hatil model or an Otobi style chair, it must work with all chairs in the same way, using the abstract Chair interface. The thing that the client code will know is that the resulting chair implements the sit() method, declared in the interface. It also knows that whichever chair will be returned, it will match the type of table and bed, produced by the same factory.

Okay, but who creates the actual factory objects? Usually, the program creates a concrete factory object at initialization stage, and the factory type is picked depending on the configuration or environment.

## Applicability
Use the Abstract Factory pattern when
* a system should be independent of how its products are created, composed, and represented.
* a system should be configured with one of multiple families of products.
* a family of related product objects is designed to be used together, and you need to enforce this constraint.
* you want to provide a class library of products, and you want to reveal just their interfaces, not their implementations.

## Structure
![Abstract Factory Pattern Structure](/resources/images/abstract-factory-structure.png)

## Participants

#### AbstractFactory (FurnitureFactory)
- declares the interface for creating all products of a family.
#### ConcreteFactory (HatilFactory, OtobiFactory)
- implements the operations to create concrete product objects.
- each concrete factory corresponds to the specific variant of a product family.
#### AbstractProduct (Chair, Table)
- declares an interface for a type of product object. Normally, there should be several different product interfaces that make a product family.
#### ConcreteProduct (HatilChair, OtobiChair, HatilTable, OtobiTable)
- defines a product object to be created by the corresponding concrete factory.
- implements the AbstractProduct interface.
#### Client (FurnitureStore)
- uses only interfaces declared by AbstractFactory and AbstractProduct classes.

## Collaborations
Normally a single instance of a ConcreteFactory class is created at run-time. This concrete factory creates product objects having a particular implementation. To create different product objects, clients should use a different concrete factory.
AbstractFactory defers creation of product objects to its ConcreteFactory subclass.

## Consequences

### Pros
* It isolates concrete classes. The Abstract Factory pattern helps you control the classes of objects that an application creates. Because a factory encapsulates the responsibility and the process of creating product objects, it isolates clients from implementation classes. Clients manipulate instances through their abstract interfaces. Product class names are isolated in the implementation of the concrete factory; they do not appear in client code.
* It makes exchanging product families easy. The class of a concrete factory appears only once in an application—that is, where it's instantiated. This makes it easy to change the concrete factory an application uses. It can use different product configurations simply by changing the concrete factory. Because an abstract factory creates a complete family of products, the whole product family changes at once. In our user interface example, we can switch from Motif widgets to Presentation Manager widgets simply by switching the corresponding factory objects and recreating the interface.
* It promotes consistency among products. When product objects in a family are designed to work together, it's important that an application use objects from only one family at a time. AbstractFactory makes this easy to enforce.
* Divides responsibilities between multiple classes.

### Cons
* Increases overall code complexity by creating multiple additional classes.
* Supporting new kinds of products is difficult. Extending abstract factories to produce new kinds of Products isn't easy. That's because the AbstractFactory interface fixes the set of products that can be created. Supporting new kinds of products requires extending the factory interface, which involves changing the AbstractFactory class and all of its subclasses.

## Implementation
Here are some useful techniques for implementing the Abstract Factory pattern.
1. *Factories as singletons.* An application typically needs only one instance of a ConcreteFactory per product family. So it's usually best implemented as a Singleton.

2. *Creating the products.* AbstractFactory only declares an interface for creating products. It's up to ConcreteProduct subclasses to actually create them. The most common way to do this is to define a factory method for each product. A concrete factory will specify its products by overriding the factory method for each. While this implementation is simple, it requires a new concrete factory subclass for each product family, even if the product families differ only slightly.

3. *Defining extensible factories.* AbstractFactory usually defines a different operation for each kind of product it can produce. The kinds of products are encoded in the operation signatures. Adding a new kind of product requires changing the AbstractFactory interface and all the classes that depend on it. A more flexible but less safe design is to add a parameter to operations that create objects. This parameter specifies the kind of object to be created. It could be a class identifier, an integer, a string, or anything else that identifies the kind of product. In fact with this approach, AbstractFactory only needs a single create operation with a parameter indicating the kind of object
to create.

## Sample Code

```java
// Abstract Product A: This is the common interface for chair family.
public abstract class Chair {
    public abstract void deliver();
}

// Concrete Product A1: This is a Hatil variant of a chair.
public class HatilChair extends Chair {
    public void deliver() {
        System.out.println("Hatil Chair is Delivered");
    }
}

// Concrete Product A2: This is another variant of a chair.
public class OtobiChair extends Chair {
    public void deliver() {
        System.out.println("Otobi Chair is Delivered");
    }
}

// Abstract Product B: Table is the second product family. It has the same variants as chairs.
public abstract class Table {
    public abstract void deliver();
}

// Concrete Product B1: Hatil variant of a table.
public class HatilTable extends Table {
    public void deliver() {
        System.out.println("Hatil Table is Delivered");
    }
}

// Concrete Product B2: Otobi variant of a table.
public class OtobiTable extends Table {
    public void deliver() {
        System.out.println("Otobi Table is Delivered");
    }
}

// Abstract Factory: knows about all (abstract) product types.
public interface FurnitureFactory {
    Chair createChair();
    Table createTable();
}

// Concrete Factory 1: Each concrete factory extends abstract factory and create products of a single variety.
public class HatilFactory implements FurnitureFactory {

    public Chair createChair() {
        return new HatilChair();
    }

    public Table createTable() {
        return new HatilTable();
    }
}

// Concrete Factory 2
public class OtobiFactory implements FurnitureFactory {

    public Chair createChair() {
        return new OtobiChair();
    }

    public Table createTable() {
        return new OtobiTable();
    }
}

/**
 * Client: Clients don't care which concrete factory they use since they work with factories and products through abstract interfaces.
 */
public abstract class FurnitureStore {

    public void orderFurniture(){
        FurnitureFactory furnitureFactory = selectFactory();

        Chair chair = furnitureFactory.createChair();
        chair.deliver();

        Table table = furnitureFactory.createTable();
        table.deliver();
    }

    abstract FurnitureFactory selectFactory();
}

// also using FACTORY METHOD in this part
public class HatilFurnitureStore extends FurnitureStore {
    public FurnitureFactory selectFactory() {
        return new HatilFactory();
    }
}

public class OtobiFurnitureStore extends FurnitureStore {
    public FurnitureFactory selectFactory() {
        return new OtobiFactory();
    }
}

// TestDrive
public class Main {

    public static void main(String[] args) {

        FurnitureStore hatil = new HatilFurnitureStore();
        hatil.orderFurniture();

        FurnitureStore otobi = new OtobiFurnitureStore();
        otobi.orderFurniture();
    }
}
```

## Relations with Other Patterns
* AbstractFactory classes are often implemented with factory methods, but they can also be implemented using Prototype.
* A concrete factory is often a singleton.
* Builder focuses on constructing a complex object step by step. Abstract Factory creates families of product objects (either simple or complex). Builder returns the product as a final step, but the Abstract Factory returns the result immediately.
* Abstract Factory can be used as an alternative to Facade to hide platform-specific classes.
* Abstract Factory can be used along with a Bridge pattern. It's useful when the "interface" part of the Bridge can work only with a particular "implementation". In this case, factory can encapsulate these relations and hide the complexity from a client.

## Q&A

*Q: I am a little confused between Factory Method and Abstract Factory? Can you explain?*

A: Factory Method and Abstract Factory are both really good at decoupling applications from specific implementations; they just do it in different ways. 

Factory Method relies on Inheritance:  to create objects using Factory Method, you need to extend a class and override a factory method to create objects. The whole point of the Factory Method Pattern is that you’re using a subclass to do your creation for you. In that way, clients only need to know the abstract type they are using, the subclass worries about the concrete type.

Abstract Factory relies on object composition. It provides an abstract type for creating a family of products. Subclasses of this type define how those products are produced. To use the factory, you instantiate one and pass it into some code that is written against the abstract type. So, like Factory Method, Abstract Factory clients are decoupled from the actual concrete products they use. Abstract Factory also groups together a set of related products.

In short, The intent of Factory Method is to allow a class to defer instantiation to its subclasses. The intent of Abstract Factory is to create families of related objects without having to depend
on their concrete classes. Both promote loose coupling by reducing the dependency of your application on concrete classes.

*Q: What happens if you need to extend a set of related products, to say add another one? Doesn’t that require changing Abstract Factory’s interface?*

A: That’s true; the interface has to change if new products are added. While Factory Method is only creating one product, so it doesn’t really need a big interface, just one method, Abstract Factory needs a big interface because it is used to create entire families of products. And changing that interface means you have to go in and change the interface of every subclass!

*Q: Does Abstract Factory often use factory methods to implement its concrete factories?*

A: Yes, its concrete factories often implement a factory method to create their products. However, in this case, they are used purely to create products while in Factory Method it usually implements code in the abstract creator that makes use of the concrete types the subclasses create.

## References

1. [Head First Design Patterns](https://www.amazon.com/Head-First-Design-Patterns-Brain-Friendly/dp/0596007124)
2. [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612/ref=sr_1_1?s=books&ie=UTF8&qid=1541448469&sr=1-1&keywords=elements+of+reusable)
3. [Refactoring Guru](https://refactoring.guru/design-patterns/abstract-factory)
4. [Source Making](https://sourcemaking.com/design_patterns/abstract_factory)