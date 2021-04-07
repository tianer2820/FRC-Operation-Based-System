# FRC Operation System
A customly made command system, aiming to be more flexable and easier to use than the offical one.

**Still in very early development stage. You should not use this.**


# Usage
This system mainly provides three new objects/classes:
- the Operation class
- the Subsystem class
- the OpManager class
These classes allow you to customize and build your program in a modular way while keep the flexibility as much as possible.

## the Operation class
Operation, as it is literally, is a operation that can be "done". Some examples are:
- running a motor
- flashing a ligth
- shooting a ball
- moving the robot to a position

### Life span of a operation
Typically, operation fall into two catagories: one that finishes instantly, like turing on a light, and ones that run over a period of time, like turing the robot for 90 degrees.

an operation will first be instanciated. However, you should not use the constructor of the class for initializing, you will have opportunity to do that later and for a good reason.

after instanciation, the properties/attributes of the instance may be modified by other parts of the program. This is the essential (only) way you can pass arguments to an operation.

The instance will then be passed to the OpManager, who keep a list of on-going operations. It will be responsible for executing and terminating the operation as well.

The invoke method will be called once. This is where you should do the initiallization. This is because the attribute/properties of the instance may have been modified after the instance is created.

The return value is assessed. Depending on it, the execute method will be called or not. Then the execute method will be called in every loop until the operation is terminated, either by the return value or by interruption from out side.

during this, you may need to get access to a subsystem. This is to ensure no operation in parallal will conflict and cause strange behaviour(like motor shaking). Do this by calling the capture or release method of a subsystem. If this operation have higher priority than the current one, it will return true and add the current operation to the top of a holder stack. otherwise it will return false. You can Force capture the subsystem by passing force=true. And each time the execute method is called, you should check if you still have access to the subsystem, as other operation may have taken over. You can decide to end the operation, or wait until you have access again. subsystem will automatically remove any operation that is not "RUNNING" from the stack.

finally, the operation can be interrupted. In this case, the onInterrup method will be called and you should do cleanup before the operation stops. Normally, the cleanup should be done before you use return value to terminate the operation.

*TODO: maybe add support to force interrupt and let user reject soft interrupt?*


## the OpManager class
All operations are handled by the OpManager class. It will hold a list of all operation instances and call the invoke, execute, etc methods automatically. You should create one instance of it and call the update method of OpManager in each interation.

### starting operations
One way to start an operation is to make an instance of the wanted operation class and send it to the OpManager. 

However, OpManager can also instanciate and start operations automatically upon user input, like "hot keys" from the xbox handle. To do this, you need to bind the operation class to some hotkey.

### terminating operations
One way is to use the return value of the execute method of operation. However, if you want to interrupt an operation, you can call the interrupt method of the operation instance. This will trigger the cleanup process, and after that, the operation will remove it self form the list of Opmanager.

## the subsystem class
This class is mainly for organizing the code and prociding a authority system. the system will not prevent you from doing anything if you insist, but following the system will keep your code safer.

Each subsystem should have only one instance and should warp the motors belong to them.

Subsystems needs to be accessable from operation objects, so they should be made static variables of some class. Many people think this is not a good practice, but this has to be done because 1. java does not provide a clean way to dynamically do the same thing. Or user will have to deal with type casting, which is not worthy. 2. passing instances of subsystems as parameters will make the parameter list super long and make code unreadable. 